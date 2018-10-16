# -*- coding: utf-8 -*-
import numpy as np

from .misc import Gen_Gaussian_Filter, apply_2d_filter


class Harris_Corner_Detector(object):
    """Apply Harris corner detector to image.   

    Parameters
    ----------     
    image: array-like
        Target image.
    neighbor: int, default 7
        Neighbor of nxn.
    avg: string, 'Box' or 'Gaussian', default 'box'
        Window averaging function.
    sigma: float, default 1
        Standard deviation if Gaussian window.
    thresold: int, default 1e5
        Threshold for 
    k: float, default 0.05
        Empirically determined constant.
    nonmax_window: int, default 3
        Window size for non-maimum suppression.
    """

    def __init__(self, image, neighbor=7, avg="box", sigma=1, 
                 thresold=1e5, k=0.05, nonmax_window=3):
        ishape = image.shape

        self.image = image
        self.image_shpae = ishape
        self.neighbor = neighbor
        self.avg = avg
        self.win_sigma = sigma
        self.thresold = thresold
        self.k = k
        self.nonmax_window = nonmax_window

        self.r_matrix = np.zeros(ishape)
        self.window_func = self._gen_window_func(avg, neighbor, sigma)

    @staticmethod
    def _gen_window_func(avg, neighbor, sigma):
        """Generate window function."""
        if "g" in avg.lower():
            window_func = Gen_Gaussian_Filter(2, sigma, size=neighbor)
        else:
            window_func = np.ones((neighbor, neighbor))
            window_func = window_func / sum(sum(window_func))

        return window_func

    @staticmethod
    def _findM(x2, y2, xy, ind, neigh, window):
        """Find C matrix of gven gradient."""
        x, y = ind
        n = int(neigh / 2)
        Gxx = x2[x - n:x + n + 1, y - n:y + n + 1]
        Gyy = y2[x - n:x + n + 1, y - n:y + n + 1]
        Gxy = xy[x - n:x + n + 1, y - n:y + n + 1]

        Ixx = window * Gxx
        Iyy = window * Gyy
        Ixy = window * Gxy

        M = np.array([[sum(sum(Ixx)), sum(sum(Ixy))], 
                      [sum(sum(Ixy)), sum(sum(Iyy))]])

        return M
    
    def _corner_response(self, M):
        """Measure of corner response.
        
        Parameters
        ----------     
        M: array-like
            Weighted C matrix.
        """
        a, b, c, d = M.reshape((1, -1))[0, :]
        det = a * c - b * d
        trace = a + c
        R = det - self.k * (trace)**2
        
        return R
        
    def harris_r_matrix(self):
        """Compute Harris R function over the image."""
        image = self.image
        neighbor = self.neighbor
        window = self.window_func

        ishape = image.shape
        xmask = np.array([[-1, 0, 1]]) * np.array([[1], [1], [1]])
        ymask = np.array([[1, 1, 1]]) * np.array([[-1], [0], [1]])
        Ix = np.zeros(ishape)
        Iy = np.zeros(ishape)
        Ex = apply_2d_filter(xmask, image)
        Ey = apply_2d_filter(ymask, image)
        
        Ix[1:-1, 1:-1] = np.copy(Ex[1:-1, 1:-1])
        Iy[1:-1, 1:-1] = np.copy(Ey[1:-1, 1:-1])
        Ixy = Ix * Iy
        Ixx = Ix ** 2
        Iyy = Iy ** 2
        ovrlay = int(neighbor / 2) + 1
        r_matrix = np.zeros(ishape)
        
        for i in np.arange(ishape[0])[ovrlay:-ovrlay]:
            for j in np.arange(ishape[1])[ovrlay:-ovrlay]:
                m = self._findM(Ixx, Iyy, Ixy, [i, j], neighbor, window)
                r = self._corner_response(m)
                r_matrix[i, j] = r
        
        self.r_matrix = r_matrix
        self.ovrlay = ovrlay

        return self
        
    def nonmax_Supression(self):
        """Do non-maimum suppression to get a sparse set of corner features."""
        threshold = self.thresold
        r_matrix = self.r_matrix
        ovrlay = self.ovrlay
        ishape = self.image_shpae
        win = self.nonmax_window

        R = r_matrix * (r_matrix > threshold)
        offset = int(win / 2)
        ovrlay += offset

        for i in np.arange(ishape[0])[ovrlay:- ovrlay]:
            for j in np.arange(ishape[1])[ovrlay:- ovrlay]:
                local_matrix = R[i - offset:i + offset + 1, 
                                 j - offset:j + offset + 1]
                local_max = max(local_matrix.reshape((1, -1))[0, :])
                local_matrix[np.where(local_matrix != local_max)] = 0
        
        self.nonmax_r = R
        
        return self

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
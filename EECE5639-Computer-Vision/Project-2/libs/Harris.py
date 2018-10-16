# -*- coding: utf-8 -*-
import numpy as np


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
    thresold: int, default 1e8
        Threshold for 
    k: float, default 0.05
        Empirically determined constant.
    """

    def __init__(self, image, neighbor=7, avg="box", thresold=1e8, k=0.05):
        ishape = image.shape

        self.image = image
        self.image_shpae = ishape
        self.neighbor = neighbor
        self.thresold = thresold
        self.k = k

        self.r_matrix = np.zeros(ishape)
        
    @staticmethod
    def findC(x2, y2, xy, ind, neigh):
        """Find C matrix of gven gradient."""
        x, y = ind
        n = int(neigh / 2)
        Ixx = x2[x - n:x + n + 1, y - n:y + n + 1]
        Iyy = y2[x - n:x + n + 1, y - n:y + n + 1]
        Ixy = xy[x - n:x + n + 1, y - n:y + n + 1]
        C = np.array([[sum(sum(Ixx)), sum(sum(Ixy))], 
                      [sum(sum(Ixy)), sum(sum(Iyy))]])
        
        return C
    
    def corner_response(self, M):
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

        ishape = image.shape
        xmask = np.array([[-1, 0, 1]]) * np.array([[1], [1], [1]])
        ymask = np.array([[1, 1, 1]]) * np.array([[-1], [0], [1]])
        Ix = np.zeros(ishape)
        Iy = np.zeros(ishape)
        Ex = self.filapp.apply_2d_filter(xmask, image)
        Ey = self.filapp.apply_2d_filter(ymask, image)
        
        Ix[1:-1, 1:-1] = np.copy(Ex[1:-1, 1:-1])
        Iy[1:-1, 1:-1] = np.copy(Ey[1:-1, 1:-1])
        Ixy = Ix * Iy
        Ixx = Ix ** 2
        Iyy = Iy ** 2
        ovrlay = int(neighbor / 2) + 1
        r_matrix = np.zeros(ishape)
        
        for i in np.arange(ishape[0])[ovrlay:-ovrlay]:
            for j in np.arange(ishape[1])[ovrlay:-ovrlay]:
                c = self.findC(Ixx, Iyy, Ixy, [i, j], neighbor)
                r = self.corner_response(c)
                r_matrix[i, j] = r
        
        self.r_matrix = r_matrix

        return self
        
    def nonmax_Supression(self):
        """Do non-maimum suppression to get a sparse set of corner features.
        
        Parameters
        ----------     
        image: array-like
            Target image.
        """
        
        
        
        
        
        
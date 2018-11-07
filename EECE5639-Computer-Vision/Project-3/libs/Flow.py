# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pylab as plt
plt.rcParams['figure.figsize'] = 9, 9

from .misc import rgb2gray, Gen_Gaussian_Filter, apply_2d_filter, gradient, solve_mateq


class Dense_Optical_Flow(object):
    """Produce a mosaic containing the union of all pixels in the two images.
    
    Parameters
    ----------
    rawimg1: array-like
        Raw image 1.
    rawimg2: array-like
        Raw image 2.
    """

    def __init__(self, rawimg1, rawimg2, params):
        self.raw_image1 = rawimg1
        self.raw_image2 = rawimg2
        self.scale = params["scale"]
        self.smooth = params["smooth"]
        self.sigma = params["sigma"]        
        self.grad_method = params["method"]
        self.neighbor = params["neighbor"]
        
        self.gray_image1 = rgb2gray(rawimg1)[::self.scale]
        self.gray_image2 = rgb2gray(rawimg2)[::self.scale]
        self.smoother = self._gen_smoother(self.smooth, self.sigma)
        self.smooth_image1 = None
        self.smooth_image2 = None
        self.image_shape = np.shape(self.gray_image1)
        
    @staticmethod
    def _gen_smoother(smooth, sigma):
        """Generate smoothing filter."""
        if smooth is not None:
            filt = Gen_Gaussian_Filter(2, sigma)
        else:
            filt = None

        return filt
    
    @staticmethod
    def _findAB(x2, y2, xy, xt, yt, ind, neigh):
        """Find C matrix of gven gradient."""
        x, y = ind
        n = int(neigh / 2)
        Ixx = x2[x - n:x + n + 1, y - n:y + n + 1]
        Iyy = y2[x - n:x + n + 1, y - n:y + n + 1]
        Ixy = xy[x - n:x + n + 1, y - n:y + n + 1]
        Ixt = xt[x - n:x + n + 1, y - n:y + n + 1]
        Iyt = yt[x - n:x + n + 1, y - n:y + n + 1]
        
        A = np.array([[Ixx.sum(), Ixy.sum()], 
                      [Ixy.sum(), Iyy.sum()]])
        B = np.array([[Ixt.sum()], [Iyt.sum()]])

        return A, B
    
    def LKMethod(self, show=True):
        """Detect corners."""
        smoother = self.smoother
        image1 = self.gray_image1
        image2 = self.gray_image2
        neighbor = self.neighbor
        ishape = self.image_shape
        
        smooth_image1 = apply_2d_filter(smoother, image1)
        smooth_image2 = apply_2d_filter(smoother, image2)
        
        Ix, Iy = gradient(smooth_image2)
        It = smooth_image2 - smooth_image1
        
        Ixy = Ix * Iy
        Ixx = Ix ** 2
        Iyy = Iy ** 2
        Ixt = Ix * It
        Iyt = Iy * It
        ovrlay = int(neighbor / 2) + 1
#        v_matrix = np.zeros(ishape, dtype=complex)
        vx_matrix = np.zeros(ishape)
        vy_matrix = np.zeros(ishape)
        
        for i in np.arange(ishape[0])[ovrlay:-ovrlay]:
            for j in np.arange(ishape[1])[ovrlay:-ovrlay]:
                a, b = self._findAB(Ixx, Iyy, Ixy, Ixt, Iyt, [i, j], neighbor)
                v = solve_mateq(a, b)
                vx, vy = v[:, 0]
                vx_matrix[i, j] = vx
                vy_matrix[i, j] = vy
#                v_matrix[i, j] = complex(vx, vy)
                
        self.vx_matrix = vx_matrix
        self.vy_matrix = vy_matrix
        self.ovrlay = ovrlay
        
        return self
    
    def show_result(self):
        vx_matrix = self.vx_matrix
        vy_matrix = self.vy_matrix
        fig, ax = plt.subplots()
        ax.axis('off')
        ax.quiver(vx_matrix, vy_matrix)
        plt.show()





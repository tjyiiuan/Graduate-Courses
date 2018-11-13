# -*- coding: utf-8 -*-
import cv2
import numpy as np
import matplotlib.pylab as plt
plt.rcParams['figure.figsize'] = 9, 9

from .misc import rgb2gray, Gen_Gaussian_Filter, apply_2d_filter, gradient, solve_mateq, norm_minmax


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
        self.smooth = params["smooth"]
        self.sigma = params["sigma"]        
        self.grad_method = params["method"]
        
        self.gray_image1 = rgb2gray(rawimg1)
        self.gray_image2 = rgb2gray(rawimg2)
        self.smoother = self._gen_smoother(self.smooth, self.sigma)
        self.smooth_image1 = None
        self.smooth_image2 = None
        self.vx_matrix = None
        self.vy_matrix = None
        self.ovrlay = None
        
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
        """Find matrix components of given gradient."""
        x, y = ind
        n = int(neigh / 2)
        Ixx = x2[x - n:x + n + 1, y - n:y + n + 1]
        Iyy = y2[x - n:x + n + 1, y - n:y + n + 1]
        Ixy = xy[x - n:x + n + 1, y - n:y + n + 1]
        Ixt = xt[x - n:x + n + 1, y - n:y + n + 1]
        Iyt = yt[x - n:x + n + 1, y - n:y + n + 1]
        
        A = np.array([[Ixx.sum(), Ixy.sum()], 
                      [Ixy.sum(), Iyy.sum()]])
        B = - np.array([[Ixt.sum()], [Iyt.sum()]])

        return A, B
    
    def LKMethod(self, scale=1, window=5, show=True):
        """Lucas-Kanade method.
    
        Parameters
        ----------
        scale: int, default 1
            Scale level of origin image.
        window: int, default 5
            Window size.
        """
        smoother = self.smoother
        image1 = self.gray_image1
        image2 = self.gray_image2
        
        smooth_image1 = apply_2d_filter(smoother, image1)[::scale, ::scale]
        smooth_image2 = apply_2d_filter(smoother, image2)[::scale, ::scale]
        ishape = np.shape(smooth_image2)
        Ix, Iy = gradient(smooth_image2)
        It = smooth_image2 - smooth_image1
        
        Ixy = Ix * Iy
        Ixx = Ix ** 2
        Iyy = Iy ** 2
        Ixt = Ix * It
        Iyt = Iy * It
        ovrlay = int(window / 2) + 1
#        v_matrix = np.zeros(ishape, dtype=complex)
        vx_matrix = np.zeros(ishape)
        vy_matrix = np.zeros(ishape)
        
        for i in np.arange(ishape[0])[ovrlay:-ovrlay]:
            for j in np.arange(ishape[1])[ovrlay:-ovrlay]:
                a, b = self._findAB(Ixx, Iyy, Ixy, Ixt, Iyt, [i, j], window)
                v = solve_mateq(a, b)
                vx, vy = v[:, 0]
                vx_matrix[i, j] = vx
                vy_matrix[i, j] = vy
#                v_matrix[i, j] = complex(vx, vy)
        
        if show:
            fig = plt.figure()
            ax1 = fig.add_subplot(121)
            ax1.axis('off')
            ax1.imshow(smooth_image2, cmap=plt.cm.gray)
            ax1.quiver(vx_matrix, vy_matrix, color='green')
            ax1.set_title(f"$scale = {scale}, window = {window}$", 
                          fontsize=18)
            
            ax2 = fig.add_subplot(122, sharex=ax1, sharey=ax1)
            ax2.axis('off')
            hs_matrix = np.copy(self.raw_image1[::scale, ::scale, :3])
#            hs_matrix = np.zeros([ishape[0], ishape[1], 3])
            direct = self._find_direction(vx_matrix, vy_matrix)
            length = np.sqrt(vx_matrix**2 + vy_matrix**2)
            
            hs_matrix[:, :, 0] = direct
            hs_matrix[:, :, 1] = length * 255 / length.max()
            hs_matrix[:, :, 2] = 255 * np.ones(ishape)
            rgb = cv2.cvtColor(hs_matrix, cv2.COLOR_HSV2BGR)
            ax2.imshow(rgb)
            ax2.set_title(f"$scale = {scale}, window = {window}$", 
                          fontsize=18)
            plt.show()
        
        self.vx_matrix = vx_matrix
        self.vy_matrix = vy_matrix
        self.ovrlay = ovrlay
        
        return self
    
    def show_origin(self):
        """Plot original images."""
        image1 = self.gray_image1
        image2 = self.gray_image2
 
        fig = plt.figure()
        ax1 = fig.add_subplot(121)
        ax1.axis('off')
        ax1.imshow(image1, cmap=plt.cm.gray)
        ax1.set_title("Raw image 1", fontsize=18)
        
        ax2 = fig.add_subplot(122, sharex=ax1, sharey=ax1)
        ax2.axis('off')
        ax2.imshow(image2, cmap=plt.cm.gray)
        ax2.set_title("Raw image 2", fontsize=18)
        plt.show()
        
    @staticmethod
    def _find_direction(vx, vy):
        alpha = np.degrees(np.arctan(vy / vx))
        vx_sign = np.sign(vx)
        vy_sign = np.sign(vy)
        direct = np.zeros(vx.shape)
        direct[np.where(vy_sign < 0)] += 180
        direct[np.where(vy_sign * vx_sign < 0)] += 180
        
        return direct + alpha        
    
# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pylab as plt
plt.rcParams['figure.figsize'] = 15, 6

from .misc import rgb2gray
from .Harris import Harris_Corner_Detector


class Image_Mosaicing(object):
    """Produce a mosaic containing the union of all pixels in the two images.
    
    Parameters
    ----------
    rawimg1: array-like
        Raw image 1.
    rawimg2: array-like
        Raw image 2.
    """

    def __init__(self, rawimg1, rawimg2, **kargs):
        self.raw_image1 = rawimg1
        self.raw_image2 = rawimg2
        self.gray_image1 = rgb2gray(rawimg1)
        self.gray_image2 = rgb2gray(rawimg2)
        
        self.neighbor = kargs["neighbor"]
        self.avg = kargs["avg"]
        self.sigma = kargs["sigma"]
        self.thresold = kargs["thresold"]
        self.k = kargs["k"]
        self.nonmax_window = kargs["nonmax_window"]

    def _apply_harris_corner_detector(self, image):
        neighbor = self.neighbor
        avg = self.avg
        sigma = self.sigma
        thresold = self.thresold
        k = self.k
        nonmax_window = self.nonmax_window
        
        detector = Harris_Corner_Detector(image, neighbor=neighbor, avg=avg, 
                                          sigma=sigma, thresold=thresold, k=k, 
                                          nonmax_window=nonmax_window)
        detector.harris_r_matrix()
        detector.nonmax_Supression()
        r = detector.nonmax_r
        
        return r

    def corner_detect(self, show=True):
        """Detect corners."""
        image1 = self.gray_image1
        image2 = self.gray_image2
        r1 = self._apply_harris_corner_detector(image1)
        r2 = self._apply_harris_corner_detector(image2)
        self.corner1 = r1
        self.corner2 = r2

        if show:
            fig = plt.figure()
            ax1 = fig.add_subplot(121)
            ax1.axis('off')
            ax1.imshow(self.raw_image1)
            ax1.scatter(np.where(r1)[1], np.where(r1)[0], c='r')
            ax2 = fig.add_subplot(122)
            ax2.axis('off')
            ax2.imshow(self.raw_image2)
            ax2.scatter(np.where(r2)[1], np.where(r2)[0], c='r')
            plt.show()

        return self
    
    def correspondences(self):
        """Find correspondences."""
        return self
    
    def homography_estimate(self):
        """Estimate homography."""
        return self
    
    def image_warpping(self):
        """Warp images."""
        return self








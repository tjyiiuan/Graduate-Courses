# -*- coding: utf-8 -*-
import numpy as np

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








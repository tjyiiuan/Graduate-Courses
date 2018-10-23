#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys
import logging
logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)

from libs.misc import Load_Images
from libs.Mosaic import Image_Mosaicing


image_path = r".\\DanaOffice\\"
parameters = {"method": "Sobel",
              "corner_neighbor": 7, 
              "avg": "Gaussian", 
              "sigma": 1.4, 
              "corner_thresold": 1e8, 
              "k": 0.05, 
              "nonmax_window": 3,
              "ncc_threshold": 0.5,
              "ncc_neighbor": 3,
              "ransac_ratio": 0.1,
              "ransac_distance": 2,
              "ransac_iteration": 1e4,
              "homo_num": 4}


if __name__ == "__main__":
    raw_images = Load_Images(image_path)
    
    mosaic = Image_Mosaicing(raw_images[7], raw_images[8], params=parameters)
    mosaic.corner_detect(show=False)
    mosaic.correspondences(show=False)
    mosaic.homography_estimate(show=False)
    mosaic.image_warp(show=False)
    

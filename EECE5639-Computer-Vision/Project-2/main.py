#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys
import logging
logging.basicConfig(stream=sys.stdout, level=logging.INFO)

from libs.misc import Load_Images
from libs.Mosaic import Image_Mosaicing


image_path = r".\\DanaOffice\\"
parameters = {"method": "Sobel",
              "corner_neighbor": 7, 
              "avg": "gaussian", 
              "sigma": 1, 
              "corner_thresold": 1e10, 
              "k": 0.05, 
              "nonmax_window": 5,
              "ncc_threshold": 0,
              "ncc_neighbor": 5,
              "ransac_ratio": 0.01,
              "ransac_distance": 10,
              "ransac_iteration": 1e2,
              "homo_num": 4}
#%%
if __name__ == "__main__":
    raw_images = Load_Images(image_path)
    mosaic = Image_Mosaicing(raw_images[7], raw_images[8], params=parameters)
    mosaic.corner_detect(show=1)
    mosaic.correspondences(show=1)
    mosaic.homography_estimate(show=1)
    mosaic.image_warp()


#!/usr/bin/env python
# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pylab as plt
#import matplotlib.image as mpimg
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300

from libs.misc import Load_Images
from libs.Mosaic import Image_Mosaicing


image_path = r".\\DanaHallWay1\\"
neighbor = 7
avg = "Gaussian"
sigma = 1
thresold = 1e6
k = 0.05
nonmax_window = 3

#%%
if __name__ == "__main__":
    raw_images = Load_Images(image_path)
    mosaic = Image_Mosaicing(raw_images[0], raw_images[1], neighbor=neighbor, 
                             avg=avg, sigma=sigma, thresold=thresold, 
                             k=k, nonmax_window=nonmax_window)
    mosaic.corner_detect()
    

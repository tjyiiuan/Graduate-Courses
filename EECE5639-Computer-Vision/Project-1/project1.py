#!/usr/bin/env python
# -*- coding: utf-8 -*-

import glob
import numpy as np
import matplotlib.pylab as plt
import matplotlib.image as mpimg
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300

from libs.FilterApp import FilterApp

#%%   
class MotionDetector(object):
    
    def __init__(self):
        self.filapp = FilterApp()
    
    @staticmethod
    def rgb2gray(img):
        """Convert RGB image to gray scale."""
        if img.shape[-1] == 3:
            tmpimg = 0.299 * img[:, :, 0] + 0.587 * img[:, :, 1] + 0.114 * img[:, :, 2]
        else:
            tmpimg = img[:, :, 0]

        return tmpimg
        
    def Load_Images(self, path, imgtype="*.jpg"):
        """Load frame images from folder.

        Parameters
        ----------
        path: string
            Image path
        imgtype: string
            Type of images
        """
        loadpath = f"{path}{imgtype}"
        all_img_path = glob.glob(loadpath)
        img_num = len(all_img_path)
        all_img = [0] * img_num
        for i in np.arange(img_num):
            one_img_path = all_img_path[i]
            rgbimg = mpimg.imread(one_img_path)
            all_img[i] = self.rgb2gray(rgbimg)

        all_img = np.array(all_img)
        self.AllImage = all_img

        return all_img
            
#%%
if __name__ == "__main__":
    Mdetector = MotionDetector()
    Mdetector.Load_Images("./EnterExitCrossingPaths2cor/")
    b = Mdetector.AllImage














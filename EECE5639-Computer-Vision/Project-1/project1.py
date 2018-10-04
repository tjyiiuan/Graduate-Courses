#!/usr/bin/env python
# -*- coding: utf-8 -*-

import glob
import numpy as np
import matplotlib.pylab as plt
import matplotlib.image as mpimg
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300

from libs.FilterApp import FilterApp
from libs.misc import Load_Images, Gen_Gaussian_Mask


MASK_LIST = ["Box", "Gaussian"]
#%%   
class MotionDetector(object):
    
    def __init__(self, frames):
        self.filapp = FilterApp()
        self.raw_frames = frames

    @staticmethod
    def rgb2gray(img):
        """Convert a RGB image to gray scale."""
        if img.shape[-1] == 3:
            tmpimg = 0.299 * img[:, :, 0] + 0.587 * img[:, :, 1] + 0.114 * img[:, :, 2]
        else:
            tmpimg = img[:, :, 0]

        return tmpimg
        
    def Convert_Gray(self):
        """Convert raw frames into gray scale."""
        tmp_frames = self.raw_frames
        img_num = tmp_frames.shape[0]
        gray_frames = [0] * img_num
        for i in np.arange(img_num):
            current_frame = tmp_frames[i]
            gray_frames[i] = self.rgb2gray(current_frame)

        gray_frames = np.array(gray_frames)
        self.gray_frames = gray_frames

        return self
    
    def Spatial_Smooth(self, mask="None", **kwargs):
        """Applying a 2D spatial smoothing fi
lter to the frames 
        before applying the temporal derivative fi
lter.
        
        Parameters
        ----------
        mask: string, default 'None'
            Spatial smoothing fi
lter to apply
        """
        
        spatial_filter = None
        if mask in MASK_LIST:
            pass

        return self
    
    def Temporal_Derive(self, operator, **kwargs):
        """apply a 1-D differential operator at each pixel to 
        compute a temporal derivative.
        
        Parameters
        ----------
        operator: string
            Differential operator to apply
        """
        
        return self
    
    def Show_Result(self):
        pass
#%%
if __name__ == "__main__":
#     
    frame_path = "./EnterExitCrossingPaths2cor/"
    
    frames = Load_Images(frame_path)
    Mdetector = MotionDetector(frames)
    Mdetector.Convert_Gray()
    Mdetector.Spatial_Smooth()
    Mdetector.Temporal_Derive("delta")
    Mdetector.Show_Result()






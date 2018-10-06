# -*- coding: utf-8 -*-
import os

import numpy as np
import matplotlib.pylab as plt
#import matplotlib.image as mpimg
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300

from .FilterApp import FilterApp
from .misc import Gen_Gaussian_Filter, Gen_Box_Filter


class MotionDetector(object):
    
    def __init__(self, frames):
        self.filapp = FilterApp()
        self.frame_num = frames.shape[0]
        self.raw_frames = frames
        self.gray_frames = np.array([])
        self.filtered_frames = np.array([])
        self.derived_frames = np.array([])
        self.derived_index = np.array([])
        self.threshold = 0
        self.filter_ovrlay = 1
        self.mean_matrix = np.array([])
        self.var_matrix = np.array([])

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
        frame_num = self.frame_num
        gray_frames = [0] * frame_num
        for i in np.arange(frame_num):
            current_frame = tmp_frames[i]
            gray_frames[i] = self.rgb2gray(current_frame)

        gray_frames = np.array(gray_frames)
        self.gray_frames = gray_frames

        return self
    
    def Spatial_Smooth(self, mask="None", **kwargs):
        """Applying a 2D spatial smoothing filter to the frames 
        before applying the temporal derivative filter.
        
        Parameters
        ----------
        mask: string, ["None", "Box", Gaussian"], default 'None'
            Spatial smoothing filter to apply
        """
        gray_frames = self.gray_frames
        frame_num = self.frame_num


        if "b" in mask.lower():
            spatial_filter = Gen_Box_Filter(kwargs['param'])
        elif 'g' in mask.lower():
            spatial_filter = Gen_Gaussian_Filter(2, kwargs['param'])
        else:
            spatial_filter = None

        if spatial_filter is None:
            filtered_frames = gray_frames
            filter_ovrlay = 1
        else:
            filter_ovrlay = int(spatial_filter.shape[0] / 2)
            tmp_frames = [0] * frame_num
            for i in np.arange(frame_num):
                tmp_frames[i] =  self.filapp.apply_2d_filter(spatial_filter, 
                                                             gray_frames[i])
            filtered_frames = np.array(tmp_frames)
        
        self.filtered_frames = filtered_frames
        self.filter_ovrlay = filter_ovrlay

        return self
    
    def Temporal_Derive_old(self, op, **kwargs):
        """Apply a 1-D differential operator at each pixel to 
        compute a temporal derivative.
        
        Parameters
        ----------
        operator: string
            Differential operator to apply
        """
        filtered_frames = self.filtered_frames
        frame_num = self.frame_num

        if "g" in op.lower():
            operator = Gen_Gaussian_Filter(1, kwargs['param'])
            derived_frames = self.filapp.apply_1d_differential(operator, filtered_frames)
            operator_length = operator.shape[1]
            ovrlay = int(operator_length / 2)
            derived_index = np.arange(frame_num)[ovrlay: -ovrlay]
        else:
            try:
                order = int(kwargs['param'])
            except:
                order = 1
            derived_frames = (filtered_frames[order:, :, :] -\
                              filtered_frames[:-order, :, :]) / order
        
            derived_index = np.arange(frame_num)[order:]
        
        self.derived_frames = derived_frames
        self.derived_index = derived_index

        return self

    def Temporal_Derive(self, op, **kwargs):
        """Apply a 1-D differential operator at each pixel to 
        compute a temporal derivative.
        
        Parameters
        ----------
        operator: string
            Differential operator to apply
        """
        filtered_frames = self.filtered_frames
        frame_num = self.frame_num

        try:
            order = int(kwargs['param'])
        except:
            order = 1
        derived_frames = (filtered_frames[order:, :, :] -\
                          filtered_frames[:-order, :, :]) / order
    
        derived_index = np.arange(frame_num)[order:]

        if "g" in op.lower():
            operator = Gen_Gaussian_Filter(1, kwargs['param']).reshape((1, -1))[0, :]
            new_derived_frames = np.zeros(derived_frames.shape)
            shape = derived_frames[0].shape
            for i in np.arange(shape[0]):
                for j in np.arange(shape[1]):
                    new_derived_frames[:, i, j] = self.filapp.apply_1d_filter(operator, 
                                                                              derived_frames[:, i, j])
        
        self.derived_frames = derived_frames
        self.derived_index = derived_index

        return self
    
    def Threshold_Select(self, percentage=0.1, n=5):
        """Select reasonable threshold for motion detection."""
        filter_ovrlay = self.filter_ovrlay
        derived_frames = self.derived_frames

        pwidth, plength = derived_frames.shape[1:]
        mean_matrix = np.zeros((pwidth, plength))
        var_matrix = np.zeros((pwidth, plength))
        for i in np.arange(pwidth):
            for j in np.arange(plength):
                one_pixel_array = np.abs(derived_frames[:, i, j])
                mean_matrix[i, j] = np.mean(one_pixel_array)
                var_matrix[i, j] = np.std(one_pixel_array)

#        valid_mean_matrix = np.copy(mean_matrix[filter_ovrlay: -filter_ovrlay, 
#                                                filter_ovrlay: -filter_ovrlay])
#        mean_array = np.sort(valid_mean_matrix.reshape((1, -1))[0, :])
#        estimated_mean = mean_array[int(percentage*len(mean_array))]
                
        valid_var_matrix = np.copy(var_matrix[filter_ovrlay: -filter_ovrlay, 
                                              filter_ovrlay: -filter_ovrlay])
        var_array = np.sort(valid_var_matrix.reshape((1, -1))[0, :])
        estimated_var = var_array[int(percentage*len(var_array))]
        threshold = n*estimated_var

#        self.mean_matrix = mean_matrix
#        self.var_matrix = var_matrix
        self.threshold = threshold

    def Show_Result(self, savepath=r".\\new\\"):
        threshold = self.threshold
        derived_frames = self.derived_frames
        
        derived_index = self.derived_index
        original_frames = self.raw_frames[derived_index]
        filtered_frames = self.filtered_frames[derived_index]
        
        mask_frames = 255 * np.ones(derived_frames.shape)
        mask_frames[np.where(derived_frames >= threshold)] = 0
        
        if not os.path.exists(savepath):
            os.makedirs(savepath)

        for i in np.arange(len(derived_index)):
            fig = plt.figure()
            ax1 = fig.add_subplot(131)
            ax1.imshow(original_frames[i])
            ax1.axis('off')
            ax1.set_title("Original", fontsize=20)
            
            ax2 = fig.add_subplot(132)
            ax2.imshow(filtered_frames[i], cmap=plt.cm.gray)
            ax2.axis('off')
            ax2.set_title("Filtered", fontsize=20)
            
            ax3 = fig.add_subplot(133)
            ax3.imshow(mask_frames[i], cmap=plt.cm.gray)
            ax3.axis('off')
            ax3.set_title("Mask", fontsize=20)
            
            fig.savefig(f"{savepath}{i}.png")
            print(f"Saving {i} frame......")
            plt.close("all")


# -*- coding: utf-8 -*-
import numpy as np


class FilterApp(object):
    
    def __init__(self):
        pass

    @staticmethod    
    def apply_1d_filter(bfilter, timage):
        """Apply given 1D filter onto an image.
        
        Parameters
        ----------
        bfilter: 1D array-like
            The filter
        timage: array-like
            Targeted image
        """
        image_length = len(timage)
        ovrlay = int(bfilter.shape[0] / 2)
        tmp_array = np.zeros(image_length + 2 * ovrlay)
        tmp_array[ovrlay:-ovrlay] = timage
        res_array = np.zeros(image_length)
        for i in np.arange(image_length) + ovrlay:
            local_matrix = tmp_array[i - ovrlay:i + ovrlay + 1]
            res_array[i - ovrlay] = sum(local_matrix * bfilter)
        return res_array

    @staticmethod    
    def apply_1d_differential(operator, simage):
        """Apply given 1D filter onto a series of 2D image.
        
        Parameters
        ----------
        operator: 1D array-like
            The filter
        simage: array-like
            Targeted image sequence
        """
        image_length = simage.shape[0]
        operator_length = operator.shape[1]
        ovrlay = int(operator_length / 2)
        res_array = np.zeros((image_length - 2 * ovrlay, 
                              simage.shape[1], 
                              simage.shape[2]))
        for i in np.arange(image_length - 2 * ovrlay):
            local_array = simage[i:i + 2 * ovrlay + 1, :, :]
            temp_zip = list(zip(local_array, operator[0, :]))
            res_array[i] = sum([j[0] * j[1] for j in temp_zip])

        res_array = np.array(res_array)

        return res_array

    @staticmethod
    def apply_2d_filter(bfilter, timage):
        """Apply given 2D filter onto an image.
        
        Parameters
        ----------
        bfilter: array-like
            The filter
        timage: array-like
            Targeted image
        """
        image_shape = timage.shape
        ovrlay = int(bfilter.shape[0] / 2)
        tmp_matrix = np.zeros(np.array(image_shape) + 2 * ovrlay)
        tmp_matrix[ovrlay:-ovrlay, ovrlay:-ovrlay] = timage
        res_matrix = np.zeros(timage.shape)
        for i in np.arange(image_shape[0]) + ovrlay:
            for j in np.arange(image_shape[1]) + ovrlay:
                local_matrix = tmp_matrix[i - ovrlay:i + ovrlay + 1, 
                                          j - ovrlay:j + ovrlay + 1]
                res_matrix[i - ovrlay, j - ovrlay] = sum(sum(local_matrix * bfilter))
        return res_matrix

    @staticmethod
    def apply_1d_median_filter(n, timage):
        """Applying a n median flter on the input image assuming that the
        border pixels are not changed.
        
        Parameters
        ----------
        n: int
            Shape of median filter
        timage: array-like
            Targeted image
        """
        image_shape = timage.shape
        ovrlay = int(n / 2)
        res_matrix = np.copy(timage)
        for i in np.arange(image_shape[0])[1:-1]:
            local_matrix = timage[i - ovrlay:i + ovrlay + 1] 
            median = np.median(local_matrix)
            res_matrix[i] = median
        return res_matrix

    @staticmethod
    def apply_2d_median_filter(n, timage):
        """Applying a nxn median filter on the input image assuming that the
        border pixels are not changed."""
        image_shape = timage.shape
        ovrlay = int(n / 2)
        res_matrix = np.copy(timage)
        for i in np.arange(image_shape[0])[1:-1]:
            for j in np.arange(image_shape[1])[1:-1]:
                local_matrix = timage[i - ovrlay:i + ovrlay + 1, 
                                      j - ovrlay:j + ovrlay + 1]
                median = np.median(local_matrix)
                res_matrix[i, j] = median
        return res_matrix

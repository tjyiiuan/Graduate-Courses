# -*- coding: utf-8 -*-
import numpy as np


__all__ = ["compute_ssd",
           "compute_correlation", 
           "compute_normalized_correlation"]

def compute_ssd(image, template):
    """Compute SSD between image and template.
    
    Parameters
    ----------
    image: array-like
        The target image
    template: array-like
        Template image
    """
    image_shape = image.shape
    ovrlay = int(template.shape[0] / 2)
    tmp_matrix = - np.ones(np.array(image_shape))
    for i in np.arange(image_shape[0])[ovrlay:-ovrlay]:
        for j in np.arange(image_shape[1])[ovrlay:-ovrlay]:
            local_matrix = image[i - ovrlay:i + ovrlay + 1, 
                                 j - ovrlay:j + ovrlay + 1]
            tmp_matrix[i, j] = sum(sum((local_matrix - template)**2))
    
    return tmp_matrix

def compute_correlation(image, template):
    """Compute cross-correlation between image and template.
    
    Parameters
    ----------
    image: array-like
        The target image
    template: array-like
        Template image
    """
    image_shape = image.shape
    ovrlay = int(template.shape[0] / 2)
    tmp_matrix =  - np.ones(np.array(image_shape))
    for i in np.arange(image_shape[0])[ovrlay:-ovrlay]:
        for j in np.arange(image_shape[1])[ovrlay:-ovrlay]:
            local_matrix = image[i - ovrlay:i + ovrlay + 1, 
                                 j - ovrlay:j + ovrlay + 1]
            tmp_matrix[i, j] = sum(sum((local_matrix * template)))
    
    return tmp_matrix

def normalize(matrix):
    """Compute normailized form.
    
    Parameters
    ----------
    matrix: array-like
        The target matrix
    """
    msum = sum(sum(matrix**2))
    if msum:
        res = matrix / msum
    else:
       res = matrix
    
    return res    
    
def compute_normalized_correlation(image, template):
    """Compute cross-correlation between image and template.
    
    Parameters
    ----------
    image: array-like
        The target image
    template: array-like
        Template image
    """
    image_shape = image.shape
    ovrlay = int(template.shape[0] / 2)
    tmp_matrix =  - np.ones(np.array(image_shape))
    nor_template = normalize(template)

    for i in np.arange(image_shape[0])[ovrlay:-ovrlay]:
        for j in np.arange(image_shape[1])[ovrlay:-ovrlay]:
            local_matrix = image[i - ovrlay:i + ovrlay + 1, 
                                 j - ovrlay:j + ovrlay + 1]
            normalized_local_matrix = normalize(local_matrix)
            tmp_matrix[i, j] = sum(sum((normalized_local_matrix * nor_template)))
    
    return tmp_matrix
# -*- coding: utf-8 -*-
import glob
import numpy as np
import matplotlib.image as mpimg


__all__ = ["Gen_Gaussian_Filter", "Load_Images", "rgb2gray"]


def Gen_Gaussian_Filter(dim, sigma, size=0):
    """Generate 1D or 2D Gaussian filter.
    
    Parameters
    ----------
    dim: int
        Dimension of filter
    sigma: float
        Standard deviation    
    n: int
        Size
    """
    n = max(2 * np.ceil(2 * sigma) + 1, size)
    ovrlay = int(n / 2)
    inds = np.arange(-ovrlay, ovrlay + 1)
    gaussian_1d = np.exp(-inds**2/(2 * sigma**2))
    mask = gaussian_1d /sum(gaussian_1d).reshape((-1, 1))
    
    if dim == 2:
        mask = gaussian_1d * gaussian_1d.T
        
    return mask

def Load_Images(path, imgtype="*.jpg"):
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
        all_img[i] = mpimg.imread(one_img_path)
#    all_img = np.array(all_img)
    
    return all_img

def rgb2gray(img):
    """Convert a RGB image to gray scale."""
    if img.shape[-1] == 3:
        grayimg = 0.299 * img[:, :, 0] + 0.587 * img[:, :, 1] + 0.114 * img[:, :, 2]
    else:
        grayimg = img[:, :, 0]

    return grayimg

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
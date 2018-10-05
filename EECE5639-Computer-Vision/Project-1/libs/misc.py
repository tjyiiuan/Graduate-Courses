# -*- coding: utf-8 -*-
import glob
import numpy as np
import matplotlib.image as mpimg


def EST_NOISE(images):
    """Implementation of EST_NOISE in Chapter 2 of Trucco and Verri."""
    num = images.shape[0]
    m_e_bar = sum(images)/num
    m_sigma = np.sqrt(sum((images - m_e_bar)**2) / (num - 1))

    return m_sigma


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
    all_img = np.array(all_img)
    
    return all_img

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

def Gen_Box_Filter(n):
    """Generate 1D or 2D Gaussian filter.
    
    Parameters
    ----------
    n: int
        Size
    """
    size = int(n)
    box_mask = np.ones((size, size)) / (size ** 2)
    
    return box_mask



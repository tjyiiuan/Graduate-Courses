# -*- coding: utf-8 -*-
import glob
import numpy as np
import matplotlib.image as mpimg


__all__ = ["Gen_Gaussian_Filter", "Load_Images", "rgb2gray", 
           "calculate_gradient", "normalized_correlation"]


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

def calculate_gradient(matrix, method='Prewitt'):
    mshape = np.shape(matrix)
    
    if "s" in method.lower():
        factor = 2
    else:
        factor = 1
    
    # x-axis
    x_gradient = np.zeros(mshape)
    matrix_x = matrix[:, 2:] - matrix[:, :-2]
    matrix_x0 = matrix_x[:-2, :]
    matrix_x1 = matrix_x[1:-1, :] * factor
    matrix_x2 = matrix_x[2:, :]
    x_gradient[1:-1, 1:-1] = matrix_x0 + matrix_x1 + matrix_x2 
    # y-axis
    y_gradient = np.zeros(mshape)
    matrix_y = matrix[2:, :] - matrix[:-2, :]
    matrix_y0 = matrix_y[:, :-2]
    matrix_y1 = matrix_y[:, 1:-1] * factor
    matrix_y2 = matrix_y[:, 2:]
    y_gradient[1:-1, 1:-1] = matrix_y0 + matrix_y1 + matrix_y2 
    
    return x_gradient, y_gradient


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
    
def normalized_correlation(image, template):
    """Compute cross-correlation between image and template.
    
    Parameters
    ----------
    image: array-like
        The target image
    template: array-like
        Template image
    """
    nimage = np.array([normalize(i) for i in image])
    ntemplate = normalize(template)
    mask_array = nimage * ntemplate
    ncc_array = mask_array.sum(axis = -1).sum(axis=1)
    
    return ncc_array

def homo_project(trans_matrix, point):
    """Project a point using given trnasformation matrix."""
    x, y = point
    homo_point = np.array([[x], [y], [1]])
    unor_point = np.matmul(trans_matrix, homo_point)
    z = unor_point[-1, 0]
    if z:
        point = tuple(unor_point[:2, 0] / z)
    else:
        point = (np.inf, np.inf)
    
    return point

def euclidean_disance(p0, p1):
    """Calculate Euclidean disance."""
    x0, y0 = p0
    x1, y1 = p1
    dis = np.sqrt((x0 - x1)**2 + (y0 - y1)**2)
    
    return dis
    
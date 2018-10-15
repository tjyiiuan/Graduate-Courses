# -*- coding: utf-8 -*-
import glob
import numpy as np
import matplotlib.image as mpimg


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

#!/usr/bin/env python
# -*- coding: utf-8 -*-

import numpy as np
import matplotlib.pylab as plt
plt.rcParams['figure.figsize'] = 15, 6
plt.rcParams['figure.dpi'] = 300


NUMBER = 10
IMG_SHAPE = (256, 256)
GREY_LEVEL = 128
MU = 0
SIGMA = 2
BOX_FILTER = np.ones((3, 3)) / 9


class Homework2(object):
    
    def __init__(self):
        pass
    
    @staticmethod
    def gen_one_image():
        """Generate one image."""
        img = GREY_LEVEL * np.ones(IMG_SHAPE)
        noise = np.random.normal(MU, SIGMA, IMG_SHAPE)
        image = img + noise
        
        return image
    
    def gen_image(self, nimage):
        """Generate all required images."""
        tmp = [0] * nimage
        for i in np.arange(nimage):
            tmp[i] = self.gen_one_image()
        
        return np.array(tmp)

    @staticmethod
    def EST_NOISE(images):
        """Implementation of EST_NOISE in Chapter 2 of Trucco and Verri."""
        num = images.shape[0]
        m_e_bar = sum(images)/num
        m_sigma = np.sqrt(sum((images - m_e_bar)**2)/(num - 1))
    
        return m_sigma
    
    def solve_p1(self):
        """Solve problem #1."""
        all_image = self.gen_image(NUMBER)
        result = self.EST_NOISE(all_image)
        self.plot_result(all_image, result)
        
        self.images = all_image
   
    @staticmethod
    def apply_box_filter(bfilter, timage):
        """Apply given filter onto an image.
        
        Parameters
        ----------
        bfilter: array-like
            The filter
        timage: array-like
            Targeted image
        """
        image_shape = timage.shape
        ovrlay = int(bfilter.shape[0] / 2)
        tmp_matrix = np.zeros(np.array(timage.shape) + 2 * ovrlay)
        tmp_matrix[1:-1, 1:-1] = timage
        res_matrix = np.zeros(timage.shape)
        for i in np.arange(image_shape[0]) + 1:
            for j in np.arange(image_shape[1]) + 1:
                local_matrix = tmp_matrix[i - ovrlay:i + ovrlay + 1, j - ovrlay:j + ovrlay + 1]
                res_matrix[i - 1, j - 1] = sum(sum(local_matrix * bfilter))
        return res_matrix
    
    def solve_p2(self):
        """Solve problem #2."""
        all_image = self.images
        filted_image = [0] * NUMBER
        for i in np.arange(NUMBER):
            filted_image[i] = self.apply_box_filter(BOX_FILTER, all_image[i])
        filted_image = np.array(filted_image)
        result = self.EST_NOISE(filted_image)
        self.plot_result(filted_image, result)
    
    @staticmethod
    def plot_result(images, res):
        """Plot results."""
        fig = plt.figure()
        for i in np.arange(NUMBER):
            ax = fig.add_subplot(2, 5, i + 1)
            ax.axis('off')
            ax.imshow(images[i], cmap=plt.cm.gray)
        fig.suptitle(f"Estimated Noise {res.mean():.4f}, Worst Case Noise {res.max():.4f}",
                     fontsize=12)
        fig.show()
    
#%%
if __name__ == "__main__":
    hw2 = Homework2()
    hw2.solve_p1()
    hw2.solve_p2()
    
    
    
    
    
    
    
    
    
    
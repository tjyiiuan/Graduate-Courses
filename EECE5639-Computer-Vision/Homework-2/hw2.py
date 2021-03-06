#!/usr/bin/env python
# -*- coding: utf-8 -*-

import numpy as np
import matplotlib.pylab as plt
import matplotlib.image as mpimg
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300


NUMBER = 10
IMG_SHAPE = (256, 256)
GREY_LEVEL = 128
MU = 0
SIGMA = 2

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
        print("\n\nSolution for problem #1.")
        self.plot_result(all_image, result)
        
        self.images = all_image
   
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
    
    def solve_p2(self):
        """Solve problem #2."""
        box_filter = np.ones((3, 3)) / 9
        all_image = self.images
        filted_image = [0] * NUMBER
        for i in np.arange(NUMBER):
            filted_image[i] = self.apply_2d_filter(box_filter, all_image[i])
        filted_image = np.array(filted_image)
        result = self.EST_NOISE(filted_image)
        print("\n\nSolution for problem #2.")
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
    
    def solve_p3(self, sigma=1.4, show=True):
        """Solve problem #1."""
        n = int(2 * np.ceil(2 * sigma) + 1)
        # 2D Gaussian
        ovrlay = int(n / 2)
        inds = np.arange(-ovrlay, ovrlay + 1)
        x, y = np.meshgrid(inds, inds)
        mask = np.exp(-(x**2 + y**2)/(2*sigma**2))
        mask = mask/sum(sum(mask))
        
        # two 1D Gaussian
        gaussian_1d = np.exp(-inds**2/(2 * sigma**2))
        gaussian_1d = gaussian_1d /sum(gaussian_1d).reshape((-1, 1))
        mask2 = gaussian_1d * gaussian_1d.T
        
        print("\n\nSolution for problem #3.")
              
        if show:
            Fsize = 16
            test_img = mpimg.imread(r".\fig\test.png")
            output_img1 = self.apply_2d_filter(mask, test_img)
            output_img2 = self.apply_2d_filter(mask2, test_img)
            fig = plt.figure()
            ax1 = fig.add_subplot(131)
            ax1.axis('off')
            ax1.imshow(test_img, cmap=plt.cm.gray)
            ax1.set_title("Raw image", fontsize=Fsize)
            ax2 = fig.add_subplot(132, sharex=ax1, sharey=ax1)
            ax2.axis('off')
            ax2.imshow(output_img1, cmap=plt.cm.gray)
            ax2.set_title(f"2D {n}x{n} Gaussian", fontsize=Fsize)
            ax3 = fig.add_subplot(133, sharex=ax1, sharey=ax1)
            ax3.axis('off')
            ax3.imshow(output_img2, cmap=plt.cm.gray)
            ax3.set_title(f"Two 1D n={n} Gaussian", fontsize=Fsize)
            fig.show()

        return mask, gaussian_1d

    @staticmethod    
    def apply_1d_filter(bfilter, timage):
        """Apply given 1D filter onto an image.
        
        Parameters
        ----------
        bfilter: array-like
            The filter
        timage: array-like
            Targeted image
        """
        image_length = len(timage)
        ovrlay = int(bfilter.shape[0] / 2)
        tmp_array = np.zeros(image_length + 2 * ovrlay)
        tmp_array[ovrlay:-ovrlay] = timage
        res_array = np.zeros(image_length )
        for i in np.arange(image_length) + ovrlay:
            local_matrix = tmp_array[i - ovrlay:i + ovrlay + 1]
            res_array[i - ovrlay] = sum(local_matrix * bfilter)
        return res_array
    
    @staticmethod
    def apply_1d_median_filter(n, timage):
        """Applying a 3 median flter on the image I assuming that the
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
    
    def solve_p4(self):
        """Solve problem #4."""
        I = np.array([10] * 5 + [40] * 5)
        filter1 = np.ones(5)/5
        filter2 = np.array([1, 2, 4, 2, 1]) / 10
        
        O1 = self.apply_1d_filter(filter1, I).astype(int)
        O2 = self.apply_1d_filter(filter2, I).astype(int)
        print("\n\nSolution for problem #4.")
        print("Filter (a)")
        print(O1)
        print("Filter (b)")
        print(O2)
        return O1, O2

    @staticmethod
    def apply_2d_median_filter(n, timage):
        """Applying a nxn median filter on the image I assuming that the
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
    
    def solve_p5(self, show=True):
        """Solve problem #5."""
        print("\n\nSolution for problem #5.")
        if show:
            fig = plt.figure()
            ax1 = fig.add_subplot(121)
            ax1.stem([-100, 0, 100], np.array([49, 42, 9])/100)
            ax1.set_ylabel("Probability")
            ax1.set_xlabel("Output")
            ax1.set_title("On the line")
            ax2 = fig.add_subplot(122)
            ax2.stem([-150, -50, 50, 100], np.array([21, 9, 21, 49])/100)
            ax2.set_ylabel("Probability")
            ax2.set_xlabel("Output")
            ax2.set_title("On the adjacent line")
            fig.show()

    def solve_p6(self):
        """Solve problem #6."""
        I = np.zeros((8, 8)).astype(int)
        for i in np.arange(8):
            for j in np.arange(8):
                I[i, j] = np.abs(i - j)
        O = self.apply_2d_median_filter(3, I).astype(int)
        print("\n\nSolution for problem #6.")
        print(I)
        print(O)
        return I, O
    
    def solve_p7(self):
        """Solve problem #7."""
        I = np.array([4] * 4 + [8] * 4)
        O1 = self.apply_1d_median_filter(3, I)
        avgfilter = np.array([1, 2, 1]) / 4
        O2 = np.copy(I)
        O2[1:-1] = self.apply_1d_filter(avgfilter, I)[1:-1]
        
        print("\n\nSolution for problem #7.")
        print("Median Filter")
        print(O1)
        print("Average Mask")
        print(O2)
        return O1, O2

if __name__ == "__main__":
    hw2 = Homework2()
    hw2.solve_p1()
    hw2.solve_p2()
    hw2.solve_p3()
    hw2.solve_p4()
    hw2.solve_p5()
    hw2.solve_p6()
    hw2.solve_p7()
#    
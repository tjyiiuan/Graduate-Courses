# -*- coding: utf-8 -*-
import warnings

import numpy as np
import matplotlib.pylab as plt

from libs.FilterApp import FilterApp
from libs import misc

warnings.filterwarnings("ignore")
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300


#%%
class Homework3(object):
    
    def __init__(self):
        self.filapp = FilterApp()

    @staticmethod
    def rotate(matrix):    
        new_matrix = np.array(list(zip(*matrix[::-1])))
        return new_matrix

    def edge_detection(self, timage, vdetector):
        """Implementaion of algorithm canny_enhancer.
        
        Parameters
        ----------
        timage: array-like
            Targeted image
        vdetector: array-like
            Vertical detector
        """
        hdetector = self.rotate(vdetector)
        xres = self.filapp.apply_2d_filter(vdetector, timage)
        yres = self.filapp.apply_2d_filter(hdetector, timage)
        magnitude = np.sqrt(xres**2 + yres**2)
        inds = np.where(xres == 0)
        orientation = np.arctan(yres / xres)
        orientation[inds] = np.arctan(np.inf)
        
        return magnitude, orientation
    
    @staticmethod
    def print_matrix_latex(matrix):
        for i in matrix:
            print(' & '.join(str(i)[1:-1].split(" ")))

    def solve_p1(self):
        """Solve problem #1."""
        row, col = np.meshgrid(np.arange(8), np.arange(8))
        image = np.abs(row - col)
        prewitt_vmask = np.array([[-1, 0, 1]]) * np.array([[1],[1],[1]])
        sobel_vmask = np.array([[-1, 0, 1]]) * np.array([[1],[2],[1]])

        print(f"\n\nSolution for problem #1.\n{'='*80}")
        for irow in image:
            print(' & '.join(str(irow)[1:-1].split(" ")))
        pmag, pori = self.edge_detection(image, prewitt_vmask)
        print("Magnitude and Orientation with Prewitt Mask.\n")
        
        print(pmag.round(4))
        print("\n")
        print(pori.round(4))
        smag, sori = self.edge_detection(image, sobel_vmask)
        print("\nMagnitude and Orientation with Sobel Mask.\n")
        print(smag.round(4))
        print("\n")
        print(sori.round(4))
       
    @staticmethod
    def findC(x2, y2, xy, ind, neigh):
        x, y = ind
        n = int(neigh / 2)
        Ixx = x2[x - n:x + n + 1, y - n:y + n + 1]
        Iyy = y2[x - n:x + n + 1, y - n:y + n + 1]
        Ixy = xy[x - n:x + n + 1, y - n:y + n + 1]
        C = np.array([[sum(sum(Ixx)), sum(sum(Ixy))], [sum(sum(Ixy)), sum(sum(Iyy))]])
        
        return C

    def solve_p2(self):
        """Solve problem #2."""
        Zero = np.zeros((10, 10))
        One = 40 * np.ones((10, 10))
        I = np.hstack([np.vstack([Zero, One]), np.vstack([One, Zero])])
        Ix = np.zeros((20, 20))
        Iy = np.zeros((20, 20))
        for i in np.arange(20)[1:-1]:
            for j in np.arange(20)[1:-1]:
                Ix[i, j] = I[i, j + 1] - I[i, j - 1]
                Iy[i, j] = I[i + 1, j] - I[i - 1, j]
        print(f"\n\nSolution for problem #2.\n{'='*80}")
        Ixy = Ix * Iy
        Ixx = Ix ** 2
        Iyy = Iy ** 2
        c = self.findC(Ixx, Iyy, Ixy, [10, 9], 7)
        
        print(c)
        
#%%       
if __name__ == "__main__":
    hw3 = Homework3()
    hw3.solve_p1()
    hw3.solve_p2()
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
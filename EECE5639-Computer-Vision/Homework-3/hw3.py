# -*- coding: utf-8 -*-
import warnings

import numpy as np
import matplotlib.pylab as plt
from matplotlib.lines import Line2D

from libs.FilterApp import FilterApp
from libs import misc

warnings.filterwarnings("ignore")
plt.rcParams['figure.figsize'] = 8, 8
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

    def solve_p3(self):
        """Solve problem #3."""
        Zero = np.zeros((10, 10))
        One = 40 * np.ones((10, 10))
        I = np.hstack([np.vstack([Zero, One]), np.vstack([One, Zero])])
        prewitt_xmask = np.array([[-1, 0, 1]]) * np.array([[1], [1], [1]])
        prewitt_ymask = np.array([[1, 1, 1]]) * np.array([[-1], [0], [1]])
        Ix = np.zeros((20, 20))
        Iy = np.zeros((20, 20))
        Ex = self.filapp.apply_2d_filter(prewitt_xmask, I)
        Ey = self.filapp.apply_2d_filter(prewitt_ymask, I)
        
        Ix[1:-1, 1:-1] = np.copy(Ex[1:-1, 1:-1])
        Iy[1:-1, 1:-1] = np.copy(Ey[1:-1, 1:-1])
        print(f"\n\nSolution for problem #2.\n{'='*80}")
        Ixy = Ix * Iy
        Ixx = Ix ** 2
        Iyy = Iy ** 2
        ovrlay = 4
        eigen = np.zeros((20, 20))
        for i in np.arange(20)[ovrlay:-ovrlay]:
            for j in np.arange(20)[ovrlay:-ovrlay]:
                c = self.findC(Ixx, Iyy, Ixy, [i, j], 7)
                eigenvalues = misc.solve_2_eigenvalues(c)
                if eigenvalues:
                    eigen[i, j] = min(eigenvalues)
        print(c)

    def solve_p4(self):
        """Solve problem #4."""
        figure, ax = plt.subplots()
        ax.plot([-10, 10], [2*np.sqrt(2) - 10, 2*np.sqrt(2) + 10], 'k')
        ax.plot([-10, 10], [4, 4], 'k')
        ax.plot([-4, -4], [-10, 10], 'k')
        ax.set_xlim([-5, 4])
        ax.set_ylim([-4, 5])
        ax.set_title("$S = 36 - 16\sqrt{2}$", fontsize=20)
        plt.show()

    def solve_p5(self):
        """Solve problem #5."""
        sqrt3 = np.sqrt(3)
        figure, ax = plt.subplots()
        ax.plot([-10, 10], [2 + 10 / sqrt3, 2 - 10 / sqrt3], 'g--')
        ax.plot([-10, 10], [3, 3], 'g--')
        ax.plot([3, 3], [-10, 10], 'g--')
        ax.plot([-10, 10], [-9, 11], 'g--')
        ax.set_xlim([-5, 4])
        ax.set_ylim([-4, 5])
        text = ['A', 'B', 'C', 'D']
        points = np.array(((2, 3), (3, 3), (3, 2- sqrt3), 
                           (sqrt3  / (sqrt3 + 1), (2 * sqrt3 + 1) / (sqrt3 + 1))))
        ax.scatter(points[:, 0], points[:, 1], c='r')
        for i in np.arange(4):
            ax.annotate(f"{text[i]}{points[i, :].round(2)}", points[i, :], fontsize=20)
        ax.set_xlim([0, 4])
        ax.set_ylim([-1, 4])
#        ax.set_title("$S = 36 - 16\sqrt{2}$", fontsize=20)
        plt.show()

#%%       
if __name__ == "__main__":
    hw3 = Homework3()
    hw3.solve_p1()
    hw3.solve_p3()
    hw3.solve_p4()
    hw3.solve_p5()
    
    
    
    
    
    
    
    
    
    
    
    
    
    
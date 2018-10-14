# -*- coding: utf-8 -*-
import numpy as np

from libs.misc import *


class Homework4(object):
    
    def __init__(self):
        pass
    
    def solve_p2(self):
        """Solve problem #1."""
        f = np.array([[0, 0, 0, 0, 0, 0, 0, 0], 
                      [0, 2, 4, 2, 0, 0, 0, 0],
                      [0, 2, 0, 0, 0, 0, 0, 0],
                      [0, 0, 2, 0, 0, 0, 2, 0],
                      [0, 0, 0, 0, 0, 0, 2, 0],
                      [1, 2, 1, 0, 0, 2, 4, 2],
                      [0, 1, 0, 0, 0, 0, 0, 0],
                      [0, 1, 0, 0, 0, 0, 0, 0]])

        g = np.array([[1, 2, 1],
                      [0, 1, 0],
                      [0, 1, 0]])

        ssd = compute_ssd(f, g)
        cfg = compute_correlation(f, g)
        nfg = compute_normalized_correlation(f, g).round(4)
        
        
        print(f"\n\nSolution for problem #1.\n{'='*80}")
        print("SSD\n")
        print(ssd)
        print("Correlation\n")
        print(cfg)        
        print("Normalized Correlation\n")
        print(nfg)

if __name__ == "__main__":
    hw4 = Homework4()
    hw4.solve_p2()

# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pylab as plt


def solve_2_eigenvalues(matrix):
    a, b, c, d = matrix.reshape((1, -1))[0, :]
    l = 1
    m = - a - d
    n = a * d - b * c
    
    delta = np.sqrt(m**2 - 4 * l * n)
    if delta > 0:
        delta = np.sqrt(delta)
        x1 = (- m + delta) / (2 * l)
        x2 = (- m - delta) / (2 * l)
        return x1, x2
    elif delta == 0:
        x = (- m) / (2 * l)
        return x, x
    else:
        return None
        
def pol2line(pol):
    rho, phi = pol
    y0 = rho / np.sin(phi)
    x0 = rho / np.cos(phi)
    
    return (x0, 0), (0, y0)

def line2pol(p1, p2):
    (x1, y1), (x2, y2) = p1, p2
    if x1 == x2:
        rho = x1
        theta = 0
    elif y1 == y2:
        rho = y1
        theta = np.pi / 2
    else:
        x0 = x1 - y1 * (x2 - x1) / (y2 - y1)
        y0 = y1 - x1 * (y2 - y1) / (x2 - x1)
        rho = np.sqrt(x0**2 * y0**2 / (x0**2 + y0**2))
        if rho:
            theta = np.sign(y0) * np.abs(np.arctan(x0/y0))
            rho = np.sign(x0) * rho
        else:
            theta = np.arctan((y2 - y1) / (x2 - x1))
    
    return rho, theta

def calc_area(p1, p2, p3):
    (x1, y1), (x2, y2), (x3, y3) = p1, p2, p3
    area = abs(x2 * y3 + x1 * y2 + x3 * y1 - x3 * y2 - x2 * y1 - x1 * y3) / 2
    
    return area

def find_enclosing(p1, p2, p3):
    (r1, t1), (r2, t2), (r3, t3) = p1, p2, p3
#%%

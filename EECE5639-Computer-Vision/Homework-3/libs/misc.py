# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pylab as plt


def cart2pol(x, y):
    rho = np.sqrt(x**2 + y**2)
    phi = np.arctan2(y, x)
    return rho, phi 

def pol2cart(rho, phi):
    x = rho * np.cos(phi)
    y = rho * np.sin(phi)
    return x, y 

def pol2line(rho, phi):
    y0 = rho / np.sin(phi)
    x0 = rho / np.cos(phi)
    
    return [[x0, 0], [0, y0]]


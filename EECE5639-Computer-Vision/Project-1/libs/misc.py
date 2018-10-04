# -*- coding: utf-8 -*-
import numpy as np


def EST_NOISE(images):
    """Implementation of EST_NOISE in Chapter 2 of Trucco and Verri."""
    num = images.shape[0]
    m_e_bar = sum(images)/num
    m_sigma = np.sqrt(sum((images - m_e_bar)**2) / (num - 1))

    return m_sigma


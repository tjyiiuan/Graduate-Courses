#!/usr/bin/env python
# -*- coding: utf-8 -*-

from libs.misc import Load_Images
from libs.Flow import Dense_Optical_Flow


image_path = r".\\toys\\"
parameters = {"smooth": "Gaussian",
              "sigma": 1.4, 
              "method": "Prewitt",
              "neighbor": 5}


if __name__ == "__main__":
    raw_images = Load_Images(image_path, imgtype="*.*")[3:]
    
    flow = Dense_Optical_Flow(raw_images[0], raw_images[1], params=parameters)
    flow.LKMethod(scale=1)
    flow.LKMethod(scale=2)
    flow.LKMethod(scale=4)

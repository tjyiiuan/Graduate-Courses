#!/usr/bin/env python
# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pylab as plt
#import matplotlib.image as mpimg
plt.rcParams['figure.figsize'] = 15, 6
#plt.rcParams['figure.dpi'] = 300
    
from libs.misc import Load_Images
from libs.Detector import MotionDetector


frame_path = r".\\Office\\"
test_case = [('no', 0, 'd', 2, 0.1),
             ('b', 3, 'd', 2, 0.1),
             ('b', 5, 'd', 2, 0.1),
             ('g', 1.4, 'd', 2, 0.1),
             ('g', 1.8, 'd', 2, 0.1),
             ('b', 5, 'g', 1, 0.1),
             ('b', 5, 'g', 1.6, 0.1),
             ('b', 5, 'g', 2.5, 0.1),
             ('b', 5, 'd', 2, 0.1),
             ('b', 5, 'd', 2, 0.2),
             ('b', 5, 'd', 2, 0.5)]


if __name__ == "__main__":
    for i in np.arange(len(test_case))[3:4]:
        case = test_case[i]
        save_path = f".\\Report\\{i}\\"
        raw_frames = Load_Images(frame_path)
        Mdetector = MotionDetector(raw_frames)
        Mdetector.Convert_Gray()
        Mdetector.Spatial_Smooth(case[0], param=case[1])
        Mdetector.Temporal_Derive(case[2], param=case[3])
        Mdetector.Threshold_Select(percentage=case[4])
        Mdetector.Show_Result(savepath=save_path)

        
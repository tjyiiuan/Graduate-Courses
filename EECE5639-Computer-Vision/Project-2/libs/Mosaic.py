# -*- coding: utf-8 -*-
import logging

import numpy as np
import matplotlib.pylab as plt
plt.rcParams['figure.figsize'] = 15, 6

from .misc import rgb2gray, homo_project
from .Harris import Harris_Corner_Detector
from .Correspondences import Correspond


class Image_Mosaicing(object):
    """Produce a mosaic containing the union of all pixels in the two images.
    
    Parameters
    ----------
    rawimg1: array-like
        Raw image 1.
    rawimg2: array-like
        Raw image 2.
    """

    def __init__(self, rawimg1, rawimg2, params):
        self.raw_image1 = rawimg1
        self.raw_image2 = rawimg2
        self.gray_image1 = rgb2gray(rawimg1)
        self.gray_image2 = rgb2gray(rawimg2)
        self.grad_method = params["method"]
        self.neighbor = params["corner_neighbor"]
        self.avg = params["avg"]
        self.sigma = params["sigma"]
        self.corner_thresold = params["corner_thresold"]
        self.k = params["k"]
        self.nonmax_window = params["nonmax_window"]
        self.ncc_threshold = params["ncc_threshold"]
        self.ncc_neighbor = params["ncc_neighbor"]
        self.ransac_ratio = params["ransac_ratio"]
        self.ransac_distance = params["ransac_distance"]
        self.ransac_iteration = params["ransac_iteration"]
        self.homo_num = params["homo_num"]
        self.correspond = None

    def _apply_harris_corner_detector(self, image):
        """Apply Harris corner detector."""
        neighbor = self.neighbor
        avg = self.avg
        sigma = self.sigma
        corner_thresold = self.corner_thresold
        k = self.k
        nonmax_window = self.nonmax_window
        
        detector = Harris_Corner_Detector(image, neighbor=neighbor, avg=avg, 
                                          sigma=sigma, k=k, 
                                          thresold=corner_thresold,
                                          nonmax_window=nonmax_window)
        detector.harris_r_matrix()
        detector.nonmax_Supression_old()
        r = detector.nonmax_r
        
        return r

    def corner_detect(self, show=True):
        """Detect corners."""
        image1 = self.gray_image1
        image2 = self.gray_image2
        r1 = self._apply_harris_corner_detector(image1)
        r2 = self._apply_harris_corner_detector(image2)
        self.corner1 = r1
        self.corner2 = r2
        logging.debug(f"R1 size:\t{len(np.where(r1)[0])}")
        logging.debug(f"R2 size:\t{len(np.where(r2)[0])}")

        if show:
            fig = plt.figure()
            ax1 = fig.add_subplot(121)
            ax1.axis('off')
            ax1.imshow(self.raw_image1)
            ax1.scatter(np.where(r1)[1], np.where(r1)[0], c="r")
            ax2 = fig.add_subplot(122)
            ax2.axis('off')
            ax2.imshow(self.raw_image2)
            ax2.scatter(np.where(r2)[1], np.where(r2)[0], c="r")
            plt.show()

        return self
    
    def correspondences(self, show=True):
        """Find correspondences."""
        threshold = self.ncc_threshold
        neighbor = self.ncc_neighbor
        image1 = self.gray_image1
        image2 = self.gray_image2
        r1 = self.corner1
        r2 = self.corner2
        correspond = Correspond(image1, image2, r1, r2, 
                                threshold=threshold, neighbor=neighbor)
        correspond.calculate_ncc()
        correspond.find_cor_pair()
        
        self.correspond = correspond
        self.correspondence_pairs = correspond.points_pair
        
        if show:
            raw_image1 = self.raw_image1
            raw_image2 = self.raw_image2
            joint_image = np.hstack((raw_image1, raw_image2))
            points1, points2 = correspond.points_pair
            points2 = np.array(points2)
            points2[:, 1] += raw_image1.shape[1]
            points_draw_pair = list(zip(np.array(points1), points2))
            fig = plt.figure()
            ax = fig.add_subplot(111)
            ax.axis("off")
            ax.imshow(joint_image)
            for i in points_draw_pair[:100]:
                tmp = np.array(i)
                ax.plot(tmp[:, 1], tmp[:, 0])
    
            plt.show()

        return self
    
    def homography_estimate(self, show=True):
        """Estimate homography."""
        ratio = self.ransac_ratio
        distance = self.ransac_distance
        iteration = self.ransac_iteration
        correspond = self.correspond
        homo_num = self.homo_num
        point1, point2 = correspond.points_pair

        zip_points_pair = list(zip(point1, point2))
        pair_length = len(zip_points_pair)

        loop_count = 0
        
        while loop_count < iteration:
            logging.info(f"Loop Count {loop_count}")
            n_init_points_inds = np.random.choice(np.arange(pair_length), 
                                                  homo_num, replace=False)
            n_init_points = list(np.array(zip_points_pair)[n_init_points_inds])
            homo_matrix = correspond.solve_homograph_matrix(n_init_points)
            new_zip_points_pair = correspond.ransac(zip_points_pair, 
                                                    homo_matrix, distance)
            new_pair_length = len(new_zip_points_pair)
            if new_pair_length / pair_length > ratio:
                zip_points_pair = new_zip_points_pair
                pair_length = new_pair_length
                loop_count += 1
            else:
                logging.info("Noop")


        if show:
            raw_image1 = self.raw_image1
            raw_image2 = self.raw_image2
            joint_image = np.hstack((raw_image1, raw_image2))
            plot_zip_points_pair = np.copy(new_zip_points_pair)
            fig = plt.figure()
            ax = fig.add_subplot(111)
            ax.axis("off")
            ax.imshow(joint_image)
            for point_pair in plot_zip_points_pair:
                point_pair[1, 1] += raw_image1.shape[1]
                ax.plot(point_pair[:, 1], point_pair[:, 0], 'r--')
            plt.show()

        self.final_zip_points_pair = new_zip_points_pair
        self.homo_projection_matrix = homo_matrix

        return self

    def image_warp(self, show=True):
        """Warp images."""
        raw_image1 = self.raw_image1
        raw_image2 = self.raw_image2
        homo_matrix = self.homo_projection_matrix
        
        print(homo_project(homo_matrix, (0, 0)))






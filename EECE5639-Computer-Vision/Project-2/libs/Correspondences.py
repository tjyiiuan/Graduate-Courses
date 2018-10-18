# -*- coding: utf-8 -*-
import logging

import numpy as np

from .misc import normalized_correlation, homo_project, euclidean_disance


class Correspond(object):
    
    def __init__(self, image1, image2, r1, r2, threshold=0, neighbor=3):
        self.image1 = image1
        self.image2 = image2
        self.r1 = r1
        self.r2 = r2
        self.threshold = threshold
        self.neighbor = neighbor
    
    def _gen_local_matrix(self, image, r_matrix):
        """Generate index array with R matrix."""
        n = self.neighbor
        row_index, col_index = np.where(r_matrix)
        ovrlay = min(min(row_index), min(col_index), int(n / 2))
        matrix_inds = list(zip(row_index, col_index))
        matrix_array = [image[i[0] - ovrlay:i[0] + ovrlay + 1,
                              i[1] - ovrlay:i[1] + ovrlay + 1] for i in matrix_inds]     
        point_inds = list(zip(row_index, col_index))

        return np.array(matrix_array), point_inds

    def calculate_ncc(self):
        """Calculate and generate NCC matrix between two images."""
        image1 = self.image1
        image2 = self.image2
        r1 = self.r1
        r2 = self.r2
        matrix_array_1, pinds1 = self._gen_local_matrix(image1, r1)
        matrix_array_2, pinds2 = self._gen_local_matrix(image2, r2)
        len1 = len(pinds1)
        len2 = len(pinds2)
        
        ncc_matrix = np.zeros((len1, len2))
        for i in np.arange(len1):
            template = matrix_array_1[i]
            ncc_row_i = normalized_correlation(matrix_array_2, template)
            ncc_matrix[i, :] = ncc_row_i
        
        self.matrix_array_1 = matrix_array_1
        self.matrix_array_2 = matrix_array_2
        self.points_inds_1 = pinds1
        self.points_inds_2 = pinds2
        self.ncc_matrix = ncc_matrix
        
        return self
    
    def find_cor_pair(self):
        """Find correspondence points pairs."""
        threshold = self.threshold
        ncc_matrix = np.copy(self.ncc_matrix)
#        matrix_array_1 = self.matrix_array_1
#        matrix_array_2 = self.matrix_array_2
        points_inds_1 = self.points_inds_1
        points_inds_2 = self.points_inds_2
        
        ncc_matrix[np.where(ncc_matrix < threshold)] = 0
        pair_length = min(len(points_inds_1), len(points_inds_2))
        ipoints_array_1 = [(0, 0)] * pair_length 
        ipoints_array_2 = [(0, 0)] * pair_length 
        count = 0

        while 1:
            current_max = ncc_matrix.max()
            if not current_max:
                break
            
            row, col = np.where(ncc_matrix == current_max)
            ipoints_array_1[count] = points_inds_1[row[0]]
            ipoints_array_2[count] = points_inds_2[col[0]]
            ncc_matrix[row[0], :] = 0
            ncc_matrix[:, col[0]] = 0

            count += 1
        
        self.points_pair = (ipoints_array_1, ipoints_array_2)

        return self
    
    def find_cor_pair_new(self):
        """Find correspondence points pairs."""
        threshold = self.threshold
        ncc_matrix = np.copy(self.ncc_matrix)
#        matrix_array_1 = self.matrix_array_1
#        matrix_array_2 = self.matrix_array_2
        points_inds_1 = self.points_inds_1
        points_inds_2 = self.points_inds_2
        
        ncc_matrix[np.where(ncc_matrix < threshold)] = 0
        pair_length = len(points_inds_1)
        ipoints_array_1 = [(0, 0)] * pair_length 
        ipoints_array_2 = [(0, 0)] * pair_length 
        
        for i in np.arange(pair_length):
            j = np.argmax(ncc_matrix[i, :])
            ipoints_array_1[i] = points_inds_1[i]
            ipoints_array_2[i] = points_inds_2[j]
        
        self.points_pair = (ipoints_array_1, ipoints_array_2)

        return self

    @staticmethod
    def solve_homograph_matrix(*args):
        """Solve homography matrix with 4 points, non-colinear."""
        co_matrix = []
        b_array = []
        for points_pair in args[0]:
            p0, p1 = points_pair
            x0, y0 = p0
            x1, y1 = p1
            co_matrix += [[x0, y0, 1, 0, 0, 0, -x0 * x1, -y0 * x1], 
                          [0, 0, 0, x0, y0, 1, -x0 * y1, -y0 * y1]]
            b_array += [x1, y1]

        co_matrix = np.vstack(co_matrix)
        b_array = np.vstack(b_array)
        
        co_matrix_inv = np.linalg.inv(np.matmul(co_matrix.T, co_matrix))

        h_array = np.append(np.matmul(co_matrix_inv, 
                                      np.matmul(co_matrix.T, b_array)), 1)
        
        h_array = h_array.reshape((3, 3))
        logging.debug(h_array)

        return h_array

    @staticmethod
    def ransac(zip_points_pair, homo_matrix, distance):
        """Copmute distance_array given points pairs and projection matrix."""
        pair_length = len(zip_points_pair)
        distance_array = - np.ones(pair_length)
        for i in np.arange(pair_length):
            p0, p1 = zip_points_pair[i]
            pro_p0 = homo_project(homo_matrix, p0)
            temp_dis = euclidean_disance(pro_p0, p1)
            distance_array[i] = temp_dis
        
        inds = np.where(distance_array <= distance)
        new_points_pair = list(np.array(zip_points_pair)[inds])
        
        return new_points_pair

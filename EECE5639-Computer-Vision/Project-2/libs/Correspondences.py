# -*- coding: utf-8 -*-
import numpy as np

from .misc import compute_normalized_correlation


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
            ncc_row_i = compute_normalized_correlation(matrix_array_2, template)
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
        ncc_matrix = self.ncc_matrix
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
    
    @staticmethod
    def _solve_homograph_matrix(pair1, pair2, pair3, pair4):
        """Solve homography matrix with 4 points, non-colinear."""
        p10, p11 = pair1
        p20, p21 = pair2
        p30, p31 = pair3
        p40, p41 = pair4
        
        x10, y10 = p10
        x20, y20 = p20
        x30, y30 = p30
        x40, y40 = p40
        
        x11, y11 = p11
        x21, y21 = p21
        x31, y31 = p31
        x41, y41 = p41
        
        co_matrix = np.array([[x10, y10, 1, 0, 0, 0, -x10 * x11, -y10 * x11],
                              [0, 0, 0, x10, y10, 1, -x10 * y11, -y10 * y11],
                              [x20, y20, 1, 0, 0, 0, -x20 * x21, -y20 * x21],
                              [0, 0, 0, x20, y20, 1, -x20 * y21, -y20 * y21],
                              [x30, y40, 1, 0, 0, 0, -x30 * x31, -y30 * x31],
                              [0, 0, 0, x30, y30, 1, -x30 * y31, -y30 * y31],
                              [x40, y40, 1, 0, 0, 0, -x40 * x41, -y40 * x41],
                              [0, 0, 0, x40, y40, 1, -x40 * y41, -y40 * y41]])
        
        b_array = np.array([x11, y11, x21, y21, x31, y31, x41, y41])
        
        co_matrix_inv = np.linalg.inv(co_matrix)
        h_array = np.append(np.matmul(co_matrix_inv, 
                                      b_array), 1).reshape((3, 3))
    
    
    def ransac(self):
        points_pair = self.points_pair

        return self
    
        
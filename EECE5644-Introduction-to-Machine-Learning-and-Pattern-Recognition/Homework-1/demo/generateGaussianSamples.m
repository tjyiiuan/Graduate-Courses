function [data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior)
%
% Function to simulate data from k Gaussian densities 
% (1 for each class) in d dimensions.
%
% INPUTS:
% mu - k-by-1 cell with the class dependent d-dimensional mean vector
% sigma - k-by-1 cell with the class dependent d-dimensional conv matrix
% nSamples - scalar indicaitng number of samples to be generated
% prior - k-by-1 vector with class dependent mean
%
% OUTPUTS:
% data - nSamples-by-d array with the simulated data distributed along the rows
% classIndex - vector of length nSamples with the class index for each datapoint

% Generating datas and outputs.
nuclas1 = binornd(nSamples, prior(1));
data1 = mvnrnd ( mu{1}, sigma{1}, nuclas1);
data2 = mvnrnd ( mu{2}, sigma{2}, nSamples - nuclas1 );
sample = [data1, ones(nuclas1, 1); data2, 2 * ones(nSamples - nuclas1, 1)];
data = sample(:, 1:2);
classIndex = sample(:, 3);
% Plots.
figure
hold on
plot(data1(:,1), data1(:,2), 'r.')
plot(data2(:,1), data2(:,2), 'k.')
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
hold off
title('$I.I.D\ Gaussian\ Samples\ of\ Two-classes.$', 'Interpreter', 'latex')
h=legend('$Class\ 1$', '$Class\ 2$');
set(h, 'Interpreter', 'latex');
grid on
end


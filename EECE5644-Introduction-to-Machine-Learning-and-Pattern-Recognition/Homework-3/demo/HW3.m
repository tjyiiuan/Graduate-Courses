%% READ ME

% Due to my poor optimization to MATLAB code, the total run time of each
% part could be up to 60s.
% Sorry for inconvenience.

%% 1 a
clear; clc; close all
load('datasetP2Train1.mat')
Nn = length(unique(classIndex));
muEstimate = cell(1, Nn);
sigmaEstimate = cell(1, Nn);
for ii = 1:Nn
	muEstimate{ii} = mean(data(classIndex==ii, :));
    sigmaEstimate{ii} = cov(data(classIndex==ii, :));
end
%% 1 b c

% Elapsed time is 3.151058 seconds.
N = numel(0:0.01:1);
load('datasetP2Train1.mat')
SIGMA = cov(data);
trainerr = zeros(1,N);
for ii = 1: N
    trainerr(:,ii) = errate('datasetP2Train1.mat',(ii-1)/100,muEstimate, sigmaEstimate, SIGMA);
end

testerr = zeros(1,N);
for jj = 1: N
    testerr(:,jj) = errate('datasetP2Test.mat',(jj-1)/100, muEstimate, sigmaEstimate, SIGMA);
end
[apl,idxBest] = min(testerr);

figure
hold on
plot(0:0.01:1, trainerr * 100, 'r');
plot(0:0.01:1, testerr * 100, 'k');
plot([(idxBest-1)/100 (idxBest-1)/100], [0 apl * 100], 'm--' )
plot([0 (idxBest-1)/100], [apl * 100 apl * 100], 'm--' )
hold off
xlabel('$\alpha$', 'Interpreter', 'latex');
ylabel('$Error\ Rate\ \%$', 'Interpreter', 'latex');
leg = legend('$Training$', '$Testing$');
set(leg, 'Interpreter', 'latex')
grid on
title('$\textbf{Training\ and\ Testing\ Error\ Rate}.$', 'Interpreter', 'latex', 'Fontsize', 18)

%% 1 d
clear; clc
load('datasetP2Train2.mat')
Nn = length(unique(classIndex));
muEstimate = cell(1, Nn);
sigmaEstimate = cell(1, Nn);
for ii = 1:Nn
	muEstimate{ii} = mean(data(classIndex==ii, :));
    sigmaEstimate{ii} = cov(data(classIndex==ii, :));
end
%% 1 e f

% Elapsed time is 30.071774 seconds.
N = numel(0:0.01:1);
load('datasetP2Train2.mat')
SIGMA = cov(data);
trainerr = zeros(1,N);
for ii = 1: N
    trainerr(:,ii) = errate('datasetP2Train2.mat',(ii-1)/100,muEstimate, sigmaEstimate, SIGMA);
end

testerr = zeros(1,N);
for jj = 1: N
    testerr(:,jj) = errate('datasetP2Test.mat',(jj-1)/100, muEstimate, sigmaEstimate, SIGMA);
end

figure
hold on
plot(0:0.01:1, trainerr * 100, 'r');
plot(0:0.01:1, testerr * 100, 'k');
hold off
xlabel('$\alpha$', 'Interpreter', 'latex');
ylabel('$Error\ Rate\ \%$', 'Interpreter', 'latex');
leg = legend('$Training$', '$Testing$');
set(leg, 'Interpreter', 'latex')
grid on
title('$\textbf{Training\ and\ Testing\ Error\ Rate}.$', 'Interpreter', 'latex', 'Fontsize', 18)

%% 2 k-means dataset 1-4
clear;clc;close all
% Elapsed time is 0.900271 seconds.
fileNames = {'dataset1.mat','dataset2.mat','dataset3.mat','dataset4.mat'};
for jj = 1 : 4
    
load(fileNames{jj})
params.criteria = 1e-8;
params.num = numel(unique(labels));
params.numofrun = 15;
marker = ['x', '+', '*'];
[estmean, label, MSE] = kmeanscluster(data, params); 

figure
%------------------------------------------
subplot(1, 3, 1)
for ii = 1 : params.num 
    hold on
    plot(data(labels == ii - 1, 1), data(labels == ii - 1, 2), marker(ii), 'MarkerSize', 8);        
    hold off
end
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['Initial clusters of ', fileNames{jj}], 'Fontsize', 14);
%------------------------------------------
subplot(1, 3, 2)
for ii = 1 : params.num
    hold on
    plot(data(label == ii, 1), data(label == ii,2), marker(ii));        
    plot(estmean(ii, 1), estmean(ii, 2), 'ko', 'MarkerSize', 8, 'LineWidth', 2);
    hold off
end
hold off
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['K-means clusters of ', fileNames{jj}], 'Fontsize', 14)
%------------------------------------------
subplot(1, 3, 3)
plot(1:params.numofrun, MSE(:, 1), 'b-')
axis([0 params.numofrun ceil(10*min(MSE(:, 1)))/10-0.21  ceil(10 * max(MSE(:, 1)))/10 + 0.21 ])
grid on
xlabel('$Number\ of\ Run$', 'Interpreter', 'latex')
ylabel('$Mean\ Square\ Error$', 'Interpreter', 'latex')
title('MSE versus Number of Samples', 'Fontsize', 14);
%------------------------------------------
end


%% 2 k-means dataset 5

load('dataset5.mat')
nSamples = size(data,1);
MSE = zeros(1, 14);

for ii = 1:14
    params.num = ii+1;
    [IDX, C, SUMD]= kmeans(data, ii); 
    MSE(ii) = sum(SUMD);
end

figure
plot(2:15, MSE/nSamples, 'ko-')
axis([ 0 15 0 200])
grid on
xlabel('$K$', 'Interpreter', 'latex')
ylabel('$Mean\ Square\ Error$', 'Interpreter', 'latex')
title('MSE of dataset5.mat with k-means', 'Fontsize', 14);

    
params.criteria = 1e-8;
params.num = 4;
params.numofrun = 30;
marker = ['o', 'x', '+', '.', '-'];
[estmean, label, MSE] = kmeanscluster(data, params); 

figure
%------------------------------------------
subplot(1, 3, 1)
plot(data(:, 1), data(:, 2), '+', 'MarkerSize', 8);        
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title('Initial clusters of dataset5.mat', 'Fontsize', 14);
%------------------------------------------
subplot(1, 3, 2)
for ii = 1 : params.num
    hold on
    plot(data(label == ii, 1), data(label == ii, 2), marker(ii));        
    plot(estmean(ii, 1), estmean(ii, 2), 'k*', 'MarkerSize', 8, 'LineWidth', 2);
    hold off
end
hold off
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title('K-means clusters of dataset5.mat', 'Fontsize', 14)
%------------------------------------------
subplot(1, 3, 3)
plot(1:params.numofrun, MSE(:, 1), 'b-')
axis([0 params.numofrun ceil(10*min(MSE(:, 1)))/10-0.21  ceil(10 * max(MSE(:, 1)))/10 + 0.21 ])
grid on
xlabel('$Number\ of\ Run$', 'Interpreter', 'latex')
ylabel('$Mean\ Square\ Error$', 'Interpreter', 'latex')
title('MSE versus Number of Samples', 'Fontsize', 14);
%------------------------------------------
% Elapsed time is 1.841690 seconds.
%% 2  




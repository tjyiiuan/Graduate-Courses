%% Problem 1 

clear; clc; close all
fileNames = {'dataset1.mat','dataset2.mat','dataset3.mat','dataset4.mat'};

for jj = 1 : 4
    
load(fileNames{jj})
[nSamples, ~] = size(data);
params.criteria = 1e-8;
params.num = numel(unique(labels));
params.numofrun = 200;
marker = ['x', '+', '*'];
[estlabel, model, llh] = mixGaussEm(data', params);
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
axis([min(data(:, 1))-0.1 max(data(:, 1))+0.1 min(data(:, 2))-0.1 max(data(:, 2))+0.1])
title(['Initial clusters on ', fileNames{jj}], 'Fontsize', 14);
%------------------------------------------
subplot(1, 3, 2)
for ii = 1 : params.num
    hold on
    plot(data(estlabel == ii, 1), data(estlabel == ii,2), marker(ii));
    [x, y] = meshgrid(linspace(min(data(:)), max(data(:)), 80)',linspace(min(data(:)), max(data(:)), 80)'); 
    X = [x(:) y(:)]; 
    estmu = model.mu([1 2] , ii)';
    estcov = model.Sigma(:, :, ii);
    if jj == 4
        estcov = cov(data(estlabel == ii, [1 2]));
    end
    pdf = mvnpdf(X, estmu, estcov);
    contour(x, y, reshape(pdf, 80, 80), 4, 'k');
    plot(estmu(1), estmu(2), 'ko', 'MarkerSize', 8, 'LineWidth', 2)
    axis([min(data(:, 1))-0.1 max(data(:, 1))+0.1 min(data(:, 2))-0.1 max(data(:, 2))+0.1])
    hold off
end
hold off
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['Clustering results with EM-GMM on ', fileNames{jj}], 'Fontsize', 14)
%------------------------------------------
subplot(1, 3, 3)
plot(llh)
grid on
xlabel('$Number\ of\ Run$', 'Interpreter', 'latex')
ylabel('$Log-likelihood$', 'Interpreter', 'latex')
title('Log-likelihood versus iteration number', 'Fontsize', 14);
end

%% Problem 2 
clear; clc; close all

fileNames = {'dataset1.mat','dataset2.mat','dataset3.mat','dataset4.mat', 'dataset5.mat'};

for jj = 1 : 5
    
load(fileNames{jj})
params.criteria = 1e-8;
params.maxnum = 10;
params.numofrun = 200;
marker = ['x', '+', '*', '.', 's', 'd', '^', 'v'];
if jj == 5
    labels = zeros(size(data,1), 1);
end
[estlabel, model, llh, costVsComplexity, llha] = mixGaussEm(data', params);
figure
%------------------------------------------
subplot(2,3,[1 4])
for ii = 1 : numel(unique(labels)) 
    hold on
    plot(data(labels == ii - 1, 1), data(labels == ii - 1, 2), marker(ii), 'MarkerSize', 8);        
    hold off
end
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
axis([min(data(:, 1))-0.1 max(data(:, 1))+0.1 min(data(:, 2))-0.1 max(data(:, 2))+0.1])
title(['Initial clusters on ', fileNames{jj}], 'Fontsize', 14);
%------------------------------------------
subplot(2,3,[2 5])
[~, K] = max(costVsComplexity);
for ii = 1 : K
    hold on
    plot(data(estlabel == ii, 1), data(estlabel == ii, 2), marker(ii));
    [x, y] = meshgrid(linspace(min(data(:)), max(data(:)), 100)',linspace(min(data(:)), max(data(:)), 100)'); 
    X = [x(:) y(:)]; 
    estmu = model.mu([1 2] , ii)';
    estcov = model.Sigma(:, :, ii);
    if jj == 4
        estcov = cov(data(estlabel == ii, [1 2]));
    end
    pdf = mvnpdf(X, estmu, estcov);
    contour(x, y, reshape(pdf, 100, 100), 4, 'k');
    plot(estmu(1), estmu(2), 'ko', 'MarkerSize', 8, 'LineWidth', 2)
    axis([min(data(:, 1))-0.1 max(data(:, 1))+0.1 min(data(:, 2))-0.1 max(data(:, 2))+0.1])
    hold off
end
hold off
grid on
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['Clustering results with EM-GMM on ', fileNames{jj}], 'Fontsize', 14)
%------------------------------------------
subplot(2, 3, 3)
plot(llh)
grid on
xlabel('$Number\ of\ Run$', 'Interpreter', 'latex')
ylabel('$Log-likelihood$', 'Interpreter', 'latex')
title('Log-likelihood versus iteration number', 'Fontsize', 14);
%------------------------------------------
subplot(2, 3, 6)
plot(costVsComplexity)
grid on
xlabel('$Number\ of\ Clusters$', 'Interpreter', 'latex')
ylabel('$BIC\ criteria$', 'Interpreter', 'latex')
title('BIC criteria versus number of clusters', 'Fontsize', 14);

end


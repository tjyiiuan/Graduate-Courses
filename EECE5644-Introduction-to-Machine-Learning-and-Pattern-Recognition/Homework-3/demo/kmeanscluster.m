
function [estMeans, estLabels, MSE] = kmeanscluster(data, parameters)   
[nSamples, dim] = size(data);
N = parameters.num;
% Initialize means for random runs
estMeans = zeros(N, dim);
for ii = 1 : N
estMeans(ii, :) = min(data) + (max(data) - min(data)) * rand;
end
stopTolerance = false;
MSE = [];
numofrun = 0;
while ~stopTolerance
    mserror = 0;
    numofrun = numofrun + 1;
  if numofrun <  (parameters.numofrun+1)
    preestmean = estMeans;            % Last estimated mean
    tmp = cell(N);
    for ii = 1 : N
        tmp{ii} = data - estMeans(ii,:);
    end
    quan = zeros(nSamples, N);
    for ii = 1 : nSamples        
        c = zeros(1, N);
        for jj = 1 : N
            c(jj) = norm(tmp{jj}(ii,:));
        end
        [msetmp, index] = min(c);
        mserror = mserror + msetmp;
        quan(ii, index) = norm(tmp{index}(ii,:));      
    end
    
    for ii = 1 : N          
        for jj = 1 : dim
            estMeans(ii,jj) = sum(quan(:,ii) .* data(:,jj)) / sum(quan(:,ii));
        end
    end
  else
    preestmean = estMeans;            % Last estimated mean
    tmp = cell(N);
    for ii = 1 : N
        tmp{ii} = data - estMeans(ii,:);
    end
    quan = zeros(nSamples, N);
    for ii = 1 : nSamples        
        c = zeros(1, N);
        for jj = 1 : N
            c(jj) = norm(tmp{jj}(ii,:));
        end
        [~, index] = min(c);
        quan(ii, index) = norm(tmp{index}(ii,:));      
    end
    for ii = 1 : N
        for jj = 1 : dim
            estMeans(ii,jj) = sum(quan(:,ii) .* data(:,jj)) / sum(quan(:,ii));
        end
    end
    
  end
   MSE = [MSE; mserror/nSamples];
   stopTolerance = norm(preestmean - estMeans) < parameters.criteria;
end

estLabels = zeros(nSamples, 1);
for ii = 1 : nSamples
    tmp = zeros(1, N);
    for jj=1:N
        tmp(jj) = norm(data(ii,:) - estMeans(jj,:));
    end
    [~, index] = min(tmp);
    estLabels(ii) = index;
end
MSE = MSE(1:parameters.numofrun, :);
end
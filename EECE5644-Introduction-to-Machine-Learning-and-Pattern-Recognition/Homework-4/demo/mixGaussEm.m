function [label, model, llh, costVsComplexity,llha] = mixGaussEm(X, params)
%%
% If number of clusters is specified, run once
if ~isfield(params, 'maxnum')
    [label, model, llh] = runmixGaussEm(X, params);
    costVsComplexity = [];
% Run with cluster num from 1 to max
else
    label = cell(params.maxnum, 1);
    model = cell(params.maxnum, 1);
    llh = cell(params.maxnum, 1);
    costVsComplexity = zeros(1, params.maxnum);
    % Run EM-GMM
    for kk = 1 : params.maxnum   
        params.num = kk; 
        [label{kk}, model{kk}, llh{kk}] = runmixGaussEm(X, params);   
        % Compute BIC = LogL - 0.5 * p_k * ln(N)
        pk = (kk-1) + kk * size(X,1) + kk * size(X,1) * ( size(X,1) + 1) /2;
        costVsComplexity(kk) = llh{kk}(end) - pk / 2 * log(size(X, 2));
    end
    
    % Choose K with with max (Log-likelihood - BIC)
    [~, best] = max(costVsComplexity);
    label = label{best};
    model = model{best};
    llha = llh;
    llh = llh{best};
end


function [label, model, llh] = runmixGaussEm(X, params)
% Perform EM algorithm for fitting the Gaussian mixture model.
% Input: 
%   X: d by n data matrix
%   parmas: parametes
% Output:
%   label: 1 x n cluster label
%   model: mu, covariacne, and prior
%   llh: loglikelihood
[nSamples, ~] = size(X);
tol = params.criteria;
maxiter = params.numofrun;
llh = -inf(1, maxiter);
R = initialization(X, params.num);
for iter = 2 : maxiter
    [~,label(1,:)] = max(R,[],2);
    R = R(:,unique(label));   % remove empty clusters
    model = maximization(X,R);
    [R, llh(iter)] = expectation(X, model);
    if abs(llh(iter)-llh(iter-1)) < tol * abs(llh(iter))
        break; 
    end
end
llh = llh(2:iter) * nSamples;

function R = initialization(X, init)
n = size(X,2);
if isstruct(init)  % init with a model
    R  = expectation(X,init);
elseif numel(init) == 1  % random init k
    k = init;
    label = ceil(k*rand(1,n));
    R = full(sparse(1:n,label,1,n,k,n));
elseif all(size(init)==[1,n])  % init with labels
    label = init;
    k = max(label);
    R = full(sparse(1:n,label,1,n,k,n));
else
    error('ERROR: init is not valid.');
end

function [R, llh] = expectation(X, model)
mu = model.mu;
Sigma = model.Sigma;
w = model.w;

n = size(X, 2);
k = size(mu,2);
R = zeros(n,k);
for i = 1:k
    R(:,i) = loggausspdf(X, mu(:,i),Sigma(:,:,i));
end
R = bsxfun(@plus,R,log(w));
T = logsumexp(R,2);
llh = sum(T)/size(X, 1); % loglikelihood
R = exp(bsxfun(@minus,R,T));

function model = maximization(X, R)
[d,n] = size(X);
k = size(R,2);
nk = sum(R,1);
w = nk/n;
mu = bsxfun(@times, X*R, 1./nk);

Sigma = zeros(d,d,k);
r = sqrt(R);
for i = 1:k
    Xo = bsxfun(@minus,X,mu(:,i));
    Xo = bsxfun(@times,Xo,r(:,i)');
    Sigma(:,:,i) = Xo * Xo' / nk(i) + eye(d) * (1e-6);
end

model.mu = mu;
model.Sigma = Sigma;
model.w = w;

function y = loggausspdf(X, mu, Sigma)
d = size(X,1);
X = bsxfun(@minus,X,mu);
[U,p]= chol(Sigma);
if p ~= 0
    error('ERROR: Sigma is not PD.');
end
Q = U'\X;
q = dot(Q,Q,1);  % quadratic term (M distance)
c = d*log(2*pi)+2*sum(log(diag(U)));   % normalization constant
y = -(c+q)/2;


function s = logsumexp(X, dim)
% Compute log(sum(exp(X),dim)) while avoiding numerical underflow.
% By default dim = 1 (columns).
if nargin == 1
    % Determine which dimension sum will use
    dim = find(size(X)~=1,1);
    if isempty(dim)
        dim = 1;
    end
end

% subtract the largest in each dim
y = max(X,[],dim);
s = y + log(sum(exp(bsxfun(@minus,X,y)),dim));  
i = isinf(y);
if any(i(:))
    s(i) = y(i);
end
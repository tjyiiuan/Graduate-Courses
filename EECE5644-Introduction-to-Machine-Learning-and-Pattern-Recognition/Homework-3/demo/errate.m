
function err = errate(filename, alpha, mu, sigma, SIGMA)
sl = load(filename);
names = fieldnames(sl);
dat = sl.(names{1});
index = sl.(names{2});
nSamples = size(dat, 1);
nclass = length(unique(index));
sigmasrk = cell(1, nclass);
prior = ones(1, nclass) / nclass;

for ii = 1 : nclass
    nii = nnz(index == ii);
    sigmasrk{ii} = ((1 - alpha) * nii * sigma{ii} + alpha * nSamples * SIGMA)...
        /((1-alpha) * nii + alpha * nSamples);
end
clsindex = zeros(nSamples, 1);
for jj = 1: nSamples
    gjj = discric(dat(jj,:)', mu, sigmasrk, nclass, prior);
    [~, n] = max(gjj);
    clsindex(jj) = n;
end
err = sum(clsindex ~= index)/nSamples;
end

function g = discric(x, mu, sigma, n, prior)
g = zeros(1, n);    
for kk = 1 : n
    g(kk) = -(x - mu{kk}')' / sigma{kk} * (x - mu{kk}')/2 -...
        log(det(sigma{kk}))/2 + log( prior(kk) );
end 
end

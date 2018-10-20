function ga = score(x, mu ,sigma, prior)
ga = zeros(1, 2);
if ~iscell(sigma)
    for ii = 1 : 2
        ga(1, ii) = -0.5 * ((x - mu{ii}')') * inv(sigma) * (x - mu{ii}') - ...
            log(2 * pi) - (0.5 * log(det(sigma))) + log(prior(ii));
    end
else
    for ii = 1 : 2
        ga(1, ii) = -0.5 * ((x - mu{ii}')') * inv(sigma{ii}) * (x - mu{ii}') - ...
            log(2 * pi) - (0.5 * log(det(sigma{ii}))) + log(prior(ii));
end
end
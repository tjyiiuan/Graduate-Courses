
function g = discric(x, mu, sigma, nSamples, prior, cas)
g = zeros(nSamples ,2);
if cas == 1
    for ii = 1 : 2
        w = mu{ii} / det(sigma);
        w0 = - mu{ii} * mu{ii}' / (2 * det(sigma)) + log (prior(ii));
        g(:, ii) = (w * x' + w0)';
    end
elseif cas == 2
    for ii = 1 : 2
        w = sigma \mu{ii}';
        w0 = - mu{ii}* (sigma\ mu{ii}') /2 + log ( prior(ii) );
        g(:, ii) = (w' * x' + w0)';
    end
elseif cas == 3
    for ii = 1 : 2
        w2 = sigma{ii} \mu{ii}';
        w0 = - mu{ii}* (sigma{ii}\ mu{ii}') /2 - log( det (sigma{ii}) )/2 + log ( prior(ii) );
        for jj= 1 : nSamples
            w1 = - inv (sigma{ii})/2;
            g(jj , ii) = (x(jj,:) * w1 * x(jj, :)' + x(jj, :) * w2 + w0);
        end
    end
end
end
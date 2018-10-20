%% 1.1

clear;clc;close all
x=-2:0.00001:5;
y=2*exp(abs(x-2)/2-abs(x-1));
hold on
plot(x,y,'k')
plot(2,2*exp(-1),'ro')
plot(1,2*exp(0.5),'ro')
hold off
title('$Likelihood\ Ratio\ as\ a\ function\ of\ x$','Interpreter','latex')
xlabel('$x$','Interpreter','latex')
ylabel('$Likelihood\ Ratio\ p(x|w_1)/p(x|w_2)$','Interpreter','latex')
grid on
%% 1.2 (b)

x = -10 : 0.0001 : 8;
y1 = exp (-0.5 * x.^2) / sqrt (2 * pi);
y2 = exp (-0.25 * (x-2).^2) / (2 * sqrt (pi) );
k1 = y1./ (y1 + y2);
k2 = y2./ (y1 + y2);
mu = 2;
sigma = sqrt (2);
delt = mu^2 + ( sigma^2 - 1 ) * ( mu^2 + 2 * sigma^2 * log (sigma) );
xopt1 = ( - mu - sqrt ( delt ) ) / ( sigma^2 - 1 );
xopt2 = ( - mu + sqrt ( delt ) ) / ( sigma^2 - 1 );

figure
subplot(2, 1, 1)
hold on
plot (x, y1, 'r', x, y2, 'k');
plot([xopt1 xopt1], [-1 2], 'k-.', [xopt2 xopt2], [-1 2], 'k-.')
hold off
axis ( [-6 8 -0.05 0.5] );
title ('$Class\ Conditional\ PDFs$', 'Interpreter', 'latex')
xlabel ('$x$', 'Interpreter', 'latex')
ylabel ('$pdf$', 'Interpreter', 'latex')
h = legend('$P(x|w_1)$', '$P(x|w_2)$', 'Boundary');
set (h, 'Interpreter', 'latex')
grid on
subplot(2, 1, 2)
hold on
plot (x, k1, 'r', x, k2, 'k')
plot([xopt1 xopt1], [-1 2], 'k-.', [xopt2 xopt2], [-1 2], 'k-.')
hold off
axis( [-10 6 -0.05 1.05] );
title('$Posterior\ Proabilities\ with\ Optimal\ Decision\ Regions$', 'Interpreter', 'latex')
xlabel ('$x$', 'Interpreter', 'latex')
ylabel ('$Posterior\ Proabilities$', 'Interpreter', 'latex')
h = legend ('$P(w_1|x)$', '$P(w_2|x)$', 'Boundary');
set (h, 'Interpreter', 'latex');
grid on
%% 1.2 (c)

mu = 2;
sigma = sqrt (2);
delta = mu^2 + ( sigma^2 - 1 ) * ( mu^2 + 2 * sigma^2 * log (sigma) );
x1 = ( - mu - sqrt ( delta ) ) / ( sigma^2 - 1 )
x2 = ( - mu + sqrt ( delta ) ) / ( sigma^2 - 1 )
pe = ( qfunc (-x1) + qfunc (x2) + qfunc ( (x1 - 2) / sigma ) -...
    qfunc ( ( x2 - 2 ) / sigma ) ) /2
%% 1.2 (d)

x=-6:0.0001:6;
sigma=10;
y1=exp(-0.5*x.^2)/sqrt(2*pi);
y2=exp(-0.5*x.^2 /sigma)/(sqrt(2*pi*sigma));
z1 =y1./(y1+y2);
z2 =y2./(y1+y2);

figure
plot(x,z1,'r',x,z2,'k');
axis([-6 6 -0.05 1.05])
title('$Posterior\ Probability\ with\ \sigma^2=10$','Interpreter','latex')
xlabel('$x$','Interpreter','latex')
ylabel('$Posterior Probability$','Interpreter','latex')
h = legend('$P(w_1|x)$','$P(w_2|x)$');
set(h,'Interpreter','latex')
grid on
%% 1.3 & 1.4 (a)

mu = {[0,0];[-3 -3]};
sigma = {eye(2),eye(2)};
nSamples = 400;
prior = [0.5, 0.5];
[data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior);
% Case 1 classifier
g = discric(data, mu, sigma{1}, nSamples, prior, 1);
clas = 2 * ones(400,1);
clas(g(:,1) >= g(:,2))=1;
accu = 1 - sum(abs(clas-classIndex))/ nSamples;
clsdata = sortrows([data, clas] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
db = ezplot(@(x, y) diff(score([x;y], mu, sigma, prior)), [-15 10 -14 16]);
set(db, 'color', 'm','LineStyle','--');
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ with\ Accuracy=$',num2str(accu)], 'Interpreter', 'latex')
axis([-7 3 -7 3])
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
%% 1.3 & 1.4 (b)

mu = {[0,0];[-3 -3]};
sigma = {[3 1; 1 0.8], [3 1; 1 0.8]};
nSamples = 400;
prior = [0.5, 0.5];
[data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior);
% Case 2 classifier
g1 = discric(data, mu, eye(2), nSamples, prior, 1);
clas1 = 2 * ones(400,1);
clas1(g1(:,1)>g1(:,2)) = 1;
accu1 = 1 - sum(abs(clas1-classIndex))/ nSamples;
clsdata = sortrows([data, clas1] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure 
subplot(1, 2, 1)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
db = ezplot(@(x, y) diff(score([x;y], mu, eye(2), prior)), [-15 10 -14 10]);
set(db, 'color', 'm','LineStyle','--');
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ I\ \ Accuracy=$',num2str(accu1)], 'Interpreter', 'latex')
axis([-10 6 -6 3])
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
% Case 2 classifier
g2 = discric(data, mu, sigma{1}, nSamples, prior, 2);
clas2 = 2 * ones(400,1);
clas2(g2(:,1)>g2(:,2))=1;
accu2 = 1 - sum(abs(clas2-classIndex))/ nSamples;
clsdata = sortrows([data, clas2] , 3);
q = 400 - sum(clsdata(:,3)-1);
subplot(1, 2, 2)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
dba = ezplot(@(x, y) diff(score([x;y], mu, sigma, prior)), [-15 10 -14 10]);
set(dba, 'color', 'm','LineStyle','--');
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ II\ \ Accuracy=$',num2str(accu2)], 'Interpreter', 'latex')
axis([-10 6 -6 3])
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
%% 1.3 & 1.4 (c)

mu = {[0,0];[2 2]};
sigma = {[2 0.5; 0.5 1], [2 -1.9; -1.9 5]};
nSamples = 400;
prior = [0.5, 0.5];
[data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior);
% Case 1 classifier
g1 = discric(data, mu, eye(2), nSamples, prior, 1);
clas1 = 2 * ones(400,1);
clas1(g1(:,1)>g1(:,2))=1;
accu1 = 1 - sum(abs(clas1 - classIndex))/ nSamples;
clsdata = sortrows([data, clas1] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure
subplot(1, 2, 1)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
dba = ezplot(@(x, y) diff(score([x;y], mu, eye(2), prior)), [-15 10 -14 10]);
set(dba, 'color', 'm','LineStyle','--');
axis([-5 8 -6 10])
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ I\ \ Accuracy=$',num2str(accu1)], 'Interpreter', 'latex')
h = legend('$Class\ 1$','$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
% Case 2 classifier
g2 = discric(data, mu, (sigma{1}+sigma{2})/2, nSamples, prior, 2);
clas2 = 2 * ones(400,1);
clas2(g2(:,1)>g2(:,2))=1;
accu2 = 1 - sum(abs(clas2 - classIndex))/ nSamples;
subplot(1, 2, 2)
hold on
clsdata = sortrows([data, clas2] , 3);
q = 400 - sum(clsdata(:,3)-1);
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
dbb = ezplot(@(x, y) diff(score([x;y], mu, (sigma{1} + sigma{2})/2, prior)), [-15 10 -14 10]);
set(dbb, 'color', 'm','LineStyle','--');
axis([-5 8 -6 10])
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ II\ \ Accuracy=$',num2str(accu2)], 'Interpreter', 'latex')
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
% Case 3 classifier
g3 = discric(data, mu, sigma, nSamples, prior, 3);
clas3 = 2 * ones(400,1);
clas3(g3(:,1)>g3(:,2))=1;
accu3 = 1 - sum(abs(clas3 - classIndex))/ nSamples;
clsdata = sortrows([data, clas3] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
db = ezplot(@(x, y) diff(score([x;y], mu ,sigma, prior)), [-15 10 -14 10]);
set(db, 'color', 'm','LineStyle','--');
axis([-5 8 -6 10])
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ with\ Accuracy=$',num2str(accu3)], 'Interpreter', 'latex')
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
%% 1.3 & 1.4 (d)

mu = {[0,0];[-3 -3]};
sigma = {eye(2),eye(2)};
nSamples = 400;
prior = [0.05, 0.95];
[data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior);
% Case 1 classifier
g = discric(data, mu, sigma{1}, nSamples, prior, 1);
clas = 2 * ones(400,1);
clas(g(:,1) >= g(:,2))=1;
accu = 1 - sum(abs(clas-classIndex))/ nSamples;
clsdata = sortrows([data, clas] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
db = ezplot(@(x, y) diff(score([x;y], mu, sigma, prior)), [-15 10 -14 16]);
set(db, 'color', 'm','LineStyle','--');
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ with\ Accuracy=$',num2str(accu)], 'Interpreter', 'latex')
axis([-7 3 -7 3])
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on

%% 1.3 & 1.4 (e)

mu = {[0,0];[-3 -3]};
sigma = {[3 1; 1 0.8], [3 1; 1 0.8]};
nSamples = 400;
prior = [0.05, 0.95];
[data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior);
% Case 1 classifier
g1 = discric(data, mu, eye(2), nSamples, prior, 1);
clas1 = 2 * ones(400,1);
clas1(g1(:,1)>g1(:,2)) = 1;
accu1 = 1 - sum(abs(clas1-classIndex))/ nSamples;
clsdata = sortrows([data, clas1] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure 
subplot(1, 2, 1)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
db = ezplot(@(x, y) diff(score([x;y], mu, eye(2), prior)), [-15 10 -14 10]);
set(db, 'color', 'm','LineStyle','--');
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ I\ \ Accuracy=$',num2str(accu1)], 'Interpreter', 'latex')
axis([-10 6 -6 3])
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
% Case 2 classifier
g2 = discric(data, mu, sigma{1}, nSamples, prior, 2);
clas2 = 2 * ones(400,1);
clas2(g2(:,1)>g2(:,2))=1;
accu2 = 1 - sum(abs(clas2-classIndex))/ nSamples;
clsdata = sortrows([data, clas2] , 3);
q = 400 - sum(clsdata(:,3)-1);
subplot(1, 2, 2)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
dba = ezplot(@(x, y) diff(score([x;y], mu, sigma, prior)), [-15 10 -14 10]);
set(dba, 'color', 'm','LineStyle','--');
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ II\ \ Accuracy=$',num2str(accu2)], 'Interpreter', 'latex')
axis([-10 6 -6 3])
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
%% 1.3 & 1.4 (f)

mu = {[0,0];[2 2]};
sigma = {[2 0.5; 0.5 1], [2 -1.9; -1.9 5]};
nSamples = 400;
prior = [0.05, 0.95];
[data, classIndex] = generateGaussianSamples(mu, sigma, nSamples, prior);
% Case 1 classifier
g1 = discric(data, mu, eye(2), nSamples, prior, 1);
clas1 = 2 * ones(400,1);
clas1(g1(:,1)>g1(:,2))=1;
accu1 = 1 - sum(abs(clas1 - classIndex))/ nSamples;
clsdata = sortrows([data, clas1] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure
subplot(1, 2, 1)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
dba = ezplot(@(x, y) diff(score([x;y], mu, eye(2), prior)), [-15 10 -14 10]);
set(dba, 'color', 'm','LineStyle','--');
axis([-5 8 -6 10])
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ I\ \ Accuracy=$',num2str(accu1)], 'Interpreter', 'latex')
h = legend('$Class\ 1$','$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
% Case 2 classifier
g2 = discric(data, mu, (sigma{1}+sigma{2})/2, nSamples, prior, 2);
clas2 = 2 * ones(400,1);
clas2(g2(:,1)>g2(:,2))=1;
accu2 = 1 - sum(abs(clas2 - classIndex))/ nSamples;
clsdata = sortrows([data, clas2] , 3);
q = 400 - sum(clsdata(:,3)-1);
subplot(1, 2, 2)
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
dbb = ezplot(@(x, y) diff(score([x;y], mu, (sigma{1} + sigma{2})/2, prior)), [-15 10 -14 10]);
set(dbb, 'color', 'm','LineStyle','--');
axis([-5 8 -6 10])
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ of\ Case\ II\ \ Accuracy=$',num2str(accu2)], 'Interpreter', 'latex')
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on
% Case 3 classifier
g3 = discric(data, mu, sigma, nSamples, prior, 3);
clas3 = 2 * ones(400,1);
clas3(g3(:,1)>g3(:,2))=1;
accu3 = 1 - sum(abs(clas3 - classIndex))/ nSamples;
clsdata = sortrows([data, clas3] , 3);
q = 400 - sum(clsdata(:,3)-1);
figure
hold on
plot( clsdata(1:q, 1), clsdata(1:q, 2), 'g.')
plot( clsdata(q + 1:400, 1), clsdata(q + 1:400, 2), 'b.')
db = ezplot(@(x, y) diff(score([x;y], mu ,sigma, prior)), [-15 10 -14 10]);
set(db, 'color', 'm','LineStyle','--');
axis([-5 8 -6 10])
hold off
xlabel('$x_1$', 'Interpreter', 'latex')
ylabel('$x_2$', 'Interpreter', 'latex')
title(['$Classification\ Result\ with\ Accuracy=$',num2str(accu3)], 'Interpreter', 'latex')
h = legend('$Class\ 1$', '$Class\ 2$','Decision\ Boundary');
set(h, 'Interpreter', 'latex');
grid on

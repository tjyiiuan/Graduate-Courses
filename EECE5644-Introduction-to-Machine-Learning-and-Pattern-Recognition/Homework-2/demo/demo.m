%% 3.1 (a)
clear;clc;close all

x = 0:0.01:5;
figure
subplot(2, 1, 1)
plot(x, exp(-x),'k')
grid on
xlabel('$x$', 'Interpreter', 'latex')
ylabel('$P(x|\theta)$', 'Interpreter', 'latex')
title('$P(x|\theta)\ versus\ x\ for\ \theta=1$', 'Interpreter', 'latex')
subplot(2, 1, 2)
plot(x, x .* exp(-2*x),'k')
grid on
xlabel('$\theta$', 'Interpreter', 'latex')
ylabel('$P(x|\theta)$', 'Interpreter', 'latex')
title('$P(x|\theta)\ versus\ \theta\ for\ x=2$', 'Interpreter', 'latex')


%% 3.17 (c)

x = 0:0.01:1;
figure
plot(x, 2*(1-x),'k', x, 2*x, 'r')
h = legend('$s_1=0$', '$s_1=1$');
set(h, 'Interpreter', 'latex')
grid on
xlabel('$\theta$', 'Interpreter', 'latex')
ylabel('$P(\theta|D)$', 'Interpreter', 'latex')
title('$P(\theta|D)\ versus\ \theta\ for\ different\ s_1\ with\ d=1,\ n=1$', 'Interpreter', 'latex', 'fontsize', 16)




% Darrick Ross
% REDACTED
% Final Project - Matlab

function problem6
% Golomb Sequence

% Using recursion, write a function/method/procedure in your language that
% computes the Golomb number, which is defined as :
%     G(n) = 1 + G(n - G( G( n - 1)))
% The base case of this recursion is G(1) = 1
% How long does your implementation take to return the value G(50)? Include
% this in your program so the timing is automatically printed when your
% program is run.

%==========================================================================
%How to use:
%   Follow the prompt, the user will be asked to select a starting n value.
%   the program will then start timing, and calculate how long it took
%   to evaluate the Golomb of n. Printing the time elapsed.
%==========================================================================

% Clearing workspace before starting the program.
clear;  %clear any variables
clc;    %clear output

disp("Golmb Sequence:");
startingValue = input('Enter the starting n value, n: ');
disp("Starting a stop watch...");
tic

answer = golomb(startingValue);

toc

fprintf('The answer is: %.0f\n',answer);
end

function output = golomb(input)
if input == 1
    output = 1;
else
    output = 1 + golomb(input - golomb(golomb(input - 1)));
end
end

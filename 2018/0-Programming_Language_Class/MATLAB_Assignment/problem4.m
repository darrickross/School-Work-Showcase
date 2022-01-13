% Darrick Ross
% REDACTED
% Final Project - Matlab

function problem4
%Generating Random Numbers

% For this problem, you must create a program that when run, outputs a
% random number between zero and one, once every second. This should
% continue forever, until a user hits any key, at which point the
% generation of random number should stop immediately, and the following
% two statistics should be printed:
%   -The total number of random numbers generated
%   -The average value of the numbers generated

%==========================================================================
%How to use:
%	When ran this program will print a random number, wait one second. Then
%	repeat. This will continue until the user presses a key (any key that
%	sends a character to the terminal, shift and ctrl are an example of
%	keys that wont stop the program from generating random numbers). After
%	this happens the program will display the number of random numbers that
%	were created during this time, along with the average of those numbers.
%==========================================================================


% Clearing workspace before starting the program.
clear;  %clear any variables
clc;    %clear output

command = "/bin/bash -c 'read -t 1 -n 1 $VAR; echo $VAR'";
numberOfRandomNumbers = 0;
sumOfRandomNumbers = 0;

while (true)
    currentNumber = rand;
    fprintf("Random Number: %f\n",currentNumber);
    numberOfRandomNumbers = numberOfRandomNumbers + 1;
    sumOfRandomNumbers = sumOfRandomNumbers + currentNumber;
    [~, output] = system(command);
    if (size(output,2) > 1)
        break;
    end
end
average = sumOfRandomNumbers / numberOfRandomNumbers;
fprintf("The program made %.0f random numbers\nThe average is %f\n",numberOfRandomNumbers,average);
end

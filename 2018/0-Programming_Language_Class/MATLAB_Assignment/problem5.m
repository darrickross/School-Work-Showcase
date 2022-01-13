% Darrick Ross
% REDACTED
% Final Project - Matlab

function problem5
% Statistics about a Matrix

% Write a function/method/procedure that takes in a single parameter, that
% is a matrix, and prints the row averages and the column averages. Make
% sure you specify how the matrix is passed as a parameter in your language
% as part of your README.

%==========================================================================
%How to use:
%  To make it easy I have made a function that will help you build a
%  matrix, then pass it to the required function. You will be asked to
%  enter the amount of rows and columns you would like for the matrix. Then
%  a random matrix will be created for you of the given size. You will then
%  be given the chance to change the contents of the matrix or keep it the
%  same. The info about the average of both columns and rows will be
%  displayed shortly after.
%==========================================================================

% Clearing workspace before starting the program.
clear;  %clear any variables
clc;    %clear output

colSize = input('How many columns in the matrix would you like?: ');
rowSize = input('How many rows in the matrix would you like?: ');
aMatrix = round((abs(randn(rowSize,colSize))+1)*5);
disp('This is a random matrix made of the size you asked for');
disp(aMatrix);
choice = '';
choice = input('Would you like to edit any of these values? (y): ','s');
if choice == 'y' || choice == 'Y'
   choice = '';
   disp('Now loop through each position column by column, then row by row');
   disp('Prompting you for each position whether you would like to change a value');

   colIndex = 1;
   while (colIndex <= colSize)
       rowIndex = 1;
       while(rowIndex <= rowSize)
           fprintf("Current Position: %.0f x %.0f\n", colIndex, rowIndex);
           choice = input('Would you like to change this position? (y): ','s');
           if choice == 'y' || choice == 'Y'
               choice = '';
               userChange = input('New value: ');
               aMatrix(colIndex,rowIndex) = userChange;
           end
           rowIndex = rowIndex + 1;
       end
       colIndex = colIndex + 1;
   end

   disp('This is the new Matrix:');
   disp(aMatrix);
end
matrixInfo(aMatrix)
disp('Done!');
end


%This prints the average of both each row and each column.
function matrixInfo(theMatrix)
disp('Here is Info about the Matrix;');
rowAvg = mean(theMatrix,2);
colAvg = mean(theMatrix);
fprintf("The average for each column is:\n");
fprintf("\t%.3f\n",colAvg);
fprintf("The average for each row is:\n");
fprintf("\t%.3f\n",rowAvg);
end

% Darrick Ross
% REDACTED
% Final Project - Matlab

% Sorting Words by Their Value
% For this problem, you must write a program that has a
% function/method/procedure that takes in an array (or equivalent data
% structure) of strings, and prints out the elements of that array in
% sorted order, one per line. You should sort the words according to a
% numerical value, calculated as follows: The numeric value of a word is
% the sum of the numeric values of the characters in it. The numeric value
% for a character is the ASCII value if your language encodes
% strings/characters using ASCII, and the Unicode Code point if your
% language encodes using Unicode.

%==========================================================================
% How to Use:
%   In this program the user will be propmted to enter a size of the array
%   they wish to construct. They will then populate that array from start
%   to finish with strings they type (note enter is used to end a string).
%   The program will then sort the array by the total ascii value of each
%   string. From least to greatest.
%==========================================================================

% Clearing workspace before starting the program.
clear;  %clear any variables
clc;    %clear output

userSize = input('Please enter the number of strings you would like to enter: ');
userArray = strings(userSize,2);

disp("You will now fill in that array with strings.");

index = 1;
while (index <= userSize)
    disp("==================================================")
    fprintf("Currently filling position %d of %d\n",index,userSize);
    userString = input('String: ','s');
    userArray(index,1) = userString;
    userArray(index,2) = calculateAsciiStr(userString);
    index = index + 1;
end

disp("This is the array of strings you made:");
printColumn1(userArray);

userArray = sortArrayRowsByCol2(userArray);
disp("Now here is a sorted version by the total ascii:");
printColumn1(userArray);




%Prints each row of the first column of the passed array
function printColumn1(arrayToPrint)
numOfRows = size(arrayToPrint,1);

printIndex = 1;
while (printIndex <= numOfRows)
    disp(arrayToPrint(printIndex,1));
    printIndex = printIndex + 1;
end
end


%This calculates the ascii value of the string passed and returns it (num)
function asciiValue = calculateAsciiStr(theString)
asciiVec = double(theString);
asciiValue = sum(asciiVec);
end


%Sorts the rows of the array by the value in the second column of the array
%Arrays passed should be n by 2 where n is any number.
function sortedArray = sortArrayRowsByCol2(stringArray)
length = size(stringArray,1); %Get Length of dimension one (dim 2 is always gunna be 2)

toBeSortedIndex = 1;
while (toBeSortedIndex <= length)
    unsortedIndex = 1;
    while (unsortedIndex < toBeSortedIndex)
        if (str2double(stringArray(toBeSortedIndex,2)) < str2double(stringArray(unsortedIndex,2)))
            stringArray = swapRows(stringArray,unsortedIndex,toBeSortedIndex);
        end
        unsortedIndex = unsortedIndex + 1;
    end
    toBeSortedIndex = toBeSortedIndex + 1;
end
sortedArray = stringArray;
end


%Swaps the rows of arrayToSwap and returns the array with swapped rows
%Arrays passed should be n by 2 where n is any number.
function arrayToSwap = swapRows(arrayToSwap,index1,index2)
holdDim1 = arrayToSwap(index1,1);               %Hold Index1
holdDim2 = arrayToSwap(index1,2);               %Hold Index1

arrayToSwap(index1,1) = arrayToSwap(index2,1);  %Move Index2 -> Index1
arrayToSwap(index1,2) = arrayToSwap(index2,2);  %Move Index2 -> Index1

arrayToSwap(index2,1) = holdDim1;
arrayToSwap(index2,2) = holdDim2;
end

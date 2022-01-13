% Darrick Ross
% REDACTED
% Final Project - Matlab

% Program 1
% Unique Words
% For this problem, you need to write a program that takes in a file name
% and an integer, N, and returns the first N unique words in the file given.
% These can be hard coded into your program, but should be very easy to
% change, either through constants or as parameters to a function in your
% code. If the file given contains less than N unique words, all unique
% words in the file should be printed, without any error conditions

%==========================================================================
% How to Use:
% 	The file should be located in the same directory as this program is
% 	being run from. The name of the file should be put in the place of
% 	textDocWithWords.txt on line 33. You will also want to add any special
% 	characters you intend to use into the list on line 30. They should be
% 	after yYzZ and before the ]. Here im going to be honest IDK how to add
% 	many weird characters like ] or ). When all of that is done, run the
% 	program and follow the prompt.
%==========================================================================

%Clearing workspace before starting the program.
clear;  %clear any variables
clc;    %clear output

%Please Add any character to be read here.
charactersToBeRead = '%[''.,aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ]';

%Put your file path here
fileID = fopen('textDocWithWords.txt','r');

%Checking if the file is good.
if (fileID == -1)
    disp('There was a problem opening the file... (press F to pay respect, jk the programs already stopped...)');
else

    %Get the int N for the number of unique words to find.
    nUniqueWords = input('Enter the N number of unique words to look for: ');

    %Unique word list
    uniqueWordList = strings(nUniqueWords,1);

    %Current Number of Unique words that have been found already
    currentUniqueWordCount = 0;

    %Loop through the file
    while(currentUniqueWordCount < nUniqueWords)
        if (feof(fileID))
            break;
        else
            isUnique = true;
            currentScannedString = "";

            %Scan for the characters in [charactersToBeRead] then stop when
            %finding a white space or character not in the array
            currentScannedString = fscanf(fileID,charactersToBeRead);


            if (feof(fileID))
                break;
            end

            fseek(fileID, ftell(fileID)+1, 'bof');

            %Check if the nothing was found (aka newline)
            if (isempty(currentScannedString))
                continue;
            end

            %Check if what was found is unique
            searchIndex = 1;
            while searchIndex < currentUniqueWordCount
                if (currentScannedString == uniqueWordList(searchIndex,1))
                    isUnique = false;
                    break;
                end
                searchIndex = searchIndex + 1;
            end

            %Increment word count
            %Add found word to list
            if isUnique
                currentUniqueWordCount = currentUniqueWordCount + 1;
                uniqueWordList(currentUniqueWordCount,1) = currentScannedString;
            end
        end
    end

    %Print found list
    disp('The following are the unique words found in the file:');
    printIndex = 1;
    while printIndex <= currentUniqueWordCount
        disp(char(9)+uniqueWordList(printIndex,1));
        printIndex = printIndex + 1;
    end
end

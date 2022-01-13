Author: Darrick Ross

# Directory Overview
- `problem1.m` : Problem 1.
- `problem2.m` : Problem 2.
- `problem3.m` : Problem 3.
- `problem4.m` : Problem 4.
- `problem5.m` : Problem 5.
- `problem6.m` : Problem 6.
- `README.md` : This README.
- `textDocWithWords.txt` : provided text doc to work with to use with program 1.

# Run each program:
*NOTE: Requires MATLAB to be installed. Ubuntu: https://www.mathworks.com/matlabcentral/answers/518584-how-do-i-install-on-ubuntu.*

`matlab <PROBLEM_FILE_HERE>`

Example:  
`matlab problem1.m`



This project includes 6 programs
# 1. Unique Words
```
For this problem, you need to write a program that takes in a file name and an integer,
N, and returns the first N unique words in the file given. These can be hard coded into
your program, but should be very easy to change, either through constants or as
parameters to a function in your code. If the file given contains less than N unique
words, all unique words in the file should be printed, without any error conditions
```


How to Use:
```
The file should be located in the same directory as this program is being run
from. The name of the file should be put in the place of textDocWithWords.txt on line
33. You will also want to add any special characters you intend to use into the list
on line 30. They should be after yYzZ and before the ]. Here im going to be honest
IDK how to add many weird characters like ] or ). When all of that is done, run the
program and follow the prompt.
```


# 2. Travel Time
```
In this problem, you must create a program that prompts the user for a single choice,
between a bicycle, car, or jet plane. Once the choice is made, you should pick a random
speed for the vehicle, defined by the ranges below, and travel for "one hour" at that
speed. You should continue iterating, pick a new speed each hour, until your vehicle
travels 1000 miles. You should then print how many hours it took, and the maximum
speed reached. Bicycle travels 5-15 mph, Car travels 20-70 mph, Jet Plane travels 400-600 mph.
```


How to Use:
```
Everything is built into the program. All you need to do is run the program, and input
either 1, 2, or 3 corresponding to the different vehicles. It will then print the
information about how long it took to travel 1000 miles with that vehicle. If you
would like to change how far the program uses please change the variable on line 35.
```

# 3. Sorting Words by Their Value
```
For this problem, you must write a program that has a function/method/procedure that
takes in an array (or equivalent data structure) of strings, and prints out the
elements of that array in sorted order, one per line. You should sort the words
according to a numerical value, calculated as follows: The numeric value of a word
is the sum of the numeric values of the characters in it. The numeric value for a
character is the ASCII value if your language encodes strings/characters using ASCII,
and the Unicode Code point if your language encodes using Unicode.
```


How to Use:
```
In this program the user will be propmted to enter a size of the array they wish to
construct. They will then populate that arrays from start to finish with strings they
type (note enter is used to end a string). The program will then sort the array by
the ascii value of each string. From least to greatest.
```

# 4. Generating Random Numbers
```
For this problem, you must create a program that when run, outputs a random number between
zero and one, once every second. This should continue forever, until a user hits any key,
at which point the generation of random number should stop immediately, and the following
two statistics should be printed:
 - The total number of random numbers generated
 - The average value of the numbers generated
```


How to Use:
```
When ran this program will print a random number, wait one second. Then repeat.
This will continue until the user presses a key (any key that sends a character
to the terminal, shift and ctrl are an example of keys that wont stop the
program from generating random numbers). After this happens the program will
display the number of random numbers that were created during this time, along
with the average of those numbers.
```

# 5. Statistics about a Matrix
```
Write a function/method/procedure that takes in a single parameter, that is a matrix,
and prints the row averages and the column averages. Make sure you specify how the matrix
is passed as a parameter in your language as part of your README.
When you run the program you will be prompted to enter the size of the matrix, then fill it.
```


How to Use:
```
To make it easy I have made a function that will help you build a matrix, then pass it
to the required function. You will be asked to enter the amount of rows and columns you
would like for the matrix. Then a random matrix will be created for you of the given size.
You will then be given the chance to change the contents of the matrix or keep it the same.
The info about the average of both columns and rows will be displayed shortly after.
```

# 6. Golomb Sequence
```
Using recursion, write a function/method/procedure in your language that computes the
Golomb number, which is defined as :

	G(n) = 1 + G(n - G( G( n - 1)))

	The base case of this recursion is G(1) = 1

How long does your implementation take to return the value G(50)? Include this in your
program so the timing is automatically printed when your program is run.
```


How to Use:
```
Follow the prompt, the user will be asked to select a starting n value. the program
will then start timing, and calculate how long it took to evaluate the Golomb of n.
Printing the time elapsed.
```

Project Description


# 0. Files:

- README.md		: This file
- textSearch.asm	: The file you will be modifying and handing in

# 1. Expected inputs/outputs


The user will input a string that they would like to search for in the text.
If that string exist (case sensitive) it will display the index that the
string starts at. If it does not exist the program will say the string
does not exist


# 2. How to compile and use this project


## Compilation
1. In the directory you would like to work in, place the .asm file

2. run the following:
	nasm -f elf64 textSearch.asm
	ld -o textSearch textSearch.o

3. done! you can run the ./textSearch file

## Usage
To run you will need to type the following:
	./textSearch
Then the program will prompt for you string to search for, press enter to continue.
The program will then output the location, or tell you the string was not found.
At which point the program is finished.



# 3. Functionality (describe your contributions here)


I took the original textSearch.asm, and added the code between init: and
found:, along with 2 lines at the start of found to clear my work, and grab
the index of the string (if found)



# 4. Limitations (if any)


If you have limitations (partial test cases passed), please submit a
typescript
-I think it might break with the use of an extended ascii char, but I don't
know how to input that into my terminal.



# 5. Applications (your thoughts) of this project


This project could be useful for any time you have an array of strings
and you would like to find a substring of that string.

Project Description
Author: Darrick Ross - dross1


# 0. Files:


- **proj5.c**: A c program that sorts the list of books then prints them,
  utilizing the bookcmp.asm file for the compare function.
- **bookcmp.asm**: An assembly program where the compare function is implemented
- **test.sh**: A bash script that will confirm if the output matches expected
  results.
- **driver.c**: Driver file for this project, it reads in test, and calls the
  sort function in proj5.c
- **book.h**: Header file that declares a structure **book**

  ``{tile - char[41], author - char[21], year - unsigned int}``
- **README.md**: This document

# 1. Expected inputs/outputs

When run the program will read in test#.dat (where # is a number) files that
  can be read in as book structures, those structures are then sorted
  and printed in order

Sorted order is as follows:
  1. If the years of books are different, those books are believed to be equal

  2. If the years are the same, books are sorted in order of the title, in
  lexicographical order (*dictionary order*).

test#.dat files should have an entire book on the same line, each book should
  be in order:
  1. Title (**40 char max no comma**)
  2. a comma
  3. Author (**20 char max no comma**)
  4. a comma
  5. Number (**max number is whatever the max unsigned integer can be in
  your platforms processor**)
  6. end line char


# 2. How to compile and use this project


## Compilation
```
nasm -f elf64 -g -F dwarf bookcmp.asm
gcc -g driver.c proj5.c bookcmp.o  -o proj5
```
## Usage
Place any books in the format above in the .dat file.


# 3. Functionality (describe your contributions here)


I implemented the code that will compare 2 given books, in the `bookcmp.asm`
  file. Along with the for loop that prints the books in sorted order inside the
  `proj5.c` file.



# 4. Limitations (if any)


No Limitations given the test examples.



# 5. Applications (your thoughts) of this project


This project could be useful for a unique way of sorting books.

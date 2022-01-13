Project Description

# 0. Files:

- README.md           - This file
- hexConverter.asm    - This is the main file.
- cfunc.c             - This is a c file containing a function with
                        printf



# 1. Expected inputs/outputs

The user will be input a 19 or 20 digit number. Only number are accepted

Here is a list of example number and their hexadecimal.

- `18446744073709551615` = `FFFFFFFFFFFFFFFF`
- `1107895634578278122` = `F600935B33E86EA`
- `9223372036854775808` = `8000000000000000`
- `12683270251100288260` = `B0040718331CB504`
- `15683272751237288260` = `D9A62D79450FD144`
- `9346678735677288564` = `81B612CA0DBDF074`


# 2. How to compile and use this project


## Compilation
While inside of the directory that includes the following Files
    -hexConverter.asm
    -cfunc.c

If the current directory includes these files run the following
```
nasm -g -f elf64 -F dwarf hexConverter.asm
gcc -g hexConverter.o cfunc.c -o converter.out
```

## Usage
To use, execute the following command in the working directory.
    ./converter.out

The user will then be prompted to enter a number (the 19 or 20 digit).
After typing the number, hit enter.


# 3. Functionality (describe your contributions here)


The main file prompts and reads a 19-20 digit number from the user. Then
the hexConverter converts the number into a hexadecimal number. Last it
prints out the hexadecimal number to the standard terminal.



# 4. Limitations (if any)


As long as the input is:
- composed of only numbers (digits 0-9)
- between 19 and 20 digits
- less than 2^64
- a positive number

There will not be any problems.



# 5. Applications (your thoughts) of this project


This project could be useful for a situation where you would like
to quickly convert a number to hexadecimal. Or binary, because it is
much easier to convert a hexadecimal number to binary, than it is to
convert a decimal number to binary.

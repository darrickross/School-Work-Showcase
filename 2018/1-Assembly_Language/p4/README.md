Project Description

# 0. Files:

- proj4	    - The executable for this program
- proj4.asm   - A file that includes functions to:
  - find the length of a string
  - printing an authors name
  - comparing a books price to a parameter to print only the author of the
    books that have prices greater than that parameter.

- driver.asm  - The main driver file, which test the proj4.asm functions with
  multiple test cases.

- library.o   - The build file of c file, that defines multiple 'books' with the
  following attributes:
    - Title  : Character Array
    - Author : Character Array
    - Price  : Double
    - Year   : unsigned int
    - Next   : struct book pointer

- Makefile	- Makes the binaries for the provided assembly file

- README.md	- This file


# 1. Expected inputs/outputs

The expected outputs are as follows:
```
Stieg Larsson
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Todd Burpo
Victoria Thompson
Debbie Macomber
Lincoln Child
Stephenie Meyer
Ally Condie
Sarah Bird
Christopher Paolini
Victoria Dahl
Richard Marsh
Charlaine Harris
Debbie Macomber
Joyce Carol Oates
Umberto Eco
Meg Meeker, M.D.
Kim Carpenter
Pamela Clare
=========
Janet Evanovich
Stieg Larsson
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Todd Burpo
Victoria Thompson
Debbie Macomber
Lincoln Child
Stephenie Meyer
Stephenie Meyer
Ally Condie
Anna Katherine Green
Sarah Bird
Claire Cook
Christopher Paolini
Victoria Dahl
Richard Marsh
Charlaine Harris
Debbie Macomber
Joyce Carol Oates
Umberto Eco
Lionel Shriver
Meg Meeker, M.D.
Nicholas Sparks
Kim Carpenter
Pamela Clare
=========
Margaret Atwood
J.K. Rowling
=========
Kathryn Stockett
Janet Evanovich
Stieg Larsson
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Todd Burpo
Victoria Thompson
Debbie Macomber
Lincoln Child
Stephenie Meyer
Stephenie Meyer
Ally Condie
Anna Katherine Green
Sarah Bird
Claire Cook
Christopher Paolini
Victoria Dahl
Richard Marsh
Charlaine Harris
Elin Hilderbrand
Debbie Macomber
Joyce Carol Oates
Umberto Eco
Lionel Shriver
Meg Meeker, M.D.
Nicholas Sparks
Charles C. Mann
Kim Carpenter
Pamela Clare
=========
Stieg Larsson
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Todd Burpo
Victoria Thompson
Debbie Macomber
Lincoln Child
Stephenie Meyer
Ally Condie
Sarah Bird
Christopher Paolini
Victoria Dahl
Richard Marsh
Charlaine Harris
Debbie Macomber
Joyce Carol Oates
Umberto Eco
Meg Meeker, M.D.
Nicholas Sparks
Kim Carpenter
Pamela Clare
=========
=========
Stieg Larsson
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Victoria Thompson
Christopher Paolini
Victoria Dahl
Richard Marsh
Joyce Carol Oates
Meg Meeker, M.D.
Kim Carpenter
=========
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Victoria Thompson
Victoria Dahl
Richard Marsh
Joyce Carol Oates
Meg Meeker, M.D.
=========
Margaret Atwood
=========
Stieg Larsson
Lee Child
Margaret Atwood
J.K. Rowling
Andrew Gross
Victoria Thompson
Debbie Macomber
Ally Condie
Christopher Paolini
Victoria Dahl
Richard Marsh
Charlaine Harris
Debbie Macomber
Joyce Carol Oates
Umberto Eco
Meg Meeker, M.D.
Kim Carpenter
=========
```

# 2. How to compile and use this project


## Compilation
    In the directory that contains all of the files listed in section 0,
    run the following command
        make
    This will build and compile the program and create the executable.

## Usage

    In the location where the file `proj4` was created (by the make file above)
    You will need to first change it execute privilege by run the following:
        chmod 700 proj4
    This will give you (the user) read/write/execute (along with nothing to
    everyone else). You can now run the program by doing
        ./proj4
    Where it will print out the result from the test cases


# 3. Functionality (describe your contributions here)


I have only changed/added to the following files
    proj4.asm
    ReadMe.txt

In proj4.asm I added comments to the function stringLength explain what it is
doing (along with following project directions to do so). Along with added the
functionality for the program to iterate over the books, stop at the end,
compare the current books floating point to the previously set floating point in
driver.asm, along with printing only the authors of books that had a price
greater than the that previously set value.



# 4. Limitations (if any)


Currently the program was not designed to handing changing of any of the input,
so it is very static


# 5. Applications (your thoughts) of this project


This project could be useful for a situation where you have a library of books
(that happened to be managed EXACTLY as library.o is) and you would like to
print out all authors of books that are more expensive than a set price.

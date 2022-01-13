Project Description


# 0. Files:


- **frac_heap.h**   - Header File for the program where the functions are
  prototyped, along with where the following are defined:
  - `fraction`
  - `fraction_block`
  - `fraction_block_u`
- **frac_heap.c**   - The file where the functions are defined
- **test1.c**       - An Example Test Case 1
- **test2.c**       - An Example Test Case 2
- **test3.c**       - An Example Test Case 3
- **test4.c**       - An Example Test Case 4
- **test5.c**       - An Example Test Case 5
- **test6.c**       - An Example Test Case 6
- **README.md**     - This File


# 1. Expected inputs/outputs


When running this program with the default `proj6.c` driver it should
  have the following output, except that the memory locations
  denoted by `0x______` will differ on each run.

## Output:
```
Called malloc(160): Returned 0x214a260

-2/3 plus 1/4 = -5/12


**** BEGIN HEAP DUMP ****

    0x214a290
    0x214a2a0
    0x214a2b0
    0x214a2c0
    0x214a2d0
    0x214a2e0
    0x214a2f0

**** END HEAP DUMP ****


**** BEGIN HEAP DUMP ****

    0x214a270
    0x214a290
    0x214a2a0
    0x214a2b0
    0x214a2c0
    0x214a2d0
    0x214a2e0
    0x214a2f0

**** END HEAP DUMP ****


**** BEGIN HEAP DUMP ****

    0x214a2b0
    0x214a2c0
    0x214a2d0
    0x214a2e0
    0x214a2f0

**** END HEAP DUMP ****
```


Outside of proj6.c This program will print a dump of the heap if the  program
calls the dump_heap() function. Otherwise the program will only print if the
malloc() function is called, or an error occurs.

Error List:
1. Malloc is returned a null: the program will inform you that there is
  no more memory left to allocate
2. Null is passed to del_frac: the program will inform you that `NULL` was
  passed to `del_frac()`



# 2. How to compile and use this project


## Compilation
```
gcc -g   -c -o proj6.o proj6.c
gcc -g   -c -o frac_heap.o frac_heap.c
gcc proj6.o frac_heap.o -o proj6
```
## Usage

After running the above commands, just run `proj6`


# 3. Functionality (describe your contributions here)


This program preforms an efficient use of memory to provide the user a
supply of fractions whenever the user request one, along with keeping any
unused fractions (or "deleted fractions") in a list to be used later.



# 4. Limitations (if any)


No known limitations



# 5. Applications (your thoughts) of this project


This project could be useful for a situation where the programmer need to
manage a number of fraction objects and required a memory efficient version.

Author: Darrick Ross  
Date: Fall 2021
Language: Java


# Table of Contents: <a name="table_of_contents"></a>
1. [Directory Overview](#directory_overview)
2. [Project Overview](#project_overview)
  1. [Heap Sort](#heap_sort)
  2. [Quick Sort](#quick_sort)
  3. [Custom Data File Creator](#custom_data_file_creator)
3. [Compile Instructions](#compile_instruction)
  1. [Java Version Check](#java_version)
  2. [Install Java Debian Based Systems](#install_debian)
  3. [Install Java RHEL Based Systems](#install_rhel)
  4. [Compile the Code](#compile_the_code)
4. [Program Arguments](#program_arguments)
  1. [Single File Mode](#mode_single_file)
  2. [Multi File Mode](#mode_multi_file)
  3. [Optional Flag](#optional_arguments)
5. [Example Calls to the Program](#example_calls)



# 1 Directory Overview: <a name="directory_overview"></a>

This is the **Project Home Directory**

+ input\
  - `asc50.dat`                 Example ascending sorted numbers 1-50
  - `ran50.dat`                 Example random numbers 1-50
  - `rev50.dat`                 Example reverse sorted numbers 1-50
+ output\
  - `asc50.datHeapSort.txt`     Output asc50 using HeapSort
  - `asc50.datQuickSort1.txt`   Output asc50 using QuickSort1
  - `asc50.datQuickSort2.txt`   Output asc50 using QuickSort2
  - `asc50.datQuickSort3.txt`   Output asc50 using QuickSort3
  - `asc50.datQuickSort4.txt`   Output asc50 using QuickSort4
  - `ran50.datHeapSort.txt`     Output ran50 using HeapSort
  - `ran50.datQuickSort1.txt`   Output ran50 using QuickSort1
  - `ran50.datQuickSort2.txt`   Output ran50 using QuickSort2
  - `ran50.datQuickSort3.txt`   Output ran50 using QuickSort3
  - `ran50.datQuickSort4.txt`   Output ran50 using QuickSort4
  - `rev50.datHeapSort.txt`     Output rev50 using HeapSort
  - `rev50.datQuickSort1.txt`   Output rev50 using QuickSort1
  - `rev50.datQuickSort2.txt`   Output rev50 using QuickSort2
  - `rev50.datQuickSort3.txt`   Output rev50 using QuickSort3
  - `rev50.datQuickSort4.txt`   Output rev50 using QuickSort4
+ src\
  - `HeapSort.java`             Heap sort sub class.
  - `Lab4.java`                 Driver program.
  - `QuickSort.java`            Quick sort sub class.
  - `RanData.java`              Custom input file creator.


# 2 Project Overview: <a name="project_overview"></a>

This program serves as a means to compare the speed and efficiency of sorting
algorithms. This program implements a standard Heap Sort, Quick Sort, and a few
variations of Quick Sort.

The program tracks each comparison made, as well as each swap made to accurately
assess how each sort method compares when working with different input types.

The main input types being compared are between:
- Pre-Sorted in Ascending order (asc)
- Random Data (ran)
- Pre-Sorted in Descending order (rev)


Duplicate data **does not** affect the sorts as implemented.

In order to make sure one sort method did not win or lose because of overhead
like recursion, both sorts were **implemented using recursion**.

One after the fact change I would have made was to make a Sort class that each
sorting method would implement. This would have made implementing other sorting
methods cleaner and would follow a best practice of using polymorphism.


## 2.1 Heap Sort: <a name="heap_sort"></a>

Standard Heap Sort is implemented. Resources:
- [Wikipedia](https://en.wikipedia.org/wiki/Heapsort)
- [GeeksforGeeks](https://www.geeksforgeeks.org/heap-sort/)

Heap Sort is a sort method based on a binary tree sorting approach. But is
implemented not with nodes and links, but within an array.

1. Heap *Tree* is always [Binary](https://en.wikipedia.org/wiki/Binary_tree) &
[Complete](https://en.wikipedia.org/wiki/Binary_tree#Types_of_binary_trees).
  + Complete:
    - All levels except the last have all possible nodes.
    - The last level is filled from left to right with no gaps.

    *Note: Image from Wikipedia* ![Complete Tree Example from Wikipedia](https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Complete_binary2.svg/220px-Complete_binary2.svg.png)

2. All Parent nodes must have a **Higher** priority than the its children node.

#### 2.1.1 Priority Order

Heap Sort is based on a priority order in which the **Highest Priority Item**
is always on top. Thus a valid heap must make sure the parent of any node has a
higher priority then the child. Null Nodes are the lowest possible priority.

Maintaining this order through deletions means that as the tree will always have
the Highest Priority at the root node.

Exploiting this property means that you could remove the root node
(*swapping it with the last node in the heap and decrementing the size*). Then
fix the heap (*heapify()*) by percolating down the new root until the heap is
valid again. Repeat for all items in the array and you then have an array sorted
from **least priority to most priority**.

In this case **priority** is defined as being **larger**. Thus `20` is higher
priority than `10`.

#### 2.1.2 Indexes Calculation:

One other important feature of heap arrays is that because all items are stored
in an array you can use math to calculate the index of children or a node's
parent.

Some implementations define the root node as array index **1**. But in my
implementation I strived to use the array in place. So instead my root node
is defined as being at **0**.

The change just means most calculations to find the **child index** from the
**parent's index** would just be *off by one* and a `+1` would be needed to fix.

###### 2.1.2.1 Left Node Index
Left node of a given parent index `parent` is then defined as:
```
private int left(int parent)
{
  return 2 * parent + 1;
}
```

###### 2.1.2.2 Right Node Index

Right node of a given parent index `parent` is then defined as:
```
private int right(int parent)
{
  return 2 * parent + 2;
}
```

###### 2.1.2.3 Parent Node Index

The parent of a given node is a bit more complicated then calculating children.
The method I chose to implement to calculate a ***child node's parent index***,
was to calculate the **parent index** based on the **right node's index**.
```
private int parent(int child)
{
  if (child % 2 == 0)
  {
    return child / 2 - 1;
  }
  return (child + 1) / 2 - 1;
}
```
This could have also used recursion, but I chose not to.

Using these calculations you can traverse the "*tree*" using math to calculate
offsets.




## 2.2 Quick Sort: <a name="quick_sort"></a>

Quick Sort by default is implemented as a recursive sort in which each iteration
sorts the sub array into 2 sections based on a pivot item.
1. Items **Smaller** than the pivot.
2. Items **Larger** than the pivot.

Each section is then recursively sorted.

The end result is a sorted array.

![Quick Sort Image by mwong068 on dev.to](https://res.cloudinary.com/practicaldev/image/fetch/s--wwfJBPCm--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://dev-to-uploads.s3.amazonaws.com/i/3odpn7gl8myiqx7rto4f.png)  
*image from https://dev.to/mwong068/quick-sort-in-ruby-2302*

#### 2.2.1 Quick Sort Variation 1:
Variation 1 has no special changes.
- **Pivot** is defined as the **first item** in the sub array.
- **Stop case** is defined as a sub array which is of **size 2 or less**.

#### 2.2.2 Quick Sort Variation 2:
Variation 2 **increases the stop case** and **implements Insertion Sort** on the
remaining items in that sub array.
- **Pivot** (*No changes from V1*) is defined as the **first item** in the sub array.
- **Stop case** is defined as a sub array which is of **size 100 or less**.

#### 2.2.3 Quick Sort Variation 3:
Variation 3 **increases the stop case** (by less than V2, still more than V1)
and **implements Insertion Sort** on the remaining items in that sub array.
- **Pivot** (*No changes from V1*) is defined as the **first item** in the sub array.
- **Stop case** is defined as a sub array which is of **size 50 or less**.

#### 2.2.4 Quick Sort Variation 4:
Variation 4 uses the default stop case (*V1*). But selects the pivot as the
**median** of the **First**, **Middle**, and **Last** items.  
- **Pivot**  is defined as the **first item** in the sub array.
- **Stop case** (*No changes from V1*) is defined as a sub array which is of
    **size 2 or less**.

## 2.3 Custom Data File Creator: <a name="custom_data_file_creator"></a>

I wrote a small program `RanData.java` to assist in creating data files to test.

The program is compiled as normal using the below [Compile Instructions](#compile_instruction).

The program has a help menu accessed by running `java RanData --help` or
`java RanData -h`. Which will output the following help message.

*Note: When running this program from the Project Home Directory use*
`java -cp src RanData [ARGUMENTS]`

```
Usages:
java RanData --help
java RanData -h
^^^ Help / Print this message ^^^

java RanData -o [file] -n [Int]
^^^ Standard Random Number in Random Order ^^^

java RanData -o [file] -n [int] -u [Double]
^^^ Add a percentange of duplicates to use ^^^

java RanData -o [file] -n [Int] -u [Double] -l [Double]
^^^ Make the percentange Random between 2 variables. ^^^

java RanData -o [file] -n [Int] -a
java RanData -o [file] -n [int] -u [Double] -a
java RanData -o [file] -n [Int] -u [Double] -l [Double] -a
^^^ Sort the output so it comes out in ascending order ^^^

java RanData -o [file] -n [Int] -d
java RanData -o [file] -n [int] -u [Double] -d
java RanData -o [file] -n [Int] -u [Double] -l [Double] -d
^^^ Sort the output so it comes out in descending order ^^^
```

# 3 Compile Instructions: <a name="compile_instruction"></a>

## 3.1 Java version check: <a name="java_version"></a>
Confirm openjdk-8-jdk is installed using the java version check command.

  `java -version`

If java is installed it should return a version number, for example:

  `openjdk version "#.#.#_###"`

This code was compiled, and tested with version **1.8.0_292** and should work
versions at or above that number.

If the command returns an error, or you need a newer version installed use the
Install instructions. Otherwise skip to [Compile the code](#compile_the_code)

## 3.2 Install Java Debian based systems: <a name="install_debian"></a>
*Note: `sudo` will require your user to be part of the sudoer group, as well as*
*enter your password.*

1. Update your repos

  `sudo apt update`

2. Install the latest openjdk-8-jdk version.

  `sudo apt install openjdk-8-jdk -y`

  The `-y` will auto accept the install. Without it you will need to accept the
  install manually.

## 3.3 Install Java RHEL based systems: <a name="install_rhel"></a>
*Note: `sudo` will require your user to be part of the sudoer/wheel group, as*
*well as enter your password.*

1. Update your repos

  `sudo yum update`

2. Install the latest openjdk-8-jdk version.

  `sudo yum install openjdk-8-jdk -y`

  The `-y` will auto accept the install. Without it you will need to accept the
  install manually.

## 3.4 Compile the Code: <a name="compile_the_code"></a>
  *Note: Working directory should be in the project home directory*

  `javac src/*.java`

  This will compile all .java files.


# 4 Program Arguments: <a name="program_arguments"></a>
The program operates from the command line using certain flags before values
to denote which argument the value belongs to. A full list and help menu can be
accessed using the `-h` flag, or `--help` argument.

## 4.1 Single File Mode: <a name="mode_single_file"></a>

Single file mode takes in one **input file**, runs the file against the
specified **sort type**, and writes the output to the specified **output file**.

In Single File mode the program requires 3 flags with values:
1. **Input File**:
  - The file location containing integers to sort.
  - Denoted by the `-i` flag.
  - Usage `... -i [INPUT FILE PATH] ...`
2. **Output File**
  - The file location where the output will be placed.
  - Denoted by the `-o` flag.
  - Usage `... -o [OUTPUT FILE PATH] ...`
  - *NOTE: Will overwrites files at the same location.*
3. **Sort Type**:
  - The type of Sort to use
  - Valid sorts types are:
    0. `0` Heap Sort
    1. `1` Quick Sort Variation 1
    2. `2` Quick Sort Variation 2
    3. `3` Quick Sort Variation 3
    4. `4` Quick Sort Variation 4
  - See [Quick Sort](#quick_sort) for information about the variations.

They can be used in any order, examples:
- `java Lab4 -o out.txt -i in.txt -t 0`
- `java -cp src Lab4 -i input/in.txt -o output/out.txt -t 1`
- `java -cp src Lab4 -t 4 -i input/in.txt -o output/out.txt`

Can not be combined with:
  - `-if` & `-of` multi file mode can not be used with single file mode.


## 4.2 Multi File Mode: <a name="mode_multi_file"></a>

In multi file mode the program takes a given directory, and runs **all files**
against **all sorts**. Outputting the result into the specified directory.

A unique output file is generated at the output location for each sort of each
file.

Required flags:
1. **Input Directory**:
  - The directory containing files to sort.
  - Denoted by the `-if` flag.
  - Usage: `... -if [INPUT DIRECTORY PATH] ...`
2. **Output Directory**
  - The file location where the output will be placed.
  - Denoted by the `-of` flag
  - Usage: `... -of [OUTPUT DIRECTORY PATH] ...`
  - *NOTE: Will overwrites files at the same location if the name matches.*

  They can be used in any order, examples:
  - `java Lab4 -of output -if input`
  - `java -cp src Lab4 -if input -of output`


Can not be combined with:
  - `-t` Sort Type Flag. All sort types will run for each input file.
  - `-i` & `-o` multi file mode can not be used with single file mode.


## 4.3 Optional Flags: <a name="optional_arguments"></a>

1. `-h` or `--help` Help / Usage menu:
  - Displays the below help menu then exits.

  ```
  Usages:
  Java Lab4 --help
  Java Lab4 -h
    ^^^ Help / Print this message

  Java Lab4 -of [Directory] -if [Directory]
    ^^^ Run all files in the input folder against all sort methods.

  Java Lab4 -o [File] -i [File] -t [#]
    ^^^ Standard sorts the input file using the method selected.

    Sort Methods:
      0) Heap Sort.
      1) Quick Sort - Partition <   3 is stop case.
      2) Quick Sort - Partition < 101 is stop case, insertion sort the rest.
      3) Quick Sort - Partition <  51 is stop case, insertion sort the rest.
      4) Quick Sort - Partition <   3 is stop case, Median-of-3 is pivot.

  Java Lab4 -o [F] -i [F] -t [#] -v
  Java Lab4 -of [D] -if [D] -v
    ^^^ Be verbose out all actions (Compares/Swaps) taken.
  ```

2. `-v` Verbose Flag
  - Will set output to print each operation (Comparision/Swap) done.
  - Verbose is automatically turned on for data sets with size below 50.


  ***NOTE: VERBOSE IS CURRENTLY INEFFICIENT AND DOES NOT PERFORM WELL ON DATA SETS ABOVE ~50. REFRAIN FROM USING ON LARGE FILES AS IT WILL SLOW THE OPERATION OF THE PROGRAM DRASTICALLY.***


# 5 Example Calls to the Program: <a name="example_calls"></a>
  *Note: Working directory should be in the projects home directory.*  
  *Note: `java -cp [FOLDER] [CLASSNAME]` runs a java class named [CLASSNAME] in*
  *the folder [FOLDER]*

1. General format:

  1. `java -cp src Lab4 -o [File] -i [File] -t [#]`
  2. `java -cp src Lab4 -of [Directory] -if [Directory]`


2. Run the file `ran50.dat` in `input` folder using heapsort `0` and places the
  result into the file `ran50output.txt` in `output` folder:

  - `java -cp src Lab4 -i input/ran50.dat -o output/ran50output.txt -t 0`


3. Run all input files in directory `input` and place the resulting outputs in
the directory `output`:

  - `java -cp src Lab4 -if input -of output`

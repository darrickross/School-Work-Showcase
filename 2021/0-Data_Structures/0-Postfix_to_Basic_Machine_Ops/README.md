Author: Darrick Ross  
Date: Fall 2021
Language: Java


# Table of Contents: <a name="table_of_contents"></a>
1. [Directory Overview](#directory_overview)
2. [Project Overview](#project_overview)
  1. [Example](#example)
  2. [Supported Operations](#supported_operations)
  3. [List of Machine Codes](#machine_codes)
3. [Compile Instructions](#compile_instruction)
  1. [Java Version Check](#java_version)
  2. [Install Java Debian Based Systems](#install_debian)
  3. [Install Java RHEL Based Systems](#install_rhel)
  4. [Compile the Code](#compile_the_code)
4. [Program Arguments](#program_arguments)
5. [Example Calls to the Program](#example_calls)



# 1 Directory Overview: <a name="directory_overview"></a>

This is the **Project Home Directory**

+ input\
  - `in.txt`              Provided inputs.
  - `in1.txt`             Example math edge case inputs.
  - `in2.txt`             Example general edge case inputs.
+ output\
  - `out.txt`             Results from provided input.
  - `out1.txt`            Results from math edge cases.
  - `out2.txt`            Results from general edge cases.
+ src\
  - `Lab1.java`           Main driver program.
  - `MachineLangGen.java` Parser converting postfix to instructions.
  - `Stack.java`          Stack implementation.

# 2 Project Overview: <a name="project_overview"></a>

This project takes basic Postfix math notation and generates a generic machine
code notation which would calculate the result of the Postfix math notation.
This does not compute, but generates the machine code.

The machine code stores each operation on 2 operands as a temporary variable of
the form `TEMP#`. Starting with `TEMP1`.

## 2.1 Example: <a name="example"></a>

The input `AB+` would be converted into:
```
LD A
AD B
ST TEMP1
```

## 2.2 Supported Operations: <a name="supported_operations"></a>
- Addition `+`
- Subtraction `-`
- Multiplication `*`
- Division `/`
- Exponential `$`
- Modulus Division `%`

All operators are **binary** operators taking in **2 operands**.

All **other characters** are treated as **operands**.

Certain math operations on the values `1` or `0` result in a known result, and
are supported.
- `A+0`=`A` and reverse
- `A-0`=`A`
- `A*1`=`A` and reverse
- `A*0`=`0` and reverse
- `0/A`=`0`
- `1/A`=`A`
- `0%A`=`0`
- `A%1`=`0`
- `1%A`=`1`
- `0^A`=`0`
- `A^0`=`1`

## 2.3 List of Machine Codes: <a name="machine_codes"></a>
- `LD` : Load
- `ST` : Store
- `AD` : Addition
- `SB` : Subtraction
- `ML` : Multiplication
- `DV` : Division
- `EX` : Exponential
- `MD` : Modulus Division

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
  The program accepts 2 arguments,

1. **args[0]** is the **input text file**. This accepts a path to a file as a
  string, containing inputs separated by new line characters `\n`.

2. **args[1]** is the **output text file**. This accepts a path to where the
  program will try to create a text file with the outputs of the program. If
  the file already exist the file is overwritten.

# 5 Example Calls to the Program: <a name="example_calls"></a>
  *Note: Working directory should be in the projects home directory.*  
  *Note: `java -cp [FOLDER] [CLASSNAME]` runs a java class named [CLASSNAME] in*
  *the folder [FOLDER]*

1. General format:

  - `java -cp src Lab1 [Path To Input Text File] [Path To Output Text File]`


2. Run the first input file `in.txt` in `input` folder and places the result
  into the `out.txt` file in `output` folder:

  - `java -cp src Lab1 input/in.txt output/out.txt`


3. Run the second input file `input/in.txt` and places the result into
  `output/out.txt`:

  - `java -cp src Lab1 input/in2.txt output/out2.txt`


4. Run the third input file `input/in.txt` and places the result into
  `output/out.txt`:

  - `java -cp src Lab1 input/in3.txt output/out3.txt`

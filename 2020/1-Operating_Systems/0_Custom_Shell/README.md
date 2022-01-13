Darrick Ross
Spring 2020

# Project Overview

The project goal was to write a C program which acted as a Linux shell.
Interpreting commands and executing program.

### Directory Overview
- `README.md` : This readme file.
- `simple_shell.c` : My simple shell program.
- `test_for_fail.txt` : Example script file which would fail mid run.
  + This will fail on the 4th command because it is invalid.
  ```
  /bin/ls
  /bin/echo "test 1" "test 2" "test 3"
  /bin/pwd
  im going to fail!!
  ```
- `test_for_success.txt` : Example script which will exit cleanly.
  + This will not run into problems.
  ```
  /bin/ls
  /bin/echo "test 1" "test 2" "test 3"
  /bin/pwd
  /bin/echo Hello\nWorld
  ```
- `utils.c` : Provided c code file with utilities.
- `utils.h` : Provided header file with utilities.

# Program Modes
The Shell has 2 modes.
1. Terminal mode | 0 arguments.
  - The program works similar to a standard *command line interface* or *terminal*.
  - The program accepts commands one at a time, and tries to execute it if possible.
  - The new line character `\n` is what dictates the end of the input.
  - Exiting the program is done using the `Exit` command. Which returns code `0`.

2. Script file mode | 1 argument.
  - This mode works similar to a scripting language running a file with multiple commands written down in it. Similar to running a script in Bash, Powershell, Python, etc.
  - The program accepts 1 input which is treated as a path to a file.
  - The file is read as ascii, interpreting each line as a command with arguments.
  - Lines must end in a new line character `\n`.
  - If any command return a code which is not `0`, the program stops with return code `1`.
  - The program exits with code `0` when it reaches the end of a file, denoted by a line with just the new line character `\n`.


# Command format

Just like many other command line interfaces the format is,

`command argument1 argument2 ... argumentN`

Where the `command` is **required**, and the arguments are **optional**.

## Example valid commands:

- `/bin/ls`
- `/bin/echo "Hello World"`

# Known Memory Problems

- The Interpreter has a memory leak when given invalid values.

# Raw Requirements:
```
"If run with no arguments, the shell shall present the user with a prompt at
which he or she can enter commands. Upon the completion of a command, it
should prompt the user for a new command."

"If run with one argument, the shell shall treat the argument as a file name
and open that file. Each line of the file shall be treated as a command to be
run, persuant to the rest of the requirements. Once the end of the file is
reached, the shell shall exit with a successful return code (0). If any
commands in the file cannot be run, the shell shall exit immediately with an
error return code (1), and not run any commands that appear after the
erroneous line in the file."

"If run with more than one argument, the shell shall print an error message to
stderr, explaining that it must be run with 0 or 1 arguments and exit with an
error return code (1)."

"The shell shall accept command input of arbitrary length (meaning you cannot
set a hard limit on command length)."

"Parse command-line arguments from the user's input and pass them along to the
program that the user requests to be started. The command to be called will be
either specified as an absolute path to a program binary (like /bin/ls) or as
the name of a program that resides in a directory in the user's $PATH
environment variable (like gcc) — relative paths are not required to be
supported (so, we will not test your code for this assignment by switching to
the /usr directory and attempting to run bin/gcc, for instance). In addition,
your argument parsing code must properly handle escape sequences and quoting.
That is to say that the input /bin/echo Hello\nWorld should be parsed into two
pieces — the program name /bin/echo and one argument to that program containing
the string Hello World with an actual newline character in place of the space
(and no quotes)."

"The shell should exit with a successful return code when the user enters the
command exit with no arguments."

```

# Sources:
  1. getline()
    1. https://dev-notes.eu/2019/07/Get-a-line-from-stdin-in-C/
    2. https://riptutorial.com/c/example/8274/get-lines-from-a-file-using-getline
  2. Execvp
    1. https://stackoverflow.com/questions/14492971/how-to-free-memory-created-by-malloc-after-using-execvp
    2. http://www.csl.mtu.edu/cs4411.ck/www/NOTES/process/fork/exec.html

Author: Darrick Ross  
Date: Fall 2021
Language: Java


# Table of Contents: <a name="table_of_contents"></a>
1. [Directory Overview](#directory_overview)
2. [Project Overview](#project_overview)
  1. [Game Setup Rules](#game_setup)
  2. [Game Play Rules](#game_play_rules)
  3. [Game Win Condition](#game_win_cond)
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
  - `in.txt`                Example custom deck input.
+ output\
  - `out.txt`               Results from provided custom deck input.
  - `out0.txt`              Results from random run.
+ src\
  - `Card.java`             Card sub class.
  - `HybridDeckArray.java`  Hybrid Deck Array sub class.
  - `Lab3.java`             Main driver program.


# 2 Project Overview: <a name="project_overview"></a>

This program plays game of Scorpion Solitaire, see
[rules](https://www.bvssolitaire.com/rules/scorpion.htm).

The game is played by a computer, outputting the results of the game to the
  provided text file.

If a input file is passed in as well, the input is read in as a custom starting
  deck.
  - Valid card strings must be formatted as 1 or 2 characters representing the rank
    combined with the character representing the suit.
  - Valid cards must be separated by a space character: ` `.
  - Example 7 cards: `... 2D KS AS 9D 5S 8C QS ...`
  - The only check made on input is to validate that each card is legal, and
    that at least 53 cards were passed in.

The computer will try to complete legal moves, if the computer can no longer
  make moves it will lose.

## 2.1 Game Setup Rules: <a name="game_setup"></a>
- Standard Deck of **52 cards**.
- Deck is shuffled into **7 piles** of **7 cards** in each.
- The **first 3 cards** of the **first 4 piles** will start **face down**.
- All other cards dealt into the piles are **face up**.
- The **remaining 3 cards** are placed **face down** into a **separate pile**.


## 2.2 Game Play Rules: <a name="game_play_rules"></a>
- Cards move similar to Solitaire, except the **suit** of **both cards** must be
  the **same**.
- A move consist of:
  1. Moving a card *(Source Card)* of a specific rank and suit.
    - *(plus all other cards laying on top of that card)*
  2. On top of the destination card of the **same suit** and **1 rank higher**
    than the source card.
  3. The **destination card** must have **no cards laying on top of it** before
    the move happens.
- Example Moves
  - Example: `Ace of Spades` can only be moved onto the `2 of Spades`
  - Example: `10 of Clubs` can only be moved onto the `Jack of Clubs`
  - Example: `King of Anything` can only be moved a `blank pile`.
- **Completed Suits** are able to be moved out of the **7 initial piles** to
  make more room.
- When a pile only contains **hidden/face down** cards, flip the bottom most
  **face up**.
- If no moves are available, you can deal the **3 extra cards face up** as the
  top most card in the **first 3 piles**.

## 2.3 Game Win Condition: <a name="game_win_cond"></a>
- Just like Solitaire the game is won when all suit piles have been completed
  with cards from king to ace, of the same suit.

- Scorpion Solitaire is a **very hard** game compared to regular Solitaire.
  **Most games are not completable**.

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
The program has 2 modes of operation:

1. If provided **1 argument**:

  1. **args[0]** is the **output text file**. This accepts a path to where the
    program will try to create a text file with the outputs of the program. If
    the file already exist the file is overwritten.

1. If provided **2 arguments**:

  1. **args[0]** is the **input text file**. This accepts a path to a file as a
    string, containing cards as defined in [Project Overview](#directory_overview) separated by a
    **space characters** ` `.

  2. **args[1]** is the **output text file**. This accepts a path to where the
    program will try to create a text file with the outputs of the program. If
    the file already exist the file is overwritten.

# 5 Example Calls to the Program: <a name="example_calls"></a>
  *Note: Working directory should be in the projects home directory.*  
  *Note: `java -cp [FOLDER] [CLASSNAME]` runs a java class named [CLASSNAME] in*
  *the folder [FOLDER]*

1. General format:

  1. `java -cp src Lab3 [Path To Input Text File] [Path To Output Text File]`
  2. `java -cp src Lab3 [Path To Output Text File]`

2. Run the first input file `in.txt` in `input` folder and places the result
  into the `out.txt` file in `output` folder:

  - `java -cp src Lab3 input/in.txt output/out.txt`


3. Run the **without** an input file and places the result into `output/out0.txt`:

  - `java -cp src Lab3 output/out0.txt`

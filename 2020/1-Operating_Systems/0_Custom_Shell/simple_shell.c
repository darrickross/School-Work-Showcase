//Defines
#define _XOPEN_SOURCE 700 //prevents the following warning message
                            //warning: implicit declaration of function ‘getline’; did you mean ‘getenv’? [-Wimplicit-function-declaration]
                          //https://www.linuxquestions.org/questions/programming-9/getline-problem-4175485184/#post5067843

//Libraries
#include <stdio.h>
#include <stdbool.h>    //Needed for bool
#include <stdlib.h>     //Needed for memory management
#include <sys/types.h>  //Needed for ssize_t
#include <string.h>     //Needed for strtok()
#include <sys/wait.h>   //Needed for wait()
#include <unistd.h>     //Needed for execl()

//External Files
#include "utils.h"
//

//Globals
const char STRTOK_SEARCH[] = " \t\n";
const char CMD_EXIT[] = "exit\n";
const char CMD_PROMPT_CHAR = '>';
const char CMD_WELCOME_MSG[] = "Welcome to the Simple Shell,\n\tTo Exit type \'exit\'\n\n"; //exit command is not using the same as defined in CMD_EXIT, this would need to be fixed in the future if this is important.
const char ERROR_EXTRA_ARGS[] = "Error, more then 1 argument was given.\nPass either 0 arguments,\nor 1 argument with the name of file to be read for commands\n";
const char ERROR_FORK_FAIL[] = "Error, Fork has failed, program is terminating.";
const char ERROR_MALLOC[] = "Error, malloc() failed to create useable ptr";
const char ERROR_REALLOC[] = "Error, realloc() failed to create useable ptr";
const char ERROR_UTILS_C[] = "Error, in utils.c";

//Headers
char** returnListOfArgs(char* charSeq, int* size);
void strToCMD(char* strInput, int *returnCode);

//Functions
int main(int argc, char *argv[])
{
  if (argc == 1)
  {
    //No arguments passed, command looks like,
    //>simple_shell

    //start command prompt, exit upon 'exit' command (no arguments given to 'exit')
    //Welcome msg
    fprintf(stdout, CMD_WELCOME_MSG);

    bool endCMD = false;

    char* userInput = NULL;
    size_t len = 0;
    ssize_t lineSize = 0;

    do {
      fprintf(stdout, "%c", CMD_PROMPT_CHAR);

      //Read in user command
      userInput = NULL;
      len = lineSize = 0;

      lineSize = getline(&userInput, &len, stdin);

      if(strcmp(userInput, CMD_EXIT) == 0)
      {
        endCMD = true;
      }
      else
      {
        //interpret the command as a command to be ran with arguments
        int returnCode = 0;
        strToCMD(userInput, &returnCode);
        printf("Return Code: %d\n", returnCode);
      }

      //clean up
      free(userInput);
    } while(!endCMD);

    printf("Ended Interpreter\n");
  }
  else if (argc == 2)
  {
    //1 argument passed, command looks like,
    //>simple_shell arg_here

    //get filename from arg
    char* fileName = argv[1];

    FILE * cmdFilePtr = NULL;

    //open file with read perms
    cmdFilePtr = fopen(fileName, "r");

    //test that file was opened
    if (!cmdFilePtr)
    {
      //file not opened
      printf("File \"%s\" was not opened.\n", fileName);
      return -1;
    }

    //prepare
    bool cleanExit = true;
    
    char * lineBuffer = NULL;
    size_t lineBufferSize = 0;
    ssize_t lineSize = 0;
    int lineCount = 0;
    int returnCode = 0;

    //start reading lines
    lineSize = getline(&lineBuffer, &lineBufferSize, cmdFilePtr);

    while (lineSize >=0)
    {
      lineCount++;

      //DEBUG
      //printf("line[%d]: chars=%zd, buf size=%zu, contents: %s\n", lineCount, lineSize, lineBufferSize, lineBuffer);

      //Do whatever you do with the line
      strToCMD(lineBuffer, &returnCode);
      //if line executes with return status != 0
      if (returnCode != 0)
      {
        //We had an error executing the command (return code != 0)
        cleanExit = false;
        break;
      }

      //grab the next line
      lineSize = getline(&lineBuffer, &lineBufferSize, cmdFilePtr);
    }

    free(lineBuffer);
    
    fclose(cmdFilePtr);

    if (cleanExit == true)
    {
      printf("Finished Executing File [%s]\n", fileName);
      return 0;
    }
    else
    {
      printf("Error in File [%s] on Line %ld\n", fileName, lineSize);
      return 1;
    }
  }
  else
  {
    //more then 1 argument passed, ERROR
    fprintf(stderr, ERROR_EXTRA_ARGS);
    return 1;
  }

  return 0;
}

void strToCMD(char* strInput, int* rcPtr)
{
  pid_t pid;

  //Obtain argList from input
  int numArgs = 0;
  char** argList = returnListOfArgs(strInput, &numArgs);
  if (argList == NULL)
  {
    printf("ReturnListOfArgs returned NULL\n");
    *rcPtr = -1;
  }
  else
  {
    int returnCode = 0;
    //char whoami = 'a';

    //fork
    pid = fork();

    //in parent wait
    if (pid > 0)
    {
      //whoami = 'p';
      while (wait(&returnCode) != pid)
      {
        continue;
      }

      //free parents version of the memory
      for (int ii = 0; ii < numArgs; ii++)
      {
        free(argList[ii]);
        //printf("Freed in %c\n", whoami);
      }
      free(argList);

      //set return code
      *rcPtr = returnCode;
    }
    //in child run command
    else if (pid == 0)
    {
      //whoami = 'c';
      //printf("hi\n");
      if ( execvp(argList[0], argList) < 0) //best part, if execvp succeed it frees memory for us.
      {
        printf("Error in execution of command\n");

        //Free childs version of the memory
        for (int ii = 0; ii < numArgs; ii++)
        {
          free(argList[ii]);
          //printf("Freed in %c\n", whoami);
        }
        free(argList);

        //still have to exit code 1
        exit(1);
      }
    }
    else
    {
      fprintf(stderr, ERROR_FORK_FAIL);
      *rcPtr = -1;
    }
  } //Malloc check else end }
  
}

//The char** returned must be freed when finished!!!!!
char** returnListOfArgs(char* input, int* size)
{
  //count spaces
  int sizeArgList = (int)count_spaces(input) +1;
  //"command arg1 arg2 arg3 arg4" upper bound (does not check in a quote)

  //find max size of input
  int numChars = strlen(input);

  //malloc that many
  char** argList = (char**)malloc(sizeof(char*)*(sizeArgList));
  if (argList == NULL)
  {
    printf("%s\n", ERROR_MALLOC);
    return NULL;
  }

  //get each item
  char * tempInput = NULL;
  int indexParse = 0;
  int argCount = 0;
  for(int ii = 0; ii < sizeArgList && indexParse < numChars; ii++, argCount++)
  {
    //update tempInput
    tempInput = input + indexParse;

    //find first_unquoted_space
    int indexNextSpace = first_unquoted_space(tempInput);

    char * cleanStr = (char*)malloc(sizeof(char)*(indexNextSpace+1));
    if (cleanStr == NULL)
    {
      printf("%s\n", ERROR_MALLOC);
      return NULL;
    }

    //copy arg over
    for(int jj = 0; jj < indexNextSpace; jj++)
    {
      cleanStr[jj] = input[indexParse+jj];
    }
    //add '\0'
    cleanStr[indexNextSpace] = '\0';

    //Put the arg in the list
    argList[ii] = unescape(cleanStr, stderr);

    //free cleanStr
    free(cleanStr);

    //update indexParse
    indexParse = indexNextSpace + indexParse + 1;
  }

  //Now we need to set up a proper 2x array to carry the proper arguments + a NULL
  char ** properArgList = (char**)malloc(sizeof(char*)*(argCount+1));
  
  //next move over the proper args and add a null at last spot
  // [arg1, arg2, arg3, NULL]
  for(int ii = 0; ii < sizeArgList; ii++)
  {
    //This is an argument
    if (ii < argCount)
    {
      properArgList[ii] = argList[ii];
    }
    else
    {
      free(argList[ii]);
    }
  }

  //set last to null
  properArgList[argCount] = NULL;

  //clear the outer array
  free(argList);

  //return the size using ptr, return the char** pointer
  *size = argCount;
  return properArgList;
}
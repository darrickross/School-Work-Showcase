#include "frac_heap.h"
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>

static fraction_block* freeBlockList;

/*
 * init_heap():
 * must be called once by the program using your
 * functions before calls to any other functions are made. This
 * allows you to set up any housekeeping needed for your memory
 * allocator. For example, this is when you can initialize
 * your free block list.
 */
void init_heap()
{
	freeBlockList = NULL;
}

/*
 * new_frac():
 * must return a pointer to fractions.
 * It should be an item taken from the list of free blocks.
 * (Don't forget to remove it from the list of free blocks!)
 * If the free list is empty, you need to get more using malloc()
 * (Number of blocks to malloc each time is specified in the project
 * description)
 */
fraction * new_frac()
{
	if (freeBlockList == NULL)
	{
		//we need more fraction blocks

		freeBlockList = (fraction_block *)malloc((NEW_BLOCK_NUMBER * sizeof(fraction_block)));
		//debug malloc message
		printf("\nCalled malloc(%d): Returned 0x%Lx\n\n", (NEW_BLOCK_NUMBER * sizeof(fraction_block)), freeBlockList);
		//debug malloc message

		//test what was returned
		if (freeBlockList == NULL)
		{
			//unable to get more memory bye bye
			printf("\nError: No more memory space left for allocation!\n");
			exit(1);
		}

		fraction_block *tempPointer = freeBlockList;
		//keep a temp pointer

		//Link up the Block of Memory
		for (int index = 1; index < NEW_BLOCK_NUMBER; index++)
		{
			tempPointer->next = freeBlockList + index;
			tempPointer = tempPointer->next;
		}
		tempPointer->next = NULL;

		//now we have a list of freeBlocks to work with
	}

	//take the first item and prepare to return it, then update freeBlockList
	//to point to the next in the list

	fraction* pointerToReturn = &(freeBlockList->frac);

	freeBlockList = freeBlockList->next;

	return pointerToReturn;
}

/*
 * del_frac():
 * takes a pointer to a fraction and adds that item to the free block list.
 * The programmer using your functions promises to never use that item again,
 * unless the item is given to her/him by a subsequent call to new_frac().
 */
void del_frac(fraction * frac)
{
	//check user input (is not NULL)
	if (frac == NULL)
	{
		printf("\nError: del_frac() issued on NULL pointer.\n");
		exit(1);
	}



	//place the old fraction into the free list


	//if the list is empty set the freeBlockList equal to the newly freed fraction block
	if (freeBlockList == NULL)
	{
		freeBlockList = (fraction_block *)frac;
		freeBlockList->next = NULL;
		//and we done
		return;
	}

	//otherwise insert the frac into the first slot of the linked list

	//keep track of old head
	fraction_block *tempPointer = freeBlockList;

	freeBlockList = (fraction_block *)frac;

	freeBlockList->next = tempPointer;
	//now we are done
}

/*
 * dump_heap():
 * For debugging/diagnostic purposes.
 * It should print out the entire contents of the free list,
 * printing out the address for each item.
 */
void dump_heap()
{
	printf("\n**** BEGIN HEAP DUMP ****\n\n");

	fraction_block *dump_pointer = freeBlockList;
	//while current item does not equal NULL
	while (dump_pointer != NULL)
	{
		//print the following
		printf("    0x%Lx\n", dump_pointer);

		//set current dump_pointer = next
		dump_pointer = dump_pointer->next;
	}

	printf("\n**** END HEAP DUMP ****\n\n");
}

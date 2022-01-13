/**
 * 
 * Inspiration from 
 * + https://www.geeksforgeeks.org/heap-sort/
 * 
 * @author Darrick Ross
 *
 */


public class HeapSort
{

	/*
	 * --------------------------------------------------------------------------
	 * Private Members
	 * --------------------------------------------------------------------------
	 */

	private int[] arrayToSort;
	private int countComparisons;
	private int countSwaps;
	private String verboseString;
	private boolean verbose;

	/*
	 * --------------------------------------------------------------------------
	 * Constructors
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Constructor
	 * 
	 * @param arr the array being sorted
	 */
	public HeapSort(int[] arr, boolean verbose)
	{
		this.arrayToSort = arr;
		this.verbose = verbose;
		reset();
	}

	/*
	 * --------------------------------------------------------------------------
	 * Public functions
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Sort the array
	 */
	public void sort()
	{
		reset();

		initializeHeap(parent(arrayToSort.length));

		sort(arrayToSort.length - 1);
	}

	/*
	 * --------------------------------------------------------------------------
	 * Getters & Setters
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Get the Array
	 * 
	 * @return The array
	 */
	public int[] getArray()
	{
		return arrayToSort;
	}

	/**
	 * Set the Array
	 * 
	 * @param arr the array being worked on
	 */
	public void setArray(int[] arr)
	{
		this.arrayToSort = arr;
	}

	/**
	 * Get the number of comparisons made in the last sort.
	 * @return the number of comparisons made
	 */
	public int getCountComparisons()
	{
		return countComparisons;
	}

	/**
	 * Get the number of swaps made in the last sort
	 * @return the number of swaps
	 */
	public int getCountSwaps()
	{
		return countSwaps;
	}
	
	/**
	 * Get verbose String
	 * @return verbose String
	 */
	public String getVerboseString()
	{
		return verboseString;
	}
	
	/**
	 * Gets a string of the results of the sort.
	 * @return Results of the sort
	 */
	public String getResults()
	{
		String results = "";
		
		results += "Number of Comparisons: " + this.countComparisons + "\n";
		results += "Number of Swaps: " + this.countSwaps + "\n";
		
		return results;
	}
	
	/*
	 * --------------------------------------------------------------------------
	 * Private Functions - Heap work
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Initialize the array the first time from an array, to a heap.
	 * 
	 * @param index
	 */
	private void initializeHeap(int index)
	{
		heapify(index, arrayToSort.length);
		if (index > 0)
		{
			initializeHeap(index - 1);
		}
	}

	/**
	 * Recursively swap the root to the end, then heapify that new root down. 
	 * Then recurse with -1 length. This leaves an array
	 * 
	 * @param endIndex last index in the heap
	 */
	private void sort(int endIndex)
	{
		if (endIndex < arrayToSort.length)
		{
			if (endIndex < 2)
			{
				// Swap the last 2
				swap(0, 1);
			} else
			{
				// Swap the root with the last item at this point
				swap(0, endIndex);

				// Fix by heapifying the root down.
				heapify(0, endIndex-1);
				
				//Recurse
				sort(endIndex -1);
			}
		}
	}

	/**
	 * Determine if one of the children is larger than
	 * 
	 * 
	 * @param rootIndex
	 * @param subLength
	 */
	private void heapify(int rootIndex, int subLength)
	{
		int largestIndex = rootIndex; 		// Initialize largest as root
		int leftIndex = left(rootIndex);
		int rightIndex = right(rootIndex);

		// If left child is larger than root
		if (leftIndex <= subLength 
				&& leftIndex < arrayToSort.length 
				&& higherPriority(leftIndex, largestIndex))
		{ // If left is
			largestIndex = leftIndex;
		}

		// If right child is larger than largest so far
		if (rightIndex <= subLength 
				&& rightIndex < arrayToSort.length 
				&& higherPriority(rightIndex, largestIndex))
		{
			largestIndex = rightIndex;
		}

		// If largest is not root
		if (largestIndex != rootIndex)
		{
			swap(rootIndex, largestIndex);

			// Recursively heapify the affected sub-tree
			heapify(largestIndex, subLength);
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * Private Functions - Utility
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Resets the counters for number of
	 * 	- Comparisons
	 * 	- Swaps
	 */
	private void reset()
	{
		countComparisons = 0;
		countSwaps = 0;
		verboseString = "";
	}
	
	/**
	 * 
	 * @param indexA
	 * @param indexB
	 * @return
	 */
	private boolean higherPriority(int indexA, int indexB)
	{
		if (indexA < 0 || 
				indexB < 0 || 
				indexA >= arrayToSort.length || 
				indexB >= arrayToSort.length)
		{
			System.out.println("Bad Index: [" + indexA + "] [" + indexB 
					+ "], length {" + arrayToSort.length + "}");
		}

		//Increment number of comparisons
		countComparisons++;
		
		if (verbose)
		{
			verbose("Compare ["+arrayToSort[indexA]+"] ["+arrayToSort[indexB]+"]");
		}
		
		if (arrayToSort[indexA] > arrayToSort[indexB])
		{
			return true;
		}

		return false;
	}

	/**
	 * Returns the the index which would be the left of the given index.
	 * 
	 * @param parent the index which is the parent
	 * @return the index where the left child of the given parent is.
	 */
	private int left(int parent)
	{
		return 2 * parent + 1;
	}

	/**
	 * Returns the the index which would be the right of the given index.
	 * 
	 * @param parent the index which is the parent
	 * @return the index where the right child of the given parent is.
	 */
	private int right(int parent)
	{
		return 2 * parent + 2;
	}

	/**
	 * Returns the the index which would be the parent of the given index.
	 * 
	 * @param child the index which is the child in question.
	 * @return the index where the parent of the given child is.
	 */
	private int parent(int child)
	{
		if (child % 2 == 0)
		{
			return child / 2 - 1;
		}
		return (child + 1) / 2 - 1;
	}

	/**
	 * Swap two indexes in the Array.
	 * 
	 * @param indexA : index 1
	 * @param indexB : index 2
	 */
	private void swap(int indexA, int indexB)
	{
		if (indexA < 0 || 
				indexB < 0 || 
				indexA >= arrayToSort.length || 
				indexB >= arrayToSort.length)
		{
			/*
			 * throw new Exception("Bad Index: ["+indexA+"] ["+indexB+"], length {" +
			 * array.length + "}" );
			 */
			return;
		}
		
		if (indexA == indexB)
		{
			return;
		}
		
		//Increment the number of swaps done.
		countSwaps++;
		
		//Verbose
		if (verbose)
		{
			verbose("Swap ["+arrayToSort[indexA]+"] ["+arrayToSort[indexB]+"]");
		}
		
		
		int temp = arrayToSort[indexA];
		arrayToSort[indexA] = arrayToSort[indexB];
		arrayToSort[indexB] = temp;
		
		
	}

	/**
	 * Print the string.
	 * 
	 * @param str The string to print verbosely;
	 */
	private void verbose(String str)
	{
		verboseString = getVerboseString() + "C: "+countComparisons+" S: "+countSwaps+" | ";
		verboseString = getVerboseString() + str + "\n";
	}

	
	
}
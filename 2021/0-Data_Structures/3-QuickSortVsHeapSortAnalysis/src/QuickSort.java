/**
 *
 * Inspiration from
 * + https://www.geeksforgeeks.org/quick-sort/
 * + https://www.geeksforgeeks.org/insertion-sort/
 *
 * @author Darrick Ross
 *
 */

public class QuickSort
{

	/*
	 * --------------------------------------------------------------------------
	 * Constants
	 * --------------------------------------------------------------------------
	 */

	//private static final int HSORT1 = 0;
	private static final int QSORT1 = 1;
	private static final int QSORT2 = 2;
	private static final int QSORT3 = 3;
	private static final int QSORT4 = 4;

	private static final int ENDCON_QSORT2 = 100;
	private static final int ENDCON_QSORT3 = 50;
	private static final int ENDCON_GEN = 2;

	private static final int COST_MEDIAN_OF_3 = 4;

	//private static final int DEFAULT_WAIT = 0;

	/*
	 * --------------------------------------------------------------------------
	 * Private Members
	 * --------------------------------------------------------------------------
	 */

	private int[] arrayToSort;
	private int sortType;
	private int countComparisons;
	private int countSwaps;
	//private boolean verbose;
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
	public QuickSort(int[] arr, boolean verbose)
	{
		this.arrayToSort = arr;
		this.verbose = verbose;
		this.sortType = QSORT1;
		reset();
	}


	/**
	 * Constructor + SortType
	 *
	 * @param arr : the array being sorted
	 * @param verbose Whether to record all actions.
	 * @param sortType : The sort type
	 * @throws Exception If sortType invalid.
	 */
	public QuickSort(int[] arr, boolean verbose, int sortType) throws Exception
	{
		this.arrayToSort = arr;
		this.verbose = verbose;
		setSortType(sortType);
		reset();
	}


	/*
	 * --------------------------------------------------------------------------
	 * Public functions
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Sort the array
	 * @throws Exception
	 */
	public void sort() throws Exception
	{
		reset();

		quickSort(0, arrayToSort.length-1);
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
	 * 	Set the sort type for QuickSort
	 *
	 * 1) First item is pivot
	 * 2) Partitions < 100 get insertion sorted
	 * 3) Partitions <  50 get insertion sorted
	 * 4) Pivot is selected as median of (first, middle, last)
	 *
	 *
	 * @param sortType the type of sort
	 * @throws Exception If an invalid Sort Type is passed in.
	 */
	public void setSortType(int sortType) throws Exception
	{
		switch (sortType)
					{
					//If sortType is not any of the following cases, do the default.
						//case HSORT1:
						case QSORT1:
						case QSORT2:
						case QSORT3:
						case QSORT4:
							this.sortType = sortType;
							break;
						default:
						{
							throw new Exception("Invalid Quicksort Type");
						}
					}
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
	 * Private Functions - Sort work
	 * --------------------------------------------------------------------------
	 */

	/**
	 *
	 * @param startIndex first index in the sub region
	 * @param endIndex last index in the sub region
	 * @throws Exception If invalid Indexes is passed in.
	 */
	private void quickSort(int startIndex, int endIndex) throws Exception
	{
		if (	startIndex 	> 	endIndex
				||startIndex 	< 	0
				||endIndex 		< 	0
				||startIndex 	>= 	arrayToSort.length
				||endIndex 		>= 	arrayToSort.length)
			{
				throw new Exception("Invalid indexs, array length {" + arrayToSort.length + "}, start {"+ startIndex + "} end {" + endIndex + "}.");
			}

		int numInSubPartition = endIndex - startIndex + 1;

		//If we have reach the edge case of QSORT2 or QSORT3 insertion sort the rest.
		if (
				(sortType == QSORT2 && numInSubPartition <= ENDCON_QSORT2) ||
				(sortType == QSORT3 && numInSubPartition <= ENDCON_QSORT3)
				)
		{
			insertionSort(startIndex, endIndex);
			return;
		}
		else if (numInSubPartition < ENDCON_GEN)
		{
			if (startIndex != endIndex
					&& higherPriority(startIndex, endIndex))
			{
				swap(startIndex, endIndex);
			}
			return;
		}
		//Otherwise do QuickSort of this subset




		//If doing QSORT4 then...
		if (sortType == QSORT4)
		{
			//Swap the median with the startIndex
			swap(startIndex, medianOfThreeIndexes(startIndex, middleIndex(startIndex,endIndex), endIndex));
		}


		

		int lessThanIndex = startIndex;

		for (int unknownIndex = startIndex+1; unknownIndex <= endIndex; unknownIndex++)
		{
			if (higherPriority(unknownIndex,startIndex))
			{
				lessThanIndex++;
				swap(lessThanIndex,unknownIndex);
			}
		}


		//Swap the pivot into place.
		swap(lessThanIndex, startIndex);

		//recurse the 2 sections, Not counting lessThanIndex as its now sorted.
		if (startIndex < lessThanIndex-1)
		{
			quickSort(startIndex, lessThanIndex -1);
		}

		if (lessThanIndex+1 < endIndex)
		{
			quickSort(lessThanIndex +1, endIndex);
		}
	}

	/**
	 * Sort the sub region using insertions sort
	 * @param startIndex first index in the sub region
	 * @param endIndex last index in the sub region
	 * @throws Exception If invalid Indexes is passed in.
	 */
	private void insertionSort(int startIndex, int endIndex) throws Exception
	{
		if (startIndex 	>= 	endIndex
			||startIndex 	< 	0
			||endIndex 		< 	0
			||startIndex 	>= 	arrayToSort.length
			||endIndex 		>= 	arrayToSort.length)
		{
			throw new Exception("Invalid indexs, array length {" + arrayToSort.length + "}, start {"+ startIndex + "} end {" + endIndex + "}.");
		}


		//
		for (int unsortedIndex = startIndex+1;
						 unsortedIndex <= endIndex
					&& unsortedIndex < arrayToSort.length;
				unsortedIndex++)
		{
			for (int jj = unsortedIndex-1;
						 jj >= startIndex &&
						 jj < endIndex
					&& !higherPriority(jj, jj+1);
					jj--)
			{
				swap (jj, jj+1);
			}
		}
	}



	/*
	 * --------------------------------------------------------------------------
	 * Private Functions - Utility
	 * --------------------------------------------------------------------------
	 */

	/**
	 * Resets:
	 * 	- Comparisons
	 * 	- Swaps
	 * 	- Verbose string
	 */
	private void reset()
	{
		countComparisons = 0;
		countSwaps = 0;
		verboseString = "";
	}

	/**
	 * Returns true if value at indexA < value at indexB
	 * Else false
	 *
	 * @param indexA
	 * @param indexB
	 * @return arrayToSort[indexA] < arrayToSort[indexB]
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

		//If verbose, record action
		if (verbose)
		{
			verbose("Compare ["+arrayToSort[indexA]+"] ["+arrayToSort[indexB]+"]");
		}


		if (arrayToSort[indexA] < arrayToSort[indexB])
		{
			return true;
		}

		return false;
	}

	/**
	 * Swap two indexes in the Array.
	 *
	 * @param indexA : index 1
	 * @param indexB : index 2
	 * @throws Exception In the case of invalid Indexes
	 */
	private void swap(int indexA, int indexB) throws Exception
	{
		if (indexA < 0 ||
				indexB < 0 ||
				indexA >= arrayToSort.length ||
				indexB >= arrayToSort.length)
		{
			throw new Exception("Bad Index: ["+indexA+"] ["+indexB+"], length {" + arrayToSort.length + "}" );
		}

		if (indexA == indexB)
		{
			return;
		}

		//Increment the number of swaps done.
		countSwaps++;


		//If verbose, record action
		if (verbose)
		{
			verbose("Swap ["+arrayToSort[indexA]+"] ["+arrayToSort[indexB]+"]");
		}


		int temp = arrayToSort[indexA];
		arrayToSort[indexA] = arrayToSort[indexB];
		arrayToSort[indexB] = temp;
	}

	/**
	 * Returns the middle index of 2 numbers
	 * @param indexA first index
	 * @param indexB second index
	 * @return int : middle index of 2 numbers
	 */
	private int middleIndex(int indexA, int indexB)
	{
		return (indexA + indexB)/2;
	}

	/**
	 * 	Returns the index which holds the Median of the Three indexes passed in.
	 *
	 * @param indexA 	First index
	 * @param indexB	Second index
	 * @param indexC	Third index
	 * @return	The index which holds the Median of the Three indexes passed in.
	 * @throws Exception if a bad index is passed in.
	 */
	private int medianOfThreeIndexes(int indexA, int indexB, int indexC) throws Exception
	{
		if (indexA < 0 ||
				indexB < 0 ||
				indexC < 0 ||
				indexA >= arrayToSort.length ||
				indexB >= arrayToSort.length ||
				indexC >= arrayToSort.length)
		{
			throw new Exception("Bad Index: ["+indexA+"] ["+indexB +"] ["+indexC+"], length {" + arrayToSort.length + "}" );
		}

		int medianValue = medianOfThreeValues(arrayToSort[indexA],arrayToSort[indexB],arrayToSort[indexC]);

		//factor in the cost of a median of three check.
		countComparisons+=COST_MEDIAN_OF_3;


		//If verbose, record action
		if (verbose)
		{
			verbose("Find Median of ["+indexA+"] ["+indexB +"] ["+indexC+"]");
		}



		if (medianValue == arrayToSort[indexA])
		{
			return indexA;
		}
		else if (medianValue == arrayToSort[indexB])
		{
			return indexB;
		}

		return indexC;
	}




	/**
	 *
	 * Returns the Median of the Three values passed in.
	 *
	 * https://stackoverflow.com/a/19027761/10729819
	 *
	 * @param a First number.
	 * @param b Second number.
	 * @param c Third number.
	 * @return The Median value of the 3 numbers.
	 * @throws Exception
	 */
	private int medianOfThreeValues(int a, int b, int c)
	{
		return Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
	}


	/**
	 * Print the string.
	 *
	 * @param str The string to print verbosely;
	 */
	private void verbose(String str)
	{
		verboseString += "C: "+countComparisons+" S: "+countSwaps+" | ";
		verboseString += str + "\n";
	}



}

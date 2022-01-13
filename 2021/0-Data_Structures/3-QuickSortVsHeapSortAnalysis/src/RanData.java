import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Darrick Ross
 * @Email dross56@jhu.edu
 * 
 */
public class RanData {

	/*
	 *---------------------------------------------------------------------------
	 *	Private Constants
	 *---------------------------------------------------------------------------
	 */
	
	//https://mariadb.com/kb/en/operating-system-error-codes/
	private static final int EC_GOOD = 0;
	//private static final int EC_GEN = 1;
	//private static final int EC_FILE = 2;
	private static final int EC_IO = 5;
	private static final int EC_2MANYARGS = 7;
	private static final int EC_ARG = 22;
	
	private static final String flagSTR_o = "-o";
	private static final String flagSTR_n = "-n";
	private static final String flagSTR_l = "-l";
	private static final String flagSTR_u = "-u";
	private static final String flagSTR_a = "-a";
	private static final String flagSTR_d = "-d";
	private static final String flagSTR_helpShort = "-h";
	private static final String flagSTR_helpLong = "--help";
	
	private static final int MIN_ITEMS = 1;
	private static final int MAX_ITEMS = Integer.MAX_VALUE;
	private static final double MAX_PERCENT = 90.0;
	private static final double MIN_PERCENT = 0.0;
	
	private static final char DELIMITER = '\n';
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Members
	 *---------------------------------------------------------------------------
	 */
	
	private static int numberOfItems;
	private static double duplicateLowerPercent;
	private static double duplicateUpperPercent;
	
	//private static String outputFP;
	private static BufferedWriter outputBW;
	
	private static boolean flag_o;
	private static boolean flag_n;
	private static boolean flag_u;
	private static boolean flag_l;
	private static boolean flag_a;
	private static boolean flag_d;
	
	private static int numberArray[];
	
	/*
	 *---------------------------------------------------------------------------
	 *	Main
	 *---------------------------------------------------------------------------
	 */
	
	public static void main(String[] args) 
	{
		init();
		
		parseArgs(args);
		
		makeArray();
		
		writeItemsToFile();

		cleanExit();
	}
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions - Utility
	 *---------------------------------------------------------------------------
	 */
	
	/**
	 *  Parses the arguments.
	 * @param args	String list of arguments to parse
	 */
	private static void parseArgs(String [] args)
	{
		for (int ii = 0; ii < args.length; ii++)
		{
			//Check for help flag
			if (args[ii].compareToIgnoreCase(flagSTR_helpShort) == 0 ||
					args[ii].compareToIgnoreCase(flagSTR_helpLong) == 0)
			{
				printUsage();
				cleanExit();
			}
			//Check for whether all the other flags have been set already
			//If they have been, then error.
			else if (flag_o && flag_n && flag_u && flag_l && flag_a && flag_d)
			{
				printUsage();
				errorExit("Too many arguments.", EC_2MANYARGS);
			}
			
			//--------------O Flag-------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_o) == 0)
			{
				if (flag_o)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				
				flag_o = true;
				
				//Try to open the next argument as an output file.
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing output filepath.", EC_ARG);
				}
				
				openBW(args[ii+1]);
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}	
			//--------------O Flag-------------------------------------------------
				
			//--------------N Flag-------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_n) == 0)
			{
				if (flag_n)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				
				flag_n = true;
				
				//Try to open the next argument as an output file.
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing amount of random numbers to generate.",EC_ARG);
				}

				try {
					numberOfItems = Integer.parseInt(args[ii+1]);
					
					
					if (numberOfItems < MIN_ITEMS)
					{
						errorExit("Invalid number of items. Number must be:"
								+ "\n\t" + "1) Greater than or equal to " + MIN_ITEMS
								+ "\n\t" + "2) Less than or equal to " + MAX_ITEMS
							,EC_ARG);
					}
					
				} catch (NumberFormatException e) {
					errorExit("Invalid number of items. Number must be Integer.",EC_ARG);
				}
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}	
			//--------------N Flag-------------------------------------------------
				
			
				
			//--------------U Flag-------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_u) == 0)
			{
				if (flag_u)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				
				flag_u = true;
				
				//Try to open the next argument as an output file.
				
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing max duplicate percent.", EC_ARG);
				}
				
				try {
					duplicateUpperPercent = Double.parseDouble(args[ii+1]);
					
					if (duplicateUpperPercent < MIN_PERCENT ||
							duplicateUpperPercent > MAX_PERCENT ||
							(flag_l && duplicateUpperPercent < duplicateLowerPercent))
					{
						errorExit("Invalid max duplicate percent. Number must be:"
								+ "\n\t" + "1) Greater than or equal to " + MIN_PERCENT
								+ "\n\t" + "2) Less than or equal to " + MAX_PERCENT
								+ "\n\t" + "3) Greater than or equal to the min duplicate %"
							,EC_ARG);
					}
					
				} catch (NumberFormatException e) {
					errorExit("Invalid max duplicate percent. Must be decimal or Int.",EC_ARG);
				}
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}
				
			//--------------U Flag-------------------------------------------------
				
			//--------------L Flag-------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_l) == 0)
			{
				if (flag_l)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				
				flag_l = true;
				
				
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing min duplicate percent.", EC_ARG);
				}
				
				try {
					duplicateLowerPercent = Double.parseDouble(args[ii+1]);
					
					if (duplicateLowerPercent < MIN_PERCENT ||
							duplicateLowerPercent > MAX_PERCENT ||
							(flag_u && duplicateUpperPercent < duplicateLowerPercent))
					{
						errorExit("Invalid max duplicate percent. Number must be:"
								+ "\n\t" + "1) Greater than or equal to " + MIN_PERCENT
								+ "\n\t" + "2) Less than or equal to " + MAX_PERCENT
								+ "\n\t" + "3) Less than or equal to the max duplicate %"
							,EC_ARG);
					}
					
				} catch (NumberFormatException e) {
					errorExit("Invalid max duplicate percent. Must be decimal or Int.",EC_ARG);
				}
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
				
			//--------------L Flag-------------------------------------------------
			
			//--------------A Flag-------------------------------------------------
			} else if (flag_a == false && 
					args[ii].compareToIgnoreCase(flagSTR_a) == 0)
			{
				/*
				 * if the flag has not already been parsed, and this is the l flag...
				 */
			
				flag_a = true;

			//--------------A Flag-------------------------------------------------
			
			//--------------D Flag-------------------------------------------------
			} else if (flag_d == false && 
					args[ii].compareToIgnoreCase(flagSTR_d) == 0)
			{
				/*
				 * if the flag has not already been parsed, and this is the l flag...
				 */
			
				flag_d = true;
				
			//--------------D Flag-------------------------------------------------
				
			} else{
				//Some extra item was passed in.
				printUsage();
				errorExit("Unknown Argument. {" + args[ii] + "}.", EC_ARG);
			}
		}//END FOR
		
		
		
		//Check that all the important flags are 
		if (flag_o != true)
			//Was the o flag not set?
		{
			printUsage();
			errorExit("'-o' Output file is Required!", EC_ARG);
		}
		else if (flag_n != true)
			//Was the n flag not set?
		{
			printUsage();
			errorExit("'-n' Number of items to make is required!", EC_ARG);
		}
		else if (flag_l && (flag_u != true))
			//Was the l flag set but not the u flag?
		{
			printUsage();
			errorExit("Can't set minimum duplicate without setting maximum duplicate.", EC_ARG);
		}
		else if (flag_a && flag_d)
		{
			printUsage();
			errorExit("Can not combine reverse sort & forward sort.", EC_ARG);
		}
		
		
		//At this point the input should be good
		//return;
	}

	/**
	 *  Prints a Helpful usage message
	 */
	private static void printUsage()
	{
		System.out.println("Usages:"
				+ "\n"	+ "java RanData --help"
				+ "\n"	+ "java RanData -h"
				+ "\n"	+ "^^^ Help / Print this message ^^^"
				+ "\n"
				+ "\n"	+ "java RanData -o [file] -n [Int]"
				+ "\n"	+ "^^^ Standard Random Number in Random Order ^^^"
				+ "\n"
				+ "\n"	+ "java RanData -o [file] -n [int] -u [Double]"
				+ "\n" 	+ "^^^ Add a percentange of duplicates to use ^^^"
				+ "\n"
				+ "\n"	+ "java RanData -o [file] -n [Int] -u [Double] -l [Double]"
				+ "\n" 	+ "^^^ Make the percentange Random between 2 variables. ^^^"
				+ "\n"
				+ "\n"	+ "java RanData -o [file] -n [Int] -a"
				+ "\n"	+ "java RanData -o [file] -n [int] -u [Double] -a"
				+ "\n"	+ "java RanData -o [file] -n [Int] -u [Double] -l [Double] -a"
				+ "\n" 	+ "^^^ Sort the output so it comes out in ascending order ^^^"
				+ "\n"
				+ "\n"	+ "java RanData -o [file] -n [Int] -d"
				+ "\n"	+ "java RanData -o [file] -n [int] -u [Double] -d"
				+ "\n"	+ "java RanData -o [file] -n [Int] -u [Double] -l [Double] -d"
				+ "\n" 	+ "^^^ Sort the output so it comes out in descending order ^^^"
				+ "\n"
				);
	}
		
	/**
	 * 	Cleanly Exit.
	 */
	private static void cleanExit() {
		closeBW();
		System.exit(EC_GOOD);
	}
	
	
	/**
	 * 	Prints an error message, then exits with the status code sent.
	 * 	"ERROR: {MSG}"
	 * @param msg 			The message to print.
	 * @param exitCode	The Exit code to use
	 */
	private static void errorExit(String msg, int exitCode)
	{
		System.out.println("ERROR: "+msg);
		closeBW();
		System.exit(exitCode);
	}
	
	/**
	 * Initialize variables as needed.
	 */
	private static void init()
	{
		flag_o = false;
		flag_n = false;
		flag_u = false;
		flag_l = false;
		flag_a = false;
		flag_d = false;
		duplicateLowerPercent = 0.0;
		duplicateUpperPercent = 0.0;
	}

	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions - Array Work
	 *---------------------------------------------------------------------------
	 */
	
	/**
	 * Write the items to file.
	 * @return	True if everything was written to file.
	 */
	private static boolean writeItemsToFile()
	{
		// Check the output is opened.
		if (outputBW == null)
		{
			return false;
		}
		
		// Write all items to file
		for (int item: numberArray)
		{
			writeStr(String.valueOf(item), String.valueOf(DELIMITER), outputBW);
		}
		
		return true;
	}
	
	/**
	 *  Swap two indexes in the Array.
	 *  
	 * @param indexA : index 1
	 * @param indexB : index 2
	 * @throws Exception 
	 */
	private static void swap(int indexA, int indexB) {
		if (indexA == indexB ||
				indexA < 0 ||
				indexB < 0 ||
				indexA >= numberArray.length ||
				indexB >= numberArray.length)
		{
			/*
			throw new Exception("Bad Index: ["+indexA+"] ["+indexB+"], length {" +
					array.length + "}" );
			*/
			return;
		}
		
		int temp = numberArray[indexA];
		numberArray[indexA] = numberArray[indexB];
		numberArray[indexB] = temp;		
	}
	
	/**
	 * 
	 * Shuffles the array by performing a number of two way swaps.
	 * The number of swaps are determined by numOfTwoWaySwapsNeeded(array's length)
	 * 
	 * @return True if successful, else False;
	 */
	private static boolean shuffle()
	{
		if (numberArray.length < 2)
		{
			return false;
		}
		
		int numOfSwaps = numOfTwoWaySwapsNeeded(numberArray.length);
		
		for (int ii = 0; numberArray.length > 1 && ii < numOfSwaps; ii++)
		{
			int randomA = randomNumber(0, numberArray.length);
			int randomB = randomNumber(0, numberArray.length);
			
			while (randomA == randomB)
			{
				randomB = randomNumber(0, numberArray.length);
			}
			
			swap(randomA, randomB);	
		}
		
		return true;
	}
	
	/**
	 * Makes the array and sort if needed. Otherwise shuffles the array.
	 */
	private static void makeArray()
	{
		//Number of duplicates
		int numDuplicate = 0;
		if (flag_u && !flag_l)
		{	//Just an upper, make the amount = upper
			numDuplicate = (int) (numberOfItems * duplicateUpperPercent);
		}
		else if (flag_u && flag_l)
		{
			if (duplicateLowerPercent == duplicateUpperPercent)
			{
				numDuplicate = (int) duplicateLowerPercent;
			}
			else
			{
				numDuplicate = randomNumber(duplicateLowerPercent, duplicateUpperPercent);
			}
		} //end else if (flag_u && flag_l)
		
		//make the array of items
		numberArray = new int[numberOfItems];
		
		//Initialize the items that will be unique
		//starting at numDuplicate will mean the amount of duplicate values are
		//already determined.
		for (int ii = numDuplicate, jj = 0; ii < numberOfItems; ii++, jj++)
		{
			numberArray[ii] = jj;
		}
		
		//Initialize the duplicates.
		for (int ii = 0; ii < numDuplicate && ii < numberOfItems; ii++)
		{
			//Get a number between [0, numberOfItems-numDuplicate).
			//That number is a duplicate
			numberArray[ii] = randomNumber(0,numberOfItems-numDuplicate);
		}
		
		if (numDuplicate > 0 && flag_a)
		{
			bubbleSort();
		}
		else if (numDuplicate > 0 && flag_d)
		{
			bubbleSortReverse();
		}
		else
		{
			shuffle();
		}
	}
	
	/**
	 * 	Bubble sort to sort the array forwards
	 * 	I do this because this program is not part of the project in which I
	 *  need to sort for Lab 4. This program only aids in generating the test
	 *  data for Lab 4. Thus I am using a quick and dirty bubble sort.
	 * 
	 * 	https://stackabuse.com/sorting-algorithms-in-java/
	 * 
	 */
	private static void bubbleSort() {
    boolean sorted = false;
    int temp;
    while(!sorted) {
        sorted = true;
        for (int ii = 0; ii < numberArray.length - 1; ii++) {
            if (numberArray[ii] > numberArray[ii+1]) {
                temp = numberArray[ii];
                numberArray[ii] = numberArray[ii+1];
                numberArray[ii+1] = temp;
                sorted = false;
            }
        }
    }
	}
	
	/**
	 * 	Reverse Bubble sort to sort the array forwards
	 * 	I do this because this program is not part of the project in which I
	 *  need to sort for Lab 4. This program only aids in generating the test
	 *  data for Lab 4. Thus I am using a quick and dirty bubble sort.
	 * 
	 * 	https://stackabuse.com/sorting-algorithms-in-java/
	 */
	private static void bubbleSortReverse() {
    boolean sorted = false;
    int temp;
    while(!sorted) {
        sorted = true;
        for (int ii = 0; ii < numberArray.length - 1; ii++) {
            if (numberArray[ii] < numberArray[ii+1]) {
                temp = numberArray[ii];
                numberArray[ii] = numberArray[ii+1];
                numberArray[ii+1] = temp;
                sorted = false;
            }
        }
    }
	}

	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions - Math functions
	 *---------------------------------------------------------------------------
	 */

	/**
	 *  Calculates the number of two way swaps needed to sufficiently randomize
	 *  an array of items with length, `itemsToShuffle`
	 *  
	 *  Based on https://math.stackexchange.com/a/127511
	 * 
	 * @param itemsToShuffle : The number of items in the array.
	 * @return The number of 2 way swaps needed to sufficiently randomize a deck.
	 */
	private static int numOfTwoWaySwapsNeeded(int itemsToShuffle)
	{
		//+1 to round up
		return (int) (Math.log10(itemsToShuffle) * itemsToShuffle * .5) + 1;
	}
	
	/**
	 * 	Returns a random number from min to max.
	 * 	Includes min, excludes max.
	 * 
	 * 	[min, max) does not include max
	 * 
	 * @param min : minimum value returned inclusive
	 * @param max : maximum value returned exclusive
	 * @return a random Int.
	 */
	private static int randomNumber(int min, int max)
	{		
		return (int) (Math.random()*(max - min -1) + min);
	}
	
	/**
	 * 	Returns a random number from min to max.
	 * 	Includes min, excludes max.
	 * 
	 * 	[min, max) does not include max
	 * 
	 * @param min : minimum value returned inclusive
	 * @param max : maximum value returned exclusive
	 * @return random double
	 */
	private static int randomNumber(double min, double max)
	{		
		return (int) (Math.random()*(max - min -1) + min);
	}
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions - BufferWriter
	 *---------------------------------------------------------------------------
	 */

	/**
	 *  Write a string to the output stream.
	 *  
	 *  Utilized from Project0 Java Example.
	 *  
	 *  @param text   The text to write.
	 *  @param delim	The delimiter character for the output
	 *  @param output The output stream to write the text to.
	 */
	private static void writeStr(String text, String delim, BufferedWriter output)
	{
		try {
			output.write(text, 0, text.length());  
			output.write(delim, 0, delim.length());
		} catch (IOException iox) {
			String message = "Failed to write to the BufferWriter!\n" + iox.toString();
			errorExit(message,EC_IO);
		}
	}
	
	/**
	 *  Write a string to the output stream.
	 *  
	 *  Utilized from Project0 Java Example.
	 *  
	 *  @param text   The text to write.
	 *  @param output The output stream to write the text to.
	 */
	@SuppressWarnings("unused")
	private static void writeStr(String text, BufferedWriter output) 
	{
		try {
			output.write(text, 0, text.length());  
			output.newLine();
		} catch (IOException iox) {
			String message = "Failed to write to the BufferWriter!\n" + iox.toString();
			errorExit(message,EC_IO);
		}
	}
	
	/**
	 * 	Tries to open the BufferWriter. If it Error out print that message.
	 */
	private static void openBW(String filepath)
	{
		try
		{
      outputBW = new BufferedWriter(new FileWriter(filepath));
		}
		catch (Exception fileIOException)
		{
			String message = "Failed to open BufferWriter!"
					+ "\n" + "FilePath {" + filepath + "}" 
					+	"\n" + fileIOException.getMessage();
			errorExit(message,EC_IO);
		}
	}
	
	/**
	 * 	Tries to close the BufferWriter. If it Error out print that message.
	 */
	private static void closeBW()
	{
		if (outputBW != null)
		{
			try {
				outputBW.close();
			} catch (IOException fileIOException) {
				fileIOException.printStackTrace();
				String message = "Failed to close BufferWriter!\n" + fileIOException.getMessage();
				errorExit(message,EC_IO);
			}
		}
	}

	
}

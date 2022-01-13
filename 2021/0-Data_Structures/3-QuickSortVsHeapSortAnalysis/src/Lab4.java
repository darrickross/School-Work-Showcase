import java.io.*;

/**
 * 
 * Inspiration from 
 * + https://www.geeksforgeeks.org/file-isdirectory-method-in-java-with-examples/
 * + https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
 * 
 * @author Darrick Ross
 *
 */

public class Lab4 {
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Constants
	 *---------------------------------------------------------------------------
	 */
	private static final String InvokeName = "Java Lab4 ";
	private static final String TAB = "  ";
	private static final String DELIMITER = 
			"====================================================================\n";
	
	//https://mariadb.com/kb/en/operating-system-error-codes/
	private static final int EC_GOOD = 0;
	private static final int EC_GEN = 1;
	//private static final int EC_FILE = 2;
	private static final int EC_IO = 5;
	private static final int EC_2MANYARGS = 7;
	private static final int EC_ARG = 22;
	
	private static final String flagSTR_i = "-i";		// Input
	private static final String flagSTR_if = "-if";	// Input Folder
	private static final String flagSTR_o = "-o";		// Output
	private static final String flagSTR_of = "-of";	// Output Folder
	private static final String flagSTR_v = "-v";		// Verbose
	//private static final String flagSTR_s = "-s";	// Slow Verbose
	//private static final String flagSTR_r = "-r";		// Results
	private static final String flagSTR_t = "-t"; 	// Sort Type
	private static final String flagSTR_helpShort = "-h";
	private static final String flagSTR_helpLong = "--help";
	//private static final String flagSTR_ALL = "--All";
	
	//private static final int MIN_SECONDS = 1;
	//private static final int MAX_SECONDS = 5;
	
	private static final int HSORT1 = 0;
	private static final int QSORT1 = 1;
	private static final int QSORT2 = 2;
	private static final int QSORT3 = 3;
	private static final int QSORT4 = 4;
	
	private static final String[] SORTS = 
		{"HeapSort", "QuickSort1", "QuickSort2", "QuickSort3", "QuickSort4"};
	
	private static final int FORCED_VERBOSE_FILE_SIZE = 50;
	/*
	 *---------------------------------------------------------------------------
	 *	Private Members
	 *---------------------------------------------------------------------------
	 */
	
	//private static String outputFP;
	//private static BufferedWriter outputBW;
	//private static BufferedReader inputBR;
	
	private static boolean flag_i;	// Input
	private static boolean flag_if; // Input folder
	private static boolean flag_o;	// Output
	private static boolean flag_of; // Output folder
	private static boolean flag_t; 	// Sort Type
	private static boolean flag_v;	// Verbose
	//private static boolean flag_s;	// Slow Verbose
	//private static boolean flag_r;	// Print Results
	
	//private static int numSecToWait;
	//private static int inputLength;
	private static int sortType;
	
	//private static int[] numbersToSort;
	
	private static File inputFolder;
	private static File outputFolder;
	private static File inputFile;
	private static File outputFile;
	
	/*
	 *---------------------------------------------------------------------------
	 *	Main
	 *---------------------------------------------------------------------------
	 */
	public static void main(String[] args) {
		init();
		parseArgs(args);
		
		if (flag_o && flag_i)
		{
			executeFile(inputFile, outputFile, flag_v, sortType);
		}
		else if (flag_of && flag_if)
		{
			//https://stackoverflow.com/a/1846349/10729819
			for (final File fileInFolder : inputFolder.listFiles())
			{
				if (fileInFolder.isDirectory() == false) {
          
					//Skip all non files.
					for (int ii = 0; ii < SORTS.length ; ii++)
					{
						File outFile = new File(outputFolder.getAbsolutePath(), 
								fileInFolder.getName() + SORTS[ii] + ".txt");
						
						executeFile(fileInFolder, outFile, false, ii);
						
					}
					
					
					
				}
			}
		}
		else
		{
			printUsage();
			errorExit("Bad arguments, missing either, " + "\n"
					+ "1) input & output file" + "\n"
					+ "2) input & output folder"+ "\n"
					,EC_ARG);
		}
		
		
		cleanExit();
	}
	
	/*
	 *---------------------------------------------------------------------------
	 *	Public Functions - Utility
	 *---------------------------------------------------------------------------
	 */

	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions - Work
	 *---------------------------------------------------------------------------
	 */
	
	private static void executeFile(File inputFile, File outputFile, 
			boolean verbose_param, int sortType)
	{
		int rawArray[] = readInputToArray(inputFile);
		int sortedArray[] = null;
		String result = "Working with input file: \n" + inputFile.getPath() 
			+ "\n";
		
		
		boolean verbose = (verbose_param || rawArray.length <= FORCED_VERBOSE_FILE_SIZE);
		
		try {
			
			/*
			 * The QuickSort and HeapSort and probably even the Insertion sort could
			 *  have been made into a inheritance structure where everything
			 *  inherited a few key methods that each of them use... but I forgot
			 *  that...
			 * 
			 */
			
			switch(sortType)
			{
				//case HSORT1: Make HSORT the default...
				case QSORT1:
				case QSORT2:
				case QSORT3:
				case QSORT4:
					result += "Sort Method: " + SORTS[sortType] + "\n\n";
					QuickSort qs = new QuickSort(rawArray, verbose, sortType);
					qs.sort();
					sortedArray = qs.getArray();
					result += qs.getResults();
					
					
					result += "\n\n";
					
					if (verbose)
					{
						result += DELIMITER;
						result += "Verbose Output: \n";
						result += qs.getVerboseString();
					}
					
					result += DELIMITER;
					result += "Sorted Array: \n\n";
					
					result += intArrayToString(sortedArray);
					
					break;
				default:
				{
					result += "Sort Method: " + SORTS[sortType] + "\n\n";
					HeapSort hs = new HeapSort(rawArray, verbose);
					hs.sort();
					sortedArray = hs.getArray();
					result += hs.getResults();
					
					result += "\n\n";
					
					if (verbose)
					{
						result += DELIMITER;
						result += "Verbose Output: \n";
						result += hs.getVerboseString();
					}
					
					result += DELIMITER;
					result += "Sorted Array: \n\n";
					
					result += intArrayToString(sortedArray);
				}
			}
		}
		catch (Exception e)
		{
			result += DELIMITER;
			result += "ERROR ";
			result += e.getMessage();
			result += "\n";
			result += DELIMITER;
		}
		catch (StackOverflowError e)
		{
			result += DELIMITER;
			result += "ERROR: Stack Overflow during Recursion";
			result += "\n";
			result += DELIMITER;
		}
		
		writeResult(result, outputFile);
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
			else if (	 (flag_i || flag_if) 
							&& (flag_o || flag_of)
							&&  flag_v
							//&& flag_r
							)
			{
				printUsage();
				errorExit("Too many arguments.", EC_2MANYARGS);
			}
			
			//--------------I Flag---------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_i) == 0)
			{
				if (flag_i)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				
				flag_i = true;
				
				//Try to open the next argument as an output file.
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing input filepath.", EC_ARG);
				}
				
				try
				{
					inputFile = new File(args[ii+1]);
					
					if (inputFile.isDirectory() == true)
					{
						printUsage();
						errorExit("Invalid input file. Path to directory provides!"
								, EC_ARG);
					}
					
					
				} catch (Exception fileError)
				{
					errorExit("Invalid input filePath.", EC_ARG);
				}
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}
			//--------------I  Flag--------------------------------------------------
			
			//--------------IF Flag--------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_if) == 0)
			{
				if (flag_if)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				else if (flag_i)
				{
					//ERROR Duplicate flag 
					errorExit("Can not combine '"+flagSTR_if+"' & '"+flagSTR_i+"'."
							,EC_2MANYARGS);
				}
				
				flag_if = true;
				
				//Try to open the next argument as an output file.
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing input directory path.", EC_ARG);
				}
				
				
				//Folder/File given is not valid.
				try
				{
					inputFolder = new File(args[ii+1]);
					
					//The file in question is a file, not a folder
					if (inputFolder.isDirectory() == false)
					{
						printUsage();
						errorExit("Invalid input directory. Path to file provides!"
								, EC_ARG);
					}
					
				} catch (Exception fileError)
				{
					errorExit("Invalid input filePath.", EC_ARG);
				}
				
				
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}
			//--------------IF Flag--------------------------------------------------
			
			//--------------O  Flag--------------------------------------------------
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
				
				try
				{
					outputFile = new File(args[ii+1]);
					
					if (outputFile.isDirectory() == true)
					{
						printUsage();
						errorExit("Invalid output file. Path to directory provides!"
								, EC_ARG);
					}
					
				} catch (Exception fileError)
				{
					errorExit("Invalid input filePath.", EC_ARG);
				}
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}
			//--------------O  Flag--------------------------------------------------
			
			//--------------OF Flag--------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_of) == 0)
			{
				if (flag_of)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				else if (flag_o)
				{
					//ERROR Duplicate flag 
					errorExit("Can not combine '"+flagSTR_of+"' & '"+flagSTR_o+"'."
							,EC_2MANYARGS);
				}
				
				flag_of = true;
				
				//Try to open the next argument as an output file.
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing output filepath.", EC_ARG);
				}
				
				
				//Folder/File given is not valid.
				try
				{
					outputFolder = new File(args[ii+1]);
					
					//The file in question is a file, not a folder
					if (outputFolder.isDirectory() == false)
					{
						printUsage();
						errorExit("Invalid output directory. Path to file provides!"
								, EC_ARG);
					}
					
				} catch (Exception fileError)
				{
					errorExit("Invalid output filePath.", EC_ARG);
				}
				
				
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}
			//--------------IF Flag--------------------------------------------------

			//--------------T Flag---------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_t) == 0)
			{
				if (flag_t)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
			
				flag_t = true;
				
				
				if (ii+1 >= args.length)
				{
					printUsage();
					errorExit("Missing Sort Type.",
							EC_ARG);
				}
				
				try {
					sortType = Integer.parseInt(args[ii+1]);
					
					switch (sortType)
					{
					//If sortType is not any of the following cases, do the default.
						case HSORT1:
						case QSORT1:
						case QSORT2:
						case QSORT3:
						case QSORT4:
							break;
						default:
						{
							printUsage();
							errorExit("Invalid Sort Type selection.",EC_ARG);
						}
					}
					
				} catch (NumberFormatException e) {
					errorExit("Invalid Sort Type selection. Must be Integer.",
							EC_ARG);
				}
				
				//we need to skip over the ii+1 value now that it has been used.
				ii++;
			}
			//--------------T Flag---------------------------------------------------
			
			//--------------v Flag---------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_v) == 0)
			{
				if (flag_v)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
				
				flag_v = true;
			}
			//--------------V Flag---------------------------------------------------
				
			/*
			//--------------R Flag---------------------------------------------------
			else if (args[ii].compareToIgnoreCase(flagSTR_r) == 0)
			{
				if (flag_r)
				{
					//ERROR Duplicate flag
					errorExit("Duplicate flag {"+args[ii]+"}",EC_2MANYARGS);
				}
			
				flag_r = true;
			}
			//--------------R Flag---------------------------------------------------
			*/
			
			else{
				//Some extra item was passed in.
				printUsage();
				errorExit("Unknown Argument. {" + args[ii] + "}.", EC_ARG);
			}
		}//END FOR ----------------------------------------------------------------
		
		
		//Check that all the important flags were set
		checkValidFlags();

		//At this point the input should be good
		//return;
	}

	private static boolean checkValidFlags()
	{
		if (flag_o && flag_of)
		{
			printUsage();
			errorExit("Can not combine '"+flagSTR_of+"' & '"+flagSTR_o+"'."
					,EC_2MANYARGS);
		}
		else if (flag_i && flag_if)
		{
			printUsage();
			errorExit("Can not combine '"+flagSTR_if+"' & '"+flagSTR_i+"'."
					,EC_2MANYARGS);
		}
		else if (flag_o ^ flag_i)
		{
			printUsage();
			errorExit("Must use '"+flagSTR_o+"' & '"+flagSTR_i+"'.", EC_ARG);
		}
		else if (flag_of ^ flag_if)
		{
			printUsage();
			errorExit("Must use '"+flagSTR_of+"' & '"+flagSTR_if+"'.", EC_ARG);
		}
		else if (flag_o && flag_i && !flag_t)
		{
			printUsage();
			errorExit("Must use '"+flagSTR_o+"' & '"+flagSTR_i+"' with '" 
								+flagSTR_t+ "' ." , EC_ARG);
		}
		else if (flag_of && flag_if && flag_t)
		{
			printUsage();
			errorExit("Must use '"+flagSTR_of+"' & '"+flagSTR_if+"' without '" 
								+ flagSTR_t+ "' ." , EC_ARG);
		}
		else if (flag_o && flag_i && 
				outputFile.getAbsolutePath().compareTo(
						inputFile.getAbsolutePath()) == 0
						)
		{
			printUsage();
			errorExit("Input and Output Files can not be the same!", EC_ARG);
		}
		else if (flag_of && flag_if && 
				outputFolder.getAbsolutePath().compareTo(
						inputFolder.getAbsolutePath()) == 0
						)
		{
			printUsage();
			errorExit("Input and Output Folder can not be the same!", EC_ARG);
		}
		
		return true;
	}
	
	/**
	 *  Prints a Helpful usage message
	 */
	private static void printUsage()
	{
		System.out.println("Usages:"
				+ "\n"	+ InvokeName + flagSTR_helpLong
				+ "\n"	+ InvokeName + flagSTR_helpShort
				+ "\n"	+ TAB + "^^^ Help / Print this message"
				+ "\n"
				+ "\n"	+ InvokeName + flagSTR_of + " [Directory] " + flagSTR_if + " [Directory] "
				+ "\n"	+ TAB + "^^^ Run all files in the input folder against all sort methods."
				+ "\n"
				+ "\n"	+ InvokeName + flagSTR_o + " [File] " + flagSTR_i + " [File] " + flagSTR_t + " [#]"
				+ "\n"	+ TAB + "^^^ Standard sorts the input file using the method selected."
				+ "\n"
				+ "\n"	+ TAB + "Sort Methods:"
				+ "\n"	+ TAB + TAB + "0) Heap Sort."
				+ "\n"	+ TAB + TAB + "1) Quick Sort - Partition <   3 is stop case."
				+ "\n"	+ TAB + TAB + "2) Quick Sort - Partition < 101 is stop case, insertion sort the rest."
				+ "\n"	+ TAB + TAB + "3) Quick Sort - Partition <  51 is stop case, insertion sort the rest."
				+ "\n"	+ TAB + TAB + "4) Quick Sort - Partition <   3 is stop case, Median-of-3 is pivot."
				+ "\n"
				+ "\n"	+ InvokeName + flagSTR_o + " [F] " + flagSTR_i + " [F] " + flagSTR_t + " [#] " + flagSTR_v
				+ "\n"	+ InvokeName + flagSTR_of + " [D] " + flagSTR_if + " [D] " + flagSTR_v
				+ "\n" 	+ TAB + "^^^ Be verbose out all actions (Compares/Swaps) taken. "
				+ "\n"
				//+ "\n"	+ InvokeName + flagSTR_o + " [F] " + flagSTR_i + " [F] " + flagSTR_t + " [#] " + flagSTR_r
				//+ "\n"	+ InvokeName + flagSTR_o + " [F] " + flagSTR_i + " [F] " + flagSTR_t + " [#] " + flagSTR_v + " " + flagSTR_r
				//+ "\n" 	+ TAB + "^^^ Print out the results for that search."
				//+ "\n"
				);
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
		System.exit(exitCode);
	}
	
	/**
	 * 	Cleanly Exit.
	 */
	private static void cleanExit() {
		System.exit(EC_GOOD);
	}
	
	/**
	 * Initialize variables as needed.
	 */
	private static void init()
	{
		flag_v = false;
		//flag_s = false;
		//flag_r = false;
		flag_i = false;
		flag_if = false;
		flag_o = false;
		flag_of = false;
		//numSecToWait = 0;
		//inputLength = 0;
		
		inputFolder = null;
		outputFolder = null;
		inputFile = null;
		outputFile = null;
	}
	
	/**
	 * Converts array of integers to string
	 * @param arrayOfInts : the array of integers
	 * @return String version of the integers.
	 */
	private static String intArrayToString(int[] arrayOfInts)
	{
		String str = "";
		
		for (int singleInt: arrayOfInts)
		{
			str += String.valueOf(singleInt);
			str += "\n";
		}
		
		return str;
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
	 *  @param output The output stream to write the text to.
	 */
	private static void writeStr(String text, BufferedWriter output) 
	{
		try {
			if (output == null)
			{
				throw new IOException("BufferWriter is not open");
			}
			output.write(text, 0, text.length());  
			output.newLine();
		} catch (IOException iox) {
			String message = "Failed to write to the BufferWriter!\n" 
					+ iox.toString();
			errorExit(message,EC_IO);
		}
	}
	
	private static void writeResult(String result, File filePath)
	{
		BufferedWriter BWriter = openBW(filePath);
		
		writeStr(result, BWriter);
		
		closeBW(BWriter);
	}
	
	/**
	 * 	Tries to open the BufferWriter. If it Error out print that message.
	 * @return 
	 */
	private static BufferedWriter openBW(File filepath)
	{
		
		BufferedWriter BufWriter = null;
		
		try
		{
			BufWriter = new BufferedWriter(new FileWriter(filepath));
		}
		catch (Exception fileIOException)
		{
			String message = "Failed to open BufferWriter!"
					+ "\n" + "FilePath {" + filepath + "}" 
					+	"\n" + fileIOException.getMessage();
			errorExit(message,EC_IO);
		}
		
		return BufWriter;
	}
	
	/**
	 * 	Tries to close the BufferWriter. If it Error out print that message.
	 */
	private static void closeBW(BufferedWriter BufWriter)
	{
		if (BufWriter != null)
		{
			try {
				BufWriter.close();
			} catch (IOException fileIOException) {
				fileIOException.printStackTrace();
				String message = "Failed to close BufferWriter!\n" 
						+ fileIOException.getMessage();
				errorExit(message,EC_IO);
			}
		}
	}

	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions - BufferReader
	 *---------------------------------------------------------------------------
	 */
	
	/**
	 * 	Tries to open the BufferReader. If it Error out print that message.
	 */
	private static BufferedReader openBR(File filepath)
	{
		BufferedReader BufReader = null;
		
		try
		{
			/*
			 * Open the input
			 * Get the length of the input
			 * close the input
			 * Open the input again so it starts at 0;
			 */
			BufReader = new BufferedReader(new FileReader(filepath));
		}
		catch (Exception fileIOException)
		{
			String message = "Failed to open BufferReader!"
					+ "\n" + "FilePath {" + filepath + "}" 
					+	"\n" + fileIOException.getMessage();
			errorExit(message,EC_IO);
		}
		
		return BufReader;
	}
	
	/**
	 * 	Tries to close the BufferReader. If it Error out print that message.
	 */
	private static void closeBR(BufferedReader BufReader)
	{
		if (BufReader != null) {
			try {
				BufReader.close();
			} catch (IOException fileIOException) {
				//fileIOException.printStackTrace();
				//String message = "Failed to close BufferReader!\n" 
				//	+ fileIOException.getMessage();
				//errorExit(message, EC_IO);
			}
		}
	}

	/**
	 * Reads the next line of input from the input stream.
	 * 
	 * @return a String representing the next line of the input stream.
	 */
	private static String readLine(BufferedReader reader)
	{
		String str = "";
		
		try
		{
			if (reader != null)
			{
				str = reader.readLine();
			}
			else
			{
				str = "Tried to readLine() on a Null BufferReader!\n";
				errorExit(str, EC_IO);
			}
		}
		catch (IOException ReadIOException)
		{
			ReadIOException.printStackTrace();
			str = "Failed to readLine() from BufferReader!\n" 
					+ ReadIOException.getMessage();
			errorExit(str, EC_IO);
		}
		
		return str;
	}

	/**
	 * Read in the data from the BR to the 
	 * @return true if success, else false
	 */
	private static int[] readInputToArray(File fileIn)
	{
		int length = getLineCount(fileIn);
		
		if (length < 1)
		{
			errorExit("Problem with inputLength (len < 1) @ readInputToArray()",
					EC_GEN);
		}
		
		BufferedReader BufReader = openBR(fileIn);
		
		int inputArray[] = new int[length];
		int ii;
		
		String str = readLine(BufReader);
		for(ii = 0; 
				ii < length && 
				str != null && 
				str.replaceAll("\\s", "").length() != 0;
				ii++)
		{
			/*
			 * I use a String library function here to check for whitespace so that
			 * I can quickly identify if the line is blank. This does not impact the
			 * actual assignment as this does not help me "parse" the string at all.
			 */
			
			try {
				inputArray[ii] = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				//what to do about bad data???
				
				//Skip this line without skipping array indexes.
				ii--;
				
				//return false; instead skip the line??
			}
			
			str = readLine(BufReader);
		}
		
		closeBR(BufReader);
		
		//If the array is too large, ie there was a difference in the array size
		//compared to the actual input.
		if (ii < length)
		{
			int[] tempArray = new int[ii];
			for (; ii >= 0; ii--)
			{
				tempArray[ii] = inputArray[ii];
			}
			
			return tempArray;
		}
		
		return inputArray;
	}
	
	private static int getLineCount(File fileIn)
	{
		int count = 0;
		
		try {
			BufferedReader BReader = new BufferedReader(new FileReader(fileIn));
			
			while (BReader.readLine() != null)
			{
				count++;
			}
			
			BReader.close();
		
		} catch (IOException ReadIOException) {
			ReadIOException.printStackTrace();
			errorExit("Problem during getLineCount()", EC_GEN);
		}
		
		return count;
	}
	


/*
 *---------------------------------------------------------------------------
 *	Private Functions - Special Tests
 *---------------------------------------------------------------------------
 */

	@SuppressWarnings("unused")
	private static void customTest()
	{
		//HeapSort
	}

}
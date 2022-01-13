
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author Darrick Ross
 * @Email dross56@jhu.edu
 *
 */
public class Lab2 {

	private final static String outputDelim = "\n========================================\n";
	
	
	/**
	 * @param args: the arguments of the program.
	 */
	public static void main(String[] args) {
		// TODO generate extreme edge cases (no ops, all ops, ops before things, 1 thing before ops).
		
		BufferedReader input;
		BufferedWriter output;
		Lab2 labObj;
		String resultStr = null;
		String errorMsg;
		
		if (args.length != 2)
		{
			System.out.println("Usage: \n\t"
					+ "java Lab2 [input filepath] [output filepath]");
      System.exit(1);
		}
		
		
		//args.length == 2
		
		//Open the files
		try
		{
			input = new BufferedReader(new FileReader(args[0]));
      output = new BufferedWriter(new FileWriter(args[1]));
		}
		catch (Exception fileIOException)
		{
			System.err.println(fileIOException.getMessage());
			return;
		}
		
		labObj = new Lab2();
		
		
		/*
		 * For each line of the input, run it through the Machine Language 
		 * Generator. Write the result to the output. File write errors
		 * 
		 */
		
		String inputLine = labObj.readInputToStr(input);
		while(inputLine != null && inputLine.replaceAll("\\s", "").length() != 0)
		{
			/*
			 * I use a String library function here to check for whitespace so that
			 * I can quickly identify if the line is blank. This does not impact the
			 * actual assignment as this does not help me "parse" the string at all.
			 */
			
			//Write a message displaying the current input after placing a delimiter.
			String outputMsg = outputDelim + "Input: [" + inputLine + "]\n" + "Output:";
			labObj.writeResult(outputMsg, output);
			
			//Create a new MachineLangGen Object.
			//But clear all whitespace characters.
			PrefixToPostfix p2p = new PrefixToPostfix(inputLine.replaceAll("\\s", ""));
			
			//Parse the file, if it errors write the message sent.
			try
			{
				resultStr = p2p.parse();
				
				// If the file write fails run a different catch.
				try
				{
					labObj.writeResult(resultStr, output);
				}
				catch (Exception fileIOException)
				{
					System.err.println(fileIOException.getMessage());
					labObj.closeFiles(input, output);
					return;
				}
				
			}
			// Catch for the parse()
			catch (Exception e)
			{
				errorMsg = "ERROR parsing ["+inputLine+"]"+"\n"+e.getMessage();
				labObj.writeResult(errorMsg, output);
			}
			
			// Get the next set of inputs
			inputLine = labObj.readInputToStr(input);
		}
		
		labObj.closeFiles(input, output);
		
		
	}
	
	
	/**
   *  Reads the next line of input from the input stream.
   *  
   *  @param input The output stream to write the text to.
   *  @return a String representing the next line of the input stream.
   */
	private String readInputToStr(BufferedReader inputStream)
	{
		String str = "";
		
		try
		{
			str = inputStream.readLine();
		}
		catch (IOException ReadIOException)
		{
			System.err.println(ReadIOException.toString());
      System.exit(2);
		}
		
		return str;
	}

	/**
   *  Writes a string array's content to the output stream provided.
   *  Adapted from Project0 Java Example.
   *  
   *  @param strList  		The string array of text to write.
   *  @param outputStream The output stream to write the text to.
   */
	@SuppressWarnings("unused") //TODO REMOVE THIS IF NOT NEEDED
	private void writeResult(String[] strList, BufferedWriter outputStream) {

		try {
			for (String line: strList)
			{
				outputStream.write(line, 0, line.length());  
				outputStream.newLine();
			}
		} catch (IOException iox) {
			System.err.println(iox.toString());
			System.exit(3);
		}

		return;
	}

	/**
	 *  Write a string to the output stream.
	 *  
	 *  Utilized from Project0 Java Example.
	 *  
	 *  @param text   The text to write.
	 *  @param output The output stream to write the text to.
	 */
	private void writeResult(String text, BufferedWriter output) {

		try {
			output.write(text, 0, text.length());  
			output.newLine();
		} catch (IOException iox) {
			System.err.println(iox.toString());
			System.exit(3);
		}

		return;
	}
	
	
	/**
   *  Closes input and output files streams.
   *  
   *  @param input  The BufferedReader stream to close
   *  @param output The BufferedWriter stream to close
   */
	private void closeFiles(BufferedReader input, BufferedWriter output)
	{
		try {
			input.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

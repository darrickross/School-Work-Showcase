
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
public class Lab3 {

	private final static String outputDelim = "\n========================================\n";
	private final static String GAME_LOSS = "Computer lost the game";
	private final static String GAME_WIN = "Computer WON the game";
	private final static String GAME_POINT = "Computer complete a stack";
	
	/**
	 * @param args: the arguments of the program.
	 */
	public static void main(String[] args) {
		
		BufferedReader input = null;
		BufferedWriter output = null;
		HybridDeckArray game;
		
		try
		{
			if (args.length == 1)
			{
	      output = new BufferedWriter(new FileWriter(args[0]));
	      
	      game = new HybridDeckArray();
			} else if (args.length == 2)
			{
				input = new BufferedReader(new FileReader(args[0]));
	      output = new BufferedWriter(new FileWriter(args[1]));
	      String customDeck = readInputToStr(input);
	      
	      game = new HybridDeckArray(customDeck);
			} else 
			{
				System.out.println("Usage: \n\t"
						+ "java Lab3 [input filepath] [output filepath]"
						+ "\n\t"
						+ "java Lab3 [output filepath]");
	      System.exit(1);
	      return; //Make compiler happy about using things before initializing them.
			}
			
			playGame(game, output);
			
			
			
			
			
			
		}
		catch (Exception fileIOException)
		{
			System.err.println(fileIOException.getMessage());
			return;
		}
		
		
		if (input != null)
		{
			closeFile(input);
		}
		closeFile(output);
		
		
	}

	
	private static void playGame(HybridDeckArray game, BufferedWriter outputStream)
	{
		boolean continueGame = true;
		
		while (continueGame == true)
		{
			writeResult(game.toString(), outputStream);
			String result = game.computerMakeMove();
			System.out.print(game.toString());
			
			if (result == null)
			{
				writeResult(GAME_LOSS, outputStream);
				continueGame = false;
				return;
			}
			
			writeResult(result, outputStream);
			
			int condition = game.checkWin();
			
			if (condition == 2)
			{
				writeResult(GAME_WIN, outputStream);
				continueGame = false;
				return;
			} else if (condition == 1)
			{
				writeResult(GAME_POINT, outputStream);
				continueGame = false;
				return;
			}
			
			writeResult(outputDelim, outputStream);
		}
	}
	
	/**
   *  Reads the next line of input from the input stream.
   *  
   *  @param input The output stream to write the text to.
   *  @return a String representing the next line of the input stream.
   */
	private static String readInputToStr(BufferedReader inputStream)
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
	private static void writeResult(String text, BufferedWriter output) {

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
   *  Closes input file stream.
   *  
   *  @param input  The BufferedReader stream to close
   */
	private static void closeFile(BufferedReader input)
	{
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
   *  Closes output file stream.
   *  
   *  @param output The BufferedWriter stream to close
   */
	private static void closeFile(BufferedWriter output)
	{
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

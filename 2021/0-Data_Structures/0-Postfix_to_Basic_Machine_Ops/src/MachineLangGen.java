/**
 * @author Darrick Ross
 * @Email dross56@jhu.edu
 *
 */
public class MachineLangGen {
	/** The Temporary Variable name	 */
	private static final String TEMP_VAR_STR = "TEMP";
	/** The number the Temporary variable starts at */
	private static final int TEMP_VAR_START_NUM = 1;
	
	/*
	 * The following are the Instruction Sets,
	 * LD: Load
	 * ST: Store
	 * AD: Add
	 * SB: Subtract
	 * ML: Multiply
	 * DV: Divide
	 * EX: Exponent
	 * 
	 */
	private static final String INSTRUCTION_LD = "LD ";
	private static final String INSTRUCTION_ST = "ST ";
	private static final String INSTRUCTION_AD = "AD ";
	private static final String INSTRUCTION_SB = "SB ";
	private static final String INSTRUCTION_ML = "ML ";
	private static final String INSTRUCTION_DV = "DV ";
	private static final String INSTRUCTION_EX = "EX ";
	private static final String INSTRUCTION_MD = "MD ";
	
	
	/**
	 * An enum to hold possible operator types,
	 * ADD
	 * SUBTRACT
	 * MULTIPLY
	 * DIVIDE
	 * EXPONENT
	 * NOT_AN_OP
	 */
	private enum Operator {
		NOT_AN_OP, ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, MODULUS
	}

	private String inputStr;
	
	/** 
	 * The current temporary variable count the system is on.
	 */
	private int tempVariableCount;
	
	
	/**
	 * Constructor
	 * 
	 * @param input - the input string in use.
	 */
	MachineLangGen(String input)
	{
		inputStr = input;
		tempVariableCount = TEMP_VAR_START_NUM;
	}
	
	/**
	 * Test the current character and return the type of operator it is.
	 * 
	 * 
	 * @param input - The input character to be tested.
	 * @return Operator - containing the type of Operator the input character is.
	 * If its not a supported operator it returns NOT_AN_OP.
	 */
	private Operator operatorType(char input)
	{
		switch (input)
		{
		case '+':
			return Operator.ADD;
			//break;
		case '-':
			return Operator.SUBTRACT;
			//break;
		case '*':
			return Operator.MULTIPLY;
			//break;
		case '/':
			return Operator.DIVIDE;
			//break;
		case '$':
			return Operator.EXPONENT;
			//break;
		case '%':
			return Operator.MODULUS;
			//break;
		default:
			return Operator.NOT_AN_OP;
			//break;
		}
			
	}
	
	/**
	 * Returns the next temporary variable the system can use.
	 * 
	 * @return Returns a String containing the next temporary variable not yet 
	 * used.
	 */
	private String getNextTempVar()
	{
		String strToReturn = TEMP_VAR_STR + tempVariableCount;
		tempVariableCount++;
		return strToReturn;
	}
	
	/**
	 * Returns the result of a binary operation. The result of the operation
	 * takes into account if one parameter is 0 or 1, returning values based on
	 * arithmetic rules. Otherwise it returns the next Temporary variable.
	 * 
	 * @param operator - The type of binary operation
	 * @param parameter1 - The first operand of the operation
	 * @param parameter2 - The second operand of the operation
	 * 
	 * @return - Returns the result of the operation.
	 * 
	 * @throws Exception if the function is passed an operator type which is 
	 * not a binary operation.
	 */
	private String binaryOperation(
			final Operator operator, 
			final String parameter1, 
			final String parameter2
			) throws Exception
	{
		switch (operator)
		{
		case ADD:
			/*
			 * 	0+B = B
			 * 	A+0 = A
			 * 	A+B = TEMP#
			 */
			if (parameter1.compareTo("0") == 0)
			{
				return parameter2;
			}
			else if (parameter2.compareTo("0") == 0)
			{
				return parameter1;
			}
			else
			{
				return getNextTempVar();
			}
			//break;
		case SUBTRACT:
			/*
			 * 	0-B = TEMP# (technically its `-B`)
			 * 	A-0 = A
			 * 	A-B = TEMP#
			 */
			if (parameter2.compareTo("0") == 0)
			{
				return parameter1;
			}
			else
			{
				return getNextTempVar();
			}
			//break;
		case MULTIPLY:
			/*
			 * 	0*B = 0
			 * 	A*0 = 0
			 *  A*1 = A
			 *  1*B = B
			 * 	A*B = TEMP#
			 */
			if (parameter1.compareTo("0") == 0 || parameter2.compareTo("0") == 0)
			{
				return "0";
			}
			else if (parameter2.compareTo("1") == 0)
			{
				return parameter1;
			}
			else if (parameter1.compareTo("1") == 0)
			{
				return parameter2;
			}
			else
			{
				return getNextTempVar();
			}
			//break;
		case DIVIDE:
			/*
			 * 	0/B = 0
			 * 	A/0 = ERROR
			 * 	A/1 = A
			 * 	A/B = TEMP#
			 */
			if (parameter1.compareTo("0") == 0)
			{
				return "0";
			}
			else if (parameter2.compareTo("0") == 0)
			{
				throw new Exception("ERROR: Divide by 0.");
			}
			else if (parameter2.compareTo("1") == 0)
			{
				return parameter1;
			}
			else
			{
				return getNextTempVar();
			}
			//break;
		case MODULUS:
			/*
			 * 	0%B = 0
			 * 	A%0 = ERROR
			 * 	A%1 = 0
			 * 	1%B = 1
			 * 	A%B = TEMP#
			 */
			if (parameter1.compareTo("0") == 0)
			{
				return "0";
			}
			else if (parameter2.compareTo("0") == 0)
			{
				throw new Exception("ERROR: Modulus Divide by 0.");
			}
			else if (parameter2.compareTo("1") == 0)
			{
				return "0";
			}
			else if (parameter1.compareTo("1") == 0)
			{
				return "1";
			}
			else
			{
				return getNextTempVar();
			}
			//break;
		case EXPONENT:
			/*
			 * 	0^B = 0
			 * 	A^0	= 1
			 *  A^B = TEMP#
			 */
			if (parameter1.compareTo("0") == 0)
			{
				return "0";
			}
			else if (parameter2.compareTo("0") == 0)
			{
				return "1";
			}
			else
			{
				return getNextTempVar();
			}
			//break;
		default:
			throw new Exception("binaryOperation() was passed a non binary operator.");
		}
	}
	
	/**
	 * Parses the inputString. For each character, the following is done:
	 * 	Whitespace is skipped,
	 * 	If its an operator Handle the operation,
	 * 	Otherwise its treated as an operand, and placed onto the working 
	 * 		inputStack.
	 * 
	 * 
	 * @return Returns a String array containing the machine language results.
	 * @throws throws an Exception if, 1) 
	 */
	public String[] parse() throws Exception
	{
		Stack operandStack = new Stack();
		Stack outputStack = new Stack();
		
		if (inputStr.length() < 3)
		{
			throw new Exception(
					"ERROR: Expected input with more than 2 Characters, Input: ["+
							inputStr+
							"]."
					);
			
		}
		
		/*
		 * Iterate over all the characters in the stack.
		 */
		for (int ii = 0; ii < inputStr.length(); ii++)
		{
			if (Character.isWhitespace(inputStr.charAt(ii)))
			{
				/*
				 * If the character presented is a whitespace character then skip this
				 * character.
				 * 
				 * I realize this is a special function, but its that or I do a long
				 * If statement or-ing ' ' \t \r \n \x0b
				 */
				continue;
			}
			
			//Get the current items type
			Operator currentItemOperatorType = operatorType(inputStr.charAt(ii));
			
			/*
			 * If the item is an operator
			 */
			if (currentItemOperatorType != Operator.NOT_AN_OP)
			{
				/*
				 * Because all possible operators are binary, shortcut is to check to make sure
				 * the stack has enough other items on it to operate on.
				 */
				if (operandStack.getSize() > 1)
				{
					/*
					 * Pop off the 2 items and use them as inputs,
					 * take the return and send it back onto the stack.
					 * 
					 * Record the machine language steps as you go along.
					 */

					String item2 = operandStack.pop();
					String item1 = operandStack.pop();
					String result = binaryOperation(currentItemOperatorType, 
							item1, item2);
					
					operandStack.push(result);

					addInstruction(currentItemOperatorType, outputStack, 
							item1, item2, result);
				}
				else
				{
					throw new Exception(
							"ERROR: Expected 2+ values on the stack, stack size = ["+
								operandStack.getSize()+ 
								"]\n"+
								"Op ["+
								inputStr.charAt(ii)+
								"] @pos: "+
								ii+
								"\n"+
								"Operand Stack:"+
								operandStack.toString()
							);
				}
			}
			else
			{
				//item is a operand
				operandStack.push(String.valueOf(inputStr.charAt(ii)));
			}
		}
		
		if (operandStack.getSize() > 1)
		{
			throw new Exception(
					"ERROR: After parsing, the stack was left with " +
						operandStack.getSize() +
						" operands. Stack: " +
						operandStack.toString());
		}
		
		/*
		 * I wrote a function in my stack implementation which would take the 
		 * stack, and return a string array of the current stack.
		 */
		return outputStack.getStringArray();
	}

	
	/**
	 * Pushes instructions based on the Operator type, items, and result, to the
	 * instructionStack
	 * 
	 * 
	 * @param currentItemOperatorType - The type of operation.
	 * @param instructionStack - The instruction stack.
	 * @param item1 - The first input to the binary operation.
	 * @param item2 - The second input to the binary operation.
	 * @param result - The result of the binary operation.
	 * @throws Exception if the Operator type is not a supported operation.
	 */
	private void addInstruction(MachineLangGen.Operator currentItemOperatorType, 
			Stack instructionStack, 
			String item1, 
			String item2,
			String result
			) throws Exception 
	{
		/*
		 * 	Order:
		 * 		LD item1
		 * 		@@ item2
		 * 		ST result
		 */
		
		// 	LD item1
		instructionStack.push(INSTRUCTION_LD + item1);
		
		//	@@ item2
		switch(currentItemOperatorType)
		{
		case ADD:
			instructionStack.push(INSTRUCTION_AD + item2);
			break;
		case SUBTRACT:
			instructionStack.push(INSTRUCTION_SB + item2);
			break;
		case MULTIPLY:
			instructionStack.push(INSTRUCTION_ML + item2);
			break;
		case DIVIDE:
			instructionStack.push(INSTRUCTION_DV + item2);
			break;
		case EXPONENT:
			instructionStack.push(INSTRUCTION_EX + item2);
			break;
		case MODULUS:
			instructionStack.push(INSTRUCTION_MD + item2);
			break;
		default:
			throw new Exception(
					"ERROR: addInstructions() was passed a non valid operator.");
		}
		
		//ST result
		instructionStack.push(INSTRUCTION_ST + result);
		
	}
	
	
}

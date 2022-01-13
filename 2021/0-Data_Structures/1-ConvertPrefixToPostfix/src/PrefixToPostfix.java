
/**
 * @author Darrick Ross
 * @Email dross56@jhu.edu
 *
 */
public class PrefixToPostfix {
	
	//Current string set to be parsed.
	private String input;
	
	/**
	 * Default constructor.
	 */
	public PrefixToPostfix() {
		input = null;
	}
	
	/**
	 * Constructor with input.
	 * @param parm_input : String input to set initially.
	 */
	public PrefixToPostfix(String parm_input) {
		this.input = parm_input;
	}
	
	/**
	 *  Setter for input
	 * @param parm_input : String to set input as.
	 */
	public void setInput(String parm_input) {
		this.input = parm_input;
	}
	
	
	/**
	 * Overload of parse()
	 * 
	 * If input is provided, it is first set, then parsed. 
	 * 
	 * The result is returned.
	 * 
	 * @param parm_input : Custom string to use as input.
	 * @return String : Resulting postfix, or error message.
	 */
	public String parse(String parm_input)
	{
		this.setInput(parm_input);
		return parse();
	}
	
	/**
	 * 	The function which calls the recursive parse function.
	 * 
	 * 	Checks for a few input errors before starting.
	 * @return String: The result of the input.
	 */
	public String parse()
	{
		if (this.input == null)
		{
			return "ERROR: Null input";
		}
		else if (this.input.length() < 3)
		{
			return "ERROR: Input too small, size="+this.input.length();
		}
		else if (isOperator(this.input.charAt(0)) == false)
		{
			return "ERROR: Missing leading Operator";
		}
		
		
		try {
			return parse_recursive(0);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	/**
	 * 	Parse the sub problem, return a string:
	 * 		Operand1 + Operand2 + Operator
	 * 
	 * @param parm_index : The index of the start of the subproblem.
	 *	Must be operator.
	 * @return String: The result of the subproblem.
	 * @throws Exception If a subproblem goes out of bounds while parsing.
	 */
	private String parse_recursive(int parm_index) throws Exception
	{
		if (parm_index +2 >= this.input.length())
		{
			throw new Exception("ERROR: Out of bounds while parsing.");
		}
		
		String firstOperand;
		String secondOperand;
		int secondOperandIndex = parm_index+2;		
		
		//if the first operand is an operator
		if (isOperator(this.input.charAt(parm_index+1)))
		{
			//Recurse
			firstOperand = parse_recursive(parm_index + 1);
		
			//based on how many characters this returned,
			// skip that many characters to find the next
			secondOperandIndex = parm_index + 1 + firstOperand.length();
			if (secondOperandIndex >= this.input.length())
			{
				throw new Exception("ERROR: Out of bounds while parsing.");
			}
		}
		else
		{
			firstOperand = String.valueOf(this.input.charAt(parm_index+1));
		}
		
		
		
		//if the second operand is an operator
		if (isOperator(this.input.charAt(secondOperandIndex)))
		{
			//Recurse
			secondOperand = parse_recursive(secondOperandIndex);
		}
		else
		{
			secondOperand = String.valueOf(this.input.charAt(secondOperandIndex));
		}
		
		String result = firstOperand 
				+ secondOperand
				+ this.input.charAt(parm_index);
		
		return result;
	}
	
	/**
	 * 	Returns if the character is an operator or not.
	 * 
	 * 	Uses the fact that switch cases will continue into the next case if 
	 * 	there is no return or break. This way if any of the cases fire
	 * 	True is returned. If default is reached false is returned.
	 * 
	 * 	@param 	parm_input item to test as an operator.
	 * 	@return	Returns true if parm_input is an operator character. Else false.
	 */
	private Boolean isOperator(char parm_input)
	{
		switch (parm_input)
		{
		case '+':
		case '-':
		case '*':
		case '/':
		case '$':
		case '%':
		case '^':
			//	A bit of a cheat. if any of the cases fire it returns true.
			return true;
		default:
			//	Otherwise it returns false.
			return false;
		}
	}	
}

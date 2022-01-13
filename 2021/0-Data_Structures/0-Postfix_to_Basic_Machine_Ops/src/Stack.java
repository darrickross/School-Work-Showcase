/**
 * @author Darrick Ross
 * @Email dross56@jhu.edu
 *
 */
public class Stack {
	
	private static final int DEFAULT_CAPACITY = 10;
	private static final int RESIZE_FACTOR = 2;

	private static final String BLANK_STRING = null;
	
	private String internalStackArray[];
	private int top;
	private int stackCapacity;
	
	
	/**
	 * Constructor with starting size
	 * 
	 * @param size
	 */
	Stack(final int size) 
	{
		internalStackArray = new String[size];
		stackCapacity = size;
		top = 0;
	}
	
	/**
	 * Default Constructor
	 */
	Stack()
	{
		internalStackArray = new String[DEFAULT_CAPACITY];
		stackCapacity = DEFAULT_CAPACITY;
		top = 0;
	}
	
	/**
	 * Resizes the stack by doubling the capacity
	 * 
	 * @throws Exception if the capacity overflows.
	 */
	private void resize() throws Exception
	{
		// Up the array capacity
		stackCapacity = stackCapacity * RESIZE_FACTOR;
		
		//check that this didn't overflow.
		if (stackCapacity < 1)
		{
			throw new Exception("'int capacity' overflow, during resize().");
		}
		
		//Create the new array
		String tempArr[] = new String[stackCapacity];
		
		/*
		 * For each item in the old array, place it into the new array at the same spot.
		 * 
		 */
		int counter = 0;
		for(String item: internalStackArray)
		{
			if (counter < stackCapacity)
			{
				tempArr[counter] = item;
				counter++;
			}
		}
		internalStackArray = tempArr;
	}
	
	/**
	 * Returns if the stack is empty.
	 * 
	 * @return Boolean of whether the stack is empty
	 */
	public boolean isEmpty()
	{
		if (top == 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns if the stack is full.
	 * 
	 * @return Boolean of whether the stack is full
	 */
	public boolean isFull()
	{
		if (top == stackCapacity)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the current stack size.
	 * 
	 * Due to `top` representing the next index for items size = top
	 * 
	 * @return the size of the stack.
	 */
	public int getSize()
	{
		return top;
	}
	
	/**
	 * Overloads the toString function, returning a string representation of the
	 * object. Only print the non NULL spots.
	 *
	 * @return String - A string representation of the object.
	 */
	public String toString()
	{
		String output = "cap: " + stackCapacity + " {";
		for (String item: internalStackArray)
		{
			if (item == null)
			{
				break;
			}
			output += "[" + item + "], ";
		}
		output += "}";
		return output;
	}
	
	/**
	 * Adds an item to the top of the stack.
	 * 
	 * @param itemToAdd - the Item to add to the top of the stack
	 * @throws Exception if when resizing the array the stack size overflows.
	 */
	public void push(final String itemToAdd) throws Exception
	{
		//If the stack is at capacity, resize it and continue on.
		if (this.isFull())
		{
			//resize the stack;
			resize();
		}
		
		//place the item into the array
		internalStackArray[top] = itemToAdd;
		
		//increment top;
		top++;
	}
	
	/**
	 * Pop the top item off the stack, returning that item.
	 * 
	 * @return String - The item on the top of the stack.
	 * @throws Exception if the stack is empty when this is called.
	 */
	public String pop() throws Exception
	{
		// Check for empty
		if (isEmpty())
		{
			throw new Exception("ERROR, called pop() on an empty stack.");
		}
		
		/*
		 * Top points to the next location on the stack, thus decrementing 
		 * leaves top at the position of the item at the current top of the 
		 * array to be removed and returned.
		 */
		top--;
		
		//Save the value
		String popValue = internalStackArray[top];
		
		//cleans up the array when printing for debug purposes.
		internalStackArray[top] = BLANK_STRING;		
		
		//return the value
		return popValue;
	}
	
	/**
	 * Returns a copy of the item on the top of the stack.
	 * 
	 * @return String - A copy of the item on the top of the stack.
	 * @throws Exception if the stack is empty when this is called.
	 */
	public String peek() throws Exception
	{
		// Check for empty
		if (isEmpty())
		{
			throw new Exception("ERROR, called pop() on an empty stack.");
		}
		
		//Top is the index 1 above the current head of the stack.
		return new String(internalStackArray[top-1]);
	}
	
	/**
	 * Returns a copy of the stacks internal array. 
	 * This is helpful when you add items into a stack and at the end want them
	 * placed into a String array.
	 * 
	 * @return String[] which is a copy of the internal stack array.
	 */
	public String[] getStringArray()
	{
		String[] strArrayToReturn = new String[this.getSize()];
		for (int ii = 0; ii < this.getSize(); ii++)
		{
			strArrayToReturn[ii] = internalStackArray[ii];
		}
		return strArrayToReturn;
	}
}


public class Card 
{
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Static Members
	 *---------------------------------------------------------------------------
	 */
	
	
	//End of List marker.
	private static int EOL = -1;
	private static int DEFAULT_VALUE = 1;
	private static char DEFAULT_SUIT = 'C';
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Members
	 *---------------------------------------------------------------------------
	 */
	
	private char cardSuit;
	private int cardValue;
	private int nextIndex;
	//private int pileNumber;
	private boolean visible;
	
	
	/*
	 *---------------------------------------------------------------------------
	 *	Constructors
	 *---------------------------------------------------------------------------
	 */
	
	
	public Card() throws Exception 
	{
		setCardValue(DEFAULT_VALUE);
		setCardSuit(DEFAULT_SUIT);
		setNextIndex();
		setVisible(true);
	}
	
	public Card(int value, char suit) throws Exception
	{
		setCardValue(value);
		setCardSuit(suit);
		setNextIndex();
		setVisible(true);
	}
	
	public Card(int value, char suit, int index) throws Exception
	{
		setCardValue(value);
		setCardSuit(suit);
		setNextIndex(index);
		setVisible(true);
	}
	
	public Card(int value, char suit, int index, boolean visibility) throws Exception 
	{
		setCardValue(value);
		setCardSuit(suit);
		setNextIndex(index);
		setVisible(visibility);
	}
	
	public Card(String value, char suit, int index, boolean visibility) throws Exception 
	{
		setCardValue(value);
		setCardSuit(suit);
		setNextIndex(index);
		setVisible(visibility);
	}

	/*
	 *---------------------------------------------------------------------------
	 *	Setters
	 *---------------------------------------------------------------------------
	 */
	
	/**
	 *  Setter for visibility
	 * @param v : boolean value to set as visibility
	 */
	public void setVisible(boolean v) 
	{
		this.visible = v;
	}
	
	/**
	 *  Setter for nextIndex
	 * @param nI : int to set as nextIndex
	 * @throws Exception 
	 */
	public void setNextIndex(int nI) throws Exception
	{
		if (nI < -1)
		{
			throw new Exception("Malformed NextIndex {"+nI+"}");
		}
		this.nextIndex = nI;
	}
	
	/**
	 *  Setter for nextIndex as EOL
	 */
	public void setNextIndex()
	{
		this.nextIndex = EOL;
	}
	
	/**
	 *  Setter for cardValue based on int.
	 * @param cV : int to set as cardValue
	 * @throws Exception 
	 */
	public void setCardValue(int cV) throws Exception
	{
		if (cV < 1 || cV > 13)
		{
			throw new Exception("Malformed Value {"+cV+"}");
		}
		else
		{
			this.cardValue = cV;
		}
	}
	
	
	/**
	 * 	Setter for cardValue based on char.
	 * @param cV : The char of the cards value to be set.
	 * @throws Exception 
	 */
	public void setCardValue(String cV) throws Exception
	{
		if (cV.compareTo("K") == 0)
		{
			this.cardValue = 13;
		} 
		else if (cV.compareTo("Q") == 0)
		{
			this.cardValue = 12;
		}
		else if (cV.compareTo("J") == 0)
		{
			this.cardValue = 11;
		}
		else if (cV.compareTo("A") == 0)
		{
			this.cardValue = 1;
		}
		else if (cV.compareTo("10") == 0)
		{
			this.cardValue = 10;
		}
		else
		{
			try
			{
				this.cardValue = Integer.parseInt(cV);
			}
			catch (Exception e)
			{
				throw new Exception("Malformed Value {"+cV+"}");
			}
		}
	}
	
	/**
	 *  Setter for cardSuit
	 * @param cS : int to set as cardSuit
	 * @throws Exception 
	 */
	public void setCardSuit(char cS) throws Exception 
	{
		switch (cS)
		{
		case 'C':
		case 'D':
		case 'H':
		case 'S':
			this.cardSuit = cS;
			break;
		default:
			throw new Exception("Malformed Suit {"+String.valueOf(cS)+"}");
		}
	}
	
	public void setAll(int cV, char cS, int nI, boolean v) throws Exception
	{
		this.setCardSuit(cS);
		this.setCardValue(cV);
		this.setNextIndex(nI);
		this.setVisible(v);
	}
	
	/*
	 *---------------------------------------------------------------------------
	 *	Getters
	 *---------------------------------------------------------------------------
	 */
	
	/**
	 *  Getter for nextIndex
	 * @return this.nextIndex
	 */
	public int getNextIndex() 
	{
		return nextIndex;
	}
	
	/**
	 *  Getter for visible
	 * @return this.visible
	 */
	public boolean getVisible() 
	{
		return visible;
	}
	
	/**
	 *  Getter for cardSuit
	 * @return this.cardSuit
	 */
	public char getCardSuit() 
	{
		return cardSuit;
	}
	
	/**
	 *  Getter for cardValue
	 * @return this.cardValue
	 */
	public int getCardValue() 
	{
		return cardValue;
	}

	
	/*
	 *---------------------------------------------------------------------------
	 *	Override Functions
	 *---------------------------------------------------------------------------
	 */
	public String toString()
	{
		return valueToString() + suitToString();
	}
	
	/*
	 *---------------------------------------------------------------------------
	 *	Public Functions
	 *---------------------------------------------------------------------------
	 */
	
	public boolean isEOL()
	{
		if (this.nextIndex == EOL)
		{
			return true;
		}
		return false;
	}

	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions
	 *---------------------------------------------------------------------------
	 */
	private String valueToString()
	{
		switch (cardValue)
		{
		case 13:
			return " K";
		case 12:
			return " Q";
		case 11:
			return " J";
		case 10:
			return "10";
		case 1:
			return " A";
		default:
			return " " + String.valueOf(this.cardValue);
		}
	}
	
	private String suitToString()
	{
		return String.valueOf(cardSuit);
	}
}

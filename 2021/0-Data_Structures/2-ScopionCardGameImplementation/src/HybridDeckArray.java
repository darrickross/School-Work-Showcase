
public class HybridDeckArray {
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Static Members
	 *---------------------------------------------------------------------------
	 */
	
	private static char SUIT_LIST[] = {'C', 'D', 'H', 'S'};
	private static int NUM_CARDS_PER_SUIT = 13;
	private static int NUM_SUITS = 4;
	private static int DECK_SIZE = NUM_CARDS_PER_SUIT * NUM_SUITS;
	private static int PILE_COUNT = 7;
	private static int NULL_VALUE = -1;
	private static int KING_VALUE = 13;
	private static int FLIP_PILES = 4;
	private static int FLIP_COUNT = 3;
	private static int CON_WIN = 2;
	private static int CON_STACK_COMPLETE = 1;
	private static int CON_NOTHING = 0;
	private static boolean FACE_DOWN = false;
	private static boolean FACE_UP = true;
	private static String MOVE_SUCCESSFUL = "Move successful!";
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Members
	 *---------------------------------------------------------------------------
	 */
	
	private Card deck[];
	private int pileHeads[];
	private int pileTails[];
	private int reservePileHead;
	private boolean reservePileAvailable;
	private int reservePileCount;
	private int completedPiles;
	
	/*
	 *---------------------------------------------------------------------------
	 *	Constructors
	 *---------------------------------------------------------------------------
	 */
	
	public HybridDeckArray() throws Exception 
	{
		shuffle();
	}
	
	public HybridDeckArray(String customShuffle) throws Exception 
	{
		shuffle(customShuffle);
	}
	
	
	/*
	 *---------------------------------------------------------------------------
	 *	Overloads
	 *---------------------------------------------------------------------------
	 */
	
	@Override
	public String toString() {
		int stackMax = longestPileSize();
		String[] array = new String[stackMax + 1];
		
		int[] pilesIndex = new int[PILE_COUNT];
		for (int pile = 0; pile < PILE_COUNT; pile++)
		{
			pilesIndex[pile] = pileHeads[pile];
		}
		
		//For each row of each pile
		for (int row = 0; row < stackMax; row++)
		{
			array[row] = "";
			for (int pile = 0; pile < PILE_COUNT; pile++)
			{
				if(	pilesIndex[pile] < DECK_SIZE && 
						pilesIndex[pile] >= 0)
				{
					if (deck[pilesIndex[pile]].getVisible() == FACE_DOWN)
					{
						array[row] += "[" +
								deck[pilesIndex[pile]].toString() +
								"] ";
					}
					else
					{
						array[row] += " " +
								deck[pilesIndex[pile]].toString() +
								"  ";
					}
					
					pilesIndex[pile] = deck[pilesIndex[pile]].getNextIndex();
				}
				else
				{
					array[row] += "      ";
				}
			}
		}
		
		array[array.length -1] = "RESERVE: ";
		if (reservePileAvailable)
		{
			int index = reservePileHead;
			while (index >= 0 && index < DECK_SIZE)
			{
				array[array.length -1] += "[" +
						deck[index].toString() +
						"] ";
				
				index = deck[index].getNextIndex();
			}
			
		}
		
		return String.join("\n", array) + "\n\n";
	}
	
	
	/*
	 *---------------------------------------------------------------------------
	 *	Public Functions
	 *---------------------------------------------------------------------------
	 */
	
	public String computerMakeMove()
	{
		boolean resultOfMove = false;
		
		//for each pile
		for (int ii = 0; ii < PILE_COUNT; ii++)
		{
			
			if (pileHeads[ii] == NULL_VALUE)
			{
				//try to move a king to the blank space.
				try {
					resultOfMove = tryToMoveAKing(ii);
				} catch (Exception e) {
					resultOfMove = false;
				}
				
				if (resultOfMove == true)
				{
					return MOVE_SUCCESSFUL;
				}
				continue;
			}
			
			//Get the tail and check if the next card "exist"
			int tailIndex = pileTails[ii];
			char tailSuit = deck[tailIndex].getCardSuit();
			int tailValue = deck[tailIndex].getCardValue() -1;
			
			if (tailValue < 1)
			{
				//Nothing can go onto an ace
				continue;
			}
			
			int movableIndex = findCardSkipPile(tailValue,tailSuit,ii);
			
			if (movableIndex == NULL_VALUE)
			{
				continue;
			}
			
			
			try {
				resultOfMove = moveCard(movableIndex, ii);
			} catch (Exception e) {
				resultOfMove = false;
			}
			
			if (resultOfMove)
			{
				return MOVE_SUCCESSFUL;
			}
		}
		
		
		// Check if the reserve can be added onto the board
		if (reservePileAvailable == true)
		{
			try {
				if (dealReserve())
				{
					return MOVE_SUCCESSFUL;
				}
			} catch (Exception e) {
				resultOfMove = false;
			}
		}
		
		
		//Return null means no move could be made.
		return null;
	}
	
	// 2 = win
	// 1 = pile complete
	// 0 = no change
	public int checkWin()
	{
		for (int pile = 0; pile < PILE_COUNT; pile++)
		{
			if(	pileHeads[pile] < DECK_SIZE && 
					pileHeads[pile] >= 0 &&
					deck[pileHeads[pile]].getCardValue() == KING_VALUE &&
					checkForCompletedPile(pile))
			{
				if (completedPiles == 4)
				{
					return CON_WIN;
				}
				return CON_STACK_COMPLETE;
			}
		}
		return CON_NOTHING;
	}




	public boolean moveCard(int srcValue, char srcSuit, int dstPile) 
			throws Exception
	{
		if (dstPile < 0 || dstPile >= PILE_COUNT)
		{
			throw new Exception("Out of bounds pile {"+dstPile+"}");
		}
		
		int indexSrc = findCard(srcValue, srcSuit);
		
		if (indexSrc >= 0 &&
				indexSrc < DECK_SIZE)
		{
			return moveCard(indexSrc, dstPile);
		}
		
		return false;
	}
	
	public int findCard(int value, char suit)
	{
		for (int ii = 0; ii < DECK_SIZE; ii++)
		{
			if (
					deck[ii].getCardSuit() == suit
					&&
					deck[ii].getCardValue() == value
					)
			{
				if (deck[ii].getVisible() == true)
				{
					return ii;
				}
				return NULL_VALUE;
			}
		}
		return NULL_VALUE;
	}
	
	public int findCardSkipPile(int value, char suit, int pile)
	{
		for (int ii = 0; ii < PILE_COUNT; ii++)
		{
			if (pile == ii)
			{
				continue;
			}
			
			int index = pileHeads[ii];
			while (index != NULL_VALUE)
			{
				if (
						deck[index].getCardSuit() == suit
						&&
						deck[index].getCardValue() == value
						)
				{
					if (deck[index].getVisible() == true)
					{
						return index;
					}
					return NULL_VALUE;
				}
				
				index = deck[index].getNextIndex();
			}
		}
		return NULL_VALUE;
	}
	
	public boolean isReserveAvailable()
	{
		return reservePileAvailable;
	}

	public boolean dealReserve() throws Exception
	{
		//If reserve pile has not been used.
		if (reservePileAvailable == true)
		{
			reservePileAvailable = false;
			
			for (int ii = 0; ii < reservePileCount; ii++)
			{
				int next = deck[reservePileHead].getNextIndex();
				//if the pile is empty...
				if (pileTails[ii] == NULL_VALUE)
				{
					pileTails[ii] = reservePileHead;
					pileHeads[ii] = reservePileHead;
					
					
					
				}
				else
				{
					deck[pileTails[ii]].setNextIndex(reservePileHead);
					deck[reservePileHead].setNextIndex();
					pileTails[ii] = reservePileHead;
				}
				reservePileHead = next;
			}
			reservePileHead = NULL_VALUE;
			return true;
		}
		return false;
	}

	
	/**
	 * 	Shuffles the deck by doing two way swaps enough times based on the
	 * 	number of cards in the deck.
	 * @throws Exception 
	 */
	public void shuffle() throws Exception
	{
		initializeDeck();
		
		int numOfSwaps = numOfTwoWaySwapsNeeded();
		
		for (int ii = 0; DECK_SIZE > 1 && ii < numOfSwaps; ii++)
		{
			int randomA = randomNumber(0, DECK_SIZE);
			int randomB = randomNumber(0, DECK_SIZE);
			
			while (randomA == randomB)
			{
				randomB = randomNumber(0, DECK_SIZE);
			}
			
			swap(randomA, randomB);			
		}
		
		dealDeck();
	}
		
	public void shuffle(String shuffledDeck) throws Exception
	{
		initializeDeck();
		
		String[] cardList = shuffledDeck.split(" ");
		int index = 0;
		
		if (cardList.length < DECK_SIZE)
		{
			throw new Exception("Custom Deck is smaller than DeckSize {"+
					DECK_SIZE+"}");
		}
		for (String cardString : cardList)
		{
			if (index >= deck.length)
			{
				break;
			}
			if (cardString.length() == 2)
			{
				deck[index].setCardValue(cardString.substring(0,1));
				deck[index].setCardSuit(cardString.charAt(1));
			}
			else if (cardString.length() == 3)
			{
				deck[index].setCardValue(cardString.substring(0,2));
				deck[index].setCardSuit(cardString.charAt(2));
			}
			else
			{
				throw new Exception("Malformed Card of size {"+ cardString.length() 
					+"}");
			}
			index++;
		}
		
		dealDeck();
	}
	
	/*
	 *---------------------------------------------------------------------------
	 *	Private Functions
	 *---------------------------------------------------------------------------
	 */
	
	private boolean tryToMoveAKing(int pileNumber) throws Exception {
		if (pileNumber < 0 && pileNumber >= PILE_COUNT)
		{
			return false;
		}
		
		for (char suit : SUIT_LIST)
		{
			int indexKing = findCard(KING_VALUE, suit);
			
			if (indexKing >= 0 &&
					indexKing < DECK_SIZE
					)
			{
				return moveKing(indexKing, pileNumber);
			}
		}
		return false;
	}
	
	private void initializeDeck() throws Exception
	{
		deck = new Card[DECK_SIZE];
		int CardValue = 1;
		for (int ii = 0, jj = ii; ii < DECK_SIZE; ii += NUM_SUITS, CardValue++)
		{
			for (char suit : SUIT_LIST)
			{
				deck[jj] = new Card(CardValue, suit);
				
				jj++;
			}
		}
	}
	
	private boolean moveKing(int deckIndex, int dstPile) throws Exception
	{
		//Assumption is made that deckIndex and pileNumber are both valid numbers
		
		if (deck[deckIndex].getCardValue() == KING_VALUE &&
				pileHeads[dstPile] == NULL_VALUE)
		{
			//Check that the king isn't already a pile head
			for (int pile = 0; pile < PILE_COUNT; pile++)
			{
				if (pileHeads[pile] == deckIndex)
				{
					return false;
				}
			}
			
			
			int srcPile = getPile(deckIndex);
			
			//clear old pointers to the King
			int oldPointersIndex = pileHeads[srcPile];
			//clear away the pointer that use to point to that value.
			while (oldPointersIndex != NULL_VALUE)
			{
				if (deck[oldPointersIndex].getNextIndex() == deckIndex)
				{
					//Clear the old pointer
					deck[oldPointersIndex].setNextIndex(NULL_VALUE);
					//move the tail to point to the new tail value.
					pileTails[srcPile] = oldPointersIndex;
					
					// if the card is face down
					if (deck[oldPointersIndex].getVisible() == FACE_DOWN)
					{
						deck[oldPointersIndex].setVisible(FACE_UP);
					}
					break; //Leave the for loop
				}
				oldPointersIndex = deck[oldPointersIndex].getNextIndex();
			}
			
			
			
			//set the empty pile to equal the cards index of the king
			pileHeads[dstPile] = deckIndex;
			
			int newTailIndex = deckIndex;
			while (deck[newTailIndex].getNextIndex() != NULL_VALUE)
			{
				//iterate
				newTailIndex = deck[newTailIndex].getNextIndex();
			}
			pileTails[dstPile] = newTailIndex;
			
			return true;
		}
		
		return false;
	}
	
	private int getPile(int deckIndex)
	{
		for (int pile = 0; pile < PILE_COUNT; pile++)
		{
			int location = pileHeads[pile];
			while (location >= 0 && location < DECK_SIZE)
			{
				if (location == deckIndex)
				{
					return pile;
				}
				location = deck[location].getNextIndex();
			}
		}
		
		return NULL_VALUE;
	}
	
	
	private boolean moveCard(int deckIndex, int pileNumber) throws Exception
	{
		//Error Check deckIndex
		if (deckIndex < 0 || deckIndex >= DECK_SIZE)
		{
			throw new Exception("Invalid deckIndex {"+deckIndex+"}");
		}
		//Error Check pileNumber
		if (pileNumber < 0 || pileNumber >= PILE_COUNT)
		{
			throw new Exception("Invalid pileNumber {"+pileNumber+"}");
		}
		
		int srcPile = getPile(deckIndex);

		//check that card is not in the same pile.
		if (srcPile == pileNumber || srcPile >= PILE_COUNT || srcPile < 0)
		{
			return false;
		}
		
		//If its a king deal with it
		if (deck[deckIndex].getCardValue() == KING_VALUE)
		{
			return moveKing(deckIndex, pileNumber);
		}
		
		
		
		int dstIndex = pileTails[pileNumber];
		
		/*
		 * 	In order:
		 * 	1) Non-Kings has to move onto another card.
		 * 	2) Suits must match
		 * 	3) Moving Card value must be 1 lower than destination card
		 */
		if (dstIndex != NULL_VALUE &&
				deck[deckIndex].getCardSuit() == deck[dstIndex].getCardSuit() &&
				deck[deckIndex].getCardValue() +1 == deck[dstIndex].getCardValue() &&
				deck[dstIndex].getNextIndex() == NULL_VALUE
				)
		{
			//clear old pointers to the card
			int oldPointersIndex = pileHeads[srcPile];
			
			//If the card moving is a head of a pile...
			if (oldPointersIndex == deckIndex)
			{
				pileHeads[srcPile] = NULL_VALUE;
				pileTails[srcPile] = NULL_VALUE;
			}
			else
			{
				//clear away the pointer that use to point to that value.
				while (oldPointersIndex != NULL_VALUE)
				{
					if (deck[oldPointersIndex].getNextIndex() == deckIndex)
					{
						//Clear the old pointer
						deck[oldPointersIndex].setNextIndex(NULL_VALUE);
						//move the tail to point to the new tail value.
						pileTails[srcPile] = oldPointersIndex;
						
						// if the card is face down
						if (deck[oldPointersIndex].getVisible() == FACE_DOWN)
						{
							deck[oldPointersIndex].setVisible(FACE_UP);
						}
						break; //Leave the for loop
					}
					oldPointersIndex = deck[oldPointersIndex].getNextIndex();
				}
			}
			
			
			
			
			//Move the card
			deck[dstIndex].setNextIndex(deckIndex);
			
			//Update Pile Tail
			int newTailIndex = deckIndex;
			while (deck[newTailIndex].getNextIndex() != NULL_VALUE)
			{
				//iterate
				newTailIndex = deck[newTailIndex].getNextIndex();
			}
			pileTails[pileNumber] = newTailIndex;
			
			
			
			
			//Successful
			return true;
		}
		return false;
	}

	private void dealDeck() throws Exception
	{
		completedPiles = 0;
		
		//Iterate over the deck, to form piles
		
		if (PILE_COUNT > deck.length)
		{
			throw new Exception("Pile count > Deck length {"+ PILE_COUNT + " | " 
					+ deck.length + "}");
		}
		
		
		
		pileTails = new int[PILE_COUNT];
		pileHeads = new int[PILE_COUNT];
		
		//Initialize indexes of the heads of each table.
		for (int ii = 0; ii < pileHeads.length; ii++)
		{
			pileHeads[ii] = ii;
		}
		
		
		for (int ii = PILE_COUNT; ii < deck.length; ii += PILE_COUNT)
		{
			//When we reach the last 3 cards aka the reserve pile.
			if (deck.length - ii < PILE_COUNT) 
				//May be a off by 1 on this calculation don't care.
			{
				reservePileHead = ii;
				reservePileCount = deck.length - ii;
				
				for (ii++; ii < deck.length; ii++)
				{
					deck[ii-1].setNextIndex(ii);
					deck[ii].setNextIndex(); //Set it to EOL
				}
			}
			//Until we reach the last 3 cards of the deck do this.
			else
			{
				for (
						int jj = ii; 
						jj < ii + PILE_COUNT && jj < deck.length && jj - PILE_COUNT >= 0;
						jj++
						)
				{
					deck[jj-PILE_COUNT].setNextIndex(jj);
					deck[jj].setNextIndex(); //Set it to EOL
					
					
					/*	
					 * 	This will overwrite each iteration, the last iteration will stay.
					 * 	Index of ii mod PileCount gets indexes 0-6 and sets them to the 
					 * 		current index.
					 * 	This helps the computer find the current tails of each pile for 
					 * 		moves.
					 * 
					 */
					pileTails[jj%PILE_COUNT]=jj;
				}
			}
		}
		
		reservePileAvailable = true;
		
		
		//Flip over the set of face down cards.
		for (int ii = 0; ii < FLIP_PILES; ii++)
		{
			// for each pile where cards start flipped over
			
			int index = pileHeads[ii];
			
			for (int jj = 0; jj < FLIP_COUNT && index != NULL_VALUE; jj++)
			{
				deck[index].setVisible(false);
				index = deck[index].getNextIndex();
			}
		}
	}
	
	/**
	 * 
	 *  Calculates the number of two way swaps needed to sufficiently randomize a
	 *  given deck of size `deckSize`.
	 *  
	 *  Based on https://math.stackexchange.com/a/127511
	 * 
	 * @param deckSize : The number of cards in the deck.
	 * @return The number of 2 way swaps needed to sufficiently randomize a deck.
	 */
	private int numOfTwoWaySwapsNeeded()
	{
		//+1 to round up
		return (int) (Math.log10(DECK_SIZE) * DECK_SIZE * .5) + 1;
	}
	
	/**
	 * 	Returns a random number from min to max
	 * 
	 * 	[min, max] inclusive.
	 * 
	 * @param min : minimum value returned
	 * @param max : maximum value returned
	 * @return
	 */
	private int randomNumber(int min, int max)
	{		
		return (int) (Math.random()*(max - min) + min);
	}
	
	/**
	 *  Swap two indexes in the Deck.
	 *  
	 * @param indexA : index 1
	 * @param indexB : index 2
	 * @throws Exception 
	 */
	private void swap(int indexA, int indexB) throws Exception
	{
		if (indexA < deck.length && 
				indexB < deck.length &&
				indexA >= 0 &&
				indexB >= 0)
		{
			char aSuit = deck[indexA].getCardSuit();
			int aValue = deck[indexA].getCardValue();
			int aNextIndex = deck[indexA].getNextIndex();
			boolean aVisibility = deck[indexA].getVisible();
			
			char bSuit = deck[indexB].getCardSuit();
			int bValue = deck[indexB].getCardValue();
			int bNextIndex = deck[indexB].getNextIndex();
			boolean bVisibility = deck[indexB].getVisible();
			
			deck[indexB].setAll(aValue, aSuit, aNextIndex, aVisibility);
			deck[indexA].setAll(bValue, bSuit, bNextIndex, bVisibility);
		}
		/*
		else
		{
			//throw error????
		}
		*/
	}
	
	private boolean checkForCompletedPile(int pileNumber)
	{
		if (pileNumber >= PILE_COUNT || 
				pileNumber < 0 || 
				pileHeads[pileNumber] >= DECK_SIZE || 
				pileHeads[pileNumber] < 0)
		{
			return false;
		}
		
		int index = pileHeads[pileNumber];
		int value = KING_VALUE;
		char suit = deck[index].getCardSuit();
		
		//Iterate over the deck
		while (index < DECK_SIZE && index >= 0)
		{
			if (deck[index].getCardValue() != value ||
					deck[index].getCardSuit() != suit)
			{
				return false;
			}
			
			index = deck[index].getNextIndex();
			value--;
		}
		
		//If the last card in the list was an Ace, 
		//then value-- would mean the value is 0;
		if (value == 0)
		{
			//Clear the piles head pointer
			pileHeads[pileNumber] = NULL_VALUE;
			pileTails[pileNumber] = NULL_VALUE;
			completedPiles++;
			return true;
		}
		
		return false;
	}
	

	private int longestPileSize()
	{
		int max = 0;
		//for each pile
		for (int pileNumber = 0; pileNumber < PILE_COUNT; pileNumber++)
		{
			int index = pileHeads[pileNumber];
			int count = 0;
			
			//for each card in the pile
			while (index >= 0 && index < DECK_SIZE)
			{
				count++;
				index = deck[index].getNextIndex();
			}
			
			if (count > max)
			{
				max = count;
			}
		}

		return max;
	}
	
}

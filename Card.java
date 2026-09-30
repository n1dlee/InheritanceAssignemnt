import java.util.Arrays;


/**
 * A class for representing standard (French) playing cards.
 * Rank numbers are 0 through 12, corresponding to possible rank names: "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K".
 * Suit numbers are 0 through 3, corresponding to possible suit names: "C", "d", "h", "S".
 * Note that upper/lowercase suit names are black/red suits.
 * The toString method for each card will be the concatenation of a rank name with a suit name.
 */
public class Card {

	/**
	 * an array of all unique Card objects
	 */
	public static Card[] allCards;
	
	/**
	 * array of abbreviated card rank names in ascending order of rank
	 */
	public static java.lang.String[] rankNames = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

	/**
	 * array of abbreviated card suit names with lowercase red suits
	 */
	public static java.lang.String[] suitNames = {"C", "d", "h", "S"}; 

	/**
	 * parallel array to suitNames indicating whether or not the corresponding suit is red
	 */
	public static boolean[] suitIsRed = {false, true, true, false};

	static {
		allCards = new Card[rankNames.length * suitNames.length];
		int i = 0;
		for (int suit = 0; suit < suitNames.length; suit++) 
			for (int rank = 0; rank < rankNames.length; rank++)
				allCards[i++] = new Card(rank, suit);		
	}

	private int rank, suit;

	/**
	 * Constructor to create a card object with the corresponding zero-based indices to rankNames and suitNames, respectively
	 * @param rank rank of card (zero-based index to rankNames)
	 * @param suit suit of card (zero-based index to suitNames)
	 */
	public Card(int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * Get rank of card (zero-based index to rankNames).
	 * @return rank of card (zero-based index to rankNames)
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Get suit of card (zero-based index to suitNames).
	 * @return suit of card (zero-based index to suitNames)
	 */
	public int getSuit() {
		return suit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public java.lang.String toString() {
		return rankNames[rank] + suitNames[suit];
	}
}

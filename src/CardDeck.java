import java.util.ArrayList;
import java.util.List;

public class CardDeck {
	
	private static ArrayList<Card> Deck;
	private static int deckNumber;

	public CardDeck(int deckNumber) {
		this.deckNumber = deckNumber;
	}

	public static void printDeck() {
		System.out.println(Deck);
	}
	
    public static synchronized void addCard(int deckNumber, int cardDesignation) {	
    	System.out.println("Adding: " + cardDesignation + " to Deck: " + deckNumber);

	}

    public synchronized int drawCard() {
        return 0;
    }
}

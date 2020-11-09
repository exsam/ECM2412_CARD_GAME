import java.util.ArrayList;

public class CardDeck {
	
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private static int deckNumber;

	public CardDeck(int deckNumber) {
		this.deckNumber = deckNumber;
	}

	public static void printDeck() {
		System.out.println(deck);
	}
	
    public static synchronized void addCard(Card card) {
		deck.add(card);
		//printDeck();
	}

    public synchronized int drawCard() {
        return 0;
    }

	@Override
	public String toString() {
		return "CardDeck " + deckNumber;
	}
}

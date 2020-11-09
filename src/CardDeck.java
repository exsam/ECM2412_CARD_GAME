import java.util.ArrayList;

public class CardDeck {
	
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private static int deckNumber;

	public CardDeck(int deckNumber) {
		this.deckNumber = deckNumber;
	}

	public ArrayList<Card> getDeck() {
		return this.deck;
	}
	
    public synchronized void addCard(Card card) {
		this.deck.add(card);
		//printDeck();
	}


    public synchronized int drawCard() {
        return 0;
    }

	@Override
	public String toString() {
		return "CardDeck " + this.deckNumber;
	}
}

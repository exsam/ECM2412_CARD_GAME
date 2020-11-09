import java.util.ArrayList;

public class CardDeck {
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	private final int deckNumber;

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


    public synchronized Card drawCard() {
		Card returnCard = this.deck.get(0);
        return returnCard;
    }

	@Override
	public String toString() {
		return "CardDeck " + this.deckNumber;
	}
}

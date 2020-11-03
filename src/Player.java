import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {

  private static final List<Card> hand = new ArrayList<Card>();
  private final int number;

  public Player(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  public void addToHand(Card card) {
    // HOW DO WE ACCESS PLAYER Ns DECK
    hand.add(card);
  }

  public void addToDeck(CardDeck cardDeck) {

  }

  public synchronized void playerMove(CardDeck deck) {
    addToDeck(deck);
  }
}

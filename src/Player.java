import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {

  private static List<Card> hand = new ArrayList<Card>();
  private int number;

  /**
   * @param card
   * @param position in ArrayList
   */
  public void addToHand(Card card, int position) {
    // HOW DO WE ACCESS PLAYER Ns DECK
    hand.set(position, card);
  }

  public void addToDeck(CardDeck cardDeck) {
    // randomly select card from hand that is not = playerNumber
    // addCard to deck1 BUT
    // WHERE DO WE ADD THE CARD IN DECK1
  }

  public synchronized void playerMove(CardDeck deck) {
    addToDeck(deck);
  }
}

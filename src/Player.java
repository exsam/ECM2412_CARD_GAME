import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {

  private List<Card> hand = new ArrayList<Card>();
  private final int playerNumber;

  public Player(int number) {
    this.playerNumber = number;
  }

  public int getPlayerNumber() {
    return playerNumber;
  }

  public void addCardToHand(Card card) {
    this.hand.add(card);
  }

  public void removeCard(int index) {
    this.hand.remove(index);
  }

  public void getHandDenominations() {
    //Prints Owner and Denomination of Card
    for (Card card : this.hand) {
      System.out.println(card.getOwner() + " " + card.getDenomination());
    }
  }

  public boolean isWinner() {
    for(Card card : this.hand){
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "Player " + playerNumber;
  }
}

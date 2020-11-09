import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {

  private static List<Card> hand = new ArrayList<Card>();
  private int playerNumber;

  public Player(int number) {
    this.playerNumber = number;
  }

  public int getPlayerNumber() {
    return playerNumber;
  }

  public void addCardToHand(Card card) {
    this.hand.add(card);
  }
  public void removeCard(int index)
  {
    this.hand.remove(index);
  }
  public void printHand(){
    System.out.println(hand);
  }
  @Override
  public String toString() {
    return "Player " + playerNumber;
  }

}

import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {

  private final int playerNumber;
  private final List<Card> hand = new ArrayList<Card>();

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

  public Card discardCard() {
    Card returnCard = this.hand.get(0);
    for(Card c : hand){
      if (c.getDenomination() != playerNumber){
        returnCard = c;
        break;
      } else {
        System.out.println("EQUALS");
        continue;
      }

    }
    this.hand.remove(returnCard);
    return returnCard;
  }

  public void getHandDenominations() {
    // Prints Owner and Denomination of Card
    for (Card card : this.hand) {
      System.out.println(card.getOwner() + " " + card.getDenomination());
    }
  }

  public boolean isWinner() {
    //cycle through player hand and checks if all values match the first value
    for (Card card : this.hand) {
      if (card.getDenomination() != this.hand.get(0).getDenomination()) {
        return false;
      }
    }
    return true;
  }

  public void run() {
    int discardDeckIndex = playerNumber - 1;
    int drawDeckIndex = playerNumber;
    if(playerNumber == CardGame.deckArray.length) {
      drawDeckIndex = 0;
    }
    System.out.println(this.toString() + " dis: " + discardDeckIndex + " draw: " + drawDeckIndex );
    System.out.println(isWinner());
    boolean winner = isWinner();
    synchronized (this) {
      // setting thread flag
      while (!winner) {
        addCardToHand(CardGame.deckArray[drawDeckIndex].drawCard());
        CardGame.deckArray[discardDeckIndex].addCard(discardCard());
        winner = isWinner();
      }
    }
    System.out.println(toString() + " has " + hand);
  }

  @Override
  public String toString() {
    return "Player " + playerNumber;
  }
}

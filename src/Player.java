import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {

  private final int playerNumber;
  private final List<Card> hand = new ArrayList<Card>();

  public Player(int number) {
    this.playerNumber = number;
  }

  public int getPlayerNumber() {
    return this.playerNumber;
  }

  public synchronized void addCardToHand(Card card) {
    card.setOwner("p" + this.playerNumber);
    this.hand.add(card);
  }

  public void removeCard(int index) {
    this.hand.remove(index);
  }

  public synchronized Card discardCard() {
    Card returnCard = this.hand.get(0);
    for (Card c : hand) {
      if (c.getDenomination() != this.playerNumber) {
        returnCard = c;
        break;
      } else {
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

  public synchronized boolean isWinner() {
    // cycle through player hand and checks if all values match the first value
    int firstCard = this.hand.get(0).getDenomination();
    for (Card card : this.hand) {
      if (CardGame.won.get() || card.getDenomination() != this.hand.get(0).getDenomination()) {
        return false;
      }
    }
    CardGame.won.set(true);
    CardGame.winningPlayer.set(playerNumber);

    return true;
  }

  private void writeToFile(String writeString) {
    try {
      FileWriter myWriter = new FileWriter("player" + this.playerNumber + "_output.txt", true);
      myWriter.write(writeString);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  // Creates an output file for each player.
  private void createFile() {
    try {
      new FileWriter("player" + this.playerNumber + "_output.txt", false);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void run() {
    int discardDeckIndex = playerNumber - 1;
    int drawDeckIndex = playerNumber;
    if (playerNumber == CardGame.deckArray.length) {
      drawDeckIndex = 0;
    }
    isWinner();
    // synchronized (this) {
    // setting thread flag
    while (!CardGame.won.get()) {

/*      if(Thread.interrupted()){
        System.out.println("Thread Broken");
        break;
      }

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
        //Thread.currentThread().interrupt();
      }*/

      synchronized (this) {
        try {
          addCardToHand(CardGame.deckArray[drawDeckIndex].drawCard());
          CardGame.deckArray[discardDeckIndex].addCard(discardCard());
        } catch (IndexOutOfBoundsException e) {
          System.out.println(e);
          getHandDenominations();
        }
      }
      isWinner();
    }
    System.out.println(
        CardGame.winningPlayer.get()
            + " has notified Player "
            + playerNumber
            + " that it has won!");
    CardDeck deckTest = CardGame.deckArray[drawDeckIndex];
    System.out.println(drawDeckIndex + " " + deckTest.getDeck());
    System.out.println("LOST");
    // }
    System.out.println(toString() + " has " + hand);
  }

  @Override
  public String toString() {
    return "Player " + playerNumber;
  }
}

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Thread {

  private final int playerNumber;
  private final List<Card> hand = new ArrayList<Card>();


  public Player(int number) throws ObjectReferenceNumberException {
    if (number == 0) {
      throw new ObjectReferenceNumberException("Index is 0");
    }
    else if (number < 0) {
      throw new ObjectReferenceNumberException("Index less than 0");
    }
    else {
      this.playerNumber = number;
    }
  }

  public int getPlayerNumber() {
    return this.playerNumber;
  }

  public List<Card> getHand() {
    return hand;
  }

  public synchronized void addCardToHand(Card card) throws FormattingException {
    card.setOwner("p" + this.playerNumber);
    writeToFile(
        System.lineSeparator() + "player " + playerNumber + " draws a " + card.getDenomination());
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
        writeToFile("\nplayer " + playerNumber + " discards a " + returnCard.getDenomination());
        break;
      } else {
        continue;
      }
    }
    this.hand.remove(returnCard);
    return returnCard;
  }

  public void printCardInformation() {
    // Prints Owner and Denomination of Card
    for (Card card : this.hand) {
      System.out.println(card.getOwner() + " " + card.getDenomination());
    }
  }

  public List<Integer> getHandDenominations() {
    List<Integer> handDenominations = new ArrayList<Integer>();
    for (Card card : this.hand) {
      handDenominations.add(card.getDenomination());
    }
    return handDenominations;
  }

  private void writeToFile(String writeString) {
    try {
      FileWriter myWriter = new FileWriter("player" + this.playerNumber + "_output.txt", true);
      myWriter.write(writeString);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred when writing player file.");
      e.printStackTrace();
    }
  }

  // Creates an output file for each player.
  private void createFile() {
    try {
      new FileWriter("player" + this.playerNumber + "_output.txt", false);
    } catch (IOException e) {
      System.out.println("An error occurred when creating player file.");
      e.printStackTrace();
    }
  }

  public void checkForWin(AtomicBoolean winBool, AtomicInteger winPlayerNumber) {
    // cycle through player hand and checks if all values match the first value
    int firstCard = this.hand.get(0).getDenomination();
    for (Card card : this.hand) {
      if (card.getDenomination() != firstCard) {
        return;
      }
    }
    synchronized (this) {
      /*CardGame.won.compareAndSet(false, true);
      if (CardGame.won.get() && CardGame.winningPlayer.get() == 0) {
        CardGame.winningPlayer.compareAndSet(0, playerNumber);
      }*/
      winBool.compareAndSet(false,true);
      if(winBool.get() && winPlayerNumber.get() == 0){
        winPlayerNumber.compareAndSet(0,playerNumber);
      }
    }
  }

  private String getHandValueString() {
    StringBuilder stringBuilder = new StringBuilder();
    hand.forEach(
        c -> {
          stringBuilder.append(c.getDenomination() + " ");
        });
    String result = stringBuilder.toString().trim();
    return result;
  }

  public void run() {

    createFile();
    writeToFile("player " + playerNumber + " initial hand " + getHandValueString());

    // initally check if hand is winning
    checkForWin(CardGame.won,CardGame.winningPlayer);

    // calculate indexes for the draw & discard decks
    int discardDeckIndex = playerNumber - 1;
    int drawDeckIndex = playerNumber;
    if (playerNumber == CardGame.deckArray.length) {
      drawDeckIndex = 0;
    }

    // setting thread flag
    while (!CardGame.won.get()) {
      checkForWin(CardGame.won,CardGame.winningPlayer);

      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      synchronized (this) {
        try {
          addCardToHand(CardGame.deckArray[drawDeckIndex].drawCard());
          writeToFile(" from deck " + CardGame.deckArray[drawDeckIndex].getDeckNumber());
          CardGame.deckArray[discardDeckIndex].addCard(discardCard());
          writeToFile(" to deck " + CardGame.deckArray[discardDeckIndex].getDeckNumber());
          writeToFile(
              System.lineSeparator()
                  + "player "
                  + playerNumber
                  + " current hand is "
                  + getHandValueString());
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Incorrect Draw or Discard Deck Index");
          e.printStackTrace();
        } catch (FormattingException e) {
          e.printStackTrace();
        }
      }

      checkForWin(CardGame.won,CardGame.winningPlayer);
    }

    // checks if this player is the winning player
    if (CardGame.winningPlayer.get() != this.playerNumber) {
      String winner = "player " + CardGame.winningPlayer.get();
      writeToFile(
          System.lineSeparator()
              + winner
              + " has informed player "
              + playerNumber
              + " that "
              + winner
              + " has won");
    } else {
      writeToFile(System.lineSeparator() + "player " + playerNumber + " wins");
      System.out.println(playerNumber + " wins");
    }
    writeToFile(System.lineSeparator() + "player " + playerNumber + " exits");
    writeToFile(
        System.lineSeparator() + "player " + playerNumber + " hand: " + getHandValueString());
    CardGame.deckArray[drawDeckIndex].outputDeckCardDenominationsToFile();
  }

  @Override
  public String toString() {
    return "Player " + playerNumber;
  }
}

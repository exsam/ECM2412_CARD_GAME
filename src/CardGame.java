import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The main class for the multi-threaded card game with n players with a hand of 4 cards, dealt from
 * a pack of 8n non-negative integers, ECM2414
 *
 * @author 012118
 * @author 033440
 * @version 1.0
 */
public class CardGame {

  // Array of Players amd CardDecks
  public static Player[] playerList;
  public static CardDeck[] deckArray;

  // AtomicVariable Flags for Thread Interruption
  public static AtomicBoolean won;
  public static AtomicInteger winningPlayer;

  private static int playerCount;

  /**
   * Imports and validates user provided pack of cards. Recursively asks for input if not valid
   *
   * @param locationInput
   * @param playerCount
   * @return ArrayList<Integer>
   */
  public static ArrayList<Integer> importPackFile(Scanner locationInput, int playerCount) {
    boolean validInput = false;

    ArrayList<Integer> loadedIntegerPack = new ArrayList<Integer>();

    String packIn = locationInput.nextLine();
    File inputFile = new File(packIn);
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      while (reader.ready()) {
        int loadedInt = Integer.valueOf(reader.readLine());
        if (loadedInt >= 0) {
          loadedIntegerPack.add(loadedInt);
        } else {
          System.out.print(
              "All Values Must Be A Non-Negative Integer \nPlease Enter The Pack Location: ");
          return importPackFile(locationInput, playerCount);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.print("Please Input The Correct File Path\nPlease Enter The Pack Location: ");
      return importPackFile(locationInput, playerCount);
    } catch (NumberFormatException e) {
      System.out.print("All Values Must Be An Integer\nPlease Enter The Pack Location: ");
      return importPackFile(locationInput, playerCount);
    } catch (IOException e) {
      System.out.println(e);
      return importPackFile(locationInput, playerCount);
    }
    if (loadedIntegerPack.size() == (8 * playerCount)) {
      return loadedIntegerPack;
    } else {
      System.out.print(
          "The Input Pack Must Only Contains 8 Times The Player Count\nPlease Enter The Pack Location: ");
      return importPackFile(locationInput, playerCount);
    }
  }

  private static int playerCountInput() {
    Scanner input = new Scanner(System.in);
    System.out.print("Please enter the number of players: ");
    try {
      playerCount = input.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Please enter an Integer.");
      playerCountInput();
    }
    if (playerCount < 2) {
      System.out.println("There must be 2 or more players.");
      playerCountInput();
    }
    return playerCount;
  }

  /**
   * Constructs Cards from ArrayList inputPack and round robin distributes them to first the Player
   * hands (untill each has 4 cards) in playerList and then to decks in cardDecks
   *
   * @param playerList list of Player objects
   * @param cardDecks list of CardDeck objects
   * @param inputPack ArrayList Integer
   */
  public static void populateGame(
      Player[] playerList, CardDeck[] cardDecks, ArrayList<Integer> inputPack) {

    // loops through 4 times leading to 4 cards per Player hand
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < playerList.length; i++) {
        try {
          playerList[i].addCardToHand(
              (new Card((((Integer) (i + 1)).toString()), inputPack.get(0))));
        } catch (FormattingException e) {
          e.printStackTrace();
        }
        inputPack.remove(0);
      }
    }
    // loops through 4 times leading to 4 cards per Deck
    for (int j = 0; j < 4; j++) {
      for (int d = 0; d < cardDecks.length; d++) {
        // for each deck, round robin deal a card
        try {
          cardDecks[d].addCard(new Card(cardDecks[d].toString(), inputPack.get(0)));
        } catch (FormattingException e) {
          e.printStackTrace();
        }
        inputPack.remove(0);
      }
    }
  }

  public static void startPlayerThreads(Player[] playerList) {
    for (int i = 0; i < playerList.length; i++) {
      playerList[i].start();
      playerList[i].setName(playerList[i].toString());
    }
  }

  public static void main(String[] args) {

    playerCount = playerCountInput();

    // defining arrays and constructing players and decks
    playerList = new Player[playerCount];
    deckArray = new CardDeck[playerCount];

    System.out.println("Setting Up A " + playerCount + " Player Game");

    // Initialise n number of Player and CardDeck objects
    for (int i = 0; i < playerCount; i++) {
      try {
        playerList[i] = new Player(i + 1);
      } catch (ObjectReferenceNumberException e) {
        e.printStackTrace();
      }
      try {
        deckArray[i] = new CardDeck(i + 1);
      } catch (ObjectReferenceNumberException e) {
        e.printStackTrace();
      }
    }
    Scanner ipInput = new Scanner(System.in);
    System.out.print("Please Enter The Pack Location: ");
    ArrayList<Integer> loadedIntegerPack = importPackFile(ipInput, playerCount);

    // Initialise AtomicVariable flags
    won = new AtomicBoolean(false);
    winningPlayer = new AtomicInteger(0);

    populateGame(playerList, deckArray, loadedIntegerPack);
    startPlayerThreads(playerList);
  }
}

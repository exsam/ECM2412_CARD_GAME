import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CardGame {

  public static Player[] playerList;
  public static CardDeck[] deckArray;

  public static AtomicBoolean won;
  public static AtomicInteger winningPlayer;

  private static int playerCount;

  public CardGame() {
  }

  //TODO delete this if not needed
  public static int getPlayerCount() {
    return playerCount;
  }

  public static ArrayList<Integer> importPackFile(int playerCount) {
    // First Get Pack Location
    Scanner locationInput = new Scanner(System.in);
    boolean validInput = false;

    ArrayList<Integer> loadedIntegerPack = new ArrayList<Integer>();

    System.out.print("Please Enter The Pack Location: ");
    String packIn = locationInput.nextLine();
    File inputFile = new File(packIn);
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      while (reader.ready()) {
        int loadedInt = Integer.valueOf(reader.readLine());
        if (loadedInt >= 0) {
          loadedIntegerPack.add(loadedInt);
        } else {
          System.out.println("All Values Must Be A Non-Negative Integer");
          return importPackFile(playerCount);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Please Input The Correct File Path");
      return importPackFile(playerCount);
    } catch (NumberFormatException e) {
      System.out.println("All Values Must Be An Integer");
      return importPackFile(playerCount);
    } catch (IOException e) {
      System.out.println(e);
      return importPackFile(playerCount);
    }
    if (loadedIntegerPack.size() == (8 * playerCount)) {
      return loadedIntegerPack;
    } else {
      System.out.println("The Input Pack Must Only Contains 8 Times The Player Count");
      return importPackFile(playerCount);
    }
  }

  private static int playerCountInput(){
    Scanner input = new Scanner(System.in);
    System.out.print("Please enter the number of players: ");
    try{
    playerCount = input.nextInt();
    }catch(InputMismatchException e){
      System.out.println("Please enter an Integer.");
      playerCountInput();
    }
    if(playerCount < 2){
      System.out.println("There must be 2 or more players.");
      playerCountInput();
    }
    return playerCount;
  }

  // populates the players hands and the card decks
  public static void populateGame(
      Player[] playerList, CardDeck[] cardDecks, ArrayList<Integer> inputPack) {
    // loops through 4 times leading to 4 cards per Player hand

    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < playerList.length; i++) {
        try {
          playerList[i].addCardToHand((new Card((((Integer) (i + 1)).toString()), inputPack.get(0))));
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

    ArrayList<Integer> loadedIntegerPack = importPackFile(playerCount);

    won = new AtomicBoolean(false);
    winningPlayer = new AtomicInteger(0);

    populateGame(playerList, deckArray, loadedIntegerPack);
    startPlayerThreads(playerList);
  }
}

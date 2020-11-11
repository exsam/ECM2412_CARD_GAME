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

  public static boolean isGameWinnable(ArrayList<Integer> loadedIntegerPack, int playerCount) {
    // hashmap to store the frequency of element
    Map<String, Integer> dict = new HashMap<>();

    for (Integer i : loadedIntegerPack) {
      String key = i.toString();
      Integer j = dict.get(key);
      dict.put(key, (j == null) ? 1 : j + 1);
    }

    for (Map.Entry<String, Integer> val : dict.entrySet()) {
      System.out.println(
          "Card Value " + val.getKey() + " " + "occurs" + ": " + val.getValue() + " times");
    }

    boolean winnable = false;

    for (int p = 1; p < playerCount + 1; p++) {
      String key = Integer.toString(p);
      try {
        if (dict.get(key) >= 4) {
          return true;
        } else {
          System.out.println("\nPlayer " + p + " could win but is at a disadvantage.");
        }
      } catch (NullPointerException e) {
        System.out.println("\nPlayer " + p + " is very unlikely no");
      }
    }

    if (winnable) {
      System.out.println("\nThere is guaranteed to be a winner.");
      return true;
    } else {
      // displaying the occurrence of elements in the arraylist
      for (Map.Entry<String, Integer> val : dict.entrySet()) {
        if (val.getValue() >= 4) {
          System.out.println(
              "\nThere is the possibility of a winning hand but the game may stagnate.");
          return true;
        }
      }
      return winnable;
    }
  }

  // populates the players hands and the card decks
  public static void populateGame(
      Player[] playerList, CardDeck[] cardDecks, ArrayList<Integer> inputPack) {
    // loops through 4 times leading to 4 cards per Player hand
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < playerList.length; i++) {
        playerList[i].addCardToHand((new Card((((Integer) (i + 1)).toString()), inputPack.get(0))));
        inputPack.remove(0);
      }
    }
    // loops through 4 times leading to 4 cards per Deck
    for (int j = 0; j < 4; j++) {
      for (int d = 0; d < cardDecks.length; d++) {
        // for each deck, round robin deal a card
        cardDecks[d].addCard(new Card(cardDecks[d].toString(), inputPack.get(0)));
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

    int playerCount = playerCountInput();

    // defining arrays and constructing players and decks
    playerList = new Player[playerCount];
    deckArray = new CardDeck[playerCount];

    System.out.println("Setting Up A " + playerCount + " Player Game");

    for (int i = 0; i < playerCount; i++) {
      playerList[i] = new Player(i + 1);
      deckArray[i] = new CardDeck(i + 1);
    }

    ArrayList<Integer> loadedIntegerPack = importPackFile(playerCount);
    isGameWinnable(loadedIntegerPack, playerCount);

    won = new AtomicBoolean(false);
    winningPlayer = new AtomicInteger(0);

    populateGame(playerList, deckArray, loadedIntegerPack);
    for (CardDeck deckTest : deckArray) {
      deckTest.outputToFile();
    }
    startPlayerThreads(playerList);
  }
}

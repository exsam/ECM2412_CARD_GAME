import java.io.File;
import java.util.*;

public class CardGame {

  public static boolean validatePack(String packLocation, int playerCount) {
    List<Integer> tempDeck = new ArrayList<>();
    File myObj = new File(packLocation);
    try {
      Scanner myReader = new Scanner(myObj);

      // NEED TO ADD CODE TO CHECK THAT THE INCOMING DATA IS INTEGER

      int currentDeckIndex = 0;
      int currentDeckSize = 0;
      while (myReader.hasNextLine()) {
        int data = Integer.parseInt(myReader.nextLine());
        if (currentDeckSize < 3) {
          CardDeck.addCard(currentDeckIndex, data);
          currentDeckSize++;
        } else if (currentDeckSize == 3) {
          CardDeck.addCard(currentDeckIndex, data);
          currentDeckIndex++;
          currentDeckSize = 0;
        }
        tempDeck.add(data);
      }
      myReader.close();
      int[] tempArrayDeck = tempDeck.stream().filter(t -> t != null).mapToInt(t -> t).toArray();
      Arrays.sort(tempArrayDeck);

      if ((8 * playerCount) == tempArrayDeck.length) {
        List<Integer> checkedDesignations = new ArrayList<>();
        for (int i = 0; i < tempArrayDeck.length; i++) {
          if (checkedDesignations.contains(tempArrayDeck[i]) == false) {
            int cardCount = 0;
            for (int j = 0; j < tempArrayDeck.length; j++) {
              // System.out.println("Current checking i: " + tempArrayDeck[i] + " against j: " +
              // tempArrayDeck[j]);
              if (tempArrayDeck[i] == tempArrayDeck[j]) {
                // System.out.println("Matches so cardcount increment");
                cardCount++;
              }
            }
            if (cardCount == 4) {
              // System.out.println("adding " + tempArrayDeck[i] + " to checked designations");
              checkedDesignations.add(tempArrayDeck[i]);
            } else if (cardCount < 4) {
              System.out.println("Not 4 of one card type!");
              return false;
            }
          } else {
            // System.out.println("This Designation: " + tempArrayDeck[i] + " has already been
            // checked!");
          }
        }
        System.out.println("Loop Finished!");
        return true;

      } else {
        System.out.println("Invalid pack length!");
        return false;
      }

    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public static ArrayList<Integer> importPack(int playerCount) {
    // First Get Pack Location
    Scanner locationInput = new Scanner(System.in);
    System.out.print(" Please Enter The Pack Location: ");
    String packIn = locationInput.nextLine();
    ArrayList<Integer> loadedIntegerPack = new ArrayList<Integer>();
    return loadedIntegerPack;
  }

  // TODO: function isGameWinnable
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
  // TODO: function generatePlayerThreads
  public static void generatePlayerThreads(int playerCount){
    // Generating The Player Threads
    for (int i = 1; i <= playerCount; i++) {
      // creating playerBase player thread object
      Player playerBase = new Player(i);
      System.out.println("player" + i);
      // starting playerBase player thread
      playerBase.start();
      // naming thread to each player
      playerBase.setName("player" + i);
    }
  }
  // TODO: function generateDecks
  public static void generateDecks(int playerCount){
    // Generating The Player Threads
    for (int i = 1; i <= playerCount; i++) {
      // creating base player thread object
      CardDeck deckBase = new CardDeck();
      System.out.println("deck" + i);
      // naming thread to each player
    }
  }
  // TODO: function populateGame
  public static void populateGame(int playerCount, ArrayList<Integer> inputPack){
    //TODO: get set of all running threads
    //TODO: loop through player threads adding cards from inputPack

    //TODO: get each Deck
    //TODO: distribute into deck
  }


  public static void main(String[] args) {
    // main method for entire game simulation
    Scanner input = new Scanner(System.in);
    System.out.print("Please Enter Number of Players:");
    int playerCount = input.nextInt();
    CardDeck.initialiseDeck(playerCount);

    System.out.println("Please Enter Pack Location/Name:");
    String packLocation = input.next();

    while (validatePack(packLocation, playerCount) == false) {
      System.out.println("Invalid Pack, please enter a new pack!");
      System.out.println("Please Enter NEW Pack Location/Name:");
      packLocation = input.next();
      validatePack(packLocation, playerCount);
    }

    generatePlayerThreads(playerCount);

    System.out.println("Setting Up A " + playerCount + " Player Game");

    CardDeck.outputDeck();

    /*
    Declare array with correct size for number of players
    int[] Pack;
    Pack = new int[8*playerCount];

    int CurrentCard = 1;
    int CurrentCount = 0;
    for (int i=0; i < Pack.length; i++ ) {
    System.out.println("Index: " + i);
    System.out.println("CurrentCard: " + CurrentCard);
    System.out.println("CurrentCount: " + CurrentCount);
    if (CurrentCount == 4) {
    CurrentCard++;
    CurrentCount = 0;
    }
    Pack[i] = CurrentCard;
    CurrentCount++;

    }

    System.out.println(Arrays.toString(Pack));
    */
  }
}

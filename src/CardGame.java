import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CardGame {

  public static Player[] playerList;
  public static CardDeck[] cardDecks;
  static int playerCount;

  /*public static boolean validatePack(String packLocation, int playerCount) {
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
  }*/

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

  // populates the players hands and the card decks
  public static void populateGame(
      Player[] playerList, CardDeck[] cardDecks, ArrayList<Integer> inputPack) {
    // loops through 4 times leading to 4 cards per Player hand
    for (int j = 0; j <= 4; j++) {
      for (int i = 0; i < playerList.length; i++) {
        // for each player, round robin deal a card to each hand
        // playerList[i].addCardToHand(new Card((((Integer)i).toString()), inputPack.get(0)));
        // inputPack.remove(0);
        //System.out.println(new Card((((Integer) (i + 1)).toString()), inputPack.get(0)));
        playerList[i].addCardToHand((new Card((((Integer) (i + 1)).toString()), inputPack.get(0))));
        System.out.println(playerList[i].toString());
        inputPack.remove(0);
      }
    }
    for (Player player : playerList) {
      player.printHand();
    }
    // loops through 4 times leading to 4 cards per Deck
    for (int j = 0; j <= 4; j++) {
      for (int i = 0; i < cardDecks.length; i++) {
        // for each deck, round robin deal a card
        CardDeck.addCard(new Card(cardDecks[i].toString(), inputPack.get(0)));
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
    // Scanner for user input
    Scanner input = new Scanner(System.in);

    System.out.print("Please Enter Number of Players: ");
    int playerCount = input.nextInt();

    // defining arrays and constructing players and decks
    playerList = new Player[playerCount];
    cardDecks = new CardDeck[playerCount];

    System.out.println("Setting Up A " + playerCount + " Player Game");

    for (int i = 0; i < playerCount; i++) {
      playerList[i] = new Player(i + 1);
      cardDecks[i] = new CardDeck(i + 1);
      System.out.println(cardDecks[i].toString());
    }

    ArrayList<Integer> loadedIntegerPack = importPackFile(playerCount);
    // isGameWinnable(loadedIntegerPack, playerCount);

    populateGame(playerList, cardDecks, loadedIntegerPack);
    startPlayerThreads(playerList);


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

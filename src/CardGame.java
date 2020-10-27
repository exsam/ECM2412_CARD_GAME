import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CardGame {
	
  static boolean validPack (String packLocation, int playerCount) {
	  //System.out.println("Valid pack function running");
	  int lines = 0;
	  List<Integer> tempDeck = new ArrayList<>();
	  File myObj = new File(packLocation);
      try {
    	  Scanner myReader = new Scanner(myObj);
    	  while (myReader.hasNextLine()) {
    		  lines++;
    	      String data = myReader.nextLine();
    	      tempDeck.add(Integer.parseInt(data));
    	      //System.out.println(data);
    	  }
    	  myReader.close();
    	  int[] tempArrayDeck = tempDeck.stream().filter(t -> t != null).mapToInt(t -> t).toArray();
    	  Arrays.sort(tempArrayDeck);
    	  
    	 
    	  if ((8*playerCount) == tempArrayDeck.length) {
    		  List<Integer> checkedDesignations = new ArrayList<>();
    		  for (int i=0; i < tempArrayDeck.length; i++ ) {
    			  if (checkedDesignations.contains(tempArrayDeck[i]) == false) {
    				  int cardCount = 0;
    				  for (int j=0; j < tempArrayDeck.length; j++ ) {
    					  //System.out.println("Current checking i: " + tempArrayDeck[i] + " against j: " + tempArrayDeck[j]);
    					  if (tempArrayDeck[i] == tempArrayDeck[j]) {
    						  //System.out.println("Matches so cardcount increment");
    						  cardCount++;
    					  }
    				  }
    				  if (cardCount == 4) {
    					  //System.out.println("adding " + tempArrayDeck[i] + " to checked designations");
    					  checkedDesignations.add(tempArrayDeck[i]);
    				  }
    				  else if (cardCount < 4) {
    					  System.out.println("Not 4 of one card type!");
    					  return false;
    				  }
    			  }
    			  else {
    				  //System.out.println("This Designation: " + tempArrayDeck[i] + " has already been checked!");
    			  }
    		  }
    		  System.out.println("Loop Finished!");
    		  return true;
    	  
    	  }  
          else {
    		  System.out.println("Invalid pack length!");
    		  return false;
    	  }
    		 
      } catch (Exception e){
    	  System.out.println(e);
    	  return false;
      }
 
  }
	
	
  public static void main(String[] args) {
    // main method for entire game simulation
    Scanner input = new Scanner(System.in);
    System.out.println("Please Enter Number of Players:");
    int playerCount = input.nextInt();
    
    Scanner input2 = new Scanner(System.in);
    System.out.println("Please Enter Pack Location/Name:");
    String packLocation = input2.nextLine();
    
    while (validPack(packLocation, playerCount) == false) {
    	Scanner input3 = new Scanner(System.in);
    	System.out.println("Invalid Pack, please enter a new pack!");
        System.out.println("Please Enter NEW Pack Location/Name:");
        packLocation = input3.nextLine();
    	validPack(packLocation, playerCount);	
    }
    //LoadPack();
  
    
    
    System.out.println("Setting Up A " + playerCount + " Player Game");
    
    //Declare array with correct size for number of players
    //int[] Pack;
    //Pack = new int[8*playerCount];
 
    //int CurrentCard = 1;
    //int CurrentCount = 0;
    //for (int i=0; i < Pack.length; i++ ) {
    	//System.out.println("Index: " + i);
    	//System.out.println("CurrentCard: " + CurrentCard);
    	//System.out.println("CurrentCount: " + CurrentCount);
    	//if (CurrentCount == 4) {
    		//CurrentCard++;
    		//CurrentCount = 0;
    	//}
    	//Pack[i] = CurrentCard;
    	//CurrentCount++;
    	
    //}
    
    //System.out.println(Arrays.toString(Pack));
    
    
    
  }
  
  
}




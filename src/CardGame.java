import java.util.Scanner;

public class CardGame {
  public static void main(String[] args) {
    // main method for entire game simulation
    Scanner input = new Scanner(System.in);
    System.out.print("Please Enter Number of Players: ");

    int n = input.nextInt();
    System.out.println("Setting Up A " + n + " Player Game");
  }
}

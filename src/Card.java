public class Card {
  private String owner;
  private int denomination;

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public int getDenomination() {
    return denomination;
  }


  @Override
  public String toString() {
    return "Card{" +
            "owner='" + owner + '\'' +
            ", denomination=" + denomination +
            '}';
  }

  public Card(String owner, int denomination) {
    this.owner = owner;
    this.denomination = denomination;
  }
}
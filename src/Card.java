public class Card {

  private String owner;
  private final int denomination;

  public Card(String owner, int denomination) {
    this.owner = owner;
    this.denomination = denomination;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) throws FormattingException {
    char firstChar = owner.charAt(0);
    if (firstChar == 'p' || firstChar == 'd') {
      this.owner = owner;
    } else {
      //
      throw new FormattingException();
    }
  }

  public int getDenomination() {
    return denomination;
  }

  @Override
  public String toString() {
    return "Card{" + "owner='" + owner + '\'' + ", denomination=" + denomination + '}';
  }
}

package wolter.philip.gamelogic.support;

public enum Bound {

  // These are the first Squares that cannont be reached
  LEFT(0), 
  RIGHT(8),
  TOP(0), 
  BOTTOM(8);

  int bound;

  Bound(int bound) {
    this.bound = bound;
  }

  public int getBound() {
    return bound;
  }
}
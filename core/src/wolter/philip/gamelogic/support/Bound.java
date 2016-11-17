package wolter.philip.gamelogic.support;

public enum Bound {

  // These are the first Squares that cannont be reached
  LEFT(0), 
  RIGHT(10), 
  TOP(0), 
  BOTTOM(16);

  int bound;

  Bound(int bound) {
    this.bound = bound;
  }

  public int getBound() {
    return bound;
  }
}
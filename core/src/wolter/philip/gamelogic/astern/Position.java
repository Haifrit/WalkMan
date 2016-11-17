package wolter.philip.gamelogic.astern;

import wolter.philip.gamelogic.support.Bound;

public class Position {
  
  private int x;
  private int y;
  
  public Position(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }
  
  public boolean isOutOfBound () {
    
    if (x > Bound.RIGHT.getBound() || x < Bound.LEFT.getBound()) {
      return true;
    }
    
    if (y > Bound.BOTTOM.getBound() || y < Bound.TOP.getBound()) {
      return true;
    }
    
    return false;
    
  }
  
  public boolean isEqual (Position position) {
    if (position.getX() == x && position.getY() == y) {
      return true;
    } else {
      return false;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
  
  public String toString () {
    StringBuilder sb = new StringBuilder();
    sb.append("X = " + x).append(" | ").append("Y = " + y);
    return sb.toString();
  }
}

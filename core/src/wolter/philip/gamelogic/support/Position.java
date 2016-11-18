package wolter.philip.gamelogic.support;

/**
 * Created by J.Wolter on 18.11.2016.
 */

public class Position {
  float xPosition;
  float yPosition;

  public Position (float xPosition, float yPosition) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
  }

  public void addToXPosition (float addX) {
    xPosition = xPosition + addX;
  }

  public void addToYPosition (float addY) {
    yPosition = yPosition + addY;
  }

  public float getxPosition() {
    return xPosition;
  }

  public float getyPosition() {
    return yPosition;
  }
}

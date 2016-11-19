package wolter.philip.gamelogic.support;

/**
 * Created by J.Wolter on 18.11.2016.
 */

public class Position {
  float xPosition;
  float yPosition;

  float xOneTileRight;
  float xOneTileLeft;
  float yOneTileUp;
  float yOneTileDown;

  public Position (float xPosition, float yPosition) {

    this.xPosition = xPosition;
    this.yPosition = yPosition;

    this.xOneTileRight = xPosition + 32;
    this.xOneTileLeft = xPosition - 32;
    this.yOneTileDown = yPosition - 32;
    this.yOneTileUp = yPosition + 32;

  }



  public void addToXPosition (float addX) {
    xPosition = xPosition + addX;
  }

  public void subFromXPosition (float subX) {
    xPosition = xPosition - subX;
  }

  public void subFromYPosition (float subY) {
    yPosition = yPosition - subY;
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

  public void setxPosition(float xPosition) {
    this.xPosition = xPosition;
  }

  public void setyPosition(float yPosition) {
    this.yPosition = yPosition;
  }
}

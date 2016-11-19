package wolter.philip.gamelogic.support;

/**
 * Created by J.Wolter on 20.11.2016.
 */

public class Waypoint {
  private String direction;
  private float destination;

  public Waypoint (String direction, float destination) {
    this.direction = direction;
    this.destination = destination;
  }

  public String getDirection() {
    return direction;
  }

  public float getDestination() {
    return destination;
  }
}

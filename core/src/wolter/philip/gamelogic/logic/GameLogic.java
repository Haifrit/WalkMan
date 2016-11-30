package wolter.philip.gamelogic.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import wolter.philip.gamelogic.astern.AstarPosition;
import wolter.philip.gamelogic.astern.Navigator;
import wolter.philip.gamelogic.astern.Node;
import wolter.philip.gamelogic.support.Waypoint;

/**
 * Created by J.Wolter on 22.11.2016.
 */

public class GameLogic {

  private static final AstarPosition START_POSITION = new AstarPosition(4,9);
  private static final AstarPosition END_POSITION = new AstarPosition(4,0);

  Navigator navigator;

  public GameLogic (List<AstarPosition> walls) {

    navigator = new Navigator(walls);

  }

  public boolean hasPath () {
    // vorher muss gatPathAsWayPoints aufgerufen werden
    return navigator.hasPathToDestination();
  }

  public List<Waypoint> getPathAsWaypoints () {
    List<Waypoint> waypointList = new ArrayList<Waypoint>();
    List<Node> nodeList = new LinkedList<Node>();
    nodeList = navigator.findPath(START_POSITION,END_POSITION);

    for (int i = 0; i < nodeList.size(); i++) {
      if ((i + 1) < nodeList.size()) {
        Waypoint waypoint = calculateWaypoint(nodeList.get(i), nodeList.get(i + 1));
        waypointList.add(waypoint);
      }
    }

    return waypointList;
  }

  private Waypoint calculateWaypoint (Node currentNode, Node nextNode) {
    AstarPosition currentPosition  = currentNode.getPosition();
    AstarPosition nextPosition = nextNode.getPosition();
    Waypoint waypoint = new Waypoint("UP",0);

    if (currentPosition.getX() < nextPosition.getX()) {
      waypoint.setDirection("RIGHT");
      waypoint.setDestination(nextPosition.getX() * 32);
    } else if (currentPosition.getX() > nextPosition.getX()) {
      waypoint.setDirection("LEFT");
      waypoint.setDestination(nextPosition.getX() * 32);
    } else if (currentPosition.getY() < nextPosition.getY()) {
      waypoint.setDirection("DOWN");
      waypoint.setDestination(nextPosition.getY() * 32);
    } else if (currentPosition.getY() > nextPosition.getY()) {
      waypoint.setDirection("UP");
      waypoint.setDestination(nextPosition.getY() * 32);
    }
    return waypoint;
  }
}

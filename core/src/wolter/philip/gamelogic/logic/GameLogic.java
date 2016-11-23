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

  private static final AstarPosition START_POSITION = new AstarPosition(3,12);
  private static final AstarPosition END_POSITION = new AstarPosition(3,0);

  Navigator navigator;

  public GameLogic (List<AstarPosition> walls) {

    navigator = new Navigator(walls);
    getPathAsWaypoints();

  }

  public List<Waypoint> getPathAsWaypoints () {
    List<Waypoint> waypointList = new ArrayList<Waypoint>();
    List<Node> nodeList = new LinkedList<Node>();
    nodeList = navigator.findPath(START_POSITION,END_POSITION);

    for (Node node : nodeList) {

    }

    return waypointList;
  }
}

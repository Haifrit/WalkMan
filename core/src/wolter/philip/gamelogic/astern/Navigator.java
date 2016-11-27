package wolter.philip.gamelogic.astern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import wolter.philip.gamelogic.support.NodeComparator;

public class Navigator {
  
  private List<AstarPosition> walls;
  private List<Node> openList;
  private List<Node> closedList;
  private Node current;
  private Node destinationAsNode;
  boolean hasPath = true;
  
  
  public Navigator(List<AstarPosition> walls) {
    super();
    this.walls = walls;
    openList = new ArrayList<Node>();
    closedList = new ArrayList<Node>();
  }
  
  public boolean hasPathToDestination () {
    return hasPath;
  }
  
  public List<Node> findPath (AstarPosition start, AstarPosition destination) {
    
    hasPath = true;
    
    // The gValue in destination as Node is not right !
    destinationAsNode = new Node(0, 0, destination);
    
    int estimatedDistanceFromDestination = estimatedDistance(start);
    
    Node startNode = new Node(0, estimatedDistanceFromDestination, start);
    
    closedList.add(startNode);
    
    this.current = startNode;
    
    // Solange die Node die als n�chstes untersucht werden soll nicht die ZielNode ist muss weiter gesucht werden
    while (!current.isEqual(destinationAsNode) && hasPath) {

      System.out.println(printNodeList(closedList));

      System.out.println(current.getPosition().toString());
      
      addAdjacentNodesToOpenList(current, false);

      System.out.println(printNodeList(openList));
      
      Collections.sort(openList, new NodeComparator());

      System.out.println(printNodeList(openList));
      
      if (openList.size() > 0) {
        current = openList.get(0);

        System.out.println(openList.get(0).getPosition().toString());
        closedList.add(openList.get(0));
        openList.remove(0);

        System.out.println(printNodeList(openList));

      } else {
        hasPath = false;
      }
    }
    return traceBackParents(current);
  }
  
  private List<Node> traceBackParents (Node node) {
    List<Node> parents = new LinkedList<Node>();
    parents.add(node);
    while(node.getParent() != null) {
      node = node.getParent();
      parents.add(node);
    }
    
    Collections.reverse(parents);
    
    return parents;
  }
  
  
  /**
   * Die Methode findet nicht nur alle angrenzenden Nodes sondern erstellt diese Nodes auch.
   * Soll hei�en das alle g, h und f Werte f�r die entsprechenden Nodes berechnet werden.
   * Es wird auch gechecked ob sich auf einem m�glichem Nachbarn eine Wand befindet.
   * @param node Die Node die gerade untersucht wird
   */
  private void addAdjacentNodesToOpenList (Node node, boolean includeDiagonalNeighbours) {
    //Nodes in the closed list wont be checked again
    List<Node> adjacentNodes = new ArrayList<Node>();
    int parentGValue = node.getgValue();
    
    
    AstarPosition leftAbove = new AstarPosition(node.getPosition().getX() - 1, node.getPosition().getY() - 1);
    AstarPosition directAbove = new AstarPosition(node.getPosition().getX(), node.getPosition().getY() - 1);
    AstarPosition rightAbove = new AstarPosition(node.getPosition().getX() + 1, node.getPosition().getY() - 1);
    AstarPosition left = new AstarPosition(node.getPosition().getX() - 1, node.getPosition().getY());
    AstarPosition right = new AstarPosition(node.getPosition().getX() + 1, node.getPosition().getY());
    AstarPosition leftBelow = new AstarPosition(node.getPosition().getX() - 1, node.getPosition().getY() + 1);
    AstarPosition directBelow = new AstarPosition(node.getPosition().getX(), node.getPosition().getY() + 1);
    AstarPosition rightBelow = new AstarPosition(node.getPosition().getX() + 1, node.getPosition().getY() + 1);
    
    if (includeDiagonalNeighbours) {
      // gValue is 14 + parentGValue because this square is reached diagonally
      Node leftAboveNode = new Node(14 + parentGValue, estimatedDistance(leftAbove), leftAbove, node);
      // Die Node wird nur hinzugef�gt wenn sich auf der Node keine Wand befindet
      // W�nde aussortieren funktioniert nicht
      if (!checkIfInWalls(leftAbove)) {
        adjacentNodes.add(leftAboveNode);
      }
      
      Node rightAboveNode = new Node(14 + parentGValue, estimatedDistance(rightAbove), rightAbove, node);
      if (!checkIfInWalls(rightAbove)) {
        adjacentNodes.add(rightAboveNode);      
      }
      
      Node leftBelowNode = new Node(14 + parentGValue, estimatedDistance(leftBelow), leftBelow, node);
      if (!checkIfInWalls(leftBelow)) {
        adjacentNodes.add(leftBelowNode);      
      }
      
      Node rightBelowNode = new Node(14 + parentGValue, estimatedDistance(rightBelow), rightBelow, node);
      if (!checkIfInWalls(rightBelow)) {
        adjacentNodes.add(rightBelowNode);
      }
    }
    
    // gValue is 10 + parentGValue because this square is reached directly
    Node directAboveNode = new Node(10 + parentGValue, estimatedDistance(directAbove), directAbove, node);
    if (!checkIfInWalls(directAbove)) {
      adjacentNodes.add(directAboveNode);
    }
    
    
    Node leftNode = new Node(10 + parentGValue, estimatedDistance(left), left, node);
    if (!checkIfInWalls(left)) {
      adjacentNodes.add(leftNode);      
    }
    
    Node rightNode = new Node(10 + parentGValue, estimatedDistance(right), right, node);
    if (!checkIfInWalls(right)) {
      adjacentNodes.add(rightNode);      
    }
    
 
    
    Node directBelowNode = new Node(10 + parentGValue, estimatedDistance(directBelow), directBelow, node);
    if (!checkIfInWalls(directBelow)) {
      adjacentNodes.add(directBelowNode);
    }
    
    for(Node adjacent : adjacentNodes) {
      checkAndAddAdjacentNodes(adjacent);
    }
  }

  
  private void checkAndAddAdjacentNodes (Node node) {
    
    Node nodeFromOpenList;
    boolean alreadyAdded = false;
    //Fehler wahrscheinlich durch die 2 add Befehle
    // if not out of Bound && not in closed list
    if (!checkIfInClosedList(node) && !node.getPosition().isOutOfBound()) {
      // if already in open list update f value and parent
      // If the Node is not out of Bound and not on the closed List it can still be on the open List
      if ((nodeFromOpenList = checkIfInOpenList(node)) != null) {
        //If the Node is on the open list check if the new g value is lower
        if (node.gValue <= nodeFromOpenList.getgValue()) {
          // If the new Way to reach this node is shorter update the open list
          openList.remove(nodeFromOpenList);
          openList.add(node);
          alreadyAdded = true;
        }
      }
      if(!alreadyAdded) {
        openList.add(node);
      }
    }
  }  
 
  private void deleteInstancesOfNodeFromOpenList (Node node) {
    for (Node n : openList) {
      if(node.equals(n)) {
        openList.remove(n);
      }
    }
  }
  
  public Node checkIfInOpenList (Node node) {
    Node isIn = null;
    for (Node n : openList) {
      if(node.isEqual(n)) {
        isIn = n;
      }
    }
    return isIn;
  }
  
  public boolean checkIfInClosedList (Node node) {
    boolean isIn = false;
    for (Node n : closedList) {
      if(node.isEqual(n)) {
        isIn = true;
      }
    }
    return isIn;
  }
  
  public boolean checkIfInWalls (AstarPosition position) {
    boolean isIn = false;
    for (AstarPosition p : walls) {
      if(position.isEqual(p)) {
        isIn = true;
      }
    }
    return isIn;
  }
  
  /**
   * Estimates the Distance to the Destination
   * Diognal Move cost is 14 straight move cost is 10
   * @param position / The AstarPosition from which the distance is calculated
   * @return the estimated distance
   */
  private int estimatedDistance (AstarPosition position) {
    // erst Schritte auf der x Achse machen 
    // dann Schritte auf der y Achse machen
    int destX = destinationAsNode.getPosition().getX();
    int destY = destinationAsNode.getPosition().getY();
    int nodeX = position.getX();
    int nodeY = position.getY();
    int needX = 0;
    int needY = 0;
    
    if (destX < nodeX) {
      needX = nodeX - destX;
    } else if (destX > nodeX) {
      needX = destX - nodeX;
    }
    
    if (destY < nodeY) {
      needY = nodeY - destY;
    } else if (destY > nodeY) {
      needY = destY - nodeY;
    }
    
    needX = needX * 10;
    needY = needY * 10;
    
    return needX + needY;
  }
  
  public String printNodeList (List<Node> listOfNodes) {
    StringBuilder sb = new StringBuilder();
    for (Node n : listOfNodes) {
      sb.append(n.getPosition().toString()).append("\n");
      sb.append("F Value = " + n.getfValue()).append("\n");
    }
    return sb.toString();
  }
  
  public String printPositionList (List<AstarPosition> listOfPositions) {
    StringBuilder sb = new StringBuilder();
    for (AstarPosition p : listOfPositions) {
      sb.append(p.toString()).append("\n");
    }
    return sb.toString();
  }


  public void setOpenList(List<Node> openList) {
    this.openList = openList;
  }


  public void setClosedList(List<Node> closedList) {
    this.closedList = closedList;
  } 
}

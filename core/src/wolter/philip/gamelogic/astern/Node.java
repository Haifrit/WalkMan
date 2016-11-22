package wolter.philip.gamelogic.astern;

public class Node {
  
  // The cost to move to this node
  int gValue;
  // The estimated cost to reach the destination
  int hValue;
  // = gValue + hValue
  int fValue;
  //The parent Node
  Node parent;
  //The position of the Node
  AstarPosition position;
  
  //Constructor for the start Node
  public Node(int gValue, int hValue, AstarPosition position) {
    super();
    this.gValue = gValue;
    this.hValue = hValue;
    this.fValue = gValue + hValue;
    this.parent = null;
    this.position = position;
  }
  
  //Constructor for all other Nodes
  public Node (int gValue, int hValue, AstarPosition position, Node parent) {
    super();
    this.gValue = gValue;
    this.hValue = hValue;
    this.fValue = gValue + hValue; 
    this.parent = parent;
    this.position = position;
  }

  // The Parent of a Node can change
  public void setParent(Node parent) {
    this.parent = parent;
  }

  public AstarPosition getPosition() {
    return position;
  }
  
  public void setgValue(int gValue) {
    this.gValue = gValue;
  }
  
  public int getgValue() {
    return gValue;
  }

  public boolean isEqual (Node node) {
    if (node.getPosition().getX() == position.getX() && node.getPosition().getY() == position.getY()) {
      return true;
    } else {
      return false;
    }
  }

  public int getfValue() {
    return fValue;
  }

  public Node getParent() {
    return parent;
  }
}
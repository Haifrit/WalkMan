package wolter.philip.gamelogic.support;

import java.util.Comparator;
import wolter.philip.gamelogic.astern.Node;

public class NodeComparator implements Comparator<Node> {

  // If arg0 == arg1 return 0
  // If arg0 > arg1 return 1 / wird weiter hinten in der Liste eingef�gt
  // If arg0 < arg1 return -1 / wird weiter vorne in der Liste eingf�gt
  @Override
  public int compare(Node arg0, Node arg1) {
    int arg0Fvalue = arg0.getfValue();
    int arg1Fvalue = arg1.getfValue();
    
    if ( arg0Fvalue == arg1Fvalue) {
      return 0;
    } else if (arg0Fvalue > arg1Fvalue) {
      return 1;
    } else {
      return -1;
    }
  }
}

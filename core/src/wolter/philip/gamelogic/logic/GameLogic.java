package wolter.philip.gamelogic.logic;

import java.util.List;

import wolter.philip.gamelogic.astern.Navigator;
import wolter.philip.gamelogic.astern.Position;

/**
 * Created by J.Wolter on 22.11.2016.
 */

public class GameLogic {

  Navigator navigator;

  public GameLogic (List<Position> walls) {

    navigator = new Navigator(walls);

  }
}

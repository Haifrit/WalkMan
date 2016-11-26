package wolter.philip.gamelogic.support;

import java.util.Random;

/**
 * Created by J.Wolter on 26.11.2016.
 */

public class RandomeGenerator {

  Random random;

  public RandomeGenerator() {

    random = new Random();

  }

  public int generateRandomeFromMinToMax (int min, int max) {
    return random.nextInt((max - min) + 1) + min;
  }
}

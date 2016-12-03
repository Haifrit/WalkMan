package wolter.philip.gamelogic.support;

import java.util.Random;

/**
 * Created by J.Wolter on 26.11.2016.
 */

public class RandomGenerator {

  Random random;

  public RandomGenerator() {

    random = new Random();

  }

  public int generateRandomFromMinToMax(int min, int max) {
    return random.nextInt((max - min) + 1) + min;
  }
}

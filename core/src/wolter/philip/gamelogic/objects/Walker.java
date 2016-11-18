package wolter.philip.gamelogic.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wolter.philip.gamelogic.support.Position;

/**
 * Created by J.Wolter on 18.11.2016.
 */

public class Walker {

  int velocity = 35;
  Position position;

  TextureRegion[][] tmp;

  Texture walkie;

  TextureRegion[][] walkieSprites;

  Animation walkingUpAnimation;
  Animation walkingDownAnimation;
  Animation walkingLeftAnimation;
  Animation walkingRightAnimation;

  TextureRegion [] walkingUpFrames;
  TextureRegion [] walkingDownFrames;
  TextureRegion [] walkingRightFrames;
  TextureRegion [] walkingLeftFrames;

  TextureRegion	currentFrame;

  public Walker(Position position) {
    this.position = position;

    walkie = new Texture("walkie.png");

    walkieSprites = TextureRegion.split(walkie,18,29);

    walkingUpFrames = walkieSprites [0];
    walkingDownFrames = walkieSprites [1];
    walkingLeftFrames = walkieSprites  [2];
    walkingRightFrames = walkieSprites  [3];

    walkingUpAnimation = new Animation(0.250f, walkingUpFrames);
    walkingDownAnimation = new Animation(0.250f, walkingDownFrames);
    walkingLeftAnimation = new Animation(0.250f, walkingLeftFrames);
    walkingRightAnimation = new Animation(0.250f, walkingRightFrames);
  }

  public void moveOneTileLeft (float stateTime) {
    currentFrame = walkingLeftAnimation.getKeyFrame(stateTime, true);
  }

  public Position getPosition() {
    return position;
  }

  public TextureRegion getCurrentFrame() {
    return currentFrame;
  }

}

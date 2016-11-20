package wolter.philip.gamelogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wolter.philip.gamelogic.support.Position;

/**
 * Created by J.Wolter on 18.11.2016.
 */

public class Walker {

  int velocity = 45;
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

    flipTextureRegions();

    walkingUpAnimation = new Animation(0.250f, walkingUpFrames);
    walkingDownAnimation = new Animation(0.250f, walkingDownFrames);
    walkingLeftAnimation = new Animation(0.250f, walkingLeftFrames);
    walkingRightAnimation = new Animation(0.250f, walkingRightFrames);
    currentFrame = walkieSprites[2][0];
  }

  private void flipTextureRegions () {
    for (TextureRegion tr : walkingLeftFrames) {
      tr.flip(false,true);
    }

    for (TextureRegion tr : walkingRightFrames) {
      tr.flip(false,true);
    }

    for (TextureRegion tr : walkingDownFrames) {
      tr.flip(false,true);
    }

    for (TextureRegion tr : walkingUpFrames) {
      tr.flip(false,true);
    }
  }

  public boolean moveLeft (float stateTime, float xDestination) {
    boolean reached = false;
    currentFrame = walkingLeftAnimation.getKeyFrame(stateTime, true);
    position.subFromXPosition(Gdx.graphics.getDeltaTime() * velocity);
    if (position.getxPosition() < xDestination) {
        position.setxPosition(xDestination);
        reached = true;
        currentFrame = walkieSprites[2][1];
    }
    return reached;
  }

  public boolean moveRight (float stateTime, float xDestination) {
    boolean reached = false;
    currentFrame = walkingRightAnimation.getKeyFrame(stateTime, true);
    position.addToXPosition(Gdx.graphics.getDeltaTime() * velocity);
    if (position.getxPosition() > xDestination) {
      position.setxPosition(xDestination);
      reached = true;
      currentFrame = walkieSprites[3][1];
    }
    return reached;
  }

  public boolean moveDown (float stateTime, float yDestination) {
    boolean reached = false;
    currentFrame = walkingDownAnimation.getKeyFrame(stateTime, true);
    position.addToYPosition(Gdx.graphics.getDeltaTime() * velocity);
    if (position.getyPosition() > yDestination) {
      position.setyPosition(yDestination);
      reached = true;
      currentFrame = walkieSprites [1][2];
    }
    return reached;
  }

  public boolean moveUp (float stateTime, float yDestination) {
    boolean reached = false;
    currentFrame = walkingUpAnimation.getKeyFrame(stateTime, true);
    position.subFromYPosition(Gdx.graphics.getDeltaTime() * velocity);
    if (position.getyPosition() < yDestination) {
      position.setyPosition(yDestination);
      reached = true;
      currentFrame = walkieSprites [0][2];
    }
    return reached;
  }

  public Position getPosition() {
    return position;
  }

  public TextureRegion getCurrentFrame() {
    return currentFrame;
  }

}

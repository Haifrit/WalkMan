package wolter.philip.gamelogic.objects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by J.Wolter on 20.11.2016.
 */

public class BackgroundTile {
  int xPosition;
  int yPosition;
  Texture texture;

  public BackgroundTile(int xPosition, int yPosition, Texture texture) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.texture = texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public int getxPosition() {
    return xPosition;
  }

  public int getyPosition() {
    return yPosition;
  }

  public Texture getTexture() {
    return texture;
  }
}

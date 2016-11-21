package wolter.philip.gamelogic.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by J.Wolter on 20.11.2016.
 */

public class BackgroundTile {
  int xPosition;
  int yPosition;
  TextureRegion texture;

  public BackgroundTile(int xPosition, int yPosition, TextureRegion texture) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.texture = texture;
  }

  public void setTexture(TextureRegion texture) {
    this.texture = texture;
  }

  public int getxPosition() {
    return xPosition;
  }

  public int getyPosition() {
    return yPosition;
  }

  public TextureRegion getTexture() {
    return texture;
  }
}

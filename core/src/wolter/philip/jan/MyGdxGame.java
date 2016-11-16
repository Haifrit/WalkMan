package wolter.philip.jan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javafx.scene.input.KeyCode;


public class MyGdxGame extends ApplicationAdapter {

	private static final String LOG_TAG =MyGdxGame.class.getSimpleName();
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 720, 1280);
	}

	@Override
	public void render () {
		float xCord = 0;
		float yCord = 0;
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, xCord, yCord);
		if (Gdx.input.isTouched()) {
			xCord = Gdx.input.getX();
			yCord = Gdx.input.getY();
			Gdx.app.log(LOG_TAG, " Screen has been touched at X = " + xCord + " Y = " + yCord);
			batch.draw(img, xCord, yCord);
		}
		batch.end();
	}
}

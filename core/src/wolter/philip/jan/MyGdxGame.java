package wolter.philip.jan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import wolter.philip.gamelogic.objects.Walker;
import wolter.philip.gamelogic.support.Position;


public class MyGdxGame extends ApplicationAdapter {

	private static final String LOG_TAG =MyGdxGame.class.getSimpleName();

	Walker walker;
	Position position;

	OrthographicCamera camera;
	SpriteBatch batch;
	Texture grass;

	float stateTime;
	float lastStateTime;
	boolean reached;
	
	@Override
	public void create () {
		reached = false;
		position = new Position(200,200);
		walker = new Walker(position);
		batch = new SpriteBatch();
		grass = new Texture("grass.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 224, 416);
		stateTime = 0f;
		lastStateTime = 0f;
	}

	@Override
	public void render () {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.app.log("RENDER", " statetime is =  " + stateTime);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		drawBackground();

		batch.draw(walker.getCurrentFrame(),walker.getPosition().getxPosition(),walker.getPosition().getyPosition());

		if (!reached) {
			reached = walker.moveUp(stateTime,150);
		}

		if (Gdx.input.isTouched()) {
			Gdx.app.log(LOG_TAG, "Screen has been touched");
		}
		batch.end();
	}

	private void drawBackground () {
		for (int x = 0; x <= 352; x = x + 32) {
			for (int y = 0; y <= 608; y = y + 32) {
				batch.draw(grass, x, y);
			}
		}
	}
}

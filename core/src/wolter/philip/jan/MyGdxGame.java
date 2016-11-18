package wolter.philip.jan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javafx.scene.input.KeyCode;


public class MyGdxGame extends ApplicationAdapter {

	private static final String LOG_TAG =MyGdxGame.class.getSimpleName();
	private static final int        FRAME_COLS = 3;
	private static final int        FRAME_ROWS = 4;
	Animation walkieAnimation;
	Animation walkingUpAnimation;
	Animation walkingDownAnimation;
	Animation walkingLeftAnimation;
	Animation walkingRightAnimation;
	TextureRegion[][] walkieSprites;
	TextureRegion [] walkieFrames;
	TextureRegion [] walkingUpFrames;
	TextureRegion [] walkingDownFrames;
	TextureRegion [] walkingRightFrames;
	TextureRegion [] walkingLeftFrames;
	TextureRegion    currentWalkieFrame;
	TextureRegion	currentWalkingRightFrame;
	TextureRegion	currentWalkingLeftFrame;
	TextureRegion	currentWalkingUpFrame;
	TextureRegion	currentWalkingDownFrame;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img,grass,stone,walkie;
	float stateTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		makeWalieFrames();
		makeWalkieAnimations();
		grass = new Texture("grass.png");
		stone = new Texture("Stone.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 224, 416);
		stateTime = 0f;
	}

	@Override
	public void render () {
		stateTime += Gdx.graphics.getDeltaTime();
		currentWalkingDownFrame = walkingDownAnimation.getKeyFrame(stateTime, true);
		currentWalkingUpFrame = walkingUpAnimation.getKeyFrame(stateTime, true);
		currentWalkingLeftFrame = walkingLeftAnimation.getKeyFrame(stateTime, true);
		currentWalkingRightFrame = walkingRightAnimation.getKeyFrame(stateTime, true);
		float xCord = 0;
		float yCord = 0;
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawBackground();
		batch.draw(currentWalkingDownFrame, 10, 10);
		batch.draw(currentWalkingUpFrame, 50, 50);
		batch.draw(currentWalkingLeftFrame, 100, 100);
		batch.draw(currentWalkingRightFrame, 180, 180);
		if (Gdx.input.isTouched()) {
			xCord = Gdx.input.getX();
			yCord = Gdx.input.getY();
			Gdx.app.log(LOG_TAG, " Screen has been touched at X = " + xCord + " Y = " + yCord);
			batch.draw(stone, xCord, yCord);
		}
		batch.end();
	}

	private void makeWalieFrames () {

		Gdx.app.log("MAKEFRAMES", "start makeWalkieFrames");

		walkie = new Texture("walkie.png");
		walkieSprites = TextureRegion.split(walkie,18,29);

		Gdx.app.log("MAKEFRAMES", "splitted Texture Region outer Array " + walkieSprites.length);
		Gdx.app.log("MAKEFRAMES", "splitted Texture Region inner Arryays " + walkieSprites[0].length + walkieSprites[1].length
		+ walkieSprites[2].length + walkieSprites[3].length);

		walkingUpFrames = walkieSprites [0];
		Gdx.app.log("MAKEFRAMES", "made walkingUpFrames");

		walkingDownFrames = walkieSprites [1];
		Gdx.app.log("MAKEFRAMES", "made walkingDownFrames");

		walkingLeftFrames = walkieSprites  [2];
		Gdx.app.log("MAKEFRAMES", "made walkingLeftFrames");

		walkingRightFrames = walkieSprites  [3];
		Gdx.app.log("MAKEFRAMES", "made walkingRightFrames");
	}

	private void makeWalkieAnimations () {
		walkingUpAnimation = new Animation(0.250f, walkingUpFrames);
		walkingDownAnimation = new Animation(0.250f, walkingDownFrames);
		walkingLeftAnimation = new Animation(0.250f, walkingLeftFrames);
		walkingRightAnimation = new Animation(0.250f, walkingRightFrames);
	}

	private void buildWalkieFrames () {
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkieFrames[index++] = walkieSprites[i][j];
			}
		}
	}

	private void drawBackground () {
		for (int x = 0; x <= 352; x = x + 32) {
			for (int y = 0; y <= 608; y = y + 32) {
				batch.draw(grass, x, y);
			}
		}
	}
}

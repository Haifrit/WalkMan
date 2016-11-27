package wolter.philip.jan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;


import java.util.ArrayList;
import java.util.List;

import wolter.philip.gamelogic.astern.AstarPosition;
import wolter.philip.gamelogic.logic.GameLogic;
import wolter.philip.gamelogic.objects.BackgroundTile;
import wolter.philip.gamelogic.objects.Walker;
import wolter.philip.gamelogic.support.Bound;
import wolter.philip.gamelogic.support.GamePhase;
import wolter.philip.gamelogic.support.Position;
import wolter.philip.gamelogic.support.RandomeGenerator;
import wolter.philip.gamelogic.support.Waypoint;


public class MyGdxGame extends ApplicationAdapter {

	private static final String LOG_TAG =MyGdxGame.class.getSimpleName();
	private static final int GAME_X_WIDTH = 288;
	private static final int GAME_Y_HEIGHT = 384;
	private static final int WALKER_START_X = GAME_X_WIDTH / 2;
	private static final int WALKER_START_Y = GAME_Y_HEIGHT;

	RandomeGenerator randomeGenerator;
	GamePhase gamePhase;
	int stoneCount;
	List<AstarPosition> astarPositionList;
	List<Waypoint> waypointList;
	List<AstarPosition> futureList;
	List<BackgroundTile> backgroundTileList;
	List<BackgroundTile> stones;
	Waypoint current;
	int index;

	GameLogic gameLogic;
	Walker walker;
	Position position;

	Vector3 vector3;

	OrthographicCamera camera;
	SpriteBatch batch;
	Texture grass,stone;
	TextureRegion trGrass;
	TextureRegion trStone;

	float stateTime;
	float lastStateTime;
	boolean reached;
	
	@Override
	public void create () {
		randomeGenerator = new RandomeGenerator();
		gamePhase = GamePhase.GENERATING;
		stoneCount = 25;
		index = 0;
		waypointList = new ArrayList<Waypoint>();
		reached = false;
		position = new Position(WALKER_START_X,WALKER_START_Y);
		walker = new Walker(position);
		batch = new SpriteBatch();
		grass = new Texture("grass.png");
		stone = new Texture("Stone.png");
		trGrass = new TextureRegion();
		trStone = new TextureRegion();
		trGrass.setRegion(grass);
		trStone.setRegion(stone);
		trGrass.flip(false,true);
		trStone.flip(false,true);
		backgroundTileList = new ArrayList<BackgroundTile>();
		stones = new ArrayList<BackgroundTile>();
		createInitialBackgroundTiles();
		astarPositionList = new ArrayList<AstarPosition>();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, GAME_X_WIDTH, GAME_Y_HEIGHT);
		stateTime = 0f;
		lastStateTime = 0f;
	}

	@Override
	public void render () {
		Gdx.app.log("RENDER"," Arraysize = " + waypointList.size());
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.app.log("RENDER", " statetime is =  " + stateTime);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawBackgroundFromList();

		if (gamePhase == GamePhase.GENERATING) {
			prePlacedStones();
			futureList = new ArrayList<AstarPosition>();
			int randomStoneCount = randomeGenerator.generateRandomeFromMinToMax(5,15);
			for (int i = 0; i <= randomStoneCount; i++) {
				// Eine Position wie sie vom Navigator benötigt wird
				int rX = randomeGenerator.generateRandomeFromMinToMax(0,(GAME_X_WIDTH / 32));
				int rY = randomeGenerator.generateRandomeFromMinToMax(0,(GAME_Y_HEIGHT / 32));
				AstarPosition astarPosition = new AstarPosition(rX, rY);
				futureList.add(astarPosition);
				if (isNotBlockingPreGame()) {
					BackgroundTile preTile = new BackgroundTile((rX * 32), (rY * 32),trStone);
					if (!isInStones(preTile)) {
						astarPositionList.add(astarPosition);
						stones.add(preTile);
					}
				}
			}
			gamePhase = GamePhase.PLACEING;
		} else if (gamePhase == GamePhase.PLACEING) {
			placingStones();
		} else if (gamePhase == GamePhase.CALCULATING) {
			gameLogic =  new GameLogic(astarPositionList);
			waypointList = gameLogic.getPathAsWaypoints();
			gamePhase = GamePhase.WALKING;
		} else if (gamePhase == GamePhase.WALKING) {
			if (waypointList.size() > 0) {
				current = waypointList.get(0);
			}
			batch.draw(walker.getCurrentFrame(),walker.getPosition().getxPosition(),walker.getPosition().getyPosition());
			moveWalkerTowardsWaypoint();
		}

		drawStones();

		batch.end();
	}

	private void prePlacedStones () {
		AstarPosition pre1 = new AstarPosition(8, 11);
		AstarPosition pre2 = new AstarPosition(7, 11);
		AstarPosition pre3 = new AstarPosition(6, 11);
		AstarPosition pre4 = new AstarPosition(5, 11);
		AstarPosition pre5 = new AstarPosition(3, 11);
		AstarPosition pre6 = new AstarPosition(2, 11);
		AstarPosition pre7 = new AstarPosition(1, 11);
		AstarPosition pre8 = new AstarPosition(0, 11);

		BackgroundTile preTile1 = new BackgroundTile((8 * 32), (11 * 32),trStone);
		BackgroundTile preTile2 = new BackgroundTile((7 * 32), (11 * 32),trStone);
		BackgroundTile preTile3 = new BackgroundTile((6 * 32), (11 * 32),trStone);
		BackgroundTile preTile4 = new BackgroundTile((5 * 32), (11 * 32),trStone);
		BackgroundTile preTile5 = new BackgroundTile((3 * 32), (11 * 32),trStone);
		BackgroundTile preTile6 = new BackgroundTile((2 * 32), (11 * 32),trStone);
		BackgroundTile preTile7 = new BackgroundTile((1 * 32), (11 * 32),trStone);
		BackgroundTile preTile8 = new BackgroundTile((0 * 32), (11 * 32),trStone);

		astarPositionList.add(pre1);
		stones.add(preTile1);

		astarPositionList.add(pre2);
		stones.add(preTile2);

		astarPositionList.add(pre3);
		stones.add(preTile3);

		astarPositionList.add(pre4);
		stones.add(preTile4);

		astarPositionList.add(pre5);
		stones.add(preTile5);

		astarPositionList.add(pre6);
		stones.add(preTile6);

		astarPositionList.add(pre7);
		stones.add(preTile7);

		astarPositionList.add(pre8);
		stones.add(preTile8);
	}

	private void placingStones () {
		if (Gdx.input.isTouched()) {
			Gdx.app.log("Touch", "Screen has been touched");
			vector3 = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(vector3);
			Gdx.app.log("placing", "in placing AT X = " + vector3.x + " At Y = " + vector3.y);
			BackgroundTile backgroundTile = new BackgroundTile(calculateStoneXY(vector3.x), calculateStoneXY(vector3.y),trStone);
			if (isNotBlocking(backgroundTile)) {
				if (!isInStones(backgroundTile)) {
					astarPositionList.add(convertTexturePositionToAstarPosition(vector3.x, vector3.y)); // TODO check for doubles
					stones.add(backgroundTile);
					stoneCount--;
				}
			}
			Gdx.app.log("Touch", "stoneCount--");
		}

		if (stoneCount == 0) {
			gamePhase = GamePhase.CALCULATING;
		}
	}

	private boolean isNotBlocking(BackgroundTile backgroundTile) {
		futureList = new ArrayList<AstarPosition>();
		futureList.addAll(astarPositionList);
		convertTextureToAstarGridAndAddToFuture(backgroundTile.getxPosition(),backgroundTile.getyPosition());
		GameLogic blockTester = new GameLogic(futureList);
		blockTester.getPathAsWaypoints();
		Gdx.app.log("PATHTEST", "Has Path ? == " + blockTester.hasPath());
		return blockTester.hasPath();
	}

	private boolean isNotBlockingPreGame () {
		GameLogic blockTester = new GameLogic(futureList);
		blockTester.getPathAsWaypoints();
		return blockTester.hasPath();
	}

	private boolean isInStones (BackgroundTile backgroundTile) {
		boolean isIn = false;
		for (BackgroundTile tileFromList : stones) {
			if (backgroundTile.isEqual(tileFromList)) {
				isIn = true;
			}
		}
		return isIn;
	}

	private void convertTextureToAstarGridAndAddToFuture (int xPos, int yPos) {
		int xPosition = (int) (xPos / 32);
		int yPosition = (int) (yPos / 32);
		AstarPosition astarPosition = new AstarPosition(xPosition,yPosition);
		futureList.add(astarPosition);
	}

	private AstarPosition convertTexturePositionToAstarPosition (float vectorX, float vectorY) {
		int xPosition = (int) (vectorX / 32);
		int yPosition = (int) (vectorY / 32);
		AstarPosition astarPosition = new AstarPosition(xPosition, yPosition);
		return astarPosition;
	}

	private int calculateStoneXY(float vector) {
		Gdx.app.log("Touch", "X Touch vector as float" + vector);
		int erg = (int) (vector / 32);
		Gdx.app.log("Touch", "X Touch vector as int" + erg);
		return erg * 32;
	}

	private void createInitialBackgroundTiles () {
		for (int x = 0; x <= GAME_X_WIDTH; x = x + 32) {
			for (int y = 0; y <= GAME_Y_HEIGHT; y = y + 32) {
				BackgroundTile backgroundTile = new BackgroundTile(x,y,trGrass);
				backgroundTileList.add(backgroundTile);
			}
		}
	}

	private void moveWalkerTowardsWaypoint () {

		if ((waypointList.size() > 0) && waypointList.get(index).getDirection() == "UP"  ) {
			if (!reached) {
				reached = walker.moveUp(stateTime,waypointList.get(index).getDestination());
			} else {
				reached = false;
				waypointList.remove(0);
			}
		}

		if ((waypointList.size() > 0) && waypointList.get(index).getDirection() == "RIGHT"  ) {
			if (!reached) {
				reached = walker.moveRight(stateTime,waypointList.get(index).getDestination());
			} else {
				reached = false;
				waypointList.remove(0);
			}
		}

		if ((waypointList.size() > 0) && waypointList.get(index).getDirection() == "LEFT"  ) {
			if (!reached) {
				reached = walker.moveLeft(stateTime,waypointList.get(index).getDestination());
			} else {
				reached = false;
				waypointList.remove(0);
			}
		}

		if ((waypointList.size() > 0) && waypointList.get(index).getDirection() == "DOWN"  ) {
			if (!reached) {
				reached = walker.moveDown(stateTime,waypointList.get(index).getDestination());
			} else {
				reached = false;
				waypointList.remove(0);
			}
		}
	}

	private void drawStones () {
		for ( BackgroundTile backgroundTile : stones) {
			batch.draw(backgroundTile.getTexture(),backgroundTile.getxPosition(),backgroundTile.getyPosition());
		}
	}

	private void drawBackgroundFromList () {
		for (BackgroundTile backgroundTile : backgroundTileList) {
			batch.draw(backgroundTile.getTexture(),backgroundTile.getxPosition(),backgroundTile.getyPosition());
		}
	}
}

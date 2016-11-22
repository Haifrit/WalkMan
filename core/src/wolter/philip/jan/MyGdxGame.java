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
import wolter.philip.gamelogic.support.Position;
import wolter.philip.gamelogic.support.Waypoint;


public class MyGdxGame extends ApplicationAdapter {

	private static final String LOG_TAG =MyGdxGame.class.getSimpleName();

	List<AstarPosition> astarPositionList;
	List<Waypoint> waypointList;
	List<BackgroundTile> backgroundTileList;
	List<BackgroundTile> stones;
	Waypoint waypoint1;
	Waypoint waypoint2;
	Waypoint waypoint3;
	Waypoint waypoint4;
	Waypoint waypoint5;
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
		index = 0;
		waypoint1 = new Waypoint("UP", 224);
		waypoint2 = new Waypoint("RIGHT", 164);
		waypoint3 = new Waypoint("DOWN",320);
		waypoint4 = new Waypoint("LEFT", 100);
		waypoint5 = new Waypoint("UP",0);
		waypointList = new ArrayList<Waypoint>();
		waypointList.add(waypoint1);
		waypointList.add(waypoint2);
		waypointList.add(waypoint3);
		waypointList.add(waypoint4);
		waypointList.add(waypoint5);
		reached = false;
		position = new Position(100,416);
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
		gameLogic = new GameLogic(makeDummyWallList());
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 224, 416);
		stateTime = 0f;
		lastStateTime = 0f;
	}

	@Override
	public void render () {
		if (waypointList.size() > 0) {
			current = waypointList.get(0);
		}
		Gdx.app.log("RENDER"," Arraysize = " + waypointList.size());
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.app.log("RENDER", " statetime is =  " + stateTime);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//drawBackground();
		drawBackgroundFromList();

		batch.draw(walker.getCurrentFrame(),walker.getPosition().getxPosition(),walker.getPosition().getyPosition());

		moveWalkerTowardsWaypoint();

		drawStones();

		if (Gdx.input.isTouched()) {
			Gdx.app.log("Touch", "Screen has been touched");
			vector3 = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(vector3);
			Gdx.app.log("Touch", "AT X = " + vector3.x + " At Y = " + vector3.y);
			convertTextureToAstarGridAndAddToWalls(vector3.x, vector3.y);
			BackgroundTile backgroundTile = new BackgroundTile(calculateStoneX(vector3.x),calculateStoneY(vector3.y),trStone);
			stones.add(backgroundTile);
		}
		batch.end();
	}

	private void convertTextureToAstarGridAndAddToWalls (float vectorX, float vectorY) {
		int xPosition = (int) (vectorX / 32);
		int yPosition = (int) (vectorY / 32);
		AstarPosition astarPosition = new AstarPosition(xPosition, yPosition);
		astarPositionList.add(astarPosition);
	}

	private int calculateStoneX (float vector) {
		Gdx.app.log("Touch", "X Touch vector as float" + vector);
		int erg = (int) (vector / 32);
		Gdx.app.log("Touch", "X Touch vector as int" + erg);
		return erg * 32;
	}

	private int calculateStoneY (float vector) {
		Gdx.app.log("Touch", "Y Touch vector as float" + vector);
		int erg = (int) (vector / 32);
		Gdx.app.log("Touch", "Y Touch vector as int" + erg);
		return erg * 32;
	}

	private void createInitialBackgroundTiles () {
		for (int x = 0; x <= 224; x = x + 32) {
			for (int y = 0; y <= 416; y = y + 32) {
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

	private List<AstarPosition> makeDummyWallList () {
		List<AstarPosition> list = new ArrayList<AstarPosition>();
		AstarPosition p1 = new AstarPosition(1,1);
		AstarPosition p2 = new AstarPosition(1,2);
		list.add(p1);
		list.add(p2);
		return list;
	}
}

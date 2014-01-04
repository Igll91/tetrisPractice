package silvio.vuk.gamedev.tetrisboxes.screens;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.entitys.Box;
import silvio.vuk.gamedev.tetrisboxes.entitys.FieldVol2;
import silvio.vuk.gamedev.tetrisboxes.entitys.Score;
import silvio.vuk.gamedev.tetrisboxes.entitys.TetrisGameOverException;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.IShape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.JShape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.LShape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.OShape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.SShape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.Shape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.TShape;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.ZShape;
import silvio.vuk.gamedev.tetrisboxes.inputProcessors.GameScreenInputProcessor;
import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final GameScreenController gsc;
	Music					   backgoundMusic;
	
	private final String BACKGROUND_MUSIC_FILE_PATH = "res/backgroundSound.mp3";
	private final String BACKGROUND_IMAGE_FILE_PATH = "res/boxesBackground.jpg";
	
	private Queue<Shape> shapes = new LinkedList<Shape>();
	private Shape currentlyUsedShape;
	
	private boolean isGamePaused = true;
	private GameScreenInputProcessor gameScreenInputProcessor;
	
	private Texture IShapeTexture;
	private Texture JShapeTexture;
	private Texture LShapeTexture;
	private Texture OShapeTexture;
	private Texture SShapeTexture;
	private Texture TShapeTexture;
	private Texture ZShapeTexture;
	private Texture DShapeTexture; // D as dead
	
	private Texture BackgroundImage;
	
	private double SHAPE_MOVEMENT_DEFAULT_DELAY_SECONDS = 0.6;
	private double shapeMovementDelaySeconds;
	private long lastMoveTime = TimeUtils.nanoTime();
	
	private Score score;
	private FieldVol2 field;
	
	private float BackgroundSoundLevel = 0.8f;
	
	public GameScreen(final GameScreenController gsc)
	{
		this.gsc = gsc;
		
		backgoundMusic = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_MUSIC_FILE_PATH));
		backgoundMusic.setLooping(true);
		
		gameScreenInputProcessor = new GameScreenInputProcessor(gsc);
		shapeMovementDelaySeconds = SHAPE_MOVEMENT_DEFAULT_DELAY_SECONDS;
		field = new FieldVol2();
		gameScreenInputProcessor.setField(field);
		score = new Score();
		
		setUpTextures();
		setUpShapes();
	}
	
	private void setUpTextures()
	{
		IShapeTexture = new Texture(Gdx.files.internal("res/obj_box001.png"));
		JShapeTexture = new Texture(Gdx.files.internal("res/obj_box002.png"));
		LShapeTexture = new Texture(Gdx.files.internal("res/obj_box002.png"));
		OShapeTexture = new Texture(Gdx.files.internal("res/obj_box003.png"));
		SShapeTexture = new Texture(Gdx.files.internal("res/obj_box004.png"));
		TShapeTexture = new Texture(Gdx.files.internal("res/obj_box005.png"));
		ZShapeTexture = new Texture(Gdx.files.internal("res/obj_box004.png"));
		DShapeTexture = new Texture(Gdx.files.internal("res/obj_box004.png"));
		
		BackgroundImage = new Texture(Gdx.files.internal(BACKGROUND_IMAGE_FILE_PATH));
	}
	
	private void setUpShapes()
	{
		try 
		{
			for(ShapeID sID: ShapeID.getShapesInRandomOrder())
				shapes.add(getShapeByID(sID));
			
			currentlyUsedShape = shapes.poll();
			gameScreenInputProcessor.setCurrentlyUsedShape(currentlyUsedShape);
		} 
		catch (InvalidAttributeValueException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(gameScreenInputProcessor.isDeadDrop())
			shapeMovementDelaySeconds = 0.1;
		else // prebaciti logiku u input processor
			shapeMovementDelaySeconds = SHAPE_MOVEMENT_DEFAULT_DELAY_SECONDS;
		
		if(isGamePaused == false)
		{
			if(TimeUtils.nanoTime() - lastMoveTime > shapeMovementDelaySeconds * 1000000000)
			{	
				try 
				{
					if(field.checkCollision(currentlyUsedShape.getArrayOfBoxes()))
					{
						// check for explosion ! 
						Set<Integer> setOfColumns = field.checkRow(currentlyUsedShape.getArrayOfBoxes());
						if(setOfColumns.isEmpty() == false)
						{
							score.updateScore(setOfColumns.size());
							destroyRows(setOfColumns);
						}
						
						getNextShape();
						gameScreenInputProcessor.setDeadDrop(false);
						shapeMovementDelaySeconds = SHAPE_MOVEMENT_DEFAULT_DELAY_SECONDS;
					}
				} 
				catch (TetrisGameOverException e){
					gsc.setScreen(new GameOverScreen(gsc, this));
				}
				
	            currentlyUsedShape.fallingShape();
	            lastMoveTime = TimeUtils.nanoTime();
			}
		}
		gsc.getBatch().begin();

		// draws background
		gsc.getBatch().draw(BackgroundImage, 0, 0, Val.SCREEN_WIDTH, Val.SCREEN_HEIGHT);
		
		gsc.getBatch().end();
		
		// draws border of field
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0 + FieldVol2.FIELD_WIDTH_OFFSET, 0, FieldVol2.FIELD_WIDTH, FieldVol2.FIELD_HEIGHT);
        shapeRenderer.end();
		
        gsc.getBatch().begin();
		
		//draws field
		for(int counter = 0; counter < FieldVol2.NUMBER_OF_CELLS; counter++)
		{
			for(int inner_counter = 0; inner_counter < FieldVol2.NUMBER_OF_ROWS; inner_counter++)
			{
				Box tempBox = field.getBoxAtPosition(counter, inner_counter);
				if(tempBox != null)
					gsc.getBatch().draw(DShapeTexture, tempBox.getX() + FieldVol2.FIELD_WIDTH_OFFSET, tempBox.getY(), Val.BOX_DIMENSION, Val.BOX_DIMENSION);
			}
		}
		
		//draws curently used shape
		for(Box box: currentlyUsedShape.getArrayOfBoxes())
			gsc.getBatch().draw(getTextureByID(currentlyUsedShape.SHAPE_TEXTURE_ID), box.getX() + FieldVol2.FIELD_WIDTH_OFFSET, box.getY(), Val.BOX_DIMENSION, Val.BOX_DIMENSION);
		
		gsc.getBatch().end();
		
		// draws GUI remove later...
		shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(0, FieldVol2.FIELD_HEIGHT, Val.SCREEN_WIDTH, Val.SCREEN_HEIGHT - FieldVol2.FIELD_HEIGHT);
        shapeRenderer.end();
		
        gsc.getBatch().begin();
        
		// shows fps
		gsc.getFontPrisma().draw(gsc.getBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Val.SCREEN_HEIGHT - 10);
		
		// shows score for now
		gsc.getFontPrisma().draw(gsc.getBatch(), "SCORE: " + score.getPoints(), 200, Val.SCREEN_HEIGHT - 10);
		
		gsc.getBatch().end();
	}

	private Texture getTextureByID(final ShapeID textureID)
	{
		switch(textureID)
		{
		case I_SHAPE_TEXTURE_ID:
			return IShapeTexture;
		case J_SHAPE_TEXTURE_ID:
			return JShapeTexture;
		case L_SHAPE_TEXTURE_ID:
			return LShapeTexture;
		case O_SHAPE_TEXTURE_ID:
			return OShapeTexture;
		case S_SHAPE_TEXTURE_ID:
			return SShapeTexture;
		case T_SHAPE_TEXTURE_ID:
			return TShapeTexture;
		case Z_SHAPE_TEXTURE_ID:
			return ZShapeTexture;
		default:
			throw new IllegalArgumentException("NO texture with that ID! id =" +textureID );
		}
	}
	
	private Shape getShapeByID(final ShapeID textureID) throws InvalidAttributeValueException
	{
		switch(textureID)
		{
		case I_SHAPE_TEXTURE_ID:
			return new IShape();
		case J_SHAPE_TEXTURE_ID:
			return new JShape();
		case L_SHAPE_TEXTURE_ID:
			return new LShape();
		case O_SHAPE_TEXTURE_ID:
			return new OShape();
		case S_SHAPE_TEXTURE_ID:
			return new SShape();
		case T_SHAPE_TEXTURE_ID:
			return new TShape();
		case Z_SHAPE_TEXTURE_ID:
			return new ZShape();
		default:
			throw new IllegalArgumentException("NO shape with that ID! id =" +textureID );
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		System.out.println("game screen shown");
		backgoundMusic.play();
		isGamePaused = false;
		Gdx.input.setInputProcessor(gameScreenInputProcessor);
		lastMoveTime = TimeUtils.nanoTime() - lastMoveTime;
	}

	@Override
	public void hide() {
		System.out.println("game screen hidden");
		backgoundMusic.pause();
		isGamePaused = true;
		lastMoveTime = TimeUtils.nanoTime() - lastMoveTime;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		backgoundMusic.dispose();
		IShapeTexture.dispose();
		JShapeTexture.dispose();
		LShapeTexture.dispose();
		OShapeTexture.dispose();
		SShapeTexture.dispose();
		TShapeTexture.dispose();
		ZShapeTexture.dispose();
		field = null;
	}
	
	private void getNextShape()
	{
		if(shapes.isEmpty())
			setUpShapes();
		else
		{
			currentlyUsedShape = shapes.poll();
			gameScreenInputProcessor.setCurrentlyUsedShape(currentlyUsedShape);
		}
	}

	private void destroyRows(final Set<Integer> setOfIntegers)
	{
		int maxRow = 0;
		for(Integer count: setOfIntegers)
		{
			if(count > maxRow) maxRow = count;
			
			for(int counter = 0; counter < FieldVol2.NUMBER_OF_CELLS; counter++)
			{
				// poziv animacije explozije i na kraju poziv zvuka explozije ...
				field.destroyBox(counter, count);
			}
		}
		
		dropDownUpperBoxes(setOfIntegers.size(), maxRow);
	}
	
	private void dropDownUpperBoxes(final int numberOfRows, int rowID)
	{
		for(int outer_counter = 0; outer_counter < FieldVol2.NUMBER_OF_CELLS; outer_counter++)
			for(int counter = rowID + 1; counter < FieldVol2.NUMBER_OF_ROWS; counter++)
				field.dropBoxes(outer_counter, counter, numberOfRows);
	}
	
	public void resetGame()
	{
		shapes.clear();
		setUpShapes();
		
		score = new Score();
		field = new FieldVol2();
		gameScreenInputProcessor.setField(field);
		
		isGamePaused = false;
		lastMoveTime = TimeUtils.nanoTime();
		
		backgoundMusic.stop();
		
		System.gc();
	}
	
	public void muteSound()
	{
		if(backgoundMusic.getVolume() != 0)
			backgoundMusic.setVolume(0);
		else
			backgoundMusic.setVolume(BackgroundSoundLevel);
	}
	
	public void volumeUp()
	{
		backgoundMusic.setVolume(BackgroundSoundLevel + 1);
		if(backgoundMusic.getVolume() > 1.0)
			backgoundMusic.setVolume(1);
	}
	
	public void volumeDown()
	{
		backgoundMusic.setVolume(BackgroundSoundLevel - 1);
		if(backgoundMusic.getVolume() < 0.0)
			backgoundMusic.setVolume(0);
		// and set is muted to true
	}
}

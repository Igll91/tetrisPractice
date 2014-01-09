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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final GameScreenController gsc;
	Music					   backgoundMusic;
	Sound					   coinSound;
	
	private Camera camera;
	
	private Rectangle viewport;
	private Rectangle PauseButtonRectangle;
	private Rectangle MuteButtonRectangle;
	private Rectangle ScoreRectangle;
	private Rectangle ComboRectangle;
	private Rectangle ComboIconRectangle;
	private Rectangle ComboAchivementRectangle;
	
	private final String BACKGROUND_MUSIC_FILE_PATH 		= "res/backgroundSound.mp3";
	private final String BACKGROUND_IMAGE_FILE_PATH 		= "res/boxesBackground.jpg";
	private final String FIELD_BACKGROUND_IMAGE_FILE_PATH 	= "res/backgroundWhite.png";
	private final String NEXT_ITEM_BACKGROUND_IMAGE 		= "res/nextItem.png";
	private final String PANEL_BACKGROUND 					= "res/panel.png";
	private final String SOUND_ON_TEXTURE_PATH 				= "res/soundon.png";
	private final String SOUND_OFF_TEXTURE_PATH 			= "res/soundoff.png";
	private final String GAME_PAUSED_TEXTURE_PATH 			= "res/pause.png";
	private final String GAME_ACTIVE_TEXTURE_PATH 			= "res/play.png";
	private final String SCORE_TEXTURE_PATH 				= "res/score.png";
	private final String COMBO_TEXTURE_PATH 				= "res/combo.png";
	private final String COMBO_ON_TEXTURE_PATH 				= "res/fullCombo.png";
	private final String COMBO_OFF_TEXTURE_PATH 			= "res/emptyCombo.png";
	private final String COIN_SOUND_PATH 					= "res/coin-dropping.mp3";
	private final String COMBO_COLORS_TEXTURE_PATH			= "res/comboColors.png";
	
	private Queue<Shape> shapes = new LinkedList<Shape>();
	private Shape currentlyUsedShape;
	
	private boolean isGamePaused = true;
	private boolean isComboAchieved = false;
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
	private Texture FieldBackgroundImage;
	private Texture NextItemTextImage;
	private Texture PanelBackground;
	private Texture soundOnTexture;
	private Texture soundOffTexture;
	private Texture gamePausedTexture;
	private Texture gameActiveTexture;
	private Texture scoreTexture;
	private Texture comboTexture;
	private Texture comboOnTexture;
	private Texture comboOffTexture;
	private Texture comboColorsTexture;
	
	private TextureRegion 	currentAnimationTextureRegion; 
	private TextureRegion[] comboAnimationTextureRegion;
	private Animation		comboAnimation;
	
	private double SHAPE_MOVEMENT_DEFAULT_DELAY_SECONDS = 0.6;
	private double shapeMovementDelaySeconds;
	private long lastMoveTime = TimeUtils.nanoTime();
	private float comboAnimationTimer;
	
	private Score score;
	private FieldVol2 field;
	
	private float BackgroundSoundLevel = 0.8f;
	
	public GameScreen(final GameScreenController gsc)
	{
		this.gsc = gsc;
		
		backgoundMusic = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_MUSIC_FILE_PATH));
		backgoundMusic.setLooping(true);
		coinSound = Gdx.audio.newSound(Gdx.files.internal(COIN_SOUND_PATH));
		
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
		
		BackgroundImage 		= new Texture(Gdx.files.internal(BACKGROUND_IMAGE_FILE_PATH));
		FieldBackgroundImage 	= new Texture(Gdx.files.internal(FIELD_BACKGROUND_IMAGE_FILE_PATH));
		NextItemTextImage 		= new Texture(Gdx.files.internal(NEXT_ITEM_BACKGROUND_IMAGE));
		PanelBackground 		= new Texture(Gdx.files.internal(PANEL_BACKGROUND));
		soundOnTexture			= new Texture(Gdx.files.internal(SOUND_ON_TEXTURE_PATH));
		soundOffTexture			= new Texture(Gdx.files.internal(SOUND_OFF_TEXTURE_PATH));
		gamePausedTexture		= new Texture(Gdx.files.internal(GAME_PAUSED_TEXTURE_PATH));
		gameActiveTexture		= new Texture(Gdx.files.internal(GAME_ACTIVE_TEXTURE_PATH));
		scoreTexture			= new Texture(Gdx.files.internal(SCORE_TEXTURE_PATH));
		comboTexture			= new Texture(Gdx.files.internal(COMBO_TEXTURE_PATH));
		comboOnTexture			= new Texture(Gdx.files.internal(COMBO_ON_TEXTURE_PATH));
		comboOffTexture			= new Texture(Gdx.files.internal(COMBO_OFF_TEXTURE_PATH));
		
		comboColorsTexture	= new Texture(Gdx.files.internal(COMBO_COLORS_TEXTURE_PATH));
		
		int elementBoxSize 	= 100;
		int comboIconOffSet = 2;
		int comboIconSize 	= 32;
		int FRAME_COLS = 2;
		int FRAME_ROWS = 2;
		
		PauseButtonRectangle = new Rectangle(0, Val.SCREEN_HEIGHT_TARGET - elementBoxSize, elementBoxSize, elementBoxSize);
		MuteButtonRectangle  = new Rectangle(0, Val.SCREEN_HEIGHT_TARGET - ( 2 * elementBoxSize ), elementBoxSize, elementBoxSize);
		ScoreRectangle  	 = new Rectangle(Val.SCREEN_WIDTH_TARGET - elementBoxSize, Val.SCREEN_HEIGHT_TARGET - elementBoxSize, elementBoxSize, elementBoxSize);
		ComboRectangle  	 = new Rectangle(Val.SCREEN_WIDTH_TARGET - elementBoxSize, Val.SCREEN_HEIGHT_TARGET - ( 2 * elementBoxSize ), elementBoxSize, elementBoxSize);
		ComboIconRectangle	 = new Rectangle(Val.SCREEN_WIDTH_TARGET - elementBoxSize + comboIconOffSet, Val.SCREEN_HEIGHT_TARGET - ( 2 * elementBoxSize ) + comboIconSize, comboIconSize, comboIconSize);
		ComboAchivementRectangle = new Rectangle(Val.SCREEN_WIDTH_TARGET / 2 - elementBoxSize, Val.SCREEN_HEIGHT_TARGET / 2 - elementBoxSize / 2, 200, elementBoxSize);
		
		TextureRegion[][] tmp = TextureRegion.split(comboColorsTexture, comboColorsTexture.getWidth() / FRAME_ROWS, comboColorsTexture.getHeight() / FRAME_COLS);
		comboAnimationTextureRegion = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		
		for(int counter = 0; counter < (FRAME_COLS * FRAME_ROWS); counter ++)
			comboAnimationTextureRegion[counter] = tmp[counter / FRAME_ROWS][counter % FRAME_ROWS];
		
		comboAnimation = new Animation(0.025f, comboAnimationTextureRegion);
		comboAnimation.setPlayMode(Animation.LOOP);
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
		// update camera
	    camera.update();
	    camera.apply(Gdx.gl10);

	    // set viewport
	    Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
	                      (int) viewport.width, (int) viewport.height);
		
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
		gsc.getBatch().draw(BackgroundImage, 0, 0, Val.SCREEN_WIDTH_TARGET, Val.SCREEN_HEIGHT_TARGET);
		gsc.getBatch().draw(FieldBackgroundImage, FieldVol2.FIELD_WIDTH_OFFSET, 0, FieldVol2.FIELD_WIDTH, FieldVol2.FIELD_HEIGHT);
		
		//draws field
		for(int counter = 0; counter < FieldVol2.NUMBER_OF_CELLS; counter++)
		{
			for(int inner_counter = 0; inner_counter < FieldVol2.NUMBER_OF_ROWS; inner_counter++)
			{
				Box tempBox = field.getBoxAtPosition(counter, inner_counter);
				if(tempBox != null)
					gsc.getBatch().draw(DShapeTexture, tempBox.x + FieldVol2.FIELD_WIDTH_OFFSET, tempBox.y, Val.BOX_DIMENSION, Val.BOX_DIMENSION);
			}
		}
		
		//draws curently used shape
		for(Box box: currentlyUsedShape.getArrayOfBoxes())
			gsc.getBatch().draw(getTextureByID(currentlyUsedShape.SHAPE_TEXTURE_ID), box.x + FieldVol2.FIELD_WIDTH_OFFSET, box.y, Val.BOX_DIMENSION, Val.BOX_DIMENSION);
        
        // draws text & HUD panel
        gsc.getBatch().draw(NextItemTextImage, PauseButtonRectangle.width, Val.SCREEN_HEIGHT_TARGET - NextItemTextImage.getHeight());
        gsc.getBatch().draw(PanelBackground, PauseButtonRectangle.width, Val.SCREEN_HEIGHT_TARGET - PanelBackground.getHeight() - NextItemTextImage.getHeight());
        gsc.getBatch().draw(scoreTexture, Val.SCREEN_WIDTH_TARGET - ScoreRectangle.width, Val.SCREEN_HEIGHT_TARGET - ScoreRectangle.height);
        gsc.getBatch().draw(comboTexture, Val.SCREEN_WIDTH_TARGET - ComboRectangle.width, Val.SCREEN_HEIGHT_TARGET - (2*ComboRectangle.height));
        
        if(isGamePaused) 	gsc.getBatch().draw(gamePausedTexture, PauseButtonRectangle.x, PauseButtonRectangle.y);
        else 				gsc.getBatch().draw(gameActiveTexture, PauseButtonRectangle.x, PauseButtonRectangle.y);
        
        if(backgoundMusic.getVolume() == 0) gsc.getBatch().draw(soundOffTexture, MuteButtonRectangle.x, MuteButtonRectangle.y);
        else 								gsc.getBatch().draw(soundOnTexture, MuteButtonRectangle.x, MuteButtonRectangle.y);
        
        int numberOfCombos = score.lastComboApperanceNumber;
        for(int counter = 0; counter < 3; counter++)
        {
        	// ako je number of combos == 3 ... animacija i sound možda COMBO !!! 
        	// postaviti bool na true i animaciju onda pokrenuti ... dakle da za u sekndi promjeni 4 slièice il tak nekaj...
        	// kod u animaciji resetira number of Combos dakle... scores.numb... = 0... !ali tek nakon kaj animacija završi da se vide promjene
        	// možda i nekak bodove extra naglasiti :/ ???
        	if(numberOfCombos == 3)
        	{
        		if(isComboAchieved == false)
        		{
        			isComboAchieved = true;
        			coinSound.play();
        			comboAnimationTimer = 0;
        		}
        	}

        	if(numberOfCombos > 0) gsc.getBatch().draw(comboOnTexture, ComboIconRectangle.x + (counter * ComboIconRectangle.width), ComboIconRectangle.y);
        	else				   gsc.getBatch().draw(comboOffTexture, ComboIconRectangle.x + (counter * ComboIconRectangle.width), ComboIconRectangle.y);

        	numberOfCombos--;
        }

        if(isComboAchieved)
        {
        	comboAnimationTimer += delta;
        	currentAnimationTextureRegion = comboAnimation.getKeyFrame(comboAnimationTimer);
        	gsc.getBatch().draw(currentAnimationTextureRegion, ComboAchivementRectangle.x, ComboAchivementRectangle.y);
        	
        	if(comboAnimationTimer > 2)
        	{
        		isComboAchieved = false;
        		score.lastComboApperanceNumber = 0;
        	}
        }
        
        //draw next shape, rework later !,,,,, so it doesnt loads all this stuff all the time
        if(shapes.isEmpty())
			setUpShapes();
        for(Box box: shapes.peek().getArrayOfBoxes())
        	gsc.getBatch().draw(getTextureByID(currentlyUsedShape.SHAPE_TEXTURE_ID), box.x + FieldVol2.FIELD_WIDTH_OFFSET, box.y, Val.BOX_DIMENSION, Val.BOX_DIMENSION);
        
        // shows fps
		gsc.getFontPrisma().draw(gsc.getBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Val.SCREEN_HEIGHT_TARGET - 10);
		
		// shows score for now
		gsc.getFontPrisma().draw(gsc.getBatch(), "SCORE: " + score.getPoints(), 200, Val.SCREEN_HEIGHT_TARGET - 10);
		
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
		 //--Aspect Ratio Maintenance--
	    // calculate new viewport
	    float aspectRatio = (float)width / (float) height;
	    float scale = 1f;
	    Vector2 crop = new Vector2(0f, 0f);

	    if(aspectRatio > Val.ASPECT_RATIO_TARGET)
	    {
	        scale = (float)height / (float) Val.SCREEN_HEIGHT_TARGET;
	        crop.x = (width - Val.SCREEN_WIDTH_TARGET * scale)/2f;
	    }
	    else if(aspectRatio < Val.ASPECT_RATIO_TARGET)
	    {
	        scale = (float)width / (float) Val.SCREEN_WIDTH_TARGET;
	        crop.y = (height - Val.SCREEN_HEIGHT_TARGET * scale)/2f;
	    }
	    else
	    {
	        scale = (float)width / (float) Val.SCREEN_WIDTH_TARGET;
	    }

	    float w = (float)Val.SCREEN_WIDTH_TARGET*scale;
	    float h = (float)Val.SCREEN_HEIGHT_TARGET*scale;
	    viewport = new Rectangle(crop.x, crop.y, w, h);
	//Maintenance ends here--
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(Val.SCREEN_WIDTH_TARGET, Val.SCREEN_HEIGHT_TARGET); //Aspect Ratio Maintenance

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
		coinSound.dispose();
		
		IShapeTexture.dispose();
		JShapeTexture.dispose();
		LShapeTexture.dispose();
		OShapeTexture.dispose();
		SShapeTexture.dispose();
		TShapeTexture.dispose();
		ZShapeTexture.dispose();
		
		field = null;
		
		BackgroundImage.dispose();
		FieldBackgroundImage.dispose();
		NextItemTextImage.dispose();
		PanelBackground.dispose();
		soundOnTexture.dispose();
		soundOffTexture.dispose();
		gamePausedTexture.dispose();
		gameActiveTexture.dispose();
		scoreTexture.dispose();
		comboTexture.dispose();
		comboOnTexture.dispose();
		comboOffTexture.dispose();
		comboColorsTexture.dispose();
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

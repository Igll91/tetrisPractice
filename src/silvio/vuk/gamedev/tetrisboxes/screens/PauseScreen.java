package silvio.vuk.gamedev.tetrisboxes.screens;

import java.util.Iterator;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.entitys.PauseMenuItems;
import silvio.vuk.gamedev.tetrisboxes.inputProcessors.PauseScreenInputProcessor;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PauseScreen implements Screen{

	final GameScreenController gsc;
	final GameScreen		   gameScreen;
	OrthographicCamera 		   camera;
	PauseScreenInputProcessor  pauseScreenInputProcessor;
	
	private final String selectedOption = "*";
	private PauseMenuItems pauseMenuItems;
	
	public PauseScreen(final GameScreenController gsc, final GameScreen gameScreen) 
	{
		this.gsc = gsc;
		pauseMenuItems = new PauseMenuItems();
		this.pauseScreenInputProcessor = new PauseScreenInputProcessor(pauseMenuItems.getMapSize()); 
		Gdx.input.setInputProcessor(pauseScreenInputProcessor);
		this.gameScreen = gameScreen;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Val.SCREEN_WIDTH, Val.SCREEN_HEIGHT);
		
		gameScreen.PauseBackgroundMusic();
	}
	
	@Override
	public void render(float delta) {
		
		gameScreen.render(delta);
		
//		Gdx.gl.glClearColor(0, 0, 0, 0.51f);
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		gsc.getBatch().setProjectionMatrix(camera.combined);
		
		int optionSelected = pauseScreenInputProcessor.getOptionSelected();
		
		if(pauseScreenInputProcessor.isEnterPressed())
		{	
			switch(optionSelected)
			{
				case PauseMenuItems.BACK_TO_GAME_ID: 
					this.dispose();
					gsc.setScreen(gameScreen);
					break;
				case PauseMenuItems.QUIT_TO_TITLE_ID:
					this.dispose();
					gameScreen.dispose();
					gsc.setScreen(new MainMenuScreen(gsc));
					break;
			}
		}
		
		gsc.getBatch().begin();
		int startYPosition = Val.SCREEN_HEIGHT - 40;
		
		Iterator<Integer> iteratorOfMenuItems = pauseMenuItems.getKeyValuesIterator();
		
		while(iteratorOfMenuItems.hasNext())
		{
			Integer id = iteratorOfMenuItems.next();
			String text = pauseMenuItems.getMapValue(id);
			
			float textSize = gsc.getFontPrisma().getBounds(text).width;
			
			if(optionSelected == id)
			{
				gsc.getFontPrisma().setColor(180, 10, 10, 0.85f);
				gsc.getFontBlockStepped().draw(gsc.getBatch(), selectedOption, Val.SCREEN_WIDTH / 2 - textSize / 2 - 20, startYPosition);
				gsc.getFontBlockStepped().draw(gsc.getBatch(), selectedOption, Val.SCREEN_WIDTH / 2 + textSize / 2 + 20, startYPosition);
			}
			else
				gsc.getFontPrisma().setColor(Color.WHITE);
			
			gsc.getFontPrisma().draw(gsc.getBatch(), text, Val.SCREEN_WIDTH / 2 - textSize / 2, startYPosition);
			startYPosition -= 100;
		}
		
		gsc.getBatch().end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
		pauseScreenInputProcessor.getMenuSwitchSound().dispose();
		gameScreen.StartBackgroundMusic();
	}

}

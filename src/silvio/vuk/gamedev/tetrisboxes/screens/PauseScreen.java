package silvio.vuk.gamedev.tetrisboxes.screens;

import java.util.Iterator;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.entitys.PauseMenuItems;
import silvio.vuk.gamedev.tetrisboxes.inputProcessors.PauseScreenInputProcessor;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

public class PauseScreen implements Screen{

	final GameScreenController gsc;
	final GameScreen		   gameScreen;
	PauseScreenInputProcessor  pauseScreenInputProcessor;
	
	private final String selectedOption = "*";
	private PauseMenuItems pauseMenuItems;
	
	public PauseScreen(final GameScreenController gsc, final GameScreen gameScreen) 
	{
		this.gsc = gsc;
		pauseMenuItems = new PauseMenuItems();
		this.pauseScreenInputProcessor = new PauseScreenInputProcessor(pauseMenuItems.getMapSize()); 
		this.gameScreen = gameScreen;
	}
	
	@Override
	public void render(float delta) {
		gameScreen.render(delta);
		
		int optionSelected = pauseScreenInputProcessor.getOptionSelected();
		
		if(pauseScreenInputProcessor.isEnterPressed())
		{	
			switch(optionSelected)
			{
				case PauseMenuItems.BACK_TO_GAME_ID:
					gsc.setScreen(gameScreen);
					break;
				case PauseMenuItems.QUIT_TO_TITLE_ID:
					gameScreen.resetGame();
					gsc.setScreen(gsc.getMainMenuScreen());
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
		pauseScreenInputProcessor.resetStates();
		Gdx.input.setInputProcessor(pauseScreenInputProcessor);
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
	}

}

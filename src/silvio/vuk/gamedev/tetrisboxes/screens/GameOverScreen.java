package silvio.vuk.gamedev.tetrisboxes.screens;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class GameOverScreen implements Screen {

	private final GameScreenController gsc;
	private final GameScreen		   gameScreen;
	private final String 			   GAME_OVER_TEXT = "GAME OVER";
	private final String 	 		   EXIT_MESSAGE = "PRESS ENTER TO CONTINUE";
	private final Sound 			   GAME_OVER_SOUND = Gdx.audio.newSound(Gdx.files.internal("res/gameOver.wav"));; 					   
	
	public GameOverScreen(final GameScreenController gsc, final GameScreen gameScreen) 
	{
		this.gsc = gsc;
		this.gameScreen = gameScreen;
	}
	
	@Override
	public void render(float delta) {
		gameScreen.render(delta);

		gsc.getBatch().begin();
		
		gsc.getFontPrisma().setColor(180, 10, 10, 0.85f);
		TextBounds textSize = gsc.getFontPrisma().getBounds(GAME_OVER_TEXT);
		gsc.getFontBlockStepped().draw(gsc.getBatch(), GAME_OVER_TEXT, Val.SCREEN_WIDTH / 2 - textSize.width / 2, Val.SCREEN_HEIGHT / 2 - textSize.height / 2 );

		textSize = gsc.getFontPrisma().getBounds(EXIT_MESSAGE);
		gsc.getFontBlockStepped().draw(gsc.getBatch(), EXIT_MESSAGE, Val.SCREEN_WIDTH / 2 - textSize.width / 2, Val.SCREEN_HEIGHT / 2 - textSize.height / 2 - 30);

		gsc.getBatch().end();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			gameScreen.resetGame();
			gsc.setScreen(gsc.getMainMenuScreen());
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		GAME_OVER_SOUND.play();
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
		GAME_OVER_SOUND.dispose();
	}

}

package silvio.vuk.gamedev.tetrisboxes.screens;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.inputProcessors.MainMenuInputProcessor;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

	final GameScreenController gsc;
	Texture                    backgroundImage;
	
	private final String entryMessage				  = "Welcome to the game!";
	private final String MAIN_SCREEN_BACKGROUND_IMAGE = "res/mainMenuBackground.png";
	
	private MainMenuInputProcessor mainMenuInputProcessor;
	
	public MainMenuScreen(GameScreenController gsc)
	{
		this.gsc = gsc;
		backgroundImage = new Texture(Gdx.files.internal(MAIN_SCREEN_BACKGROUND_IMAGE));
		mainMenuInputProcessor = new MainMenuInputProcessor(gsc);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		float middleOfScreenFontPrisma = Val.SCREEN_WIDTH_TARGET / 2f  - gsc.getFontPrisma().getBounds(entryMessage).width / 2f;
		
		gsc.getBatch().begin();
		gsc.getBatch().draw(backgroundImage, 0, 0, Val.SCREEN_WIDTH_TARGET, Val.SCREEN_HEIGHT_TARGET);
		gsc.getFontPrisma().draw(gsc.getBatch(), entryMessage, middleOfScreenFontPrisma, 200);
		gsc.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(mainMenuInputProcessor);
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
		backgroundImage.dispose();
	}

}

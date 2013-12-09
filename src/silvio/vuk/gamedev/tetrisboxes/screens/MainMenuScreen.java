package silvio.vuk.gamedev.tetrisboxes.screens;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

	final GameScreenController gsc;
	OrthographicCamera 		   camera;
	Texture                    backgroundImage;
	
	private boolean keyIsPressed = false;
	private String entryMessage = "Welcome to the game!";
	
	private final String MAIN_SCREEN_BACKGROUND_IMAGE = "res/mainMenuBackground.png";
	
	public MainMenuScreen(GameScreenController gsc)
	{
		this.gsc = gsc;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Val.SCREEN_WIDTH, Val.SCREEN_HEIGHT);
		Texture.setEnforcePotImages(false);
		backgroundImage = new Texture(Gdx.files.internal(MAIN_SCREEN_BACKGROUND_IMAGE));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		float middleOfScreenFontPrisma = Val.SCREEN_WIDTH / 2f  - gsc.getFontPrisma().getBounds(entryMessage).width / 2f;
		
		gsc.getBatch().begin();
		gsc.getBatch().draw(backgroundImage, 0, 0, Val.SCREEN_WIDTH, Val.SCREEN_HEIGHT);
		gsc.getFontPrisma().draw(gsc.getBatch(), entryMessage, middleOfScreenFontPrisma, 200);
		gsc.getBatch().end();
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			if(keyIsPressed)
				Gdx.app.exit();
		}
		
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			if(keyIsPressed)
				gsc.setScreen(new GameScreen(gsc));
		}
		else
			keyIsPressed = true;
		
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
		backgroundImage.dispose();
	}

}

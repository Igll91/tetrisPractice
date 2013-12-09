package silvio.vuk.gamedev.tetrisboxes.screens;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {

	final GameScreenController gsc;
	OrthographicCamera 		   camera;
	Music					   backgoundMusic;
	
	private String testMessage = "This is test message!";
	private final String BACKGROUND_MUSIC_FILE_PATH = "res/backgroundSound.mp3";
	
	public GameScreen(final GameScreenController gsc)
	{
		this.gsc = gsc;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Val.SCREEN_WIDTH, Val.SCREEN_HEIGHT);
		backgoundMusic = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_MUSIC_FILE_PATH));
		backgoundMusic.setLooping(true);
		backgoundMusic.play();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		gsc.getBatch().setProjectionMatrix(camera.combined);
		
		float middleOfScreenFontPrisma = Val.SCREEN_WIDTH / 2f  - gsc.getFontBlockStepped().getBounds(testMessage).width / 2f;
		
		gsc.getBatch().begin();
		gsc.getFontBlockStepped().draw(gsc.getBatch(), testMessage, middleOfScreenFontPrisma, 200);
		gsc.getBatch().end();
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			// pause game !
			gsc.setScreen(new PauseScreen(gsc, this));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		System.out.println("shown");
	}

	@Override
	public void hide() {
		System.out.println("hidden");
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
	}

	public void PauseBackgroundMusic()
	{
		backgoundMusic.pause();
	}
	
	public void StartBackgroundMusic()
	{
		backgoundMusic.play();
	}
}

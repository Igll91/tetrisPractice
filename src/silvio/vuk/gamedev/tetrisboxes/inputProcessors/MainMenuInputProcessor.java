package silvio.vuk.gamedev.tetrisboxes.inputProcessors;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class MainMenuInputProcessor implements InputProcessor {

	private GameScreenController gsc;
	private boolean isKeyPressed = true;
	
	public MainMenuInputProcessor(GameScreenController gsc)
	{
		this.gsc = gsc;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(isKeyPressed)
		{
			if(keycode == Keys.ENTER)
				gsc.setScreen(gsc.getGameScreen());
			if(keycode == Keys.ESCAPE)
				Gdx.app.exit();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		isKeyPressed = true;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

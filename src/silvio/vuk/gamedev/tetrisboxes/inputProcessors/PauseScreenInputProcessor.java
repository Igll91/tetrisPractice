package silvio.vuk.gamedev.tetrisboxes.inputProcessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;

public class PauseScreenInputProcessor implements InputProcessor {

	int lastKey;
	int optionSelected;
	int numberOfOptions;
	boolean isEnterPressed;
	
	private final String menuSwitchSoundLocation = "res/menuSwapItem.wav";
	private Sound menuSwitchSound;
	
	public PauseScreenInputProcessor(int numberOfOptions)
	{
		resetStates();
		this.numberOfOptions = numberOfOptions;
		menuSwitchSound = Gdx.audio.newSound(Gdx.files.internal(menuSwitchSoundLocation));
	}
	
	public void resetStates()
	{
		lastKey = -1;
		optionSelected = 0;
		isEnterPressed = false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.UP && lastKey == -1)
		{
			lastKey = keycode;
			optionSelected -= 1;
			if(optionSelected < 0) optionSelected = numberOfOptions -1;
			menuSwitchSound.play();
		}
		else if(keycode == Keys.DOWN && lastKey == -1)
		{
			lastKey = keycode;
			optionSelected += 1;
			if(optionSelected >= numberOfOptions) optionSelected = 0;
			menuSwitchSound.play();
		}
		else if(keycode == Keys.ENTER && lastKey == -1)
		{
			lastKey = keycode;
			isEnterPressed = true;
		}
		else
			lastKey = -1;
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		lastKey = -1;
		isEnterPressed = false;
//		System.out.println("Key up: lastKey = " +lastKey);
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

	public int getOptionSelected() {
		return optionSelected;
	}

	public Sound getMenuSwitchSound() {
		return menuSwitchSound;
	}

	public boolean isEnterPressed() {
		return isEnterPressed;
	}

}

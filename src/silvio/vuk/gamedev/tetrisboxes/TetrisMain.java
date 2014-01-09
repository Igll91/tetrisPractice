package silvio.vuk.gamedev.tetrisboxes;

import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class TetrisMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LwjglApplication(new GameScreenController(), Val.GAME_TITLE, Val.SCREEN_WIDTH_TARGET, Val.SCREEN_HEIGHT_TARGET, false);
	}

}

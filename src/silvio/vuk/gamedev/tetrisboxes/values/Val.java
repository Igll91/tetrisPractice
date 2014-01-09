package silvio.vuk.gamedev.tetrisboxes.values;


/**
 * Class that holds static variables for game.
 * 
 * @author Silvio Vuk
 *
 */
public final class Val {

	private Val(){}
	
	// ovo bude trebalo dinamièki sreðivat da bude uvijek odreðen broje redova i stupaca...
	// resizanje ne valja... kasnije to srediti 
	
	public static String GAME_TITLE = "Tetris boxes";
	public static int  SCREEN_WIDTH_TARGET 	= 400;
	public static int SCREEN_HEIGHT_TARGET	= 840;
	public static float ASPECT_RATIO_TARGET = (float)SCREEN_WIDTH_TARGET / (float)SCREEN_HEIGHT_TARGET;
	public static final int BOX_DIMENSION 	= 32;
}

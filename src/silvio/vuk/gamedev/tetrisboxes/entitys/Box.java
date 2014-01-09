package silvio.vuk.gamedev.tetrisboxes.entitys;

/**
 * Represents a box which are used to form a well known tetris shapes.
 *
 * Each box contains x and y coordinates. 
 * 
 * @author Silvio Vuk
 *
 */
public class Box {

	public int x;
	public int y;
	
	public Box()
	{
		x = 0;
		y = 0;
	}
	
	public Box(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

}

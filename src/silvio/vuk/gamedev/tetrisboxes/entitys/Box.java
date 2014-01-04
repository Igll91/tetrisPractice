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

	private int x;
	private int y;
	
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}

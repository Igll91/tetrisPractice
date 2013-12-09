package silvio.vuk.gamedev.tetrisboxes.entitys;

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
	
}

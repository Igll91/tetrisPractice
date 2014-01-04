package silvio.vuk.gamedev.tetrisboxes.values;

public enum ScorePoints {
	SINGLE(100), DOUBLE(250), TRIPLE(400), QUADRUPLE(600), PENTUPLE(800), HEXTUPLE(1000);
	
	private int value;
	
	private ScorePoints(int v)
	{
		this.value = v;
	}
	
	public int getValue()
	{
		return value;
	}
}

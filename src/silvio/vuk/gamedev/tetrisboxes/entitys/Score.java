package silvio.vuk.gamedev.tetrisboxes.entitys;

import silvio.vuk.gamedev.tetrisboxes.values.ScorePoints;

public class Score {

	private int points;
	private ScorePoints lastCombo;
	public int lastComboApperanceNumber = 0;
	
	public Score(){}
	
	public void updateScore(final int numberOfRows)
	{
		switch(numberOfRows)
		{
		case 1:
			calculateScore(ScorePoints.SINGLE);
			break;
		case 2:
			calculateScore(ScorePoints.DOUBLE);
			break;
		case 3:
			calculateScore(ScorePoints.TRIPLE);
			break;
		case 4:
			calculateScore(ScorePoints.QUADRUPLE);
			break;
		case 5:
			calculateScore(ScorePoints.PENTUPLE);
			break;
		case 6:
			calculateScore(ScorePoints.HEXTUPLE);
			break;
		default:
			calculateScore(ScorePoints.HEXTUPLE);	
		}
	}

	private void calculateScore(final ScorePoints combo)
	{
		points += combo.getValue();
		
		if(lastCombo == combo)
		{
			lastComboApperanceNumber++;
			if(lastComboApperanceNumber == 3)
			{
				points += (combo.getValue() * 2);
//				lastComboApperanceNumber = 0;
			}
		}
		else
		{
			lastCombo = combo;
			lastComboApperanceNumber = 1;
		}
	}
	
	public int getPoints() {
		return points;
	}
}

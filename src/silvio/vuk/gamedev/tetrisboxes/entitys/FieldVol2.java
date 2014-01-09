package silvio.vuk.gamedev.tetrisboxes.entitys;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.utils.Array;

/**
 * 
 * 
 * @author Silvio Vuk
 *
 */
public class FieldVol2 {

	public static final int NUMBER_OF_CELLS  = 10;
	public static final int NUMBER_OF_ROWS   = 20;
	public static final int FIELD_WIDTH  = NUMBER_OF_CELLS * Val.BOX_DIMENSION;
	public static final int FIELD_HEIGHT = NUMBER_OF_ROWS  * Val.BOX_DIMENSION;
	public static final int FIELD_WIDTH_OFFSET = (Val.SCREEN_WIDTH_TARGET - FIELD_WIDTH) / 2;
	
	private Box [][] boxesOnField;
	
	public FieldVol2()
	{
		boxesOnField = new Box[NUMBER_OF_CELLS][NUMBER_OF_ROWS];
	}
	
	/**
	 * Inserts box into array, that contains all boxes currently on field.
	 * 
	 * @param box the box that will be inserted into fields array {@link FieldVol2#boxesOnField}.
	 */
	public void insertBox(final Box box)
	{
		final int posX = box.x / Val.BOX_DIMENSION; 
		final int posY = box.y / Val.BOX_DIMENSION;
		
		boxesOnField[posX][posY] = box;
	}
	
	/**
	 * Removes box from the fields array {@link FieldVol2#boxesOnField}.
	 * 
	 * Receives position of the box that will be set to null.
	 * Used in the action to remove whole row as a result of tetris rule.
	 * 
	 * @param posX column of the removing box.
	 * @param posY row of the removing box. 
	 */
	public void destroyBox(final int posX, final int posY)
	{
		boxesOnField[posX][posY] = null;
	}
	
	/**
	 * Positions the box few rows bellow.
	 * 
	 * For box in position defined by first two parameters, decrease current position Y by number of rows that were destroyed.
	 * 
	 * @param posX moving box position X, or the number of column.
	 * @param posY moving box position Y, or the number of row.
	 * @param rows number of rows it will be droped down.
	 */
	public void dropBoxes(final int posX, final int posY, final int rows)
	{
		Box box = boxesOnField[posX][posY];
		
		if(box != null)
		{
			box.y -= (rows * Val.BOX_DIMENSION);
			boxesOnField[posX][posY - rows] = box;
			boxesOnField[posX][posY] = null;
		}
	}
	
	public Box [][] getField()
	{
		return boxesOnField;
	}
	
	public boolean checkCollision(final Array<Box> arrayOfBoxes) throws TetrisGameOverException
	{
		Iterator<Box> iterator = arrayOfBoxes.iterator();

		boolean collision = false;
		while(iterator.hasNext())
		{
			Box currentBox = iterator.next();
			int posX = currentBox.x / Val.BOX_DIMENSION;
			int posY = currentBox.y / Val.BOX_DIMENSION;

			Box currentBoxsCellNearestBox = getColumnsLastBox(posX, posY);
			
			if(currentBoxsCellNearestBox == null)
			{
				if(posY == 0)
				{
					collision = true;
					break;
				}
			}
			else
			{
				int nearestBoxPosy = currentBoxsCellNearestBox.y / Val.BOX_DIMENSION;
				
				if(posY == (nearestBoxPosy + 1))
				{
					collision = true;
					break;
				}
			}
		}

		if(collision == true)
		{
			if(isGameOver(arrayOfBoxes)) throw new TetrisGameOverException();
			for(Box box: arrayOfBoxes)
				insertBox(box);
		}
		return collision;
	}
	
	/**
	 * Get the Box {@link Box} that is closest to the box we are moving.
	 * 
	 * @param column the id of the column we are checking.
	 * @return the box nearest to the box we are moving.
	 */
	public Box getColumnsLastBox(final int column, int row)
	{
		Box topBox = null;
		if(row >= NUMBER_OF_ROWS) row = NUMBER_OF_ROWS -1;
		
		// current solutions runs all places, need to happen because of possible holes between boxes in field.
		for(int counter = 0; counter < row; counter++)
		{
			if(boxesOnField[column][counter] != null)
				topBox = boxesOnField[column][counter];
		}
		
		return topBox;
	}
	
	/**
	 * Returns box at this position.
	 * 
	 * @param column column number of the box.
	 * @param row row number of the box.
	 * @return box from field at this position.
	 */
	public Box getBoxAtPosition(final int column,final int row)
	{
		return boxesOnField[column][row];
	}
	
	/**
	 * Checks if the newly added box did fill all spaces in rows.
	 * 
	 * @param arrayOfBoxes boxes that represent last shape we used.
	 * @return set of columns that are filled with boxes.
	 */
	public Set<Integer> checkRow(final Array<Box> arrayOfBoxes)
	{
		Set<Integer> setOfIntegers = new TreeSet<>();
		
		for(Box box: arrayOfBoxes)
			setOfIntegers.add(box.y / Val.BOX_DIMENSION);
		
		Iterator<Integer> iterator = setOfIntegers.iterator();
		
		while(iterator.hasNext())
		{
			int currentChekingRow = iterator.next();
			for(int counter = 0; counter < NUMBER_OF_CELLS; counter++)
				if(boxesOnField[counter][currentChekingRow] == null)
				{
					iterator.remove();
					break;
				}
		}
		
		return setOfIntegers;
	}
	
	/**
	 * Checks if one of boxes in {@link #boxesOnField} did cross over maximum allowed Y position.
	 * 
	 * @param arrayOfBoxes array of last used shape boxes. 
	 * @return true if game over, else false.
	 */
	public boolean isGameOver(final Array<Box> arrayOfBoxes)
	{
		for(Box box: arrayOfBoxes)
			if(box.y / Val.BOX_DIMENSION >= NUMBER_OF_ROWS)
				return true;
		
		return false;
	}
}

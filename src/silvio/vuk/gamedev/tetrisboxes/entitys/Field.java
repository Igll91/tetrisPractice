package silvio.vuk.gamedev.tetrisboxes.entitys;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Non functional, FieldVol2 is the answer.
 * 
 *@deprecated 
 * @author Silvio
 *
 */
public class Field {

	private OrderedMap<Integer, Stack<Box>> mapsOfBoxes;
	public static final int NUMBER_OF_CELLS  = 10;
	public static final int NUMBER_OF_ROWS   = 20;
	public static final int FIELD_WIDTH  = NUMBER_OF_CELLS * Val.BOX_DIMENSION;
	public static final int FIELD_HEIGHT = NUMBER_OF_ROWS  * Val.BOX_DIMENSION;
	
	public Field()
	{
		mapsOfBoxes = new OrderedMap<>(NUMBER_OF_CELLS);
		for(int counter = 0; counter < NUMBER_OF_CELLS; counter++)
			mapsOfBoxes.put(counter, new Stack<Box>());
	}
	
	// totalno izmjenjati ... !
	public void checkRow(Array<Box> arrayOfBoxes)
	{
		Set<Integer> setOfIntegers = new TreeSet<>();
		
		for(Box box: arrayOfBoxes)
		{
			setOfIntegers.add(box.getY() / Val.BOX_DIMENSION);
		}
		
		int numberOfRowsToBeDestroyed = 0;
		boolean isCurrentRowFull = true;
		for(Integer currentChekingRow: setOfIntegers)
		{
			for(int counter = 0; counter <= NUMBER_OF_CELLS; counter++)
			{
				try
				{
					Box box = mapsOfBoxes.get(counter).get(currentChekingRow);
				}
				catch(IndexOutOfBoundsException ex)
				{
					isCurrentRowFull = false;
					break;
				}
			}
			
			if(isCurrentRowFull == false)
				break;
			
			numberOfRowsToBeDestroyed++;
		}
		
		while(numberOfRowsToBeDestroyed > 0)
		{
			for(int counter = 0; counter <= NUMBER_OF_CELLS; counter++)
			{
				mapsOfBoxes.get(counter).pop();
			}
			
			numberOfRowsToBeDestroyed--;
		}
	}
	
	public void insertBox(final Box box)
	{
		// id of the cell box is currently at
		final int posX = box.getX() / Val.BOX_DIMENSION; 
		
		mapsOfBoxes.get(posX).push(box);
		
	}
	
	public boolean checkCollision(final Array<Box> arrayOfBoxes)
	{
		Iterator<Box> iterator = arrayOfBoxes.iterator();

		boolean collision = false;

		while(iterator.hasNext())
		{
			Box currentBox = iterator.next();
			int posX = currentBox.getX() / Val.BOX_DIMENSION;
			int posY = currentBox.getY() / Val.BOX_DIMENSION;

			if(mapsOfBoxes.get(posX).empty())
			{
				if(posY == 0)
				{
					collision = true;
					break;
				}
			}
			else
			{
				Box currentBoxsCellNearestBox = mapsOfBoxes.get(posX).peek();
				int nearestBoxPosy = currentBoxsCellNearestBox.getY() / Val.BOX_DIMENSION;

				if(posY == (nearestBoxPosy + 1))
				{
					collision = true;
					break;
				}
			}
		}

		if(collision == true)
		{
			for(Box box: arrayOfBoxes)
			{
				insertBox(box);
			}
		}

		return collision;
	}

	public boolean checkIfGameOver()
	{
		for(int counter = 0; counter < NUMBER_OF_CELLS; counter++)
		{
			if(mapsOfBoxes.get(counter).empty())
				continue;
			else
			{
				Box box = mapsOfBoxes.get(counter).peek();
				int posY = box.getY() / Val.BOX_DIMENSION;
				if(posY >= NUMBER_OF_ROWS)
					return true;
			}
		}
		
		return false;
	}
	
	public Array<Box> getAllFieldBlocks()
	{
		Array<Box> allBoxes = new Array<Box>();
		
		for(int counter = 0; counter < mapsOfBoxes.size; counter++)
		{
			Iterator<Box> iterator = mapsOfBoxes.get(counter).iterator();
			
			while(iterator.hasNext())
				allBoxes.add(iterator.next());
		}
				
		return allBoxes;
	}
}

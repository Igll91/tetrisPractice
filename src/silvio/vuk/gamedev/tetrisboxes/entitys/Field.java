package silvio.vuk.gamedev.tetrisboxes.entitys;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public class Field {

	private OrderedMap<Integer, Array<Box>> mapsOfBoxes;
	private static final int NUMBER_OF_CELLS  = 10;
	private static final int NUMBER_OF_ROWS   = 22;
	
	public Field()
	{
		mapsOfBoxes = new OrderedMap<>(NUMBER_OF_CELLS);
	}
	
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
	
	public void insertBox(Box box)
	{
		// id of the cell box is currently at
		final int posX = box.getX() / Val.BOX_DIMENSION; 
		
		mapsOfBoxes.get(posX).add(box);
		
	}
	
	public boolean checkCollision(Array<Box> arrayOfBoxes)
	{
		Iterator<Box> iterator = arrayOfBoxes.iterator();
		
		boolean collision = false;
		
		while(iterator.hasNext())
		{
			Box currentBox = iterator.next();
			int posX = currentBox.getX() / Val.BOX_DIMENSION;
			int posY = currentBox.getY() / Val.BOX_DIMENSION;
			
			Box currentBoxsCellNearestBox = mapsOfBoxes.get(posX).peek();
			if(currentBoxsCellNearestBox != null)
			{
				int nearestBoxPosy = currentBoxsCellNearestBox.getY() / Val.BOX_DIMENSION;
				
				if(posY == (nearestBoxPosy + 1))
				{
					collision = true;
					break;
				}
			}
			else
			{
				if(posY == 0)
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
			Box box = mapsOfBoxes.get(counter).peek();
			
			if(box == null)
				continue;
			else
			{
				int posY = box.getY() / Val.BOX_DIMENSION;
				if(posY >= NUMBER_OF_ROWS)
					return true;
			}
		}
		
		return false;
	}
	
}

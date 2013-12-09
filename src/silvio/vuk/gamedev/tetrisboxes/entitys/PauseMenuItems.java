package silvio.vuk.gamedev.tetrisboxes.entitys;

import java.util.Iterator;

import com.badlogic.gdx.utils.OrderedMap;

public class PauseMenuItems {
 
	public static final int BACK_TO_GAME_ID = 0;
	public static final int QUIT_TO_TITLE_ID = 1;
	
	OrderedMap<Integer, String> orderedMapOfOptions;
	
	public PauseMenuItems()
	{
		// setup initial size for space
		setUpItems(2);
	}
	
	private void setUpItems(int size)
	{
		orderedMapOfOptions = new OrderedMap<>(size);
		
		orderedMapOfOptions.put(BACK_TO_GAME_ID, "BACK TO GAME");
		orderedMapOfOptions.put(QUIT_TO_TITLE_ID, "QUIT TO TITLE");
	}
	
	public int getMapSize()
	{
		return orderedMapOfOptions.size;
	}
	
	public String getMapValue(int key) throws IndexOutOfBoundsException
	{
		if(orderedMapOfOptions.containsKey(key))
		{
			return orderedMapOfOptions.get(key);
		}
		else
			throw new IndexOutOfBoundsException("OrderedMap does not contain key with value: " + key);
	}
	
	public Iterator<Integer> getKeyValuesIterator()
	{
		return orderedMapOfOptions.keys();
	}
}

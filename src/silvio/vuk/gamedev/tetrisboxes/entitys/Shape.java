package silvio.vuk.gamedev.tetrisboxes.entitys;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public abstract class Shape {

	protected Array<Box> arrayOfBoxes;
	protected int currentRotationState = 0;
	protected OrderedMap<Integer, String> mapOfRotationStates;
	protected Box centerBox;
	
	public void rotateLeft()
	{
		currentRotationState--;
		if(currentRotationState < 0)
			currentRotationState = mapOfRotationStates.size -1;
		
		updateBlocksAfterRotation();
	}
	
	public void rotateRight()
	{
		currentRotationState++;
		if(currentRotationState >= mapOfRotationStates.size)
			currentRotationState = 0;
		
		updateBlocksAfterRotation();
	}
	
	protected abstract void updateBlocksAfterRotation();
	
	public Array<Box> getArrayOfBoxes() {
		return arrayOfBoxes;
	}

	public String getRotationState(int key) throws IndexOutOfBoundsException
	{
		if(mapOfRotationStates.containsKey(key))
		{
			return mapOfRotationStates.get(key);
		}
		else
			throw new IndexOutOfBoundsException("No key with value: " + key + " in mapOfRoationStates.");
	}
}

package silvio.vuk.gamedev.tetrisboxes.entitys;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public abstract class Shape {
	
	protected Rectangle rotationRectangleChecker; // kako provjeriti sa Fieldom dali se preklapa ?... još razmislitit malo o tome...
	protected int NUMBER_OF_BOXES_IN_SHAPE;
	protected int NUMBER_OF_BOXES_IN_ROW;
	protected int NUMBER_OF_BOXES_IN_COLUMN;
	
	protected final boolean ROTATION_DIRECTION_RIGHT = true;
	protected final boolean ROTATION_DIRECTION_LEFT  = false;
	
	protected Array<Box> arrayOfBoxes;
	protected int currentRotationState = 0;
	protected OrderedMap<Integer, String> mapOfRotationStates;
	protected Box centerBox;
	
	public Shape(int numberOfBoxesInRow, int numberOfBoxesInColumn, int numberOfBoxesInShape)
	{
		NUMBER_OF_BOXES_IN_COLUMN 	= numberOfBoxesInColumn;
		NUMBER_OF_BOXES_IN_ROW 		= numberOfBoxesInRow;
		NUMBER_OF_BOXES_IN_SHAPE	= numberOfBoxesInShape;
	}
	
	public void rotateLeft() throws InvalidAttributeValueException
	{
		rotateObject(ROTATION_DIRECTION_LEFT);
	}
	
	public void rotateRight() throws InvalidAttributeValueException
	{
		rotateObject(ROTATION_DIRECTION_RIGHT);
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
	
	/**
	 * 
	 * @param rotateDirection {@link Shape#ROTATION_DIRECTION_LEFT} {@link Shape#ROTATION_DIRECTION_RIGHT}
	 * @throws InvalidAttributeValueException
	 */
	private void rotateObject(boolean rotateDirection) throws InvalidAttributeValueException
	{
		int centerBlockPos[] = getCurrentRotationStateCenterBlockPosition();

		// starting position row = 0, column = 0... all other all calculated on base of this
		int posZeroZero_X_value = centerBox.getX() - (Val.BOX_DIMENSION * centerBlockPos[0]);
		int posZeroZero_Y_value = centerBox.getY() - (Val.BOX_DIMENSION * centerBlockPos[1]);
		
		if(rotateDirection == ROTATION_DIRECTION_RIGHT)
			currentRotationState++;
		else
			currentRotationState--;
		
		if(currentRotationState < 0)
			currentRotationState = mapOfRotationStates.size -1;
		else if(currentRotationState >= mapOfRotationStates.size)
			currentRotationState = 0;
		
		// prema tim pozicijama sad kerirati blokove !
		
		
	}
	
	/**
	 * Returns row and column values of current rotation state center block. (in that order!)
	 * 
	 * return[2] :
	 *    value [0] represents row
	 * 	  value [1] represents column
	 * 
	 * @return centerBlockPosition values of center block row and column
	 * @throws InvalidAttributeValueException if no center block was found.
	 */
	private int[] getCurrentRotationStateCenterBlockPosition() throws InvalidAttributeValueException
	{
		int [] centerBlockPosition = new int[2];
		
		int pos = mapOfRotationStates.get(currentRotationState).indexOf("C");
		
		if(pos == -1)
			throw new InvalidAttributeValueException("No center notation found in rotation states! currentRotationState = " +currentRotationState);

		centerBlockPosition[0] = pos % NUMBER_OF_BOXES_IN_ROW;
		centerBlockPosition[0] = pos / NUMBER_OF_BOXES_IN_ROW;
		
		return centerBlockPosition;
	}
	
	protected void testMapOfRotationStates() throws InvalidAttributeValueException
	{
		for(int counter = 0; counter < NUMBER_OF_BOXES_IN_SHAPE; counter++ )
		{
			String currentRotationState = mapOfRotationStates.get(counter);
			int numberOfBlocks = 0;
			int numberOfCenterBlocks = 0;
			
			for(int counter_row = 0; counter_row < NUMBER_OF_BOXES_IN_ROW; counter_row++)
			{
				for(int counter_column = 0; counter_column < NUMBER_OF_BOXES_IN_COLUMN; counter_column++)
				{
					char testChar = currentRotationState.charAt(counter_row * NUMBER_OF_BOXES_IN_ROW + counter_column);
					
					switch(testChar)
					{
					case 'x':
						break;
					case 'B':
						numberOfBlocks++;
						break;
					case 'C':
						numberOfBlocks++;
						numberOfCenterBlocks++;
						break;
					default:
						throw new InvalidAttributeValueException("Invalid character in currentRotationState! " +
								"\ncurrentRotationState key = " + counter +
								"\ncharacter read: " +testChar);
					}
				}
				
				if(numberOfBlocks != NUMBER_OF_BOXES_IN_SHAPE)
					throw new InvalidAttributeValueException("Invalid number of boxes in currentRotationState! \n" +currentRotationState);
				if(numberOfCenterBlocks != 1)
					throw new InvalidAttributeValueException("There must be 1 center block in rotationState!\n number of center blocks found: " +numberOfCenterBlocks);
			}
		}
	}
	
	protected abstract void createRotationShapes();
}

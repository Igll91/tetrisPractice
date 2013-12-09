package silvio.vuk.gamedev.tetrisboxes.entitys;

import javax.management.InvalidAttributeValueException;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class IShape extends Shape {
	
	private Rectangle rotationRectangleChecker; // kako provjeriti sa Fieldom dali se preklapa ?... još razmislitit malo o tome...
	private final int NUMBER_OF_BOXES_IN_SHAPE  = 4;
	private final int NUMBER_OF_BOXES_IN_ROW 	= 4;
	private final int NUMBER_OF_BOXES_IN_COLUMN = 4;
	
	public IShape()
	{
		super.arrayOfBoxes = new Array<>(NUMBER_OF_BOXES_IN_SHAPE);
		createRotationShapes();
	}
	
	private void createRotationShapes()
	{
		String [] positions = new String[NUMBER_OF_BOXES_IN_SHAPE];
		
		positions[0] =  "xxxx" +
						"BCBB" +
						"xxxx" +
						"xxxx";
		
		positions[1] =  "xxBx" +
						"xxCx" +
						"xxBx" +
						"xxBx";
		
		positions[2] =  "xxxx" +
						"xxxx" +
						"BCBB" +
						"xxxx";
		
		positions[3] =  "xBxx" +
						"xCxx" +
						"xBxx" +
						"xBxx";
				
		for(int counter = 0; counter < NUMBER_OF_BOXES_IN_SHAPE; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}

	@Override
	protected void updateBlocksAfterRotation() {
		// TODO Auto-generated method stub
		
	}
	
	private void rotateObject(boolean rotateDirection)
	{
		// trenutni rotationShape
		int lastRotationShapeCenterBlockPositionX = super.centerBox.getX();
		int lastRotationShapeCenterBlockPositionY = super.centerBox.getX();
		
		
		
	}
	
	private int[] getCurrentRotationStateCenterBlockPosition() throws InvalidAttributeValueException
	{
		int [] centerBlockPosition = new int[2];
		
		int pos = super.mapOfRotationStates.get(currentRotationState).indexOf("C");
		
		if(pos == -1)
			throw new InvalidAttributeValueException("No center notation found in rotation states! currentRotationState = " +currentRotationState);
		
		
		
		return centerBlockPosition;
	}
	
	private void testMapOfRotationStates() throws InvalidAttributeValueException
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
						break;
					case 'C':
						break;
					default:
						throw new InvalidAttributeValueException("Invalid character in currentRotationState! " +
								"\ncurrentRotationState key = " + counter);
					}
				}
			}
		}
	}
}

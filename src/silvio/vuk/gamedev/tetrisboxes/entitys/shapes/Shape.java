package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.entitys.Box;
import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public abstract class Shape {
	
	protected Rectangle rotationRectangleChecker; // kako provjeriti sa Fieldom dali se preklapa ?... jo� razmislitit malo o tome...
	protected int NUMBER_OF_BLOCKS_IN_SHAPE;
	protected int NUMBER_OF_ROTATION_STEPS;
	protected int NUMBER_OF_BOXES_IN_ROW;
	protected int NUMBER_OF_BOXES_IN_COLUMN;
	public ShapeID SHAPE_TEXTURE_ID;
	
	protected final boolean ROTATION_DIRECTION_RIGHT = true;
	protected final boolean ROTATION_DIRECTION_LEFT  = false;
	
	protected Array<Box> arrayOfBoxes;
	protected int currentRotationState = 0;
	protected OrderedMap<Integer, String> mapOfRotationStates;
	protected Box centerBox;
	
	/**
	 * sets up parameters needed to work functionally, and calls @link {@link Shape#createRotationShapes()}
	 *  
	 * @param numberOfBoxesInRow number of boxes in one row 
	 * @param numberOfBoxesInColumn number of boxes in one row 
	 * @param numberOfBlocksInShape
	 */
	public Shape(int numberOfBoxesInRow, int numberOfBoxesInColumn, int numberOfBlocksInShape, int numberOfRotationSteps, ShapeID shapeTextureID)
	{
		NUMBER_OF_BOXES_IN_COLUMN 	= numberOfBoxesInColumn;
		NUMBER_OF_BOXES_IN_ROW 		= numberOfBoxesInRow;
		NUMBER_OF_BLOCKS_IN_SHAPE	= numberOfBlocksInShape;
		NUMBER_OF_ROTATION_STEPS	= numberOfRotationSteps;
		SHAPE_TEXTURE_ID 			= shapeTextureID;

		arrayOfBoxes = new Array<>(NUMBER_OF_BLOCKS_IN_SHAPE);
		for(int counter = 0; counter < NUMBER_OF_BLOCKS_IN_SHAPE; counter++)
			arrayOfBoxes.add(new Box());
			
		mapOfRotationStates = new OrderedMap<>(NUMBER_OF_ROTATION_STEPS);
		
		createRotationShapes();
		// kasnije staviti na polovicu game screena, a ne screena od cijelog appa
//		setupBlocks(Val.SCREEN_WIDTH/2 - Val.BOX_DIMENSION/2, Val.SCREEN_HEIGHT + Val.BOX_DIMENSION);
		setupBlocks(100, Val.SCREEN_HEIGHT + Val.BOX_DIMENSION);
//		setupBlocks(Val.SCREEN_WIDTH/2 - Val.BOX_DIMENSION/2, 400);
	}
	
	public void rotateLeft() throws InvalidAttributeValueException
	{
		rotateObject(ROTATION_DIRECTION_LEFT);
	}
	
	public void rotateRight() throws InvalidAttributeValueException
	{
		rotateObject(ROTATION_DIRECTION_RIGHT);
	}

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

		// starting position row = 0, column = 0... all others are calculated on base of this
		int posZeroZero_X_value = centerBox.getX() - (Val.BOX_DIMENSION * centerBlockPos[0]);
		int posZeroZero_Y_value = centerBox.getY() + (Val.BOX_DIMENSION * centerBlockPos[1]);
		
//		System.out.println("center block position: \nx: " +centerBox.getX() +", y: " +centerBox.getY());
//		System.out.println("center block row: " + centerBlockPos[0] + ", column: " +centerBlockPos[1]);
//		System.out.println("zero x: " +posZeroZero_X_value);
//		System.out.println("zero y: " +posZeroZero_Y_value);
		
		if(rotateDirection == ROTATION_DIRECTION_RIGHT)
			currentRotationState++;
		else
			currentRotationState--;
		
		if(currentRotationState < 0)
			currentRotationState = mapOfRotationStates.size -1;
		else if(currentRotationState >= mapOfRotationStates.size)
			currentRotationState = 0;
		
		setupBlocks(posZeroZero_X_value, posZeroZero_Y_value);
	}
	
	private void setupBlocks(int posZeroX, int posZeroY)
	{
		String rotationState = mapOfRotationStates.get(currentRotationState);
		
		int blockCounter = 0;
		
		for(int counter = 0; counter < rotationState.length(); counter++)
		{
			char currentChar = rotationState.charAt(counter);

			int currentBlockX = posZeroX + counter % NUMBER_OF_BOXES_IN_ROW * Val.BOX_DIMENSION;
			int currentBlockY = posZeroY - counter / NUMBER_OF_BOXES_IN_ROW * Val.BOX_DIMENSION;
			
			switch(currentChar)
			{
			case 'B':
				arrayOfBoxes.get(blockCounter).setXY(currentBlockX, currentBlockY);
				blockCounter++;
				break;
			case 'C':
				arrayOfBoxes.get(blockCounter).setXY(currentBlockX, currentBlockY);
				centerBox = arrayOfBoxes.get(blockCounter);
				blockCounter++;
				break;
			}
		}
		
		rotationRectangleChecker = new Rectangle(posZeroX, posZeroY - NUMBER_OF_BOXES_IN_COLUMN * Val.BOX_DIMENSION + Val.BOX_DIMENSION, NUMBER_OF_BOXES_IN_ROW * Val.BOX_DIMENSION, NUMBER_OF_BOXES_IN_COLUMN * Val.BOX_DIMENSION);
//		System.out.println("PosZeroX: " + posZeroX + ", Y: " +posZeroY);
//		System.out.println("Rectangle: " +rotationRectangleChecker.getX()
//				+ "\t" + +rotationRectangleChecker.getY()
//				+ "\t" + +rotationRectangleChecker.getWidth()
//				+ "\t" + +rotationRectangleChecker.getHeight());
		
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
		centerBlockPosition[1] = pos / NUMBER_OF_BOXES_IN_ROW;
		
//		System.out.println("position of C block: " +pos);
//		System.out.println("center block row   : " + centerBlockPosition[0]);
//		System.out.println("center block column: " + centerBlockPosition[1]);
		
		return centerBlockPosition;
	}
	
	protected void testMapOfRotationStates() throws InvalidAttributeValueException
	{
		for(int counter = 0; counter < NUMBER_OF_BLOCKS_IN_SHAPE; counter++ )
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
			}
			
			if(numberOfBlocks != NUMBER_OF_BLOCKS_IN_SHAPE)
				throw new InvalidAttributeValueException("Invalid number of boxes in currentRotationState! \n" +currentRotationState);
			if(numberOfCenterBlocks != 1)
				throw new InvalidAttributeValueException("There must be 1 center block in rotationState!\n number of center blocks found: " +numberOfCenterBlocks);
		}
	}
	
	/**
	 * Fill {@link Shape#mapOfRotationStates} with string that represent shapes position in certain rotation state
	 */
	protected abstract void createRotationShapes();
	
	public void testPrint()
	{
		System.out.println("Current Shape state:" + "\n" +
				"current rotation state " + currentRotationState +"\n" +
				"shape texture id: " + SHAPE_TEXTURE_ID.name() +"\n" +
				"number of blocks: " + NUMBER_OF_BLOCKS_IN_SHAPE +"\n" +
				"number of boxes in column: " + NUMBER_OF_BOXES_IN_COLUMN +"\n" +
				"number of boxes in row: " + NUMBER_OF_BOXES_IN_ROW +"\n" +
				"number of rotation steps: " + NUMBER_OF_ROTATION_STEPS);
	}

	public Rectangle getRotationRectangleChecker() {
		return rotationRectangleChecker;
	}

	public void setRotationRectangleChecker(Rectangle rotationRectangleChecker) {
		this.rotationRectangleChecker = rotationRectangleChecker;
	}
	
	public void moveShape(boolean toTheRight)
	{
		if(toTheRight)
		{
			for(Box box: arrayOfBoxes)
				box.setX(box.getX() + Val.BOX_DIMENSION);
			
			rotationRectangleChecker.setX(rotationRectangleChecker.getX() + Val.BOX_DIMENSION);
		}
		else
		{
			for(Box box: arrayOfBoxes)
				box.setX(box.getX() - Val.BOX_DIMENSION);
			
			rotationRectangleChecker.setX(rotationRectangleChecker.getX() - Val.BOX_DIMENSION);
		}
	}
	
	public void fallingShape()
	{
		for(Box box: arrayOfBoxes)
			box.setY(box.getY() - Val.BOX_DIMENSION);
		
		rotationRectangleChecker.setY(rotationRectangleChecker.getY() - Val.BOX_DIMENSION);
	}
}

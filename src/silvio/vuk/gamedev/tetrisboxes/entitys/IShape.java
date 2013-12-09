package silvio.vuk.gamedev.tetrisboxes.entitys;

import javax.management.InvalidAttributeValueException;

import com.badlogic.gdx.utils.Array;

public class IShape extends Shape {
	
	public IShape() throws InvalidAttributeValueException
	{
		super(4, 4, 4);
		
		super.arrayOfBoxes = new Array<>(NUMBER_OF_BOXES_IN_SHAPE);
		createRotationShapes();
		testMapOfRotationStates();
		
		// inicijalizacija prvih elemenata u objektu !
	}
	
	@Override
	protected void createRotationShapes()
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
		
}

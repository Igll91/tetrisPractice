package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class SShape extends Shape {

	public SShape() throws InvalidAttributeValueException
	{
		super(3, 3, 4, 4, ShapeID.S_SHAPE_TEXTURE_ID);
		testMapOfRotationStates();
	}
	
	@Override
	protected void createRotationShapes()
	{
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
		positions[0] =  "xBB" +
						"BCx" +
						"xxx" ;
		
		positions[1] =  "xBx" +
						"xCB" +
						"xxB";
		
		positions[2] =  "xxx" +
						"xCB" +
						"BBx";
		
		positions[3] =  "Bxx" +
						"BCx" +
						"xBx";
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}

}

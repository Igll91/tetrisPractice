package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class ZShape extends Shape {

	public ZShape() throws InvalidAttributeValueException
	{
		super(3, 3, 4, 4, ShapeID.Z_SHAPE_TEXTURE_ID);
		testMapOfRotationStates();
	}
	
	@Override
	protected void createRotationShapes()
	{
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
		positions[0] =  "BBx" +
						"xCB" +
						"xxx" ;
		
		positions[1] =  "xxB" +
						"xCB" +
						"xBx";
		
		positions[2] =  "xxx" +
						"BCx" +
						"xBB";
		
		positions[3] =  "xBx" +
						"BCx" +
						"Bxx";
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}
}

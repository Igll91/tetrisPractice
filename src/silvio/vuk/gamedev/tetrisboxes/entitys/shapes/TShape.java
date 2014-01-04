package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class TShape extends Shape {

	public TShape() throws InvalidAttributeValueException
	{
		super(3, 3, 4, 4, ShapeID.T_SHAPE_TEXTURE_ID);
		testMapOfRotationStates();
	}
	
	@Override
	protected void createRotationShapes()
	{
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
		positions[0] =  "xBx" +
						"BCB" +
						"xxx" ;
		
		positions[1] =  "xBx" +
						"xCB" +
						"xBx";
		
		positions[2] =  "xxx" +
						"BCB" +
						"xBx";
		
		positions[3] =  "xBx" +
						"BCx" +
						"xBx";
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}
}

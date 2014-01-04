package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class IShape extends Shape {
	
	public IShape() throws InvalidAttributeValueException
	{
		super(4, 4, 4, 4, ShapeID.I_SHAPE_TEXTURE_ID);
		testMapOfRotationStates();
	}
	
	@Override
	protected void createRotationShapes()
	{
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
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
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}

}

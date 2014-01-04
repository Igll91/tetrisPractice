package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class LShape extends Shape {

	public LShape()
	{
		super(3, 3, 4, 4, ShapeID.L_SHAPE_TEXTURE_ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createRotationShapes() {
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
		positions[0] =  "xxB" +
						"BCB" +
						"xxx";
		
		positions[1] =  "xBx" +
						"xCx" +
						"xBB";
		
		positions[2] =  "xxx" +
						"BCB" +
						"Bxx";
		
		positions[3] =  "BBx" +
						"xCx" +
						"xBx";
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}
}

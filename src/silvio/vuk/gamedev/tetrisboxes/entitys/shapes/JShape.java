package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class JShape extends Shape {

	public JShape()
	{
		super(3, 3, 4, 4, ShapeID.J_SHAPE_TEXTURE_ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createRotationShapes() {
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
		positions[0] =  "Bxx" +
						"BCB" +
						"xxx";
		
		positions[1] =  "xBB" +
						"xCx" +
						"xBx";
		
		positions[2] =  "xxx" +
						"BCB" +
						"xxB";
		
		positions[3] =  "xBx" +
						"xCx" +
						"BBx";
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}

}

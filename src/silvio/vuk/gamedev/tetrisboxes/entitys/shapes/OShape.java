package silvio.vuk.gamedev.tetrisboxes.entitys.shapes;

import silvio.vuk.gamedev.tetrisboxes.values.ShapeID;

public class OShape extends Shape {

	public OShape()
	{
		super(4, 3, 4, 1, ShapeID.O_SHAPE_TEXTURE_ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createRotationShapes() {
		String [] positions = new String[NUMBER_OF_ROTATION_STEPS];
		
		positions[0] =  "xBBx" +
						"xCBx" +
						"xxxx";
				
		for(int counter = 0; counter < NUMBER_OF_ROTATION_STEPS; counter++)
			super.mapOfRotationStates.put(counter, positions[counter]);
	}

}

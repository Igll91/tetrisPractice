package silvio.vuk.gamedev.tetrisboxes.values;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ShapeID {

	I_SHAPE_TEXTURE_ID, 
	J_SHAPE_TEXTURE_ID,
	L_SHAPE_TEXTURE_ID,
	O_SHAPE_TEXTURE_ID,
	S_SHAPE_TEXTURE_ID,
	T_SHAPE_TEXTURE_ID,
	Z_SHAPE_TEXTURE_ID;

	private static final List<ShapeID> VALUES = Collections.checkedList(Arrays.asList(values()), ShapeID.class);

	
	public static List<ShapeID> getShapesInRandomOrder()
	{
		long seed = System.nanoTime();
		Collections.shuffle(VALUES, new Random(seed));
		
		return VALUES;
	}
}

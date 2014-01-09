package silvio.vuk.gamedev.tetrisboxes.inputProcessors;

import javax.management.InvalidAttributeValueException;

import silvio.vuk.gamedev.tetrisboxes.GameScreenController;
import silvio.vuk.gamedev.tetrisboxes.entitys.Box;
import silvio.vuk.gamedev.tetrisboxes.entitys.FieldVol2;
import silvio.vuk.gamedev.tetrisboxes.entitys.shapes.Shape;
import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

public class GameScreenInputProcessor implements InputProcessor {

	private boolean isKeyUpPressed;
	private Shape currentlyUsedShape;
	private FieldVol2 field;
	private boolean deadDrop;
	private GameScreenController gsc;
	
	public GameScreenInputProcessor(GameScreenController gsc)
	{
		this.gsc = gsc;
		isKeyUpPressed = false;
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{

		if(keycode == Keys.VOLUME_UP)
			gsc.getGameScreen().volumeUp();
		
		if(keycode == Keys.VOLUME_DOWN)
			gsc.getGameScreen().volumeDown();
		
		if(isKeyUpPressed)
		{
			if(keycode == Keys.ESCAPE)
				gsc.setScreen(gsc.getPauseScreen());

			if(keycode == Keys.SHIFT_RIGHT)
			{
				gsc.getGameScreen().muteSound();
			}
			
			try 
			{
				if(keycode == Keys.UP)
				{
					if(deadDrop == true) deadDrop = false;
					else
						if(checkIfRotationAllowed())
							currentlyUsedShape.rotateRight();
				}
				if(keycode == Keys.DOWN)
				{
					if(deadDrop == true) deadDrop = false;
					else
						if(checkIfRotationAllowed())
							currentlyUsedShape.rotateLeft();
				}
				if(keycode == Keys.LEFT)
				{
					if(deadDrop == true) deadDrop = false;
					else
					{
						for(Box box: currentlyUsedShape.getArrayOfBoxes())
						{
							int currentBoxX = box.x / Val.BOX_DIMENSION;
							int currentBoxY = box.y / Val.BOX_DIMENSION;

							if(currentBoxX == 0 || currentBoxY  >= FieldVol2.NUMBER_OF_ROWS)
								return false;
							if(field.getBoxAtPosition(currentBoxX - 1, currentBoxY) != null)
								return false;
						}

						currentlyUsedShape.moveShape(false);
					}
				}
				if(keycode == Keys.RIGHT)
				{
					if(deadDrop == true) deadDrop = false;
					else
					{
						for(Box box: currentlyUsedShape.getArrayOfBoxes())
						{
							int currentBoxX = box.x / Val.BOX_DIMENSION;
							int currentBoxY = box.y / Val.BOX_DIMENSION;

							if(currentBoxX == FieldVol2.NUMBER_OF_CELLS - 1 || currentBoxY  >= FieldVol2.NUMBER_OF_ROWS)
								return false;
							if(field.getBoxAtPosition(currentBoxX + 1, currentBoxY) != null)
								return false;
						}

						currentlyUsedShape.moveShape(true);
					}
				}
				if(keycode == Keys.CONTROL_RIGHT)
				{
					if(deadDrop == true) deadDrop = false;
					else deadDrop = true;
				}
			}
			catch (InvalidAttributeValueException e) 
			{
				System.out.println(e.getMessage());
			}
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		isKeyUpPressed = true;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCurrentlyUsedShape(Shape currentlyUsedShape) {
		this.currentlyUsedShape = currentlyUsedShape;
	}

	public boolean isDeadDrop() {
		return deadDrop;
	}

	public void setDeadDrop(boolean deadDrop) {
		this.deadDrop = deadDrop;
	}

	public void setField(FieldVol2 field) {
		this.field = field;
	}
	
	private boolean checkIfRotationAllowed()
	{
		Rectangle rotationRec = currentlyUsedShape.getRotationRectangleChecker();
		
		// example ... -28 / 32 = -0.... turn to int = 0... 
		if(rotationRec.getX() < 0)
			return false;
		
		int posStartX = (int) rotationRec.getX() / Val.BOX_DIMENSION;
		int posStartY = (int) rotationRec.getY() / Val.BOX_DIMENSION;
		int posEndX   = posStartX + (int)rotationRec.getWidth() / Val.BOX_DIMENSION;
		int posEndY   = posStartY + (int) rotationRec.getHeight() / Val.BOX_DIMENSION;
		
		if(posEndX > FieldVol2.NUMBER_OF_CELLS)
			return false;
		if(posStartY < 0 || posEndY > FieldVol2.NUMBER_OF_ROWS)
			return false;
		
		for(int counter = posStartX; counter < posEndX; counter++)
			if(field.getBoxAtPosition(counter, posStartY) != null || field.getBoxAtPosition(counter, posEndY - 1) != null )
				return false;
		
		for(int counter = posStartY; counter < posEndY; counter++)
			if(field.getBoxAtPosition(posStartX, counter) != null || field.getBoxAtPosition(posEndX - 1, counter) != null )
				return false;
		
		return true;
	}
}

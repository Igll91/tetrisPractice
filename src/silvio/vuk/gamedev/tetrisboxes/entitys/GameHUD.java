package silvio.vuk.gamedev.tetrisboxes.entitys;

import silvio.vuk.gamedev.tetrisboxes.values.Val;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameHUD extends Stage {

	public GameHUD()
	{
		setViewport(Val.SCREEN_WIDTH_TARGET, Val.SCREEN_HEIGHT_TARGET - FieldVol2.FIELD_HEIGHT);
		setUpHUD();
	}
	
	private void setUpHUD()
	{
		Table mainTable = new Table();
		mainTable.setFillParent(true);
		addActor(mainTable);
	}
	
}

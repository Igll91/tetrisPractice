package silvio.vuk.gamedev.tetrisboxes;

import silvio.vuk.gamedev.tetrisboxes.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameScreenController extends Game {

	BitmapFont  fontPrisma;
	BitmapFont  fontBlockStepped;
	SpriteBatch batch;
	
	public static int FONT_SIZE = 24; // size in pixels
	
	@Override
	public void create() 
	{
		FileHandle fontFile = Gdx.files.internal("res/Prisma.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		
        // setup custom fonts 
        // http://www.1001freefonts.com/blockstepped.font
        // http://www.1001freefonts.com/prisma.font
		fontPrisma = generator.generateFont(FONT_SIZE);
		
		fontFile = Gdx.files.internal("res/Blockstepped.ttf");
		generator = new FreeTypeFontGenerator(fontFile);
		
		fontBlockStepped = generator.generateFont(FONT_SIZE);
		
		generator.dispose();
		
		fontPrisma.setColor(Color.WHITE);
		fontBlockStepped.setColor(Color.WHITE);
		
		batch = new SpriteBatch();
		
		// set the screen
		setScreen(new MainMenuScreen(this));
	}

	public void render() 
	{
		super.render(); //important!
	}
	
	public void dispose() 
	{
		batch.dispose();
	}

	public BitmapFont getFontPrisma() {
		return fontPrisma;
	}

	public void setFontPrisma(BitmapFont fontPrisma) {
		this.fontPrisma = fontPrisma;
	}

	public BitmapFont getFontBlockStepped() {
		return fontBlockStepped;
	}

	public void setFontBlockStepped(BitmapFont fontBlockStepped) {
		this.fontBlockStepped = fontBlockStepped;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
}

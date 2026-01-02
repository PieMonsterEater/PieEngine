package net.piescode.PieEngine.Visuals;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

public BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage ss) {
		this.spriteSheet = ss;
	}
	
	public static BufferedImage grabSprite(String spriteLocal, int x, int y, int width, int height) {
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = loader.loadImage(spriteLocal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return spriteSheet.getSubimage(x, y, width, height);
	}
	
}

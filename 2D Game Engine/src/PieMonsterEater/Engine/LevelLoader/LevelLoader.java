package PieMonsterEater.Engine.LevelLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import BuildingBlocks.Block;
import PieMonsterEater.Engine.Entities.Enemy;
import PieMonsterEater.Engine.Entities.Flag;
import PieMonsterEater.Engine.EntityCore.GameObject;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;
import PieMonsterEater.Engine.Visuals.BufferedImageLoader;

public class LevelLoader {
	
	Handler handler;
	BufferedImageLoader loader = new BufferedImageLoader();
	private int x = 0, y = 0, levelCounter = 0;
	
	public LevelLoader(Handler handler) {
		this.handler = handler;
	}
	
	public void loadLevel(String path) {
		BufferedImage level;
		try {
			level = loader.loadImage(path);
			int height = level.getHeight();
			int width = level.getWidth();
			
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					int pixel = level.getRGB(x, y);
					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = (pixel) & 0xff;
					
					if(red == 0 && green == 38 && blue == 255) {
						for(int i = 0; i < handler.object.size(); i++) {
							GameObject tempObject = handler.object.get(i);
							
							if(tempObject.getID() == ID.Player) {
								tempObject.setX(x*32);
								tempObject.setY(y*32);
							}
						}
					}
					if(red == 255 && green == 0 && blue == 0) handler.addObj(new Enemy(x*32, y*32, handler));
					if(red == 128 && green == 128 && blue == 128) handler.addObj(new Block(x*32, y*32, handler));
					if(red == 246 && green == 255 && blue == 0) handler.addObj(new Flag(x*32, y*32, handler));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Block) handler.removeObj(tempObject);
			if(tempObject.getID() == ID.Enemy) handler.removeObj(tempObject);
			if(tempObject.getID() == ID.Flag) handler.removeObj(tempObject);
		}
	}
	
	public void nextLevel() {
		levelCounter++;
		switch(levelCounter) {
		case 1: this.loadLevel("res/level.png");
				break;
		case 2: this.loadLevel("res/level2.png");
		break;
		}
	}
	
}

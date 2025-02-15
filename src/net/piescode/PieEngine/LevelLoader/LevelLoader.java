package net.piescode.PieEngine.LevelLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.piescode.PieEngine.BuildingBlocks.Block;
import net.piescode.PieEngine.BuildingBlocks.Ellipse;
import net.piescode.PieEngine.Entities.Enemy;
import net.piescode.PieEngine.Entities.Flag;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.Visuals.BufferedImageLoader;

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
					if(red == 156 && green == 128 && blue == 128) handler.addObj(new Block(x*32, y*32, 45, 25, 45, handler));
					if(red == 156 && green == 145 && blue == 128) {
						//handler.addObj(new Block(x*32, y*32, 32, 32, 0, handler));
						//handler.addObj(new Block(x*32, y*32, 32, 15, 0, handler));
						handler.addObj(new Block(x*32, y*32, 45, 25, 225, handler));
					}
					if(red == 156 && green == 157 && blue == 128) handler.addObj(new Block(x*32, y*32, 32, 32, 0, handler));
					if(red == 246 && green == 255 && blue == 0) handler.addObj(new Flag(x*32, y*32, handler));
					
					if(red == 156 && green == 128 && blue == 146) {
						Block b = new Block(x*32, y*32, 45, 15, 0, handler);
						b.setThetaChange(Math.toRadians(1));
						handler.addObj(b);
						}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		for(int i = handler.object.size() - 1; i >= 0; i--) {
			GameObject tempObject = handler.object.get(i);
			
			if(!tempObject.dontDestroyOnLoad) handler.removeObj(tempObject);
		}
	}
	
	public void nextLevel() {
		levelCounter++;
		levelCounter++;
		switch(levelCounter) {
		case 1: handler.addObj(new Block(0, 100, handler));
				handler.addObj(new Block(100, 0, 45, handler));
				handler.addObj(new Block(200, 100, 15, 45, 70, handler));
				handler.addObj(new Ellipse(100, 200, 150f, 45f, handler));
				for(int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					
					if(tempObject.getID() == ID.Player) {
						tempObject.setX(100);
						tempObject.setY(100);
					}
				}
				break;
		case 2: this.loadLevel("res/level.png");
				break;
		case 3: this.loadLevel("res/level2.png");
		break;
		}
	}
	
	public void reset() {
		clear();
		levelCounter = 0;
	}
	
}

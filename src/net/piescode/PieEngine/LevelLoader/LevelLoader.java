package net.piescode.PieEngine.LevelLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.piescode.PieEngine.BuildingBlocks.Block;
import net.piescode.PieEngine.BuildingBlocks.Ellipse;
import net.piescode.PieEngine.BuildingBlocks.Triangle;
import net.piescode.PieEngine.Entities.Enemy;
import net.piescode.PieEngine.Entities.Flag;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.Utils.Pair;
import net.piescode.PieEngine.Visuals.BufferedImageLoader;

public class LevelLoader {
	
	Handler handler;
	BufferedImageLoader loader = new BufferedImageLoader();
	private int x = 0, y = 0, levelCounter = 0;
	
	public static final int STANDARD_WIDTH = 32, STANDARD_HEIGHT = 32;
	public static final int STANDARD_OFFSETX = 32, STANDARD_OFFSETY = 32;
	
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
					if(red == 255 && green == 0 && blue == 0) handler.addObj(new Enemy(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, handler));
					if(red == 128 && green == 128 && blue == 128) handler.addObj(new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, null, handler));
					if(red == 128 && green == 128 && blue == 128) handler.addObj(new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, null, handler));
					if(red == 194 && green == 125 && blue == 0) {
						Block woodenPlank = new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, Block.WOODEN_PLANK, handler);
						woodenPlank.solid = false;
						
						handler.addObj(woodenPlank);
					}
					if(red == 128 && green == 132 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 0, STANDARD_OFFSETY, new Pair<Integer, Double>(32, -90d), new Pair<Integer, Double>(32, 0d), Block.WOODEN_PLANK, handler));
					if(red == 128 && green == 136 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, STANDARD_OFFSETX, STANDARD_OFFSETY, new Pair<Integer, Double>(32, -90d), new Pair<Integer, Double>(32, 180d), Block.WOODEN_PLANK, handler));
					if(red == 128 && green == 140 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, new Pair<Integer, Double>(32, 0d), new Pair<Integer, Double>(32, 90d), Block.WOODEN_PLANK, handler));
					if(red == 128 && green == 144 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, STANDARD_OFFSETX, 0, new Pair<Integer, Double>(32, 90d), new Pair<Integer, Double>(32, 180d), Block.WOODEN_PLANK, handler));
					if(red == 128 && green == 148 && blue == 128) {
						Triangle rotatingTriangle = new Triangle(x*x*STANDARD_WIDTH, y*STANDARD_HEIGHT, new Pair<Integer, Double>(32, -90d), new Pair<Integer, Double>(32, 0d), Block.WOODEN_PLANK, handler);
						rotatingTriangle.setThetaChange(1);
						handler.addObj(rotatingTriangle);
						}
					if(red == 128 && green == 152 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 0, STANDARD_OFFSETY, new Pair<Integer, Double>(40, 55d), new Pair<Integer, Double>(150, 330d), null, handler));
					if(red == 128 && green == 156 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 0, STANDARD_OFFSETY, new Pair<Integer, Double>(40, 145d), new Pair<Integer, Double>(150, 330d), null, handler));
					if(red == 128 && green == 160 && blue == 128) handler.addObj(new Triangle(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, new Pair<Integer, Double>(40, 235d), new Pair<Integer, Double>(150, 330d), null, handler));
					if(red == 156 && green == 128 && blue == 128) handler.addObj(new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 45, 25, 45, null, handler));
					if(red == 156 && green == 145 && blue == 128) {
						//handler.addObj(new Block(x*32, y*32, 32, 32, 0, handler));
						//handler.addObj(new Block(x*32, y*32, 32, 15, 0, handler));
						handler.addObj(new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 45, 25, 225, null, handler));
					}
					if(red == 156 && green == 157 && blue == 128) handler.addObj(new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, STANDARD_WIDTH, STANDARD_HEIGHT, 0, null, handler));
					if(red == 246 && green == 255 && blue == 0) handler.addObj(new Flag(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, handler));
					if(red == 50 && green == 50 && blue == 50) handler.addObj(new Ellipse(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 45f, 45f, handler));
					
					if(red == 156 && green == 128 && blue == 146) {
						Block b = new Block(x*STANDARD_WIDTH, y*STANDARD_HEIGHT, 90, 15, 0, null, handler);
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
		switch(levelCounter) {
		case 2: handler.addObj(new Block(0, 100, null, handler));
				handler.addObj(new Block(100, 0, 45, null, handler));
				handler.addObj(new Block(200, 100, 15, 45, 70, null, handler));
				handler.addObj(new Ellipse(100, 200, 45f, 45f, handler));
				handler.addObj(new Triangle(300, 100, new Pair<Integer, Double>(32, -90d), new Pair<Integer, Double>(32, 0d), null, handler));
				handler.addObj(new Flag(0, 400, handler));
				for(int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					
					if(tempObject.getID() == ID.Player) {
						tempObject.setX(100);
						tempObject.setY(100);
					}
				}
				break;
		case 1: this.loadLevel("res/levels/house.png");
				break;
		case 3: this.loadLevel("res/levels/level2.png");
				break;
		case 4: this.loadLevel("res/levels/level3.png");
				break;
		}
	}
	
	public void reset() {
		clear();
		levelCounter = 0;
	}
	
}

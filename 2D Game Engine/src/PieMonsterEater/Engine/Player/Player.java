package PieMonsterEater.Engine.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import BuildingBlocks.Block;
import PieMonsterEater.Engine.EntityCore.GameObject;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;

public class Player extends GameObject {
	
	private Handler handler;
	private BufferedImage[] images = new BufferedImage[2];
	
	public Player(int x, int y, Handler handler) {
		super(x, y, handler);
		this.handler = handler;
		this.setID(ID.Player);
		this.setSpriteSheet("res/SpriteSheet.png");
		
		images[0] = ss.grabSprite(0, 0, 21, 32);
		images[1] = ss.grabSprite(122, 0, 30, 29);
		
		this.setAnimation(images, 20);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		ani.tick();
		this.setSpr(ani.getImage());
		
		collision();
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g.drawRect(x, y, 32, 64);
		
		g.drawImage(sprite, x, y, 32, 64, null);
	}
	
	public void collision() {
	for(int i = 0; i < handler.object.size(); i++) {
		GameObject tempObject = handler.object.get(i);
		
		if(tempObject.getID() == ID.Block) {
			Block b = (Block) tempObject;
			if(getBounds().intersects(b.getBoundsTop())) y = b.getY() - 65;
			if(getBounds().intersects(b.getBoundsBottom())) y = b.getY() + 30;
			if(getBounds().intersects(b.getBoundsRight())) x = b.getX() + 32;
			if(getBounds().intersects(b.getBoundsLeft())) x = b.getX() - 31;
		}
	}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 64);
	}

}

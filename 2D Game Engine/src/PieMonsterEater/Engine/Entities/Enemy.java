package PieMonsterEater.Engine.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import BuildingBlocks.Block;
import PieMonsterEater.Engine.EntityCore.GameObject;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;

public class Enemy extends GameObject {
	
	private Handler handler;
	private boolean touching = false;

	public Enemy(int x, int y, Handler handler) {
		super(x, y, handler);
		this.handler = handler;
		this.setID(ID.Enemy);
	}

	public void tick() {
		y += velY;
		
		if(touching == false) velY = 2;
		if(touching == true) velY = -2;
		collision();
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 16, 16);
	}

	public void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Block) {
				Block b = (Block) tempObject;
				if(getBounds().intersects(b.getBoundsTop())) {
					y = b.getY() - 15;
					touching = true;
				}
				if(getBounds().intersects(b.getBoundsBottom())) {
					y = b.getY() + 30;
					touching = false;
				}
				if(getBounds().intersects(b.getBoundsRight())) x = b.getX() + 32;
				if(getBounds().intersects(b.getBoundsLeft())) x = b.getX() - 31;
			}
		}
		}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}

}

package BuildingBlocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import PieMonsterEater.Engine.EntityCore.GameObject;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;

public class Block extends GameObject {

	public Block(int x, int y, Handler handler) {
		super(x, y, handler);
		this.setID(ID.Block);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.darkGray);
		g.fillRect(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(x + 10, y, 10, 8);
	}
	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 10, y + 23, 10, 8);
	}
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 2, 8, 28);
	}
	public Rectangle getBoundsRight() {
		return new Rectangle(x + 23, y + 2, 8, 28);
	}

}

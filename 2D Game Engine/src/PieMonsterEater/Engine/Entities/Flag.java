package PieMonsterEater.Engine.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import PieMonsterEater.Engine.Core.Game;
import PieMonsterEater.Engine.EntityCore.GameObject;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;

public class Flag extends GameObject {

	private Handler handler;
	
	public Flag(int x, int y, Handler handler) {
		super(x, y, handler);
		this.handler = handler;
		this.setID(ID.Flag);
	}

	public void tick() {
		collision();
	}

	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 32, 32);
	}
	
	public void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Player) {
				if(getBounds().intersects(tempObject.getBounds())) { 
					for(int j = 0; j < 100; j++) Game.ll.clear();
					Game.ll.nextLevel();
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}

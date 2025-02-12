package net.piescode.PieEngine.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;

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
				if(getBounds().intersects((Rectangle) tempObject.getBounds())) { 
					Game.ll.clear();
					Game.ll.nextLevel();
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}

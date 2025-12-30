package net.piescode.PieEngine.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

import net.piescode.PieEngine.BuildingBlocks.Block;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.Utils.Pair;

public class Enemy extends GameObject {
	
	private Handler handler;
	private boolean touching = false;

	public Enemy(int x, int y, Handler handler) {
		super(x, y, handler);
		this.handler = handler;
		this.setID(ID.Enemy);
		
		velY = 2;
		velX = 0;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		collision();
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 16, 16);
	}

	public void collision() {
		Pair<Double, Double> collideVector = new Pair<>(0d, 0d); // Tracks the direction in which the player is pushed by a collision on a frame
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			// Accumulates all of the different directions the enemy was pushed on this frame
			Pair<Double, Double> currCollideVector = collideWithEnvironment(tempObject);
			collideVector = new Pair<Double, Double>(collideVector.getX() + currCollideVector.getX(), collideVector.getY() + currCollideVector.getY());
		}
		
		// Normalize the resulting vector
		double collideVectorLength = Math.sqrt(collideVector.getX()*collideVector.getX() + collideVector.getY()*collideVector.getY());
		//System.out.println("Before normal collideX: " + collideVector.getX() + ", collideY: " + collideVector.getY());
		collideVector = new Pair<Double, Double>(collideVector.getX()/collideVectorLength, collideVector.getY()/collideVectorLength);
		
		if(collideVectorLength != 0) {
			
			setVelX((int)(collideVector.getX() * Math.sqrt(8)));
			setVelY((int)(collideVector.getY() * Math.sqrt(8)));
			
			//System.out.println("collideX: " + collideVector.getX() + ", collideY: " + collideVector.getY());
			//System.out.println("velX: " + getVelX() + ", velY: " + getVelY());
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}

}

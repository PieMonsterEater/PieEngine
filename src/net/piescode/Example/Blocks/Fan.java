package net.piescode.Example.Blocks;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import net.piescode.PieEngine.BuildingBlocks.Ellipse;
import net.piescode.PieEngine.BuildingBlocks.Triangle;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.Utils.Pair;
import net.piescode.PieEngine.Visuals.RenderingLayer;

public class Fan extends Ellipse {
	
	Triangle blade1 = null;
	Triangle blade2 = null;
	Triangle blade3 = null;
	Triangle blade4 = null;
	
	Handler handler = null;
	
	long startTime = 0;

	int initialX = 0;
	int initialY = 0;
	
	public Fan(int x, int y, RenderingLayer renderingLayer, Handler handler) {
		super(x, y, 32, 32, null, renderingLayer, handler);
		this.handler = handler;
		initialX = x;
		initialY = y;
		
		setVelX(5);
		createChildObjects();
		
		startTime = System.currentTimeMillis();
	}

	public void tick() {
		super.tick();
		
		if(getX() > initialX + 100) setVelX(-5);
		if(getX() < initialX - 100) setVelX(5);
		
		x += velX;
		
		blade1.setX(blade1.getX() + velX);
		blade2.setX(blade2.getX() + velX);
		blade3.setX(blade3.getX() + velX);
		blade4.setX(blade4.getX() + velX);
		
		// if(System.currentTimeMillis() > startTime + 15000) handler.removeObj(this);
	}

	public void render(Graphics g) {
		super.render(g);
	}

	public Ellipse2D.Float getBounds() {
		return super.getBounds();
	}

	public void createChildObjects() {
		blade1 = new Triangle(getX() + (int)(width/2), getY() + (int)(height/2), new Pair<Integer, Double>(64, 0d), new Pair<Integer, Double>(24, 30d), null, RenderingLayer.MIDDLEGROUND, handler);
		blade2 = new Triangle(getX() + (int)(width/2), getY() + (int)(height/2), new Pair<Integer, Double>(64, 90d), new Pair<Integer, Double>(24, 120d), null, RenderingLayer.MIDDLEGROUND, handler);
		blade3 = new Triangle(getX() + (int)(width/2), getY() + (int)(height/2), new Pair<Integer, Double>(64, 180d), new Pair<Integer, Double>(24, 210d), null, RenderingLayer.MIDDLEGROUND, handler);
		blade4 = new Triangle(getX() + (int)(width/2), getY() + (int)(height/2), new Pair<Integer, Double>(64, 270d), new Pair<Integer, Double>(24, 300d), null, RenderingLayer.MIDDLEGROUND, handler);
		
		blade1.setThetaChange(5);
		blade2.setThetaChange(5);
		blade3.setThetaChange(5);
		blade4.setThetaChange(5);
		
		handler.addObj(blade1);
		handler.addObj(blade2);
		handler.addObj(blade3);
		handler.addObj(blade4);
	}

	public void destroyChildObjects() {
		handler.removeObj(blade1);
		handler.removeObj(blade2);
		handler.removeObj(blade3);
		handler.removeObj(blade4);
	}

}

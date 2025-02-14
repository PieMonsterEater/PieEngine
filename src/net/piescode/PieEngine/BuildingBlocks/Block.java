package net.piescode.PieEngine.BuildingBlocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.Player.Player;

public class Block extends GameObject {
	
	private double theta = 0, thetaChange = 0;
	private Handler handler;
	private int length = 32, height = 32, projRectSize = 5, shortenProj = 0;
	private boolean movRight = true;
	private Shape bounds;

	public Block(int x, int y, Handler handler) {
		super(x, y, handler);
		this.setID(ID.Block);
		this.handler = handler;
		bounds = createBounds();
		this.solid = true;
	}
	
	public Block(int x, int y, double theta, Handler handler) {
		super(x, y, handler);
		this.setID(ID.Block);
		this.theta = Math.toRadians(theta);
		this.handler = handler;
		bounds = createBounds();
	}
	
	public Block(int x, int y, int length, int height, double theta, Handler handler) {
		super(x, y, handler);
		this.setID(ID.Block);
		this.length = length;
		this.height = height;
		this.theta = Math.toRadians(theta);
		this.handler = handler;
		bounds = createBounds();
	}


	public void tick() {
		theta += thetaChange;
		bounds = createBounds();
		collison();
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		double phi = theta + Math.toRadians(90);
		
		
		
		g.setColor(Color.DARK_GRAY);
		//g.fillRect(x, y, 32, 32);
		g2d.fill(getBounds());
		
		/*
		g.setColor(Color.blue);
		g.drawLine(x, y, (int)(50*Math.cos(phi+Math.toRadians(180))) + x, (int)(50*Math.sin(phi+Math.toRadians(180))) + y);
		g2d.fill(getBoundsTop());
		
		g.setColor(Color.green);
		g.drawLine(x, y, (int)(50*Math.cos(theta)) + x, (int)(50*Math.sin(theta)) + y);
		g2d.fill(getBoundsRight());
		
		g.setColor(Color.pink);
		g.drawLine(x, y, (int)(50*Math.cos(phi)) + x, (int)(50*Math.sin(phi)) + y);
		g2d.fill(getBoundsBottom());
		
		g.setColor(Color.red);
		g.drawLine(x, y, (int)(50*Math.cos(theta+Math.toRadians(180))) + x, (int)(50*Math.sin(theta+Math.toRadians(180))) + y);
		g2d.fill(getBoundsLeft());
		
		*/
		
	}
	
	public void collison() {
		
	}
	
	public Shape createBounds() {
		Rectangle bounds = new Rectangle(x, y, length, height);
		AffineTransform at = AffineTransform.getRotateInstance(theta, x, y);
		Shape rotatedBounds = at.createTransformedShape(bounds);
		return rotatedBounds;
	}

	public Shape getBounds() {
		return bounds;
	}
	
	public Shape getBoundsTop() {
		double phi = theta + Math.toRadians(-90);
		Rectangle topRect = new Rectangle(x + (shortenProj/2), y, projRectSize, length - shortenProj);
		AffineTransform at = AffineTransform.getRotateInstance(phi, topRect.x, topRect.y);
		Shape topBound = at.createTransformedShape(topRect);
		return topBound;
	}
	
	//(int)((height*Math.cos(Math.toRadians(phi)) + x)),  (int)((height*Math.sin(Math.toRadians(phi)) + y))
	public Shape getBoundsBottom() {
		double phi = theta + Math.toRadians(90);
		double transConstX = height*Math.cos(phi) + length*Math.cos(theta); //+ length*Math.cos(theta)
		double transConstY = height*Math.sin(phi) + length*Math.sin(theta); //+ length*Math.sin(theta)
		Rectangle bottomRect = new Rectangle((int)(transConstX + x) + (shortenProj/2), (int)(transConstY + y), projRectSize, length - shortenProj);
		AffineTransform at = AffineTransform.getRotateInstance(phi, bottomRect.x, bottomRect.y);
		Shape bottomBound = at.createTransformedShape(bottomRect);
		return bottomBound;
	}
	public Shape getBoundsLeft() {
		double transConstX = projRectSize*Math.cos(theta + Math.toRadians(180)); //+ length*Math.cos(theta)
		double transConstY = projRectSize*Math.sin(theta + Math.toRadians(180)); //+ length*Math.sin(theta)
		Rectangle topRect = new Rectangle((int)(transConstX + x) + (shortenProj/2), (int)(transConstY + y), projRectSize, height - shortenProj);
		AffineTransform at = AffineTransform.getRotateInstance(theta, topRect.x, topRect.y);
		Shape topBound = at.createTransformedShape(topRect);
		return topBound;
	}
	public Shape getBoundsRight() {
		double transConstX = length*Math.cos(theta); //+ length*Math.cos(theta)
		double transConstY = length*Math.sin(theta); //+ length*Math.sin(theta)
		Rectangle topRect = new Rectangle((int)(transConstX + x) + (shortenProj/2), (int)(transConstY + y), projRectSize, height - shortenProj);
		AffineTransform at = AffineTransform.getRotateInstance(theta, topRect.x, topRect.y);
		Shape topBound = at.createTransformedShape(topRect);
		return topBound;
	}
	
	public double getTheta() {
		return theta;
	}
	
	public void setThetaChange(double thetaChange) {
		this.thetaChange = thetaChange;
	}
	
	public double getThetaChange() {
		return thetaChange;
	}
	

}

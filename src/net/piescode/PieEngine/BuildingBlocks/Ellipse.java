package net.piescode.PieEngine.BuildingBlocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;

public class Ellipse extends GameObject {
	
	private float width = 0;
	private float height = 0;
	
	private Ellipse2D.Float bounds;

	public Ellipse(int x, int y, float width, float height, Handler handler) {
		super(x, y, handler);
		
		this.width = width;
		this.height = height;
		
		createBounds();
		this.solid = true;
	}

	public void tick() {
		// TODO Auto-generated method stub
		
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.gray);
		g2d.fill(getBounds());
		
		g2d.setColor(Color.CYAN);
		g2d.fillRect(x + (int) width/2, y + (int) height/2, 3, 3);
	}

	public Ellipse2D.Float getBounds() {
		return bounds;
	}
	
	private void createBounds() {
		bounds = new Ellipse2D.Float(x, y, width, height);
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}

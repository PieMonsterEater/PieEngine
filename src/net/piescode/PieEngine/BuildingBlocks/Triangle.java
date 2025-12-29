package net.piescode.PieEngine.BuildingBlocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;

import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.Utils.Pair;

public class Triangle extends GameObject {
	
	private Shape bounds = null;
	
	Pair<Integer, Double> p2 = null; // Length and angle of point 2
	Pair<Integer, Double> p3 = null; // Length and angle of point 3
	
	Pair<Double, Double> v12, v13, v23 = null; // Holds the vectors for pushing back
	
	public Triangle(int x, int y, Pair<Integer, Double> p2, Pair<Integer, Double> p3, Handler handler) {
		super(x, y, handler);
		
		this.p2 = p2;
		this.p3 = p3;
		
		bounds = createBounds();
	}
	
	private Shape createBounds() {
		// Remember that in the context of Pair objects, X and Y are just names: X represents the distance to the next point and Y represents the angle toward it from the first point on the next 2 lines
		int[] xCoords = {x, (int) (p2.getX()*Math.cos(Math.toRadians(p2.getY()))) + x, (int) (p3.getX()*Math.cos(Math.toRadians(p3.getY()))) + x};
		int[] yCoords = {y, (int) (p2.getX()*Math.sin(Math.toRadians(p2.getY()))) + y, (int) (p3.getX()*Math.sin(Math.toRadians(p3.getY()))) + y};
		
		// Get the coordinate of the centroid of the triangle
		double centerX = (xCoords[0] + xCoords[1] + xCoords[2])/3;
		double centerY = (yCoords[0] + yCoords[1] + yCoords[2])/3;
		
		// Begin calculation for triangle normal vectors
		double sideToCenterX = centerX - xCoords[0];
		double sideToCenterY = centerY - yCoords[0];
		
		
		
		return new Polygon(xCoords, yCoords, 3);
	}

	public void tick() {
		bounds = createBounds();
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.GRAY);
		g2d.fill(bounds);
	}

	public Shape getBounds() {
		return bounds;
	}

}

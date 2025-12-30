package net.piescode.PieEngine.BuildingBlocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.Utils.Pair;

public class Triangle extends GameObject {
	
	private Shape bounds = null;
	
	Pair<Integer, Double> p2 = null; // Length and angle of point 2
	Pair<Integer, Double> p3 = null; // Length and angle of point 3
	
	Pair<Double, Double> v12, v13, v23 = null; // Holds the vectors for each of the sides
	Pair<Double, Double> n12, n13, n23 = null; // Holds the normal vectors for each of the sides
	
	private int projRectSize = 2;
	
	public Triangle(int x, int y, Pair<Integer, Double> p2, Pair<Integer, Double> p3, Handler handler) {
		super(x, y, handler);
		
		this.p2 = forceAngle365(p2);
		this.p3 = forceAngle365(p3);
		
		bounds = createBounds();
	}
	
	private Shape createBounds() {
		// Remember that in the context of Pair objects, X and Y are just names: X represents the distance to the next point and Y represents the angle toward it from the first point on the next 2 lines
		int[] xCoords = {x, (int) (p2.getX()*Math.cos(Math.toRadians(p2.getY()))) + x, (int) (p3.getX()*Math.cos(Math.toRadians(p3.getY()))) + x};
		int[] yCoords = {y, (int) (p2.getX()*Math.sin(Math.toRadians(p2.getY()))) + y, (int) (p3.getX()*Math.sin(Math.toRadians(p3.getY()))) + y};
		
		// Get the coordinate of the centroid of the triangle
		double centerX = (xCoords[0] + xCoords[1] + xCoords[2])/3;
		double centerY = (yCoords[0] + yCoords[1] + yCoords[2])/3;
		
		// Calculation for triangle side vectors
		v12 = new Pair<Double, Double>(p2.getX()*Math.cos(Math.toRadians(p2.getY())), p2.getX()*Math.sin(Math.toRadians(p2.getY())));
		v13 = new Pair<Double, Double>(p3.getX()*Math.cos(Math.toRadians(p3.getY())), p3.getX()*Math.sin(Math.toRadians(p3.getY())));
		v23 = new Pair<Double, Double>(p3.getX()*Math.cos(Math.toRadians(p3.getY())) - p2.getX()*Math.cos(Math.toRadians(p2.getY())), p3.getX()*Math.sin(Math.toRadians(p3.getY())) - p2.getX()*Math.sin(Math.toRadians(p2.getY())));
		
		// Calculation for normal vectors
		double v12Length = Math.sqrt(v12.getX()*v12.getX() + v12.getY()*v12.getY());
		double v13Length = Math.sqrt(v13.getX()*v13.getX() + v13.getY()*v13.getY());
		double v23Length = Math.sqrt(v23.getX()*v23.getX() + v23.getY()*v23.getY());
		
		if(p2.getY() <= p3.getY() && p3.getY() - p2.getY() > 180) {
			n12 = new Pair<Double, Double>(-v12.getY()/v12Length, v12.getX()/v12Length); // Adding 90 degrees (goes counterclockwise)
			n13 = new Pair<Double, Double>(v13.getY()/v13Length, -v13.getX()/v13Length); // Subtracting 90 degrees (goes clockwise)
			
			n23 = new Pair<Double, Double>(-v23.getY()/v23Length, v23.getX()/v23Length);
		} 
		else if(p2.getY() <= p3.getY()) {
			n12 = new Pair<Double, Double>(v12.getY()/v12Length, -v12.getX()/v12Length); // Subtracting 90 degrees (goes clockwise)
			n13 = new Pair<Double, Double>(-v13.getY()/v13Length, v13.getX()/v13Length); // Adding 90 degrees (goes counterclockwise)
			
			n23 = new Pair<Double, Double>(v23.getY()/v23Length, -v23.getX()/v23Length);
		} 
		// This is the same normals as else above
		else if (p2.getY() > p3.getY() && p2.getY() - p3.getY() > 180) {
			n12 = new Pair<Double, Double>(v12.getY()/v12Length, -v12.getX()/v12Length); // Subtracting 90 degrees (goes clockwise)
			n13 = new Pair<Double, Double>(-v13.getY()/v13Length, v13.getX()/v13Length); // Adding 90 degrees (goes counterclockwise)
			
			n23 = new Pair<Double, Double>(v23.getY()/v23Length, -v23.getX()/v23Length);
		}
		// This is the same normals as first case
		else if(p2.getY() > p3.getY()) {
			n12 = new Pair<Double, Double>(-v12.getY()/v12Length, v12.getX()/v12Length); // Adding 90 degrees (goes counterclockwise)
			n13 = new Pair<Double, Double>(v13.getY()/v13Length, -v13.getX()/v13Length); // Subtracting 90 degrees (goes clockwise)
			
			n23 = new Pair<Double, Double>(-v23.getY()/v23Length, v23.getX()/v23Length);
		}
		
		//System.out.println("12: <" + v12.getX() + ", " + v12.getY() + "> 12 Normal: <" + n12.getX() + ", " + n12.getY() + ">");
		//System.out.println("13: <" + v13.getX() + ", " + v13.getY() + "> 13 Normal: <" + n13.getX() + ", " + n13.getY() + ">");
		
		
		return new Polygon(xCoords, yCoords, 3);
	}

	public void tick() {
		bounds = createBounds();
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.GRAY);
		g2d.fill(bounds);
		/*
		g2d.setColor(Color.CYAN);
		g2d.fill(getBounds12());
		g2d.setColor(Color.GREEN);
		g2d.fill(getBounds13());
		g2d.setColor(Color.BLUE);
		g2d.fill(getBounds23());
		*/
	}
	

	public Shape getBounds12() {
		double v12Length = Math.sqrt(v12.getX()*v12.getX() + v12.getY()*v12.getY());
		Rectangle rectBounds = new Rectangle(x, y, (int) v12Length, projRectSize);
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(p2.getY()), rectBounds.x, rectBounds.y);
		Shape bounds12 = at.createTransformedShape(rectBounds);
		return bounds12;
	}
	
	public Shape getBounds13() {
		double v13Length = Math.sqrt(v13.getX()*v13.getX() + v13.getY()*v13.getY());
		Rectangle rectBounds = new Rectangle(x, y, (int) v13Length, projRectSize);
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(p3.getY()), rectBounds.x, rectBounds.y);
		Shape bounds13 = at.createTransformedShape(rectBounds);
		return bounds13;
	}
	
	public Shape getBounds23() {
		double v23Length = Math.sqrt(v23.getX()*v23.getX() + v23.getY()*v23.getY());
		Rectangle rectBounds = new Rectangle(x + v12.getX().intValue(), y + v12.getY().intValue(), (int) v23Length, projRectSize);
		AffineTransform at = AffineTransform.getRotateInstance(Math.atan2(v23.getY(), v23.getX()), rectBounds.x, rectBounds.y);
		Shape bounds23 = at.createTransformedShape(rectBounds);
		return bounds23;
	}
	
	public Shape getBounds() {
		return bounds;
	}
	
	public Pair<Double, Double> getNormal12() {
		return n12;
	}
	
	public Pair<Double, Double> getNormal13() {
		return n13;
	}
	
	public Pair<Double, Double> getNormal23() {
		return n23;
	}
	
	private Pair<Integer, Double> forceAngle365(Pair<Integer, Double> toForce) {
		double angle = toForce.getY();
		
		angle %= 365;
		if(angle < 0) angle += 365;
		
		return new Pair<Integer, Double>(toForce.getX(), angle);
	}

}

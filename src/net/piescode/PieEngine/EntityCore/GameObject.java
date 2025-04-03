package net.piescode.PieEngine.EntityCore;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.piescode.PieEngine.Audio.SoundEffect;
import net.piescode.PieEngine.BuildingBlocks.Block;
import net.piescode.PieEngine.BuildingBlocks.Ellipse;
import net.piescode.PieEngine.Utils.Pair;
import net.piescode.PieEngine.Visuals.Animator;
import net.piescode.PieEngine.Visuals.BufferedImageLoader;
import net.piescode.PieEngine.Visuals.SpriteSheet;


public abstract class GameObject {
	protected int x, y, velX, velY, currentAnimation;
	protected ID id;
	protected BufferedImage sprite;
	protected BufferedImage spriteSheet;
	protected SpriteSheet ss;
	protected Animator ani;
	protected SoundEffect se;
	protected boolean animating = false;
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Shape getBounds();
	
	public boolean dontDestroyOnLoad = false;
	public boolean solid = false;
	
	public GameObject(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		
		ani = new Animator();
		se = new SoundEffect();
	}
	
	public void setSpriteSheet(String spriteLocal) {
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			spriteSheet = loader.loadImage(spriteLocal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ss = new SpriteSheet(spriteSheet);
		
	}
	
	public void setAnimation(BufferedImage[] images, int delay) {
		//currentAnimation = anim;
		ani.setFrames(images);
		ani.setDelay(delay);
	}
	
	public void playSound(String name) {
		se.playSound(name);
	}
	
	public void setSprite(int pixelX, int pixelY, int pixelWidth, int pixelHeight) {
		sprite = ss.grabSprite(pixelX, pixelY, pixelWidth, pixelHeight);
	}
	
	public void animate() {
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public ID getID() {
		return id;
	}
	
	public void setID(ID id) {
		this.id = id;
	}

	public void setSpr(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	// Gives all GameObjects a simple method to implement to collide with the environment
	// Returns the vector that pushes the object out
	protected Pair<Double, Double> collideWithEnvironment(GameObject obj) {
		
		if(obj instanceof Block) {
			Block b = (Block) obj;
			
			//Uses a slightly less resource heavy version of collision detection if this object has a Rectangle hitbox
			if(getBounds() instanceof Rectangle) {
				if(b.getBounds().intersects((Rectangle2D) getBounds())) {
					return handleBlockCollision(b);
				}
			
				return new Pair<Double, Double>(0d, 0d);
			}
		
			//Uses the default Shape collision with areas which is more resource intensive by checking if there is any overlap in the areas of the hitboxes
			Area collisionArea = new Area(this.getBounds());
			collisionArea.intersect(new Area(b.getBounds()));
		
			if(!collisionArea.isEmpty()) return handleBlockCollision(b);
			return new Pair<Double, Double>(0d, 0d);
		}
		
		if(obj instanceof Ellipse) {
			Ellipse e = (Ellipse) obj;
			
			//Uses a slightly less resource heavy version of collision detection if this object has a Rectangle hitbox
			if(getBounds() instanceof Rectangle) {
				if(e.getBounds().intersects((Rectangle2D) getBounds())) {
					return handleEllipseCollision(e);
				}
			
				return new Pair<Double, Double>(0d, 0d);
			}
		
			//Uses the default Shape collision with areas which is more resource intensive by checking if there is any overlap in the areas of the hitboxes
			Area collisionArea = new Area(this.getBounds());
			collisionArea.intersect(new Area(e.getBounds()));
		
			if(!collisionArea.isEmpty()) handleEllipseCollision(e);
			return new Pair<Double, Double>(0d, 0d);
		}
		
		return new Pair<Double, Double>(0d, 0d);
		
	}
	
	private Pair<Double, Double> handleEllipseCollision(Ellipse e) {
		double pushX = 0;
		double pushY = 0;
		double movXFlag = 0;
		double movYFlag = 0;

		Area ellipseArea = new Area(e.getBounds());
		ellipseArea.intersect(new Area(getBounds()));

		Rectangle overlap = ellipseArea.getBounds();

		// Gets the center of the ellipse (in absolute coordinates) to use as a reference to radially push out the entity
		int centerX = e.getX() + (int) e.getWidth()/2;
		int centerY = e.getY() + (int) e.getHeight()/2;		
		
		// Finds the lengths for the two non-hypotenuse sides of the overlap rectangle, divided by two to make some of the collisions in the left quadrant feel better(?)
		double adjacent = overlap.x + overlap.width/2 - centerX;
		double opposite = overlap.y + overlap.height/2 - centerY;
		
		
		double hypLength = Math.sqrt(adjacent*adjacent + opposite*opposite);

		double theta = Math.acos(adjacent/hypLength);
		
		// Adjusting the angle for the fact that acos only returns angle values for half of a circle
		if(opposite < 0 && adjacent > 0) theta -= Math.toRadians(90);
		if(opposite < 0 && adjacent < 0) theta += Math.toRadians(90);
		
		// Find the shortest distance to push the entity so that it is no longer in the ellipse
		// Moving the entity in the direction of the vector is great, but the objective is to make the overlap area 0
		// Since the overlap area is 0 if the x OR the y of the overlap is 0, we find which one is shorter and then use that
		if(!(Math.cos(theta) < 0.00001) || !(Math.cos(theta) > -0.00001)) {
			movXFlag = overlap.width/Math.cos(theta);
		}
		
		if(!(Math.sin(theta) < 0.00001) || !(Math.sin(theta) > -0.00001)) {
			movYFlag = overlap.height/Math.sin(theta);
		}
		
		if(movXFlag < 0) movXFlag *= -1;
		if(movYFlag < 0) movYFlag *= -1;

		if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
			//System.out.println("Using movXFlag");
			pushX += movXFlag*Math.cos(theta);
			pushY += movXFlag*Math.sin(theta);
		} else if(movYFlag != 0){
			//System.out.println("Using movYFlag");
			pushX += movYFlag*Math.cos(theta);
			pushY += movYFlag*Math.sin(theta);
		}
		
		setX(getX() + (int)pushX);
		setY(getY() + (int)pushY);
		
		// Return the normalized push vector
		return new Pair<Double, Double>(Math.cos(theta), Math.sin(theta));
	}
	
	private Pair<Double, Double> handleBlockCollision(Block b) {
		Area blockArea = new Area(b.getBounds()); 
		blockArea.intersect(new Area(getBounds())); //Modifies the block area to just the area it intersects with the current object

		Rectangle overlap = blockArea.getBounds();

		double theta = b.getTheta(); // Direction the block is protruding in
		double phi = theta + Math.toRadians(90); // Direction orthogonal to the protruding block, phi is used to find the direction of the push boxes
		double movXFlag = 0; // Represents how far you'd have to move in the top push box direction to make the x overlap 0
		double movYFlag = 0; // Represents how far you'd have to move in the top push box direction to make the y overlap 0
		double pushX = 0;
		double pushY = 0;

		Area objArea = new Area(getBounds()); //Area of the current object
		Area topArea = new Area(b.getBoundsTop());
		Area bottomArea = new Area(b.getBoundsBottom());
		Area rightArea = new Area(b.getBoundsRight());
		Area leftArea = new Area(b.getBoundsLeft());

		// These are shape areas representing the intersection of the current object and the push boxes of the block its colliding with, not literal numerical areas
		topArea.intersect(objArea);
		bottomArea.intersect(objArea);
		rightArea.intersect(objArea);
		leftArea.intersect(objArea);

		// Numerical areas 
		int topAreaArea = topArea.getBounds().width*topArea.getBounds().height;
		int bottomAreaArea = bottomArea.getBounds().width*bottomArea.getBounds().height;
		int rightAreaArea = rightArea.getBounds().width*rightArea.getBounds().height;
		int leftAreaArea = leftArea.getBounds().width*leftArea.getBounds().height;


		if(!topArea.isEmpty() & topAreaArea > rightAreaArea & topAreaArea > leftAreaArea) {
			
			// Sometimes even if the value of cos should be 0, it returns something close but not actually 0
			if(!(Math.cos(phi + Math.toRadians(180)) < 0.00001) || !(Math.cos(phi + Math.toRadians(180)) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(phi + Math.toRadians(180)); //Phi + 180 is the direction of the top box
			}
	
			//Sometimes even if the value of sin should be 0, it returns something close but not actually 0
			if(!(Math.sin(phi + Math.toRadians(180)) < 0.00001) || !(Math.sin(phi + Math.toRadians(180)) > -0.00001)) {
				movYFlag = overlap.height/Math.sin(phi + Math.toRadians(180)); //Phi + 180 is the direction of the top box
			}
	
			if(movXFlag < 0) movXFlag *= -1;
			if(movYFlag < 0) movYFlag *= -1;
	
			if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
				//System.out.println("Using movXFlag");
				pushX += movXFlag*Math.cos(phi + Math.toRadians(180));
				pushY += movXFlag*Math.sin(phi + Math.toRadians(180));
			} else if(movYFlag != 0){
				//System.out.println("Using movYFlag");
				pushX += movYFlag*Math.cos(phi + Math.toRadians(180));
				pushY += movYFlag*Math.sin(phi + Math.toRadians(180));
			}
		}

		if(!bottomArea.isEmpty() & bottomAreaArea > rightAreaArea & bottomAreaArea > leftAreaArea) {
	
			if(!(Math.cos(phi) < 0.00001) || !(Math.cos(phi) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(phi);
		
			}
	
			if(!(Math.sin(phi) < 0.00001) || !(Math.sin(phi) > -0.00001)) {
				movYFlag = overlap.height/Math.sin(phi);
			}
	
	
			if(movXFlag < 0) movXFlag *= -1;
			if(movYFlag < 0) movYFlag *= -1;
	
			if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
				pushX += movXFlag*Math.cos(phi);
				pushY += movXFlag*Math.sin(phi);
			} else if(movYFlag != 0){
				pushX += movYFlag*Math.cos(phi);
				pushY += movYFlag*Math.sin(phi);
			}
		}	

		if(!rightArea.isEmpty() & rightAreaArea > topAreaArea & rightAreaArea > bottomAreaArea) {
	
			if(!(Math.cos(theta) < 0.00001) || !(Math.cos(theta) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(theta);
			}
	
			if(!(Math.sin(theta) < 0.00001) || !(Math.sin(theta) > -0.00001)) {
			movYFlag = overlap.height/Math.sin(theta);
			}
	
	
			if(movXFlag < 0) movXFlag *= -1;
			if(movYFlag < 0) movYFlag *= -1;
	
			if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
				pushX += movXFlag*Math.cos(theta);
				pushY += movXFlag*Math.sin(theta);
			} else if(movYFlag != 0){
				pushX += movYFlag*Math.cos(theta);
				pushY += movYFlag*Math.sin(theta);
			}
		}

		if(!leftArea.isEmpty() & leftAreaArea > topAreaArea & leftAreaArea > bottomAreaArea) {
	
			if(!(Math.cos(theta + Math.toRadians(180)) < 0.00001) || !(Math.cos(theta + Math.toRadians(180)) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(theta + Math.toRadians(180));
			}
	
			if(!(Math.sin(theta + Math.toRadians(180)) < 0.00001) || !(Math.sin(theta + Math.toRadians(180)) > -0.00001)) {
				movYFlag = overlap.height/Math.sin(theta + Math.toRadians(180));
			}
	
	
			if(movXFlag < 0) movXFlag *= -1;
			if(movYFlag < 0) movYFlag *= -1;
	
			if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
				pushX += movXFlag*Math.cos(theta + Math.toRadians(180));
				pushY += movXFlag*Math.sin(theta + Math.toRadians(180));
			} else if(movYFlag != 0){
				pushX += movYFlag*Math.cos(theta + Math.toRadians(180));
				pushY += movYFlag*Math.sin(theta + Math.toRadians(180));
			}
		}

	
		setX(getX() + (int)pushX);
		setY(getY() + (int)pushY);

		if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) return new Pair<Double, Double>(pushX/movXFlag, pushY/movXFlag);
		else if(movYFlag != 0) return new Pair<Double, Double>(pushX/movYFlag, pushY/movYFlag);
		return new Pair<Double, Double>(0d, 0d);
	}
	
}

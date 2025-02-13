package net.piescode.PieEngine.EntityCore;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import BuildingBlocks.Block;
import net.piescode.PieEngine.Audio.SoundEffect;
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
	
	//Gives all GameObjects a simple method to implement to collide with the blocks
	protected void collideWithBlocks(Block b) {
		//Uses a slightly less resource heavy version of collision detection if this object has a Rectangle hitbox
		if(getBounds() instanceof Rectangle) {
			if(b.getBounds().intersects((Rectangle2D) getBounds())) {
				handleBlockCollision(b);
			}
			
			return;
		}
		
		//Uses the default Shape collision with areas which is more resource intensive by checking if there is any overlap in the areas of the hitboxes
		Area collisionArea = new Area(this.getBounds());
		collisionArea.intersect(new Area(b.getBounds()));
		
		if(!collisionArea.isEmpty()) handleBlockCollision(b);
	}
	
	private void handleBlockCollision(Block b) {
		Area blockArea = new Area(b.getBounds());
		blockArea.intersect(new Area(getBounds()));

		Rectangle overlap = blockArea.getBounds();

		double theta = b.getTheta();
		double phi = theta + Math.toRadians(90);
		double pushX = 0;
		double pushY = 0;

		//System.out.println("theta: " + theta + "        phi: " + phi);
		//System.out.println("cos(phi): " + Math.cos(phi) + "        sin(phi): " + Math.sin(phi));

		Area objArea = new Area(getBounds());
		Area topArea = new Area(b.getBoundsTop());
		Area bottomArea = new Area(b.getBoundsBottom());
		Area rightArea = new Area(b.getBoundsRight());
		Area leftArea = new Area(b.getBoundsLeft());

		topArea.intersect(objArea);
		bottomArea.intersect(objArea);
		rightArea.intersect(objArea);
		leftArea.intersect(objArea);

		int topAreaArea = topArea.getBounds().width*topArea.getBounds().height;
		int bottomAreaArea = bottomArea.getBounds().width*bottomArea.getBounds().height;
		int rightAreaArea = rightArea.getBounds().width*rightArea.getBounds().height;
		int leftAreaArea = leftArea.getBounds().width*leftArea.getBounds().height;

		//System.out.println("topArea: " + topAreaArea + " rightArea: " + rightAreaArea + " leftArea: " + leftAreaArea + " bottomArea: " + bottomAreaArea);

		if(!topArea.isEmpty() & topAreaArea > rightAreaArea & topAreaArea > leftAreaArea) {
			double movXFlag = 0;
			double movYFlag = 0;
	
			//System.out.println((Math.cos(phi + Math.toRadians(180)) < 0.00001) || (Math.cos(phi + Math.toRadians(180)) > -0.00001));
			if(!(Math.cos(phi + Math.toRadians(180)) < 0.00001) || !(Math.cos(phi + Math.toRadians(180)) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(phi + Math.toRadians(180));
		
			}
	
			//System.out.println((Math.sin(phi + Math.toRadians(180)) < 0.00001) || (Math.sin(phi + Math.toRadians(180)) > -0.00001));
			if(!(Math.sin(phi + Math.toRadians(180)) < 0.00001) || !(Math.sin(phi + Math.toRadians(180)) > -0.00001)) {
				movYFlag = overlap.height/Math.sin(phi + Math.toRadians(180));
			}
	
			//System.out.println("movXFlag: " + movXFlag + "        movYFlag: " + movYFlag);
	
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
			double movXFlag = 0;
			double movYFlag = 0;
	
			//System.out.println((Math.cos(phi) < 0.00001) || (Math.cos(phi) > -0.00001));
			if(!(Math.cos(phi) < 0.00001) || !(Math.cos(phi) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(phi);
		
			}
	
			//System.out.println((Math.sin(phi) < 0.00001) || (Math.sin(phi) > -0.00001));
			if(!(Math.sin(phi) < 0.00001) || !(Math.sin(phi) > -0.00001)) {
				movYFlag = overlap.height/Math.sin(phi);
			}
	
			//System.out.println("movXFlag: " + movXFlag + "        movYFlag: " + movYFlag);
	
			if(movXFlag < 0) movXFlag *= -1;
			if(movYFlag < 0) movYFlag *= -1;
	
			if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
				//System.out.println("Using movXFlag");
				pushX += movXFlag*Math.cos(phi);
				pushY += movXFlag*Math.sin(phi);
			} else if(movYFlag != 0){
				//System.out.println("Using movYFlag");
				pushX += movYFlag*Math.cos(phi);
				pushY += movYFlag*Math.sin(phi);
			}
		}	

		if(!rightArea.isEmpty() & rightAreaArea > topAreaArea & rightAreaArea > bottomAreaArea) {
			double movXFlag = 0;
			double movYFlag = 0;
	
	
			//System.out.println("cos(theta): " + Math.cos(theta) + "         sin(theta): " + Math.sin(theta));
			//System.out.println((Math.cos(theta) < 0.00001) || (Math.cos(theta) > -0.00001));
			if(!(Math.cos(theta) < 0.00001) || !(Math.cos(theta) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(theta);
			}
	
			//System.out.println((Math.sin(theta) < 0.00001) || (Math.sin(theta) > -0.00001));
			if(!(Math.sin(theta) < 0.00001) || !(Math.sin(theta) > -0.00001)) {
			movYFlag = overlap.height/Math.sin(theta);
			}
	
			//System.out.println("movXFlag: " + movXFlag + "        movYFlag: " + movYFlag);
	
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
		}

		if(!leftArea.isEmpty() & leftAreaArea > topAreaArea & leftAreaArea > bottomAreaArea) {
			double movXFlag = 0;
			double movYFlag = 0;
	
			//System.out.println("cos(theta): " + Math.cos(theta) + "         sin(theta): " + Math.sin(theta));
			//System.out.println((Math.cos(theta) < 0.00001) || (Math.cos(theta) > -0.00001));
			if(!(Math.cos(theta + Math.toRadians(180)) < 0.00001) || !(Math.cos(theta + Math.toRadians(180)) > -0.00001)) {
				movXFlag = overlap.width/Math.cos(theta + Math.toRadians(180));
			}
	
			//System.out.println((Math.sin(theta) < 0.00001) || (Math.sin(theta) > -0.00001));
			if(!(Math.sin(theta + Math.toRadians(180)) < 0.00001) || !(Math.sin(theta + Math.toRadians(180)) > -0.00001)) {
				movYFlag = overlap.height/Math.sin(theta + Math.toRadians(180));
			}
	
			//System.out.println("movXFlag: " + movXFlag + "        movYFlag: " + movYFlag);
	
			if(movXFlag < 0) movXFlag *= -1;
			if(movYFlag < 0) movYFlag *= -1;
	
			if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
				//System.out.println("Using movXFlag");
				pushX += movXFlag*Math.cos(theta + Math.toRadians(180));
				pushY += movXFlag*Math.sin(theta + Math.toRadians(180));
			} else if(movYFlag != 0){
				//System.out.println("Using movYFlag");
				pushX += movYFlag*Math.cos(theta + Math.toRadians(180));
				pushY += movYFlag*Math.sin(theta + Math.toRadians(180));
			}
		}

		//System.out.println("pushX: " + pushX + "        pushY: " + pushY);
		//System.out.println("Player pos before push added: X: " + getX() + "        Y: " + getY());
	
		setX(getX() + (int)pushX);
		setY(getY() + (int)pushY);

		//System.out.println("Player pos: X: " + getX() + "        Y: " + getY());
		//System.exit(1);
	}
	
}

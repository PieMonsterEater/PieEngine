package net.piescode.PieEngine.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import BuildingBlocks.Block;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;

public class Player extends GameObject {
	
	public static final int IDLE_DOWN = 0, IDLE_UP = 1, IDLE_RIGHT = 2, IDLE_LEFT = 3, WALK_DOWN = 5, WALK_UP = 6, WALK_RIGHT = 7, WALK_LEFT = 8;
	
	private Handler handler;
	
	private BufferedImage[] idleDown = new BufferedImage[2];
	private BufferedImage[] idleUp = new BufferedImage[2];
	private BufferedImage[] idleRight = new BufferedImage[2];
	private BufferedImage[] idleLeft = new BufferedImage[2];
	
	private BufferedImage[] walkDown = new BufferedImage[4];
	private BufferedImage[] walkUp = new BufferedImage[4];
	private BufferedImage[] walkRight = new BufferedImage[4];
	private BufferedImage[] walkLeft = new BufferedImage[4];
	
	public Player(int x, int y, Handler handler) {
		super(x, y, handler);
		this.handler = handler;
		this.setID(ID.Player);
		this.setSpriteSheet("res/Guy.png");
		
		idleDown[0] = ss.grabSprite(0, 0, 32, 32);
		idleDown[1] = ss.grabSprite(32, 0, 32, 32);
		
		idleUp[0] = ss.grabSprite(0, 32, 32, 32);
		idleUp[1] = ss.grabSprite(32, 32, 32, 32);
		
		idleRight[0] = ss.grabSprite(0, 64, 32, 32);
		idleRight[1] = ss.grabSprite(32, 64, 32, 32);
		
		idleLeft[0] = ss.grabSprite(0, 96, 32, 32);
		idleLeft[1] = ss.grabSprite(32, 96, 32, 32);
		
		walkDown[0] = ss.grabSprite(65, 0, 32, 32);
		walkDown[1] = ss.grabSprite(0, 0, 32, 32);
		walkDown[2] = ss.grabSprite(98, 0, 32, 32);
		walkDown[3] = ss.grabSprite(0, 0, 32, 32);
		
		walkUp[0] = ss.grabSprite(65, 32, 32, 32);
		walkUp[1] = ss.grabSprite(0, 32, 32, 32);
		walkUp[2] = ss.grabSprite(98, 32, 32, 32);
		walkUp[3] = ss.grabSprite(0, 32, 32, 32);
		
		walkRight[0] = ss.grabSprite(65, 64, 32, 32);
		walkRight[1] = ss.grabSprite(0, 64, 32, 32);
		walkRight[2] = ss.grabSprite(98, 64, 32, 32);
		walkRight[3] = ss.grabSprite(0, 64, 32, 32);
		
		walkLeft[0] = ss.grabSprite(65, 96, 32, 32);
		walkLeft[1] = ss.grabSprite(0, 96, 32, 32);
		walkLeft[2] = ss.grabSprite(98, 96, 32, 32);
		walkLeft[3] = ss.grabSprite(0, 96, 32, 32);
		
		this.currentAnimation = IDLE_DOWN;
		this.setAnimation(idleDown, 20);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		//System.out.println("Player pos: X: " + getX() + "        Y: " + getY());
		
		ani.tick();
		this.setSpr(ani.getImage());
		
		collision();
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g.drawRect(x, y, 32, 48);
		
		g.drawImage(sprite, x - 16, y - 10, 64, 64, null);
	}
	
	public void collision() {
	for(int i = 0; i < handler.object.size(); i++) {
		GameObject tempObject = handler.object.get(i);
		
			if(tempObject.getID() == ID.Block) {
				//System.out.println("Running Block Collision");
				
				Block b = (Block) tempObject;
				
				if(b.getBounds().intersects(getBounds())) {
					Area objArea = new Area(b.getBounds());
					objArea.intersect(new Area(getBounds()));
				
					Rectangle overlap = objArea.getBounds();
				
					double theta = b.getTheta();
					double phi = theta + Math.toRadians(90);
					double pushX = 0;
					double pushY = 0;
				
					//System.out.println("theta: " + theta + "        phi: " + phi);
					//System.out.println("cos(phi): " + Math.cos(phi) + "        sin(phi): " + Math.sin(phi));
				
					Area playerArea = new Area(getBounds());
					Area topArea = new Area(b.getBoundsTop());
					Area bottomArea = new Area(b.getBoundsBottom());
					Area rightArea = new Area(b.getBoundsRight());
					Area leftArea = new Area(b.getBoundsLeft());
				
					topArea.intersect(playerArea);
					bottomArea.intersect(playerArea);
					rightArea.intersect(playerArea);
					leftArea.intersect(playerArea);
				
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
	}
	}
	
	public void switchAnimation(int animation) {
		if(this.currentAnimation == animation) return;
		
		switch(animation) {
		case IDLE_DOWN: this.setAnimation(idleDown, 20);
						this.currentAnimation = animation;
						break;
		case IDLE_UP: this.setAnimation(idleUp, 20);
						this.currentAnimation = animation;
						break;
		case IDLE_RIGHT: this.setAnimation(idleRight, 20);
						this.currentAnimation = animation;
						break;
		case IDLE_LEFT: this.setAnimation(idleLeft, 20);
						this.currentAnimation = animation;
						break;
		case WALK_DOWN: this.setAnimation(walkDown, 10);
						this.currentAnimation = animation;
						break;
		case WALK_UP: this.setAnimation(walkUp, 10);
					  this.currentAnimation = animation;
					  break;
		case WALK_RIGHT: this.setAnimation(walkRight, 10);
						this.currentAnimation = animation;
						break;
		case WALK_LEFT: this.setAnimation(walkLeft, 10);
						this.currentAnimation = animation;
						break;
		
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 48);
	}

}

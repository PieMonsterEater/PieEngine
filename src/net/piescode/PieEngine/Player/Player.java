package net.piescode.PieEngine.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import net.piescode.PieEngine.BuildingBlocks.Block;
import net.piescode.PieEngine.BuildingBlocks.Ellipse;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;

public class Player extends GameObject {
	
	public static final int IDLE_DOWN = 0, IDLE_UP = 1, IDLE_RIGHT = 2, IDLE_LEFT = 3, WALK_DOWN = 5, WALK_UP = 6, WALK_RIGHT = 7, WALK_LEFT = 8;
	
	private Handler handler;
	
	private double testTheta = 0;
	private double testHyp = 0;
	private int eCenterX = 0;
	private int eCenterY = 0;
	
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
		this.dontDestroyOnLoad = true;
		
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
		
		g.drawLine(0, 0, (int) (testHyp*Math.cos(testTheta)), (int) (testHyp*Math.sin(testTheta)));
		
		g.setColor(Color.RED);
		g.drawRect((int) (testHyp*Math.cos(testTheta)), (int) (testHyp*Math.sin(testTheta)), 0, 0);
	}
	
	public void collision() {
	for(int i = 0; i < handler.object.size(); i++) {
		GameObject tempObject = handler.object.get(i);
		
			collideWithEnvironment(tempObject);
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

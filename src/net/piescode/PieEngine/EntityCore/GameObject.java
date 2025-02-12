package net.piescode.PieEngine.EntityCore;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
	
}

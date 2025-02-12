package net.piescode.PieEngine.Menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import net.piescode.PieEngine.Core.Game;

public abstract class Button {
	
	protected int x, y, width, height;
	protected Rectangle rect;
	protected String text;
	protected Boolean mouseActivated = false;
	protected Game game;
	
	private int mx = 0, my = 0;

	public Button(Game game, int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect = new Rectangle(x, y, width, height);
		this.text = text;
		this.game = game;
	}
	
	public abstract void action();
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Rectangle mRect = new Rectangle(mx, my, 1, 1);
		
		g.setColor(Color.WHITE);
		g.drawString(text, x, y + height);
		
		if(mRect.intersects(rect)) {
			g.setColor(new Color(255, 255, 255, 150));
			g2d.fill(rect);;
		}
	}
	
	public void setRectangle(Rectangle rect) {
		this.rect = rect;
	}
	
	public Rectangle getRectangle() {
		return rect;
	}
	
	public void setMouseX(int mx) {
		this.mx = mx;
	}
	
	public void setMouseY(int my) {
		this.my = my;
	}
	
}

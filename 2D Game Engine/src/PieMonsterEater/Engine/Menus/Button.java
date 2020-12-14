package PieMonsterEater.Engine.Menus;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button {
	
	int x, y;
	Rectangle rect;
	Menu menu;
	String text;
	
	boolean toGame = false;
	boolean isImage = false;
	
	BufferedImage image;

	public Button(int x, int y, int width, int height, String text, Menu menu) {
		this.x = x;
		this.y = y;
		this.rect = new Rectangle(x, y, width, height);
		this.menu = menu;
		toGame = false;
	}
	
	public Button(int x, int y, int width, int height, String text, boolean game) {
		this.x = x;
		this.y = y;
		this.rect = new Rectangle(x, y, width, height);
		this.menu = menu;
		toGame = game;
	}
	
	public Button(int x, int y, int width, int height, Menu menu, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.rect = rect;
		this.menu = menu;
		this.image = image;
		this.isImage = true;
		toGame = false;
	}
	
	public Button(int x, int y, int width, int height,  boolean game, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.rect = new Rectangle(x, y, width, height);
		this.menu = menu;
		this.image = image;
		this.isImage = true;
		toGame = game;
	}
	
	public boolean getToGame() {
		return toGame;
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

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isImage() {
		return isImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
	
}

package net.piescode.PieEngine.Menus;

import java.awt.Font;

public class Label {

	int x, y;
	Font font;
	String text;
	
	public Label(int x, int y, Font font, String text) {
		this.x = x;
		this.y = y;
		this.font = font;
		this.text = text;
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

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
}

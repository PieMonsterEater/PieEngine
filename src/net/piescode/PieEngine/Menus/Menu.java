package net.piescode.PieEngine.Menus;

import java.awt.Graphics;
import java.util.ArrayList;

import net.piescode.PieEngine.Core.Game;

public abstract class Menu {
	
	public boolean mouseActivated = false;
	
	private int mx = 0, my = 0;

	public abstract void tick();
	public abstract void render(Graphics g);
	
	protected ArrayList<Button> buttons = new ArrayList<Button>();
	protected Game game;
	
	public Menu(Game game) {
		this.game = game;
	}
	
	public void setMouseActive(boolean active) {
		mouseActivated = active;
	}
	
	public boolean getMouseActive() {
		return mouseActivated;
	}
	
	public int getMouseX() {
		return mx;
	}
	
	public int getMouseY() {
		return my;
	}
	
	public void setMouseX(int mx) {
		this.mx = mx;
	}
	
	public void setMouseY(int my) {
		this.my = my;
	}
}

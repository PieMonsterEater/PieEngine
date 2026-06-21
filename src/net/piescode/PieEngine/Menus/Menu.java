package net.piescode.PieEngine.Menus;

import java.awt.Graphics;
import java.util.ArrayList;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.InputSystem.InputEvent;
import net.piescode.PieEngine.InputSystem.InputListener;

public abstract class Menu implements InputListener {
	public boolean isActive = false;
	
	private int mx = 0, my = 0;

	public abstract void tick();
	public abstract void render(Graphics g);
	
	protected ArrayList<Button> buttons = new ArrayList<Button>();
	protected Game game;
	
	public Menu(Game game) {
		this.game = game;
		
		Game.listeners.add(this);
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
	
	public void onMouseMoved(int x, int y) {
		setMouseX(x);
		setMouseY(y);
	}
	
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public boolean getActive() {
		return isActive;
	}
}

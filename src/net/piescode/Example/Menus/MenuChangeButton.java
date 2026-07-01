package net.piescode.Example.Menus;

import java.awt.event.MouseEvent;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.Menus.Button;
import net.piescode.PieEngine.Menus.Menu;

public class MenuChangeButton extends Button {
	
	private Menu switchMenu;

	public MenuChangeButton(Game game, int x, int y, int width, int height, String text, Menu switchMenu) {
		super(game, x, y, width, height, text);
		this.switchMenu = switchMenu;
	}

	
	public void action() {
		game.setMenu(switchMenu);
	}
	
	public void setMenu(Menu menu) {
		switchMenu = menu;
	}
	
	public Menu getMenu() {
		return switchMenu;
	}

}

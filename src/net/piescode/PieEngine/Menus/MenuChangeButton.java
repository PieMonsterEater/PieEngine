package net.piescode.PieEngine.Menus;

import java.awt.event.MouseEvent;

import net.piescode.PieEngine.Core.Game;

public class MenuChangeButton extends Button {
	
	private Menu switchMenu;

	public MenuChangeButton(Game game, int x, int y, int width, int height, String text, Menu switchMenu) {
		super(game, x, y, width, height, text);
		this.switchMenu = switchMenu;
	}

	
	public void action() {
		Game.lastMenu = Game.currentMenu;
		Game.currentMenu = switchMenu;
		
	}
	
	public void setMenu(Menu menu) {
		switchMenu = menu;
	}
	
	public Menu getMenu() {
		return switchMenu;
	}

}

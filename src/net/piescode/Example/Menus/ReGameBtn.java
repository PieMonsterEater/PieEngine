package net.piescode.Example.Menus;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.Menus.Button;
import net.piescode.PieEngine.Menus.StateID;

public class ReGameBtn extends Button {

	public ReGameBtn(Game game, int x, int y, int width, int height, String text) {
		super(game, x, y, width, height, text);
		// TODO Auto-generated constructor stub
	}

	
	public void action() {
		game.state = StateID.Play;
		game.lastMenu = game.pMenu;
		
	}

}

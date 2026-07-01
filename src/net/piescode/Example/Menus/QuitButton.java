package net.piescode.Example.Menus;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.Menus.Button;

public class QuitButton extends Button {

	public QuitButton(Game game, int x, int y, int width, int height, String text) {
		super(game, x, y, width, height, text);
		// TODO Auto-generated constructor stub
	}

	public void action() {
		System.exit(1);
	}

}

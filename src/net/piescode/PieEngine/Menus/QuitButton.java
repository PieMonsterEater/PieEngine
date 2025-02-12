package net.piescode.PieEngine.Menus;

import net.piescode.PieEngine.Core.Game;

public class QuitButton extends Button {

	public QuitButton(Game game, int x, int y, int width, int height, String text) {
		super(game, x, y, width, height, text);
		// TODO Auto-generated constructor stub
	}

	public void action() {
		System.exit(1);
	}

}

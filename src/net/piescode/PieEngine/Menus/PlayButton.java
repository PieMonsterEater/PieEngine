package net.piescode.PieEngine.Menus;

import net.piescode.PieEngine.Core.Game;

public class PlayButton extends Button {

	public PlayButton(Game game, int x, int y, int width, int height, String text) {
		super(game, x, y, width, height, text);
		
	}

	public void action() {
		game.initGame();
	}
	
	

}

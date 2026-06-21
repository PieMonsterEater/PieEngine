package net.piescode.PieEngine.Core;

import net.piescode.PieEngine.InputSystem.InputEvent;
import net.piescode.PieEngine.InputSystem.InputListener;
import net.piescode.PieEngine.Menus.StateID;

public class CentralGameInputs implements InputListener {
	
	private Game game;
	
	public CentralGameInputs(Game game) {
		this.game = game;
		Game.addInputListener(this);
	}
	
	public void onKeyPressed(InputEvent ie) {
		if(ie.getInputName() == "MENU_BACK") {
			if(Game.state == StateID.Play) Game.state = StateID.PlayMenu;
			game.backMenu();
		}
		
		if(ie.getInputName() == "SHOW_DEBUG") Game.showDebug = !Game.showDebug;
	}
}

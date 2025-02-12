package net.piescode.PieEngine.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import net.piescode.PieEngine.Core.Game;

public class MouseMoveInput implements MouseMotionListener {
	
	private Game game;

	public MouseMoveInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		game.currentMenu.setMouseX(e.getX());
		game.currentMenu.setMouseY(e.getY());
		
	}

}

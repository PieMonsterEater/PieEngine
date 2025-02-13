package net.piescode.PieEngine.Player;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.Menus.*;

public class MouseInput implements MouseListener {
	public Random r;
	private Game game;
	
	public MouseInput(Handler handler, Game game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/*
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY(); 
		
		if(Game.state == StateID.MainMenu) {
			if(mx >= Game.WIDTH/3 + 35 && mx <= Game.WIDTH/3 + 135) {
				if(my >= 150 && my <= 200) {
					Game.state = StateID.Play;
					game.initGame();
					}
			}
			if(mx >= Game.WIDTH/3 + 35 && mx <= Game.WIDTH/3 + 135) {
				if(my >= 250 && my <= 300) Game.state = StateID.InfoMenu;
			}
			if(mx >= Game.WIDTH/3 + 35 && mx <= Game.WIDTH/3 + 135) {
				if(my >= 350 && my <= 400) System.exit(1);
			}
		}
		
	}
	*/
	
	public void mousePressed(MouseEvent e) {
		//int mx = e.getX();
		//int my = e.getY();
		
		//Rectangle mRect = new Rectangle(mx, my, 1, 1);
		
		game.currentMenu.setMouseActive(true);
	}

	public void mouseReleased(MouseEvent arg0) {
		game.currentMenu.setMouseActive(false);
		
	}

}
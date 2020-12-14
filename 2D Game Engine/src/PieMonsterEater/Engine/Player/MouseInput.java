package PieMonsterEater.Engine.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import PieMonsterEater.Engine.Core.Game;
import PieMonsterEater.Engine.Menus.*;

public class MouseInput implements MouseListener {
	public Random r;

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

	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY(); 
		
		if(Game.state == StateID.MainMenu) {
			if(mx >= Game.WIDTH/3 + 35 && mx <= Game.WIDTH/3 + 135) {
				if(my >= 150 && my <= 200) Game.state = StateID.Play;
			}
			if(mx >= Game.WIDTH/3 + 35 && mx <= Game.WIDTH/3 + 135) {
				if(my >= 250 && my <= 300) Game.state = StateID.InfoMenu;
			}
			if(mx >= Game.WIDTH/3 + 35 && mx <= Game.WIDTH/3 + 135) {
				if(my >= 350 && my <= 400) System.exit(1);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
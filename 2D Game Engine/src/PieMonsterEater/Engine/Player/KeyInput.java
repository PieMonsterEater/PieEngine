package PieMonsterEater.Engine.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import PieMonsterEater.Engine.Core.Game;
import PieMonsterEater.Engine.EntityCore.GameObject;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;
import PieMonsterEater.Engine.Menus.StateID;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private Player player;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
		if(tempObject.getID() == ID.Player) {
			Player p = (Player) tempObject;
			
			if(key == KeyEvent.VK_W) p.setVelY(-5);
			if(key == KeyEvent.VK_S) p.setVelY(5);
			if(key == KeyEvent.VK_A) {
				p.setVelX(-5);
			}
			if(key == KeyEvent.VK_D) p.setVelX(5);
			if(key == KeyEvent.VK_SPACE) p.playSound("res/Shoot.wav");
			if(key == KeyEvent.VK_L) {
				p.setX(100);
				p.setY(100);
			}
			if(key == KeyEvent.VK_C) Game.ll.clear();
		}
	}
		
		if(key == KeyEvent.VK_ESCAPE) Game.state = StateID.MainMenu;
}
	
	public void keyReleased(KeyEvent e) {
int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
		
			
		if(tempObject.getID() == ID.Player) {
			Player p = (Player) tempObject;
			
			if(key == KeyEvent.VK_W) p.setVelY(0);
			if(key == KeyEvent.VK_S) p.setVelY(0);
			if(key == KeyEvent.VK_D) p.setVelX(0);
			if(key == KeyEvent.VK_A) {
				p.setVelX(0);
			}
		}
	}
		
		
	}
	
	
}

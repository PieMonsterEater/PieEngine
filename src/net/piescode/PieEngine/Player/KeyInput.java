package net.piescode.PieEngine.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.Menus.StateID;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private Game game;
	
	//0 = UP, 1 = DOWN, 2 = RIGHT, 3 = LEFT
	private boolean[] movKeyHeld = new boolean[4];
	
	private final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		
		movKeyHeld[UP] = false;
		movKeyHeld[DOWN] = false;
		movKeyHeld[RIGHT] = false;
		movKeyHeld[LEFT] = false;
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
		if(tempObject.getID() == ID.Player) {
			Player p = (Player) tempObject;
			
			if(key == KeyEvent.VK_W) {p.setVelY(-5); movKeyHeld[UP] = true; p.switchAnimation(Player.WALK_UP);}
			if(key == KeyEvent.VK_S) {p.setVelY(5); movKeyHeld[DOWN] = true; p.switchAnimation(Player.WALK_DOWN);}
			if(key == KeyEvent.VK_A) {p.setVelX(-5); movKeyHeld[LEFT] = true; p.switchAnimation(Player.WALK_LEFT);}
			if(key == KeyEvent.VK_D) {p.setVelX(5); movKeyHeld[RIGHT] = true; p.switchAnimation(Player.WALK_RIGHT);}
			if(key == KeyEvent.VK_SPACE) p.playSound("res/Shoot.wav");
			if(key == KeyEvent.VK_L) {
				p.setX(100);
				p.setY(100);
			}
			if(key == KeyEvent.VK_C) Game.ll.clear();
		}
	}
		
		if(key == KeyEvent.VK_ESCAPE) {
			if(Game.state == StateID.Play) Game.state = StateID.PlayMenu;
			game.setMenu(game.lastMenu);
		}
}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
		
			
		if(tempObject.getID() == ID.Player) {
			Player p = (Player) tempObject;
			
			if(key == KeyEvent.VK_W) movKeyHeld[UP] = false;
			if(key == KeyEvent.VK_S) movKeyHeld[DOWN] = false;
			if(key == KeyEvent.VK_D) movKeyHeld[RIGHT] = false;
			if(key == KeyEvent.VK_A) movKeyHeld[LEFT] = false;
			
			if(!movKeyHeld[UP] && !movKeyHeld[DOWN]) {
				int prevVelY = p.getVelY();
				
				p.setVelY(0);
				
				if(p.getVelX() == 0) {
					if(prevVelY < 0) p.switchAnimation(Player.IDLE_UP);
					else if(prevVelY > 0) p.switchAnimation(Player.IDLE_DOWN);
				}
				else if(p.getVelX() < 0) p.switchAnimation(Player.WALK_LEFT);
				else if(p.getVelX() > 0) p.switchAnimation(Player.WALK_RIGHT);
			}
			if(!movKeyHeld[RIGHT] && !movKeyHeld[LEFT]) {
				int prevVelX = p.getVelX();
				
				p.setVelX(0);
				
				if(p.getVelY() == 0) {
					if(prevVelX > 0) p.switchAnimation(Player.IDLE_RIGHT);
					else if(prevVelX < 0) p.switchAnimation(Player.IDLE_LEFT);
				}
				else if(p.getVelY() < 0) p.switchAnimation(Player.WALK_UP);
				else if(p.getVelX() > 0) p.switchAnimation(Player.WALK_DOWN);
			}
		}
	}
		
		
	}
	
	
}

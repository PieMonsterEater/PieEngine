package net.piescode.Example.Player;

import java.awt.event.KeyEvent;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.InputSystem.InputEvent;
import net.piescode.PieEngine.InputSystem.InputListener;
import net.piescode.PieEngine.InputSystem.KeyInput;
import net.piescode.PieEngine.Menus.StateID;

public class PlayerInputHandler implements InputListener {
	
	// 0 = UP, 1 = DOWN, 2 = RIGHT, 3 = LEFT
	public boolean[] movKeyHeld = new boolean[4];
	public final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	
	private Game game;
	private Player p;
	
	public PlayerInputHandler(Player player) {
		this.game = game;
		this.p = player;
		
		movKeyHeld[UP] = false;
		movKeyHeld[DOWN] = false;
		movKeyHeld[RIGHT] = false;
		movKeyHeld[LEFT] = false;
		
		Game.keyInput.addKeyInput("WALK_UP", KeyEvent.VK_W);
		Game.keyInput.addKeyInput("WALK_DOWN", KeyEvent.VK_S);
		Game.keyInput.addKeyInput("WALK_LEFT", KeyEvent.VK_A);
		Game.keyInput.addKeyInput("WALK_RIGHT", KeyEvent.VK_D);
		Game.keyInput.addKeyInput("CLEAR_LEVEL", KeyEvent.VK_C);
		Game.keyInput.addKeyInput("PLAY_SOUND", KeyEvent.VK_L);
		Game.keyInput.addKeyInput("CHANGE_UP", KeyEvent.VK_P);
		Game.keyInput.addKeyInput("CHANGE_RIGHT", KeyEvent.VK_K);
		Game.keyInput.addKeyInput("RESET_KEYS", KeyInput.MOUSE5);
		Game.keyInput.addKeyInput("LIST_KEYS", KeyInput.MOUSE4);
		
		Game.addInputListener(this);
	}
	
	public void onKeyPressed(InputEvent ie) {
		if(this.p == null) return;
			
		if(ie.getInputName() == "WALK_UP") {p.setVelY(-5); movKeyHeld[UP] = true; p.switchAnimation(Player.WALK_UP);}
		if(ie.getInputName() == "WALK_DOWN") {p.setVelY(5); movKeyHeld[DOWN] = true; p.switchAnimation(Player.WALK_DOWN);}
		if(ie.getInputName() == "WALK_LEFT") {p.setVelX(-5); movKeyHeld[LEFT] = true; p.switchAnimation(Player.WALK_LEFT);}
		if(ie.getInputName() == "WALK_RIGHT") {p.setVelX(5); movKeyHeld[RIGHT] = true; p.switchAnimation(Player.WALK_RIGHT);}
		if(ie.getInputName() == "PLAY_SOUND") p.playSound("res/sounds/Shoot.wav");
		
		if(ie.getInputName() == "CLEAR_LEVEL") Game.ll.clear();
		if(ie.getInputName() == "CHANGE_UP") Game.keyInput.changeKeyInput("WALK_UP", KeyInput.LEFT_CLICK);
		if(ie.getInputName() == "CHANGE_RIGHT") Game.keyInput.changeKeyInput("WALK_RIGHT", KeyEvent.VK_H);
		if(ie.getInputName() == "RESET_KEYS") Game.keyInput.resetToDefaults();
		if(ie.getInputName() == "LIST_KEYS") {
			System.out.println("PlayerInputHandler: WALK_UP: " + Game.keyInput.getKeyByInputName("WALK_UP"));
			System.out.println("PlayerInputHandler: WALK_DOWN: " + Game.keyInput.getKeyByInputName("WALK_DOWN"));
			System.out.println("PlayerInputHandler: WALK_LEFT: " + Game.keyInput.getKeyByInputName("WALK_LEFT"));
			System.out.println("PlayerInputHandler: WALK_RIGHT: " + Game.keyInput.getKeyByInputName("WALK_RIGHT"));
			System.out.println("PlayerInputHandler: CLEAR_LEVEL: " + Game.keyInput.getKeyByInputName("CLEAR_LEVEL"));
			System.out.println("PlayerInputHandler: PLAY_SOUND: " + Game.keyInput.getKeyByInputName("PLAY_SOUND"));
			System.out.println("PlayerInputHandler: CHANGE_UP: " + Game.keyInput.getKeyByInputName("CHANGE_UP"));
			System.out.println("PlayerInputHandler: CHANGE_RIGHT: " + Game.keyInput.getKeyByInputName("CHANGE_RIGHT"));
			System.out.println("PlayerInputHandler: RESET_KEYS: " + Game.keyInput.getKeyByInputName("RESET_KEYS"));
			System.out.println("PlayerInputHandler: LIST_KEYS: " + Game.keyInput.getKeyByInputName("LIST_KEYS"));
		}
	}
	
	public void onKeyReleased(InputEvent ie) {
		if(this.p == null) return;
			
		if(ie.getInputName() == "WALK_UP") movKeyHeld[UP] = false;
		if(ie.getInputName() == "WALK_DOWN") movKeyHeld[DOWN] = false;
		if(ie.getInputName() == "WALK_LEFT") movKeyHeld[LEFT] = false;
		if(ie.getInputName() == "WALK_RIGHT") movKeyHeld[RIGHT] = false;
			
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
				else if(p.getVelY() > 0) p.switchAnimation(Player.WALK_DOWN);			
		}
			
			if(!movKeyHeld[UP] && movKeyHeld[DOWN]) {
				p.switchAnimation(Player.WALK_DOWN);
				p.setVelY(5);
			}
			if(movKeyHeld[UP] && !movKeyHeld[DOWN]) {
				p.switchAnimation(Player.WALK_UP);
				p.setVelY(-5);
			}
			
			if(!movKeyHeld[RIGHT] && movKeyHeld[LEFT]) {
				p.switchAnimation(Player.WALK_LEFT);
				p.setVelX(-5);
			}
			if(movKeyHeld[RIGHT] && !movKeyHeld[LEFT]) {
				p.switchAnimation(Player.WALK_RIGHT);
				p.setVelX(5);
			}
	}
}

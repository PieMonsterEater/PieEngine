package net.piescode.PieEngine.InputSystem;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.Menus.*;

public class MouseInput implements MouseListener, MouseMotionListener {
	public Random r;
	private Game game;
	
	private HashMap<String, Integer> nameToKey = new HashMap<String, Integer>();
	private HashMap<Integer, String> keyToName = new HashMap<Integer, String>();
	
	public MouseInput(Handler handler, Game game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int key = e.getButton();
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onMouseClicked(new InputEvent("", -1, 'A', key), e.getX(), e.getY());
		}
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
		int key = e.getButton();
		
		String inputName = keyToName.get(key);
		//System.out.println("Key Input Name: " + inputName);
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyPressed(new InputEvent(inputName, -1, 'A', key));
		}
	}

	public void mouseReleased(MouseEvent e) {
		int key = e.getButton();
		
		String inputName = keyToName.get(key);
		//System.out.println("Key Input Name: " + inputName);
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyReleased(new InputEvent(inputName, -1, 'A', key));
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onMouseMoved(e.getX(), e.getY());
		}
	}

	public void mouseMoved(MouseEvent e) {
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onMouseMoved(e.getX(), e.getY());
		}
	}

	public boolean addKeyInput(String inputName, int key) {
		if(keyToName.get(key) != null) return false;
		if(Game.keyInput.isKeyboardInput(inputName)) return false;
		
		nameToKey.put(inputName, key);
		keyToName.put(key, inputName);
		
		Game.keyInput.properties.setProperty(inputName + ".defaultm", String.valueOf(key));
		// Change inputs to previously saved ones
		if(Game.keyInput.properties.getProperty(inputName + "m") != null) 
			if(!changeKeyInput(inputName, Integer.parseInt(Game.keyInput.properties.getProperty(inputName + "m")))) 
				System.err.println("MouseInput: Could not load saved keybind for " + inputName);
		if(Game.keyInput.properties.getProperty(inputName + "k") != null) {
			return Game.mouseInput.changeKeyInput(inputName, Integer.parseInt(Game.keyInput.properties.getProperty(inputName + "k")));
		}
		return true;
	}
	
	public boolean changeKeyInput(String inputName, int key) {
		if(keyToName.get(key) != null) return false;
		if(nameToKey.get(inputName) == null && !Game.keyInput.isKeyboardInput(inputName)) return false;
		
		Game.keyInput.clearKeyInput(inputName);
		
		keyToName.remove(nameToKey.get(inputName));
		nameToKey.put(inputName, key);
		keyToName.put(key, inputName);
		
		Game.keyInput.properties.setProperty(inputName + "m", String.valueOf(key));
		
		return true;
	}
	
	public void clearKeyInput(String inputName) {
		keyToName.remove(nameToKey.get(inputName));
		nameToKey.remove(inputName);
		Game.keyInput.properties.remove(inputName + "m");
	}
	
	public boolean isMouseInput(String inputName) {
		return nameToKey.get(inputName) != null;
	}
}
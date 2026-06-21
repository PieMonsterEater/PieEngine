package net.piescode.PieEngine.InputSystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import net.piescode.Example.Player.Player;
import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.Menus.StateID;

public class KeyInput extends KeyAdapter implements MouseListener, MouseMotionListener {
	
	private Handler handler;
	private Game game;
	
	private Properties properties;
	
	private HashMap<String, Integer> nameToKey = new HashMap<String, Integer>();
	private HashMap<Integer, String> keyToName = new HashMap<Integer, String>();
	
	public static final int LEFT_CLICK = 5001;
	public static final int MIDDLE_CLICK = 5002;
	public static final int RIGHT_CLICK = 5003;
	public static final int MOUSE4 = 5006;
	public static final int MOUSE5 = 5007;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		
		// Initiate loading/saving controls
		properties = new Properties();
		try {
			FileInputStream in = new FileInputStream("res/controls/controls.properties");
			properties.load(in);
			
		} catch (IOException e) {
			System.err.println("KeyInput: There was an error opening your controls.properties file. Ensure one exists at \"res/controls/controls.properties\"");
			System.exit(0);
		}
		
		// Ensure that keybinds are saved before closing
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("KeyInput: Saving keybinds on exit");
			saveKeyBinds();
		}));
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		char keyChar = e.getKeyChar();
		
		String inputName = keyToName.get(key);
		//System.out.println("Key Input Name: " + inputName);
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyPressed(new InputEvent(inputName, key, keyChar, -1));
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		char keyChar = e.getKeyChar();
		
		String inputName = keyToName.get(key);
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyReleased(new InputEvent(inputName, key, keyChar, -1));
		}
	}
	
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		char keyChar = e.getKeyChar();
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyTyped(new InputEvent("", key, keyChar, -1));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int key = e.getButton() + 5000;
		
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
		int key = e.getButton() + 5000;
		
		String inputName = keyToName.get(key);
		//System.out.println("Key Input Name: " + inputName);
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyPressed(new InputEvent(inputName, -1, 'A', key));
		}
	}

	public void mouseReleased(MouseEvent e) {
		int key = e.getButton() + 5000;
		
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
	
	// Adding a new key input
	public boolean addKeyInput(String inputName, int key) {
		if(keyToName.get(key) != null) return false;
		if(nameToKey.get(inputName) != null) {return false;}
		
		nameToKey.put(inputName, key);
		keyToName.put(key, inputName);
		
		properties.setProperty(inputName + ".default", String.valueOf(key));
		// Change inputs to previously saved ones
		if(properties.getProperty(inputName) != null) if(!changeKeyInput(inputName, Integer.parseInt(properties.getProperty(inputName)))) 
			System.err.println("KeyInput: Could not load saved keybind for " + inputName);
		
		return true;
	}
	
	// Change an existing input
	public boolean changeKeyInput(String inputName, int key) {
		// If changing the keybind to something it is already assigned to, just report successful
		// This is to prevent false negatives on things like resetToDefaults()
		if(nameToKey.get(inputName) != null && nameToKey.get(inputName) == key) return true;
		// If the new key is already assigned, stop
		if(keyToName.get(key) != null) { System.err.println("KeyInput: Change key new key already bound"); return false;} 
		// Is input name valid
		if(nameToKey.get(inputName) == null) { System.err.println("KeyInput: Change key input name does not exist " + inputName); return false;}
		
		keyToName.remove(nameToKey.get(inputName));
		nameToKey.put(inputName, key);
		keyToName.put(key, inputName);
		
		properties.setProperty(inputName, String.valueOf(key));
		
		return true;
	}
	
	public void clearKeyInput(String inputName) {
		keyToName.remove(nameToKey.get(inputName));
		nameToKey.remove(inputName);
	}
	
	public void saveKeyBinds() {
		try {
			FileOutputStream out = new FileOutputStream("res/controls/controls.properties");
			properties.store(out, "Updated keybind saves");
		} catch (IOException e) {
			System.err.println("KeyInput: There was an error opening your controls.properties file. Ensure one exists at \"res/controls/controls.properties\"");
			System.exit(0);
		}
	}
	
	public boolean resetToDefaults() {
		System.out.println("KeyInput: Attempting to reset keybinds to default");
		boolean successfullyChanged = true;
		ArrayList<String> inputNames = new ArrayList<String>(nameToKey.keySet());
		
		for(int i = 0; i < inputNames.size(); i++) {
			if(!changeKeyInput(inputNames.get(i), Integer.parseInt(properties.getProperty(inputNames.get(i) + ".default")))) 
				successfullyChanged = false;
		}
		
		if(!successfullyChanged) System.err.println("Failed to reset key inputs");
		return successfullyChanged;
	}
	
	public boolean isKeyboardInput(String inputName) {
		return nameToKey.get(inputName) != null;
	}
}

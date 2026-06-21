package net.piescode.PieEngine.InputSystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private Game game;
	
	public static Properties properties;
	
	private HashMap<String, Integer> nameToKey = new HashMap<String, Integer>();
	private HashMap<Integer, String> keyToName = new HashMap<Integer, String>();
	
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
			Game.listeners.get(i).onKeyPressed(new InputEvent(inputName, key, keyChar, 0));
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		char keyChar = e.getKeyChar();
		
		String inputName = keyToName.get(key);
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyReleased(new InputEvent(inputName, key, keyChar, 0));
		}
	}
	
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		char keyChar = e.getKeyChar();
		
		for(int i = 0; i < Game.listeners.size(); i++) {
			Game.listeners.get(i).onKeyTyped(new InputEvent("", key, keyChar, 0));
		}
	}
	
	public boolean addKeyInput(String inputName, int key) {
		if(keyToName.get(key) != null) return false;
		if(Game.mouseInput.isMouseInput(inputName)) return false;
		
		nameToKey.put(inputName, key);
		keyToName.put(key, inputName);
		
		properties.setProperty(inputName + ".defaultk", String.valueOf(key));
		// Change inputs to previously saved ones
		if(properties.getProperty(inputName + "k") != null) if(!changeKeyInput(inputName, Integer.parseInt(properties.getProperty(inputName + "k")))) 
			System.err.println("KeyInput: Could not load saved keybind for " + inputName);
		if(properties.getProperty(inputName + "m") != null) {
			return Game.mouseInput.changeKeyInput(inputName, Integer.parseInt(properties.getProperty(inputName + "m")));
		}
		
		return true;
	}
	
	public boolean changeKeyInput(String inputName, int key) {
		if(keyToName.get(key) != null) return false;
		if(nameToKey.get(inputName) == null && !Game.mouseInput.isMouseInput(inputName)) return false;
		
		Game.mouseInput.clearKeyInput(inputName);
		
		keyToName.remove(nameToKey.get(inputName));
		nameToKey.put(inputName, key);
		keyToName.put(key, inputName);
		
		properties.setProperty(inputName + "k", String.valueOf(key));
		
		return true;
	}
	
	public void clearKeyInput(String inputName) {
		keyToName.remove(nameToKey.get(inputName));
		nameToKey.remove(inputName);
		properties.remove(inputName + "k");
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
	
	public boolean isKeyboardInput(String inputName) {
		return nameToKey.get(inputName) != null;
	}
}

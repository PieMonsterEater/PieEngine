package net.piescode.PieEngine.EntityCore;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.Player.Player;


public class Handler {
	private ArrayList<GameObject> tickObjects = new ArrayList<GameObject>();
	private ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();
	
	public void tick()  {
		for (int i = 0; i < tickObjects.size(); i++) {
			GameObject tempObject = tickObjects.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < renderObjects.size(); i++) {
			GameObject tempObject = renderObjects.get(i);
	
			tempObject.render(g);
		}
	}
	
	public void addObj(GameObject object) {
		this.tickObjects.add(object);
		addRenderObj(object);
		
		if(object instanceof Player) {
			Game.keyInput.setPlayer((Player) object);
		}
	}
	
	public void removeObj(GameObject object) {
		object.destroyChildObjects();
		this.tickObjects.remove(object);
		removeRenderObj(object);
	}
	
	public void removeObj(int index) {
		this.tickObjects.get(index).destroyChildObjects();
		removeRenderObj(this.tickObjects.get(index));
		this.tickObjects.remove(index);
	}
	
	public GameObject getObj(int index) {
		if(index > this.tickObjects.size() - 1) return null;
		
		return this.tickObjects.get(index);
	}
	
	public int getSize() {
		return this.tickObjects.size();
	}
	
	// Performs a binary search to find where to insert an object in the renderer
	// such that it renders on the proper layer
	public void addRenderObj(GameObject object) {
		int low = 0;
		int high = renderObjects.size() - 1;
		
		while(low <= high) {
			int mid = low + (high - low)/2;
			
			if(renderObjects.get(mid).getRenderingLayer().ordinal() == object.getRenderingLayer().ordinal()) {
				renderObjects.add(mid + 1, object);
				return;
			}
			else if(renderObjects.get(mid).getRenderingLayer().ordinal() < object.getRenderingLayer().ordinal()) {
				low = mid + 1;
			}
			else high = mid - 1;
		}
		
		// There is no object on the same rendering layer in the list yet
		this.renderObjects.add(low, object);
	}
	
	public void removeRenderObj(GameObject object) {
		this.renderObjects.remove(object);
	}
}

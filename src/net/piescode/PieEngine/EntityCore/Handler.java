package net.piescode.PieEngine.EntityCore;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;



public class Handler {
	public ArrayList<GameObject> object = new ArrayList<GameObject>();
	
	public void tick()  {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
	
			tempObject.render(g);
	
		}

	}
	
	public void addObj(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObj(GameObject object) {
		this.object.remove(object);
	}
}

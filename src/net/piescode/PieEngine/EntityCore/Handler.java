package net.piescode.PieEngine.EntityCore;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.IdentityHashMap;

import net.piescode.PieEngine.Utils.Pair;
import net.piescode.PieEngine.Visuals.RenderingLayer;


public class Handler {
	private IdentityHashMap<GameObject, Pair<Integer, Integer>> indexHash = new IdentityHashMap<GameObject, Pair<Integer, Integer>>();
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<ArrayList<GameObject>> renderObjects = createRenderingArray();
	private ArrayList<GameObject> pendingRemoval = new ArrayList<GameObject>();
	
	public void tick()  {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			
			if(tempObject.isTickable && !tempObject.markedForDeath) tempObject.tick();
		}
		
		// Kill objects
		if(!pendingRemoval.isEmpty()) clearPendingRemovals();
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < renderObjects.size(); i++) {
			ArrayList<GameObject> renderLayer = renderObjects.get(i);
			for(int j = 0; j < renderLayer.size(); j++) {
				GameObject tempObject = renderLayer.get(j);
				
				if(tempObject.isRenderable) tempObject.render(g);
			}
		}
	}
	
	public void addObj(GameObject object) {
		int renderingOrdinal = object.renderinglayer.ordinal();
		indexHash.put(object, new Pair<Integer, Integer>(this.objects.size(), this.renderObjects.get(renderingOrdinal).size()));
		this.objects.add(object);
		this.renderObjects.get(renderingOrdinal).add(object);
	}
	
	public void removeObj(GameObject object) {
		object.destroyChildObjects();
		object.markedForDeath = true;
		this.pendingRemoval.add(object);
	}
	
	public void removeObj(int index) {
		removeObj(this.objects.get(index));
	}
	
	public GameObject getObj(int index) {
		if(index > this.objects.size() - 1) return null;
		
		return this.objects.get(index);
	}
	
	public int getSize() {
		return this.objects.size();
	}
	
	// Sets up the rendering array to have exactly as many buckets as there are rendering layers
	public ArrayList<ArrayList<GameObject>> createRenderingArray() {
		ArrayList<ArrayList<GameObject>> tempArray = new ArrayList<ArrayList<GameObject>>();
		
		for(RenderingLayer layer : RenderingLayer.values()) {
			tempArray.add(new ArrayList<GameObject>());
		}
		
		return tempArray;
	}
	
	private void clearPendingRemovals() {
		System.out.println("Pending Removal: " + pendingRemoval.size());
		
		 // Ensures that the last object doesn't simply swap with itself

		if(this.objects.size() == 1) {
			GameObject toRemove = pendingRemoval.removeLast();
			this.objects.remove(toRemove);
			this.renderObjects.get(toRemove.renderinglayer.ordinal()).remove(toRemove);
			this.indexHash.remove(toRemove);
			return;
		} 
		
		for(int i = pendingRemoval.size() - 1; i >= 0; i--) {
			GameObject tempObject = pendingRemoval.get(i);
			Pair<Integer, Integer> removeIndicies = indexHash.get(tempObject);
			
			// This prevents an error if an object was added to the pending removal list twice
			// Or prevents us trying to remove an object that was never added in the first place
			if(removeIndicies == null) {pendingRemoval.removeLast(); continue;} 
			
			// Remove from objects
			GameObject lastObject = this.objects.removeLast();
			if(tempObject != lastObject) {
				Pair<Integer, Integer> lastObjectIndicies = indexHash.get(lastObject);
				this.objects.set(removeIndicies.getX(), lastObject);
				indexHash.put(lastObject, new Pair<Integer, Integer>(removeIndicies.getX(), lastObjectIndicies.getY()));
			}
			
			
			// Remove from the renderer
			ArrayList<GameObject> renderLayer = this.renderObjects.get(tempObject.renderinglayer.ordinal());
			lastObject = renderLayer.removeLast();
			if(tempObject != lastObject) {
				Pair<Integer, Integer> lastObjectIndicies = indexHash.get(lastObject);
				renderLayer.set(removeIndicies.getY(), lastObject);
				indexHash.put(lastObject, new Pair<Integer, Integer>(lastObjectIndicies.getX(), removeIndicies.getY()));
			}
			
			// Remove from helper lists
			indexHash.remove(pendingRemoval.removeLast());
		}
	}
}

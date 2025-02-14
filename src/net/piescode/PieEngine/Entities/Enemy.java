package net.piescode.PieEngine.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

import net.piescode.PieEngine.BuildingBlocks.Block;
import net.piescode.PieEngine.EntityCore.GameObject;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;

public class Enemy extends GameObject {
	
	private Handler handler;
	private boolean touching = false;

	public Enemy(int x, int y, Handler handler) {
		super(x, y, handler);
		this.handler = handler;
		this.setID(ID.Enemy);
	}

	public void tick() {
		y += velY;
		
		if(touching == false) velY = 2;
		if(touching == true) velY = -2;
		collision();
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 16, 16);
	}

	public void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			
			
				if(tempObject.getID() == ID.Block) {
					
					Block b = (Block) tempObject;
					
					if(b.getBounds().intersects(getBounds())) {
						Area objArea = new Area(tempObject.getBounds());
						objArea.intersect(new Area(getBounds()));
					
						touching = !touching;
					
						//System.out.println("Running Block Collision");
						Rectangle overlap = objArea.getBounds();
					
						double theta = b.getTheta();
						double phi = theta + Math.toRadians(90);
						double pushX = 0;
						double pushY = 0;
					
						//System.out.println("theta: " + theta + "        phi: " + phi);
						//System.out.println("cos(phi): " + Math.cos(phi) + "        sin(phi): " + Math.sin(phi));
					
						Area enemyArea = new Area(getBounds());
						Area topArea = new Area(b.getBoundsTop());
						Area bottomArea = new Area(b.getBoundsBottom());
						Area rightArea = new Area(b.getBoundsRight());
						Area leftArea = new Area(b.getBoundsLeft());
					
						topArea.intersect(enemyArea);
						bottomArea.intersect(enemyArea);
						rightArea.intersect(enemyArea);
						leftArea.intersect(enemyArea);
					
						int topAreaArea = topArea.getBounds().width*topArea.getBounds().height;
						int bottomAreaArea = bottomArea.getBounds().width*bottomArea.getBounds().height;
						int rightAreaArea = rightArea.getBounds().width*rightArea.getBounds().height;
						int leftAreaArea = leftArea.getBounds().width*leftArea.getBounds().height;
					
						//System.out.println("topArea: " + topAreaArea + " rightArea: " + rightAreaArea + " leftArea: " + leftAreaArea + " bottomArea: " + bottomAreaArea);
					
						if(!topArea.isEmpty() & topAreaArea > rightAreaArea & topAreaArea > leftAreaArea) {
							double movXFlag = 0;
							double movYFlag = 0;
						
							//System.out.println((Math.cos(phi + Math.toRadians(180)) < 0.00001) || (Math.cos(phi + Math.toRadians(180)) > -0.00001));
							if(!(Math.cos(phi + Math.toRadians(180)) < 0.00001) || !(Math.cos(phi + Math.toRadians(180)) > -0.00001)) {
								movXFlag = overlap.width/Math.cos(phi + Math.toRadians(180));
							
							}
						
							//System.out.println((Math.sin(phi + Math.toRadians(180)) < 0.00001) || (Math.sin(phi + Math.toRadians(180)) > -0.00001));
							if(!(Math.sin(phi + Math.toRadians(180)) < 0.00001) || !(Math.sin(phi + Math.toRadians(180)) > -0.00001)) {
								movYFlag = overlap.height/Math.sin(phi + Math.toRadians(180));
							}
						
							//System.out.println("movXFlag: " + movXFlag + "        movYFlag: " + movYFlag);
						
							if(movXFlag < 0) movXFlag *= -1;
							if(movYFlag < 0) movYFlag *= -1;
						
							if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
								//System.out.println("Using movXFlag");
								pushX += movXFlag*Math.cos(phi + Math.toRadians(180));
								pushY += movXFlag*Math.sin(phi + Math.toRadians(180));
							} else if(movYFlag != 0){
								//System.out.println("Using movYFlag");
								pushX += movYFlag*Math.cos(phi + Math.toRadians(180));
								pushY += movYFlag*Math.sin(phi + Math.toRadians(180));
							}
						
							pushY -= 3;
						}
					
						if(!bottomArea.isEmpty() & bottomAreaArea > rightAreaArea & bottomAreaArea > leftAreaArea) {
							double movXFlag = 0;
							double movYFlag = 0;
						
							//System.out.println((Math.cos(phi) < 0.00001) || (Math.cos(phi) > -0.00001));
							if(!(Math.cos(phi) < 0.00001) || !(Math.cos(phi) > -0.00001)) {
								movXFlag = overlap.width/Math.cos(phi);
							
							}
						
							//System.out.println((Math.sin(phi) < 0.00001) || (Math.sin(phi) > -0.00001));
							if(!(Math.sin(phi) < 0.00001) || !(Math.sin(phi) > -0.00001)) {
								movYFlag = overlap.height/Math.sin(phi);
							}
						
							//System.out.println("movXFlag: " + movXFlag + "        movYFlag: " + movYFlag);
						
							if(movXFlag < 0) movXFlag *= -1;
							if(movYFlag < 0) movYFlag *= -1;
						
							if(movXFlag < movYFlag & movXFlag != 0 || movYFlag == 0) {
								//System.out.println("Using movXFlag");
								pushX += movXFlag*Math.cos(phi);
								pushY += movXFlag*Math.sin(phi);
							} else if(movYFlag != 0){
								//System.out.println("Using movYFlag");
								pushX += movYFlag*Math.cos(phi);
								pushY += movYFlag*Math.sin(phi);
							}
						
							pushY += 3;
						}
					
					
						//System.out.println("pushX: " + pushX + "        pushY: " + pushY);
						//System.out.println("Player pos before push added: X: " + getX() + "        Y: " + getY());
					
						setX(getX() + (int)pushX);
						setY(getY() + (int)pushY);
					
						//System.out.println("Player pos: X: " + getX() + "        Y: " + getY());
						//System.exit(1);
					}
				}

			
		}
		}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}

}

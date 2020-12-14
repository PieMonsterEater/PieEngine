package PieMonsterEater.Engine.Menus;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import PieMonsterEater.Engine.Core.Game;

public class Menu {

	ArrayList<Button> buttons = new ArrayList<Button>();
	ArrayList<Label> labels = new ArrayList<Label>();
	ArrayList<MenuImage> images = new ArrayList<MenuImage>();
	
	public Menu() {
		this.addMouseListner(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent e) {
				int mx = e.getX();
				int my = e.getY(); 
				
				Rectangle clickBox = new Rectangle(mx, my, 1, 1);
				
				for(int i = 0; i < buttons.size(); i++) {
				Button b = buttons.get(i);
					
				if(Game.getMenu().equals(this)) {
					if(clickBox.intersects(b.getRect())) {
						if(b.getToGame()) {
							Game.state = StateID.Play;
						} else {
							Game.setMenu(b.getMenu());
						}
					}
				}
			}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			
			if(b.isImage) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), null);
			} else g.drawString(b.getText(), b.getX(), b.getY());
		}
		
		for(int j = 0; j < labels.size(); j++) {
			g.drawString(labels.get(j).getText(), labels.get(j).getX(), labels.get(j).getY());
		}
		
		for(int k = 0; k < images.size(); k++) {
			MenuImage image = images.get(k);
			
			g.drawImage(image.getImage(), image.getX(), image.getY(), null);
		}
	}
	
	public void addMouseListner(MouseListener ms) {
		
	}
	
}

package PieMonsterEater.Engine.Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import PieMonsterEater.Engine.Core.Game;

public class MainMenu {
	private Rectangle playButton = new Rectangle(Game.WIDTH/3 + 35, 150, 100, 50);
	private Rectangle infoButton = new Rectangle(Game.WIDTH/3 + 35, 250, 100, 50);
	private Rectangle quitButton = new Rectangle(Game.WIDTH/3 + 35, 350, 100, 50);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt = new Font("arial", Font.BOLD, 50);
		Font fnt1 = new Font("arial", Font.BOLD, 20);
		
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		g.drawString("PieEngine", Game.WIDTH/3, 50);
		//g2d.draw(playButton);
		g.drawString("Play", playButton.x, playButton.y + 35);
		//g2d.draw(helpButton);
		g.drawString("Info", infoButton.x, infoButton.y + 35);
		//g2d.draw(quitButton);
		g.drawString("Quit", quitButton.x, quitButton.y + 35);
		
		g.setFont(fnt1);
		g.drawString("Version 1_02", 500, 440);
	}
}

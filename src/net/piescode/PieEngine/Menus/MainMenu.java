package net.piescode.PieEngine.Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.Visuals.BufferedImageLoader;

public class MainMenu extends Menu {
	//private Rectangle playButtonRect;
	//private Rectangle infoButtonRect;
	//private Rectangle quitButtonRect;
	
	BufferedImageLoader bil = new BufferedImageLoader();
	BufferedImage titleImage;
	
	private Button playButton;
	private Button infoButton;
	private Button optionsButton;
	private Button quitButton;
	
	public MainMenu(Game game) {
		super(game);
		//playButtonRect = new Rectangle(Game.WIDTH/3 + 35, 150, 100, 50);
		//infoButtonRect = new Rectangle(Game.WIDTH/3 + 35, 250, 100, 50);
		//quitButtonRect = new Rectangle(Game.WIDTH/3 + 35, 350, 100, 50);
		
		try {
			titleImage = bil.loadImage("res/Pie Engine Title.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		playButton = new PlayButton(game, Game.WIDTH/3 + 35, 100, 100, 50, "Play");
		infoButton = new MenuChangeButton(game, Game.WIDTH/3 + 35, 175, 100, 50, "Info", Game.iMenu);
		optionsButton = new MenuChangeButton(game, Game.WIDTH/4 + 50, 250, 190, 50, "Options", Game.iMenu);
		quitButton = new QuitButton(game, Game.WIDTH/3 + 35, 325, 100, 50, "Quit");
		
		buttons.add(playButton);
		buttons.add(infoButton);
		buttons.add(optionsButton);
		buttons.add(quitButton);
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt = new Font("arial", Font.BOLD, 50);
		Font fnt1 = new Font("arial", Font.BOLD, 20);
		
		g.drawImage(titleImage, Game.WIDTH/7 - 25, 0, 512, 90, null);
		
		g.setFont(fnt);
		//g.setColor(Color.WHITE);
		//g.drawString("PieEngine", Game.WIDTH/3, 50);
		//g2d.draw(playButton);
		//g.drawString("Play", playButton.x, playButton.y + 35);
		//g2d.draw(helpButton);
		//g.drawString("Info", infoButton.x, infoButton.y + 35);
		//g2d.draw(quitButton);
		//g.drawString("Quit", quitButton.x, quitButton.y + 35);
		
		for(int i = 0; i < buttons.size(); i++) buttons.get(i).render(g);
		
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawString("Version 1_03", 500, 430);
	}

	public void tick() {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setMouseX(getMouseX());
			buttons.get(i).setMouseY(getMouseY());
		}
		
		if(mouseActivated) {
			Rectangle mRect = new Rectangle(getMouseX(), getMouseY(), 1, 1);
			for(int i = 0; i < buttons.size(); i++) {
				buttons.get(i).mouseActivated = mouseActivated;
				if(buttons.get(i).getRectangle().intersects(mRect)) buttons.get(i).action();
			}
			
			mouseActivated = false;
		}
		
	}
}

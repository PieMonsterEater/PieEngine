package net.piescode.Example.Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.piescode.PieEngine.Core.Game;
import net.piescode.PieEngine.InputSystem.InputEvent;
import net.piescode.PieEngine.Menus.Button;
import net.piescode.PieEngine.Menus.Menu;

public class PlayMenu extends Menu {
	
	private Button playButton;
	private Button optionsButton;
	private Button quitButton;

	public PlayMenu(Game game) {
		super(game);


		playButton = new ReGameBtn(game, Game.WIDTH/4 - 50, 150, 375, 50, "Return to Game");
		optionsButton = new MenuChangeButton(game, Game.WIDTH/4 + 50, 250, 190, 50, "Options", game.oMenu);
		quitButton = new QtMainBtn(game, Game.WIDTH/3 + 35, 350, 100, 50, "Quit");
		
		buttons.add(playButton);
		buttons.add(optionsButton);
		buttons.add(quitButton);
	}

	public void tick() {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setMouseX(getMouseX());
			buttons.get(i).setMouseY(getMouseY());
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt = new Font("arial", Font.BOLD, 50);
		Font fnt1 = new Font("arial", Font.BOLD, 20);
		
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		g.drawString("Game Menu", Game.WIDTH/3 - 50, 50);
		
		for(int i = 0; i < buttons.size(); i++) buttons.get(i).render(g);
		
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawString("Version 1_03", 500, 430);
	}
	
	public void onMouseClicked(InputEvent ie, int x, int y) {
		if(!isActive) return;
		
		//System.out.println("Play Menu Clicked firing!");
		
		Rectangle mRect = new Rectangle(getMouseX(), getMouseY(), 1, 1);
		for(int i = 0; i < buttons.size(); i++) {
			if(buttons.get(i).getRectangle().intersects(mRect)) buttons.get(i).action();
		}
	}

}

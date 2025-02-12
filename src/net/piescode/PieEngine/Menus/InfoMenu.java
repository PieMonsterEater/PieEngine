package net.piescode.PieEngine.Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.piescode.PieEngine.Core.Game;

public class InfoMenu extends Menu {
	
	public InfoMenu(Game game) {
		super(game);
	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", Font.BOLD, 15);
		
		g.setFont(fnt);
		g.setColor(Color.GREEN);
		g.drawString("Enter any information you want about your game here!", 20, 50);
		g.setColor(Color.ORANGE);
		g.drawString("-Pie", 20, 130);
	}

	public void tick() {
		
		
	}
	
}

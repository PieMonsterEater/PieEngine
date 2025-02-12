package net.piescode.PieEngine.Core;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	public Window(String name, int width, int height, Game game) {
		JFrame frame = new JFrame(name);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
	
}

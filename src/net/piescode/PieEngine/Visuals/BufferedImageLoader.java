package net.piescode.PieEngine.Visuals;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	public BufferedImage loadImage(String path) throws IOException {
		BufferedImage img = ImageIO.read(new FileInputStream(path));
		return img;
	}
	
}

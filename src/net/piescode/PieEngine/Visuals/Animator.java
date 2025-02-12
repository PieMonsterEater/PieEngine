package net.piescode.PieEngine.Visuals;

import java.awt.image.BufferedImage;

public class Animator {

	BufferedImage[] images;
	int currentImage, numImages, count, delay, timesPlayed;
	
	
	public Animator() {
		timesPlayed = 0;
	}
	
	public void tick() {
		if(delay == -1) return;
		
		count++;
		
		if(count == delay) {
			currentImage++;
			count = 0;
		}
		if(currentImage == numImages) {
			currentImage = 0;
			timesPlayed++;
		}
	}
	
	public void setFrames(BufferedImage[] images) {
		this.images = images;
		currentImage = 0;
		count = 0;
		timesPlayed = 0;
		delay = 2;
		numImages = images.length;
	}
	
	public void setDelay(int i) {
		delay = i;
	}
	
	public void setImage(int i) {
		currentImage = i;
	}
	
	public void setnumImages(int i) {
		numImages = i;
	}

	public int getCount() {
		return count;
	}

	public int getImgNum() {
		return currentImage;
	}

	public BufferedImage getImage() {
		return images[currentImage];
	}

	public int getNumImages() {
		return numImages;
	}

	public int getDelay() {
		return delay;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}
	
	public boolean hasPlayedOnce() {
		return timesPlayed > 0;
	}
	
	public boolean hasPlayed(int i) {
		return timesPlayed == i;
	}
	
	
}

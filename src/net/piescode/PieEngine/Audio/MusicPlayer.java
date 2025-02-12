package net.piescode.PieEngine.Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
	Clip clip;
	
	public void playSong(String name) {
		File file = new File(name);
		AudioInputStream song;
		try {
			song = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(song);
			clip.start();
			loop();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
		clip.close();
	}
}

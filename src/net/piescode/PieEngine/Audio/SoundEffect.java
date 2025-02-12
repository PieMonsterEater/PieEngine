package net.piescode.PieEngine.Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
Clip clip;

public void playSound(String name) {
	File file = new File(name);
	try {
		AudioInputStream sound = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(sound);
		clip.setFramePosition(0);
		clip.start();
	} catch (UnsupportedAudioFileException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (LineUnavailableException e) {
		e.printStackTrace();
	}
}
}

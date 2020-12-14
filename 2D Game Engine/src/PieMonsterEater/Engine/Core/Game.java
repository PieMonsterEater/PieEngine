package PieMonsterEater.Engine.Core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import PieMonsterEater.Engine.Audio.MusicPlayer;
import PieMonsterEater.Engine.Entities.Enemy;
import PieMonsterEater.Engine.EntityCore.Handler;
import PieMonsterEater.Engine.EntityCore.ID;
import PieMonsterEater.Engine.LevelLoader.LevelLoader;
import PieMonsterEater.Engine.Menus.InfoMenu;
import PieMonsterEater.Engine.Menus.MainMenu;
import PieMonsterEater.Engine.Menus.Menu;
import PieMonsterEater.Engine.Menus.StateID;
import PieMonsterEater.Engine.Player.Camera;
import PieMonsterEater.Engine.Player.KeyInput;
import PieMonsterEater.Engine.Player.MouseInput;
import PieMonsterEater.Engine.Player.Player;

public class Game extends Canvas implements Runnable {
	
	public static final int SCALE=100;
	public static final int WIDTH = 640, HEIGHT = 640;
	public static StateID state = StateID.MainMenu;
	public static LevelLoader ll;
	public boolean running = false;
	
	private Thread thread;
	private Handler handler;
	private MusicPlayer mp;
	
	private MainMenu mainM = new MainMenu();
	private InfoMenu iMenu = new InfoMenu();
	
	private static Menu menu;
	
	Camera camera = new Camera(0, 0);;
	
	public Game() {
		new Window("PieEngine", WIDTH, HEIGHT, this);
		
		handler = new Handler();
		handler.addObj(new Player(100, 100, handler));
		handler.addObj(new Enemy(250, 250, handler));
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput());
		ll = new LevelLoader(handler);
		ll.nextLevel();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		thread = new Thread(this);
		try {
			thread.join();
			running = false;
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
				if (running) 
					render();
					frames++;
					
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						System.out.println("FPS:" + frames);
						frames = 0;
					}
				
			}
			stop();
	}
	
	public void tick() {
		if(this.state == StateID.Play) {
		handler.tick();
		for(int i =0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getID() == ID.Player) camera.tick(handler.object.get(i));
		}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//////////////////////////////////////////
		if(this.state == StateID.MainMenu) mainM.render(g);
		if(this.state == StateID.InfoMenu) iMenu.render(g);
		if(this.state == StateID.Play) {
		g2d.translate(camera.getX(), camera.getY());
		handler.render(g);
		g2d.translate(-camera.getX(), -camera.getY());
		}
		//////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	public static Menu getMenu() {
		return menu;
	}
	
	public static void setMenu(Menu menu) {
		menu = menu;
	}
	
	public static void main(String[] args) {
		new Game();

	}

}

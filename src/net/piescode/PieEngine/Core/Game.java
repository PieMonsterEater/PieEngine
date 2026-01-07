package net.piescode.PieEngine.Core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import net.piescode.PieEngine.Audio.MusicPlayer;
import net.piescode.PieEngine.Entities.Enemy;
import net.piescode.PieEngine.EntityCore.Handler;
import net.piescode.PieEngine.EntityCore.ID;
import net.piescode.PieEngine.LevelLoader.LevelLoader;
import net.piescode.PieEngine.Menus.InfoMenu;
import net.piescode.PieEngine.Menus.MainMenu;
import net.piescode.PieEngine.Menus.Menu;
import net.piescode.PieEngine.Menus.PlayMenu;
import net.piescode.PieEngine.Menus.StateID;
import net.piescode.PieEngine.Player.Camera;
import net.piescode.PieEngine.Player.KeyInput;
import net.piescode.PieEngine.Player.MouseInput;
import net.piescode.PieEngine.Player.MouseMoveInput;
import net.piescode.PieEngine.Player.Player;
import net.piescode.PieEngine.Visuals.RenderingLayer;

public class Game extends Canvas implements Runnable {
	
	public static final int SCALE=100;
	public static final int WIDTH = 640, HEIGHT = 480;
	public static StateID state;
	public static LevelLoader ll;
	public static KeyInput keyInput;
	public boolean running = false;
	
	private Thread thread;
	private Handler handler;
	private MusicPlayer mp;
	
	public MainMenu mainM;
	public InfoMenu iMenu;
	public PlayMenu pMenu;
	
	public Menu currentMenu;
	public Menu lastMenu;

	public ArrayList<Menu> prevMenus;
	
	Camera camera = new Camera(0, 0);
	
	public Game() {
		handler = new Handler();
		//handler.addObj(new Player(100, 100, handler));
		//handler.addObj(new Enemy(250, 250, handler));
		keyInput = new KeyInput(handler, this);
		this.addKeyListener(keyInput);
		this.addMouseListener(new MouseInput(handler, this));
		this.addMouseMotionListener(new MouseMoveInput(this));
		ll = new LevelLoader(handler);
		//ll.nextLevel();
		
		
		iMenu = new InfoMenu(this);
		mainM = new MainMenu(this);
		pMenu = new PlayMenu(this);
		
		currentMenu = mainM;
		lastMenu = currentMenu;
		
		Game.state = StateID.MainMenu;
		
		prevMenus = new ArrayList<Menu>();
		
		new Window("PieEngine", WIDTH, HEIGHT, this);
	}
	
	public void initGame() {
		this.lastMenu = pMenu;
		this.currentMenu = pMenu;
		Game.state = StateID.Play;
		handler.addObj(new Player(100, 100, RenderingLayer.PLAYER, handler));
		ll.nextLevel();
	}
	
	public void destructGame() {
		ll.reset();
		if(handler.getSize() > 0) handler.removeObj(0);
		handler.tick(); // This has to be here so the handler properly removes everything
		this.lastMenu = mainM;
		this.currentMenu = mainM;
		Game.state = StateID.MainMenu;
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
		if(Game.state == StateID.Play) {
		handler.tick();
		for(int i =0; i < handler.getSize(); i++) {
			if(handler.getObj(i).getID() == ID.Player) camera.tick(handler.getObj(i));
		}
		} else {
			currentMenu.tick();
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
		if(Game.state == StateID.Play || Game.state == StateID.PlayMenu) {
		g2d.translate(camera.getX(), camera.getY());
		handler.render(g);
		g2d.translate(-camera.getX(), -camera.getY());
		} else {
			currentMenu.render(g);
		}
		
		if(Game.state == StateID.PlayMenu) {
			g.setColor(new Color(0, 0, 0, 155));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			currentMenu.render(g);
		}
		//////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
	public void setMenu(Menu menu) {
		prevMenus.add(currentMenu);
		currentMenu = menu;
	}
	
	public void backMenu() {
		if(prevMenus.size() == 0) return;
		
		currentMenu = prevMenus.get(prevMenus.size() - 1);
		prevMenus.remove(prevMenus.size() - 1);
	}
	
}

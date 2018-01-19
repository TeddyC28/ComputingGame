package com.teddyc28.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.teddyc28.game.entity.mob.Player;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.input.Keyboard;
import com.teddyc28.game.level.Level;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private int width = 512;
	private int height = 288;
	private int scale = 3;
	public static String title = "Game";
	
	private boolean running = false;
	
	private Thread gameThread;
	private JFrame frame;
	private Screen screen;
	private Keyboard key;
	private Player player;
	private Level level;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		level = Level.spawnLevel;
		frame = new JFrame();
		key = new Keyboard();
		player = new Player(key);
		player.level = level;
		
		addKeyListener(key);
	}
	
	public int getWindowWidth() {
		return width * scale;
	}
	
	public int getWindowHeight() {
		return height * scale;
	}
	
	private void start() {
		running = true;
		gameThread = new Thread(this, "Display");
		gameThread.start();
	}
	
	private void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + " | " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void update() {
		key.update();
		player.update();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		level.getRoom(player.roomX, player.roomY).render(screen);
		player.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
	
}
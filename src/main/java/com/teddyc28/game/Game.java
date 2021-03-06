package com.teddyc28.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFrame;
import com.teddyc28.game.entity.mob.Player;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.input.Keyboard;
import com.teddyc28.game.input.Mouse;
import com.teddyc28.game.level.Level;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 512;
	private static int height = 288;
	private static int scale = 3;
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
		setPreferredSize(new Dimension(width * scale, height * scale));

		screen = new Screen(width, height);
		level = Level.spawnLevel;
		frame = new JFrame();
		key = new Keyboard();
		player = new Player(key);
		player.initLevel(level);
		player.initRoom(10, 10);
		level.rooms[player.getRoomX() + player.getRoomY() * level.width].setPlayer(player);
//		level.rooms[player.getRoomX() + player.getRoomY() * level.width].add(new Dummy(5, 5, level, player.getRoomX(), player.getRoomY()));

		Mouse mouse = new Mouse();
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
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
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + frames + " fps");
				frames = 0;
			}
		}
		stop();
	}

	private void update() {
		key.update();
		player.update();
		level.rooms[player.getRoomX() + player.getRoomY() * level.width].update();
		if (player.getHealth() <= 9) {
			gameOver();
			System.exit(0);
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		level.rooms[player.getRoomX() + player.getRoomY() * level.width].render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		//g.setColor(Color.WHITE);
		//g.setFont(new Font("Verdana", 0, 50));
		//g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
		//if (Mouse.getButton() != -1) g.drawString("Button: " + Mouse.getButton(), 80, 80);
		g.dispose();
		bs.show();
	}

	public void gameOver() {
		File target = new File(System.getProperty("user.dir"));
		File main = new File(target.getParent() + File.separator + "Score.txt");
		try {
			PrintWriter pw = new PrintWriter(main);
			pw.println("Score: " + player.getScore());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
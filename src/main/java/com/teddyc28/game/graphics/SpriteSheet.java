package com.teddyc28.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256, 256);
	public static SpriteSheet floors = new SpriteSheet("/textures/sheets/floors.png", 48, 48);
	public static SpriteSheet walls = new SpriteSheet("/textures/sheets/walls.png", 48, 48);
	public static SpriteSheet doors = new SpriteSheet("/textures/sheets/doors.png", 64, 32);
			
	public SpriteSheet(String path, int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.path = path;
		pixels = new int[width * height];
		load();
	}
	
	private void load() {
		System.out.println(SpriteSheet.class.getResource(path));
		System.out.println(path);
		System.out.println(SpriteSheet.class.getResource(path));
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

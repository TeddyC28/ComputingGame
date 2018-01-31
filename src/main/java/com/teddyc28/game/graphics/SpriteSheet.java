package com.teddyc28.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	private Sprite[] sprites;

	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256, 256);
	public static SpriteSheet floors = new SpriteSheet("/textures/sheets/floors.png", 48, 48);
	public static SpriteSheet walls = new SpriteSheet("/textures/sheets/walls.png", 48, 48);
	public static SpriteSheet doors = new SpriteSheet("/textures/sheets/doors.png", 64, 32);
	public static SpriteSheet projectiles = new SpriteSheet("/textures/sheets/projectiles.png", 48, 48);

	public static SpriteSheet player = new SpriteSheet("/textures/sheets/player_sheet.png", 128, 96);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 3, 0, 1, 3, 32);
	
	public static SpriteSheet dummy = new SpriteSheet("/textures/sheets/King_Cherno.png", 128, 96);
	public static SpriteSheet dummy_down = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
	public static SpriteSheet dummy_up = new SpriteSheet(dummy, 1, 0, 1, 3, 32);
	public static SpriteSheet dummy_left = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
	public static SpriteSheet dummy_right = new SpriteSheet(dummy, 3, 0, 1, 3, 32);

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int rX = x * spriteSize;
		int rY = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w * h];
		for (int yA = 0; yA < h; yA++) {
			int yPos = rY + yA;
			for (int xA = 0; xA < w; xA++) {
				int xPos = rX + xA;
				pixels[xA + yA * w] = sheet.pixels[xPos + yPos * sheet.WIDTH];
			}
		}

		int frame = 0;
		sprites = new Sprite[width * height];
		for (int yA = 0; yA < height; yA++) {
			for (int xA = 0; xA < width; xA++) {
				int[] spritePixels = new int[(int) Math.pow(spriteSize, 2)];
				for (int y2 = 0; y2 < spriteSize; y2++) {
					for (int x2 = 0; x2 < spriteSize; x2++) {
						spritePixels[x2 + y2 * spriteSize] = pixels[(x2 + xA * spriteSize) + (y2 + yA * spriteSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
			
	public SpriteSheet(String path, int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.path = path;
		pixels = new int[width * height];
		load();
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

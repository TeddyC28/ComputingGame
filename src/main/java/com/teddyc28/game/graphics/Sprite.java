package com.teddyc28.game.graphics;

import java.util.Random;

public class Sprite {

	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	private static Random random = new Random();

	public static Sprite floor = new Sprite(16, random.nextInt(3), random.nextInt(3), SpriteSheet.floors);
	public static Sprite wallEdge = new Sprite(16, random.nextInt(3), random.nextInt(3), SpriteSheet.walls);
	public static Sprite wallCorner = new Sprite(16, random.nextInt(3), random.nextInt(3), SpriteSheet.walls);
	
	public static Sprite player = new Sprite(32, 2, 5, SpriteSheet.tiles);

	public static Sprite topDoorLeft = new Sprite(16, 1, 0, SpriteSheet.doors);
	public static Sprite topDoorRight = new Sprite(16, 2, 0, SpriteSheet.doors);
	
	public static Sprite bottomDoorLeft = new Sprite(16, 1, 1, SpriteSheet.doors);
	public static Sprite bottomDoorRight = new Sprite(16, 2, 1, SpriteSheet.doors);
	
	public static Sprite leftDoorTop = new Sprite(16, 0, 0, SpriteSheet.doors);
	public static Sprite leftDoorBottom = new Sprite(16, 0, 1, SpriteSheet.doors);

	public static Sprite rightDoorTop = new Sprite(16, 3, 0, SpriteSheet.doors);
	public static Sprite rightDoorBottom = new Sprite(16, 3, 1, SpriteSheet.doors);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		pixels = new int[size * size];
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		load();
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x * SIZE) + (y + this.y * SIZE) * sheet.WIDTH];
			}
		}
	}

}
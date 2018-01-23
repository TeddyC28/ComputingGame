package com.teddyc28.game.graphics;

import java.util.Random;

public class Sprite {

	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	private static Random random = new Random();

	public static Sprite floor = new Sprite(3, 3, SpriteSheet.floors);
	public static Sprite wallEdge = new Sprite(3, 3, SpriteSheet.walls);

	//Player Non-moving Sprites
	public static Sprite player_forward = new Sprite(32, 0, 5, SpriteSheet.tiles);
	public static Sprite player_back = new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_left = new Sprite(32, 3, 5, SpriteSheet.tiles);
	public static Sprite player_right = new Sprite(32, 1, 5, SpriteSheet.tiles);
	
	//Player Moving Animation Sprites
	public static Sprite player_forward_1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
	public static Sprite player_forward_2 = new Sprite(32, 0, 7, SpriteSheet.tiles);
	
	public static Sprite player_back_1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
	public static Sprite player_back_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);
	
	public static Sprite player_left_1 = new Sprite(32, 3, 6, SpriteSheet.tiles);
	public static Sprite player_left_2 = new Sprite(32, 3, 7, SpriteSheet.tiles);

	public static Sprite player_right_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_right_2 = new Sprite(32, 1, 7, SpriteSheet.tiles);

	//Door Sprites
	public static Sprite topDoorLeft = new Sprite(16, 1, 0, SpriteSheet.doors);
	public static Sprite topDoorRight = new Sprite(16, 2, 0, SpriteSheet.doors);

	public static Sprite bottomDoorLeft = new Sprite(16, 1, 1, SpriteSheet.doors);
	public static Sprite bottomDoorRight = new Sprite(16, 2, 1, SpriteSheet.doors);

	public static Sprite leftDoorTop = new Sprite(16, 0, 0, SpriteSheet.doors);
	public static Sprite leftDoorBottom = new Sprite(16, 0, 1, SpriteSheet.doors);

	public static Sprite rightDoorTop = new Sprite(16, 3, 0, SpriteSheet.doors);
	public static Sprite rightDoorBottom = new Sprite(16, 3, 1, SpriteSheet.doors);

	//Void Sprite
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		pixels = new int[size * size];
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		load();
	}

	public Sprite(int xMax, int yMax, SpriteSheet sheet) {
		this.SIZE = 16;
		pixels = new int[SIZE * SIZE];
		this.x = random.nextInt(xMax);
		this.y = random.nextInt(yMax);
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int colour) {
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}

	private void setColour(int colour) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = colour;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x * SIZE) + (y + this.y * SIZE) * sheet.WIDTH];
			}
		}
	}

}

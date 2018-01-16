package com.teddyc28.game.graphics;

import com.teddyc28.game.Game;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.level.room.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public static final int ROOM_WIDTH = 32;
	public static final int ROOM_HEIGHT = 18;
	public Tile[] tiles = new Tile[ROOM_WIDTH * ROOM_HEIGHT];

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		generateRoom();
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void generateRoom() {
		for (int y = 0; y < ROOM_HEIGHT; y++) {
			for (int x = 0; x < ROOM_WIDTH; x++) {
				tiles[x + y * ROOM_WIDTH] = Tile.floor;
			}
		}
	}

	public void render() {
		for (int y = 0; y < ROOM_HEIGHT; y++) {
			for (int x = 0; x < ROOM_WIDTH; x++) {
				tiles[x + y * ROOM_WIDTH].render(x, y, this);
			}
		}
	}

	private int tileCoordinate(int x, int y) {
		return (x / 16) + (y / 16) * ROOM_WIDTH;
	}

	public void renderPlayer(int xPos, int yPos, Sprite sprite) {
		for (int y = 0; y < sprite.SIZE; y++) {
			for (int x = 0; x < sprite.SIZE; x++) {
				if ((x + xPos) < -32 || (x + xPos) >= width || (y + yPos) < 0 || (y + yPos) >= height) break;
				if (sprite.pixels[x+y*sprite.SIZE] != 0xffff00ff) pixels[(xPos + x) + (yPos + y) * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}

	public void renderTile(int xPos, int yPos, Tile tile) {
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				pixels[((xPos * tile.sprite.SIZE) + x) + ((yPos * tile.sprite.SIZE) + y) * width] = tile.sprite.pixels[(x) + (y) * tile.sprite.SIZE];
			}
		}
	
	}
}

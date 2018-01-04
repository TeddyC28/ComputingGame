package com.teddyc28.game.graphics;

import com.teddyc28.game.graphics.Sprite;

public class Screen {

	public int width, height;
	public int[] pixels;
	public static final int ROOM_WIDTH = 32;
	public static final int ROOM_HEIGHT = 18;
	public int[] tiles = new int[ROOM_WIDTH * ROOM_HEIGHT];

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
//corners
				if (tileCoordinate(x, y) == 0 || tileCoordinate(x, y) == ROOM_WIDTH - 1 || tileCoordinate(x, y) == (ROOM_HEIGHT - 1) * ROOM_WIDTH || tileCoordinate(x, y) == (ROOM_WIDTH * ROOM_HEIGHT) - 1) {
					pixels[x + y * width] = Sprite.wallCorner.pixels[(x & (Sprite.wallCorner.SIZE - 1)) + (y & (Sprite.wallCorner.SIZE - 1)) * Sprite.wallCorner.SIZE];
}
// top doors
				else if (tileCoordinate(x, y) == ROOM_WIDTH / 2 - 1) {
					pixels[x + y * width] = Sprite.topDoorLeft.pixels[(x & (Sprite.topDoorLeft.SIZE - 1) + (y & (Sprite.topDoorLeft.SIZE - 1)) * Sprite.topDoorLeft.SIZE)];
}
				else if (tileCoordinate(x, y) == ROOM_WIDTH / 2) {
					pixels[x + y * width] = Sprite.topDoorRight.pixels[(x & (Sprite.topDoorRight.SIZE - 1)) + (y & (Sprite.topDoorRight.SIZE - 1)) * Sprite.topDoorRight.SIZE];
}
// bottom doors
				else if (tileCoordinate(x, y) == ((ROOM_HEIGHT - 1) * ROOM_WIDTH) + ROOM_WIDTH / 2 - 1) {
					pixels[x + y * width] = Sprite.bottomDoorLeft.pixels[(x & (Sprite.bottomDoorLeft.SIZE - 1) + (y & (Sprite.bottomDoorLeft.SIZE - 1)) * Sprite.bottomDoorLeft.SIZE)];
}
				else if (tileCoordinate(x, y) == ((ROOM_HEIGHT - 1) * ROOM_WIDTH) + ROOM_WIDTH / 2) {
					pixels[x + y * width] = Sprite.bottomDoorRight.pixels[(x & (Sprite.bottomDoorRight.SIZE - 1)) + (y & (Sprite.bottomDoorRight.SIZE - 1)) * Sprite.bottomDoorRight.SIZE];
}
// left doors
				else if (tileCoordinate(x, y) == ((ROOM_HEIGHT / 2) - 1) * ROOM_WIDTH) {
					pixels[x + y * width] = Sprite.leftDoorTop.pixels[(x & (Sprite.leftDoorTop.SIZE - 1)) + (y & (Sprite.leftDoorTop.SIZE - 1)) * Sprite.leftDoorTop.SIZE];
}
				else if (tileCoordinate(x, y) == (ROOM_HEIGHT / 2) * ROOM_WIDTH) {
					pixels[x + y * width] = Sprite.leftDoorBottom.pixels[(x & (Sprite.leftDoorBottom.SIZE - 1)) + (y & (Sprite.leftDoorBottom.SIZE - 1)) * Sprite.leftDoorBottom.SIZE];
}
// right doors
				else if (tileCoordinate(x, y) == ((ROOM_HEIGHT / 2)) * ROOM_WIDTH - 1) {
					pixels[x + y * width] = Sprite.rightDoorTop.pixels[(x & (Sprite.leftDoorTop.SIZE - 1)) + (y & (Sprite.leftDoorTop.SIZE - 1)) * Sprite.leftDoorTop.SIZE];
}
				else if (tileCoordinate(x, y) == ((ROOM_HEIGHT / 2) + 1) * ROOM_WIDTH - 1) {
					pixels[x + y * width] = Sprite.rightDoorBottom.pixels[(x & (Sprite.leftDoorBottom.SIZE - 1)) + (y & (Sprite.leftDoorBottom.SIZE - 1)) * Sprite.leftDoorBottom.SIZE];
}
// edges
				else if (tileCoordinate(x, y) % 32 == 0)
					pixels[x + y * width] = Sprite.wallEdge.pixels[(x & 15) + (y & 15) * Sprite.wallEdge.SIZE];
				else if ((tileCoordinate(x, y) + 1) % 32 == 0)
					pixels[x + y * width] = Sprite.wallEdge.pixels[(x & 15) + (y & 15) * Sprite.wallEdge.SIZE];
				else if (tileCoordinate(x, y) < ROOM_WIDTH)
					pixels[x + y * width] = Sprite.wallEdge.pixels[(x & 15) + (y & 15) * Sprite.wallEdge.SIZE];
				else if (tileCoordinate(x, y) > (ROOM_HEIGHT - 1) * ROOM_WIDTH)
					pixels[x + y * width] = Sprite.wallEdge.pixels[(x & 15) + (y & 15) * Sprite.wallEdge.SIZE];

// fill central area
				else
					pixels[x + y * width] = Sprite.floor.pixels[(x & (Sprite.leftDoorTop.SIZE - 1))
							+ (y & (Sprite.leftDoorTop.SIZE - 1)) * Sprite.leftDoorTop.SIZE];
			}
		}
	}

	private int tileCoordinate(int x, int y) {
		return (x / 16) + (y / 16) * ROOM_WIDTH;
	}

	public void renderPlayer(int xPos, int yPos, Sprite sprite) {
		for (int y = 0; y < sprite.SIZE; y++) {
			for (int x = 0; x < sprite.SIZE; x++) {
				if (sprite.pixels[x+y*sprite.SIZE] != 0xffff00ff) pixels[(xPos + x) + (yPos + y) * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}
}

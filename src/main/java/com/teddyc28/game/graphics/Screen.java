package com.teddyc28.game.graphics;

import com.teddyc28.game.entity.mob.Mob;
import com.teddyc28.game.entity.projectile.Projectile;
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

	public void renderSprite(int xPos, int yPos, Sprite sprite) {
		for (int y = 0; y < sprite.getHeight(); y++) {
			for (int x = 0; x < sprite.getWidth(); x++) {
				if ((x + xPos) < 0 || (x + xPos) >= width || (y + yPos) < 0 || (y + yPos) >= height) break;
				if (visible(sprite.pixels[x + y * sprite.getWidth()])) {
					pixels[(x + xPos) + (y + yPos) * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	public void renderMob(int xPos, int yPos, Mob mob) {
		for (int y = 0; y < mob.getSpriteSize(); y++) {
			for (int x = 0; x < mob.getSpriteSize(); x++) {
				if ((x + xPos) < -mob.getSpriteSize() || (x + xPos) >= width || (y + yPos) < 0 || (y + yPos) >= height) break;
				if (visible(mob.getSprite().pixels[x + y * mob.getSpriteSize()])) {
					pixels[(xPos + x) + (yPos + y) * width] = mob.getSprite().pixels[x + y * mob.getSpriteSize()];
				}
			}
		}
	}

	public void renderTile(int xPos, int yPos, Tile tile) {
		for (int y = 0; y < tile.getSprite().getHeight(); y++) {
			for (int x = 0; x < tile.getSprite().getWidth(); x++) {
				pixels[((xPos * tile.getSprite().getWidth()) + x) + ((yPos * tile.getSprite().getHeight()) + y) * width] = tile.getSprite().pixels[(x) + (y) * tile.getSprite().getWidth()];
			}
		}
	
	}

	public void renderProjectile(int xPos, int yPos, Projectile p) {
		for (int y = 0; y < p.getSprite().getHeight(); y++) {
			int ya = y + yPos;
			for (int x = 0; x < p.getSprite().getWidth(); x++) {
				int xa = x + xPos;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				if (visible(p.getSprite().pixels[x + y * p.getSpriteSize()])) {
					pixels[xa + ya * width] = p.getSprite().pixels[x + y * p.getSprite().getWidth()];
				}
			}
		}
	}

	private boolean visible(int current) {
		if (current == 0xffff00ff) return false;
		return true;
	}
}

package com.teddyc28.game.graphics;

import com.teddyc28.game.entity.mob.Chaser;
import com.teddyc28.game.entity.mob.Follower;
import com.teddyc28.game.entity.mob.Mob;
import com.teddyc28.game.entity.mob.Shooter;
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
		for (int y = 0; y < sprite.getSize(); y++) {
			for (int x = 0; x < sprite.getSize(); x++) {
				if ((x + xPos) < 0 || (x + xPos) >= width || (y + yPos) < 0 || (y + yPos) >= height) break;
				int col = sprite.pixels[x + y * sprite.getSize()];
				if (col != 0xffff00ff) pixels[(xPos + x) + (yPos + y) * width] = col;
			}
		}
	}

	public void renderMob(int xPos, int yPos, Sprite sprite) {
		for (int y = 0; y < sprite.getSize(); y++) {
			for (int x = 0; x < sprite.getSize(); x++) {
				if ((x + xPos) < -sprite.getSize() || (x + xPos) >= width || (y + yPos) < 0 || (y + yPos) >= height) break;
				int col = sprite.pixels[x + y * sprite.getSize()];
				if (col != 0xffff00ff) pixels[(xPos + x) + (yPos + y) * width] = col;
			}
		}
	}

	public void renderMob(int xPos, int yPos, Mob mob) {
		for (int y = 0; y < mob.getSprite().getSize(); y++) {
			for (int x = 0; x < mob.getSprite().getSize(); x++) {
				if ((x + xPos) < -mob.getSprite().getSize() || (x + xPos) >= width || (y + yPos) < 0 || (y + yPos) >= height) break;
				int col = mob.getSprite().pixels[x + y * mob.getSprite().getSize()];
				if ((mob instanceof Shooter) && col == 0xff472BBF) col = 0xff00FF00;
				if ((mob instanceof Chaser) && col == 0xff472BBF) col = 0xffBA0015;
				if ((mob instanceof Follower) && col == 0xff472BBF) col = 0xff000000;
				if (col != 0xffff00ff) pixels[(xPos + x) + (yPos + y) * width] = col;
			}
		}
	}

	public void renderTile(int xPos, int yPos, Tile tile) {
		for (int y = 0; y < tile.getSprite().getSize(); y++) {
			for (int x = 0; x < tile.getSprite().getSize(); x++) {
				pixels[((xPos * tile.getSprite().getSize()) + x) + ((yPos * tile.getSprite().getSize()) + y) * width] = tile.getSprite().pixels[(x) + (y) * tile.getSprite().getSize()];
			}
		}
	
	}

	public void renderProjectile(int xPos, int yPos, Projectile p) {
		for (int y = 0; y < p.getSprite().getSize(); y++) {
			int ya = y + yPos;
			for (int x = 0; x < p.getSprite().getSize(); x++) {
				int xa = x + xPos;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSprite().getSize()];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

}

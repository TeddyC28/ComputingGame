package com.teddyc28.game.entity.mob;

import java.util.ArrayList;
import java.util.List;
import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.entity.projectile.BallProjectile;
import com.teddyc28.game.entity.projectile.Projectile;
import com.teddyc28.game.graphics.AnimatedSprite;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.graphics.SpriteSheet;
import com.teddyc28.game.input.Keyboard;
import com.teddyc28.game.input.Mouse;

public class Player extends Mob {
		
	private Keyboard input;
	
	private boolean allowDoorMove = false;
	private boolean shooting = false;
	
	
	private AnimatedSprite up = new AnimatedSprite(32, SpriteSheet.player_up, 3);
	private AnimatedSprite down = new AnimatedSprite(32, SpriteSheet.player_down, 3);
	private AnimatedSprite left = new AnimatedSprite(32, SpriteSheet.player_left, 3);
	private AnimatedSprite right = new AnimatedSprite(32, SpriteSheet.player_right, 3);
	private AnimatedSprite animSprite = down;
	
	private int score = 0;
	
	public Player(Keyboard input) {
		this.input = input;
		this.x = 256; 
		this.y = 144; 
		this.sprite = Sprite.player_back;
		this.fireRate = BallProjectile.FIRE_RATE;
		this.speed = 2;
	}
	
	public boolean getAllowDoors() {
		return allowDoorMove;
	}
	
	public void setAllowDoors(boolean allowDoorMove) {
		this.allowDoorMove = allowDoorMove;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public int getRoomX() {
		return roomX;
	}	

	public int getRoomY() {
		return roomY;
	}

	public void update() {
		if (fireRate > 0) fireRate--;
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		double xa = 0, ya = 0;
		if (input.up) {
			animSprite = up;
			ya -= speed;
			dir = Direction.UP;
		} else if (input.down) {
			animSprite = down;
			ya += speed;
			dir = Direction.DOWN;
		}
		if (input.left) {
			animSprite = left;
			xa -= speed;
			dir = Direction.LEFT;
		} else if (input.right) {
			animSprite = right;
			xa += speed;
			dir = Direction.RIGHT;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya, allowDoorMove);
			walking = true;
		} else {
			walking = false;
		}

		if (input.su || input.sd || input.sl || input.sr || Mouse.getButton() == 1) shooting = true;
		else shooting = false;
		updateShooting();
		updateProjectiles();
        if (level.rooms[roomX + roomY * level.width].getEntities().size() == 0) {
            allowDoorMove = true;
		}
	}


	protected void updateShooting() {
		if (!shooting || fireRate > 0) return;
		List<Mob> targets = new ArrayList<Mob>();
		List<Entity> temp = level.rooms[roomX + roomY * level.width].getEntities();
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) instanceof Dummy || temp.get(i) instanceof Chaser || temp.get(i) instanceof Follower || temp.get(i) instanceof Shooter) {
				targets.add((Mob) temp.get(i));
			}
		}
		double dir = Math.atan2((Mouse.getY() / 3) - y, (Mouse.getX() / 3) - x);
		shoot(x, y, dir, targets);
		if (input.su) shoot(x, y, -(Math.PI / 2), targets);
		else if (input.sr) shoot(x, y, 0, targets);
		else if (input.sd) shoot(x, y, (Math.PI / 2), targets);
		else if (input.sl) shoot(x, y, Math.PI, targets);
		fireRate = BallProjectile.FIRE_RATE;
	}

	public void render(Screen screen) {
		screen.renderMob((int) (x - 16), (int) (y - 16), animSprite.getSprite());
	}
	

}

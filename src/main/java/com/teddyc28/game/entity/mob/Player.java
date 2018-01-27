package com.teddyc28.game.entity.mob;

import com.teddyc28.game.entity.projectile.BallProjectile;
import com.teddyc28.game.entity.projectile.Projectile;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.input.Keyboard;
import com.teddyc28.game.input.Mouse;

public class Player extends Mob {
		
	private Keyboard input;
	private int anim = 0;
	private boolean walking = false;

	private int fireRate  = 0;
	
	public Player(Keyboard input) {
		this.input = input;
		this.x = 256;
		this.y = 144;
		this.fireRate = BallProjectile.FIRE_RATE;
	}
	
	public int getRoomX() {
		return roomX;
	}	

	public int getRoomY() {
		return roomY;
	}

	public void update() {
		int xa = 0, ya = 0; //variables for handling movement on the axis
		if (anim < 7500) anim ++;
		else anim = 0;
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;
		if (xa != 0 || ya != 0) { //if the player is moving
			walking = true;
			move(xa, ya, true);
		} else {
			walking = false;
		}
		if (fireRate > 0) fireRate--;
		if (fireRate <= 0) updateShooting();
		updateProjectiles();
	}

	private void updateProjectiles() {
		for (int i = 0; i < level.rooms[roomX + roomY * level.width].getProjectiles().size(); i++) {
			Projectile p = level.rooms[roomX + roomY * level.width].getProjectiles().get(i);
			
			if (p.isRemoved()) level.rooms[roomX + roomY * level.width].getProjectiles().remove(i);
		}
	}
	
	private void updateShooting() {
		if (Mouse.getButton() == 1) {
			double dir = Math.atan2((Mouse.getY() / 3) - y, (Mouse.getX() / 3) - x);
			shoot(x, y, dir);
		}
		else if (input.su) shoot(x, y, -(Math.PI / 2));
		else if (input.sr) shoot(x, y, 0);
		else if (input.sd) shoot(x, y, (Math.PI / 2));
		else if (input.sl) shoot(x, y, Math.PI);
		fireRate = BallProjectile.FIRE_RATE;
	}

	public void render(Screen screen) {
		if (dir == 0) {
			sprite = Sprite.player_forward;

			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_forward_1;
				} else {
					sprite = Sprite.player_forward_2;
				}
			}
		}

		if (dir == 1) {
			sprite = Sprite.player_right;

			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_right_1;
				} else {
					sprite = Sprite.player_right_2;
				}
			}
		}

		if (dir == 2) {
			sprite = Sprite.player_back;

			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_back_1;
				} else {
					sprite = Sprite.player_back_2;
				}
			}
		}

		if (dir == 3) {
			sprite = Sprite.player_left;

			if (walking){
				if (anim % 20 > 10) {
					sprite = Sprite.player_left_1;
				} else {
					sprite = Sprite.player_left_2;
				}
			}
		}
		
		screen.renderMob(x - 16, y - 16, this);
	}

}

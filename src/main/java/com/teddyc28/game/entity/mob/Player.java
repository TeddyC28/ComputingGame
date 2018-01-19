package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.input.Keyboard;

public class Player extends Mob {
		
	private Keyboard input;
	//private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	
	public Player(Keyboard input) {
		this.input = input;
		this.x = 200;
		this.y = 200;
		this.roomX = 10;
		this.roomY = 10;
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
			move(xa, ya);
		} else {
			walking = false;
			//dir = -1;
		}
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
		
		screen.renderPlayer(x - 16, y - 16, sprite);
	}

}

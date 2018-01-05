package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.input.Keyboard;

public class Player extends Mob {
		
	private Keyboard input;
	private Sprite sprite;
	private boolean walking = false;
	
	public Player(Keyboard input, Sprite sprite) {
		this.input = input;
		this.sprite = sprite;
		this.x = 200;
		this.y = 200;
	}
	
	public void update() {
		int xa = 0, ya = 0; //variables for handling movement on the axis
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;
		if (xa != 0 || ya != 0) { //if the player is moving
			move(xa, ya);
		} else {
			//walking = false;
			//dir = -1;
		}
		//if (dir == 0) x++;
		//if (dir == 1) y--;
		//if (dir == 2) x--;
		//if (dir == 3) y++;
	}
	
	public void render(Screen screen) {
		screen.renderPlayer(x, y, sprite);
	}

}

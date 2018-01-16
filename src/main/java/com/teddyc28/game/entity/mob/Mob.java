package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.level.room.Room;

public class Mob {
    
    public int x, y;

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;
	
	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;    

        if (!collision(xa, ya)) {
            x += xa;
            y += ya;
        }
		
	}
	
	//this will handle all of the logic for each mob
	public void update() {	
	}
	
	//this will handle the visuals for each mob
	public void render() {
	}
	
	//this will handle projectiles for mobs when they are implemented
	public void shoot() {
	}
	
	//this will handle when collisions are detected when we implement this
	private boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 10 - 7) / 16;
			int yt = ((y + ya) + c / 2 * 15) / 16;
			if (Room.spawnRoom.getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	

}

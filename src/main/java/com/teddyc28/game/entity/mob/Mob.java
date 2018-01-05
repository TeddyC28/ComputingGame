package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.Sprite;

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
        
        if (!collision()) {
            x += xa;
            y += ya;
        }
		
		if (xa > 0) dir = 0;
		if (xa < 0) dir = 2;
		if (ya > 0) dir = 3;
		if (ya < 0) dir = 1;
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
	public boolean collision() {
		return false; //return false for now because of the boolean type for the method
	}
	

}

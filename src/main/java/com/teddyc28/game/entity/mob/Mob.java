package com.teddyc28.game.entity.mob;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.entity.projectile.Projectile;
import com.teddyc28.game.entity.projectile.BallProjectile;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;

public abstract class Mob extends Entity {
    
	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;
	
	
    public Sprite getSprite() {
        return sprite;
    }
    
    public int getSpriteSize() {
        return sprite.SIZE;
    }

	public void move(int xa, int ya, boolean allowDoors) {
		if (xa != 0 && ya != 0) {
			move(xa, 0, allowDoors);
			move(0, ya, allowDoors);
			return;
		}
		
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;    

		if (allowDoors) {		
			System.out.println("Allow Doors");
			if (checkDoor(xa, ya) >= 0) {
				roomChange(checkDoor(xa, ya));
				return;
			}
		}
		if (checkDoor(xa, ya) >= 0) return;
        if (!collision(xa, ya)) {
            x += xa;
			y += ya;
		}
		
	}
	
	//this will handle all of the logic for each mob
	public abstract void update();
	
	//this will handle the visuals for each mob
	public abstract void render(Screen screen);
	
	//this will handle projectiles for mobs when they are implemented
	public void shoot(int x, int y, double dir) {
		Projectile p = new BallProjectile(x, y, dir);
		p.initLevel(level);
		p.initRoom(roomX, roomY);
		level.rooms[roomX + roomY * level.width].add(p);
	}
	
	//this will handle when collisions are detected when we implement this
	protected boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 10 - 7) / 16;
			int yt = ((y + ya) + c / 2 * 15) / 16;
			if (level.rooms[roomX + roomY * level.width].getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	
	private int checkDoor(int xa, int ya) {
		for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 10 - 7) / 16;
			int yt = ((y + ya) + c / 2 * 15) / 16;
			if (level.rooms[roomX + roomY * level.width].getTile(xt, yt).doorDirection() >= 0) {
				return level.rooms[roomX + roomY * level.width].getTile(xt, yt).doorDirection();
			}
		}
		return -1;
	}

	private void roomChange(int dir) {
		if (roomY != 0 && dir ==0) { 
			roomY--;
			x = 256;
			y = 256;
		}
		if (roomX != level.width && dir == 1) { 
			roomX++;
			x = 32;
			y = 144;
		}
		if (roomY != level.height && dir == 2) {
			roomY++;
			x = 256;
			y = 32;
		}
		if (roomX != 0 && dir ==3) { 
			roomX--;
			x = 480;
			y = 144;
		}
		//level.rooms[roomX + roomY * level.width].entities = new ArrayList<Entity>();
	}

}

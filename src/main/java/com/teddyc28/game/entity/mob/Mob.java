package com.teddyc28.game.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.entity.projectile.BallProjectile;
import com.teddyc28.game.entity.projectile.Projectile;
import com.teddyc28.game.graphics.Screen;

public abstract class Mob extends Entity {
	
	protected int health = 10;
	protected double speed = 1;
	protected int rateOfFire = BallProjectile.FIRE_RATE;
	protected int fireRate = BallProjectile.FIRE_RATE;
	protected boolean moving = false;
	protected boolean walking = false;
	
    protected enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

	protected Direction dir;
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	protected void move(double xa, double ya, boolean allowDoors) {
		if (xa != 0 && ya != 0) {
			move(xa, 0, allowDoors);
			move(0, ya, allowDoors);
			return;
		}
		
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;    

		//if (checkDoor(xa, ya) >= 0) return;
		
		while (xa != 0) {
			if (allowDoors && (checkDoor(abs(xa), ya) >= 0)) {
				roomChange(checkDoor(abs(xa), ya));
				return;
			}
			if ((Math.abs(xa) - 1) > 0) {
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}

		while (ya != 0) {
			if (allowDoors && (checkDoor(xa, abs(ya)) >= 0)) {
				roomChange(checkDoor(xa, abs(ya)));
				return;
			}
			if ((Math.abs(ya) - 1) > 0) {
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}

	}

	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}
	
	//this will handle all of the logic for each mob
	public abstract void update();
	
	//this will handle the visuals for each mob
	public abstract void render(Screen screen);
	
	//this will handle projectiles for mobs when they are implemented
	public void shoot(double x, double y, double dir, List<Mob> targets) {
		Projectile p = new BallProjectile(x, y, dir, targets);
		p.initLevel(level);
		p.initRoom(roomX, roomY);
		level.rooms[roomX + roomY * level.width].add(p);
	}
	
	//this will handle when collisions are detected when we implement this
	protected boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 2 - 8) / 16;
			double yt = ((y + ya) - c / 2) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.rooms[roomX + roomY * level.width].getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}
	
	private int checkDoor(double xa, double ya) {
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 2 - 8) / 16;
			double yt = ((y + ya) - c / 2) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.rooms[roomX + roomY * level.width].getTile(ix, iy).doorDirection() >= 0) {
				return level.rooms[roomX + roomY * level.width].getTile(ix, iy).doorDirection();
			}
		}
		return -1;
	}

	private void roomChange(int dir) {
		if (roomY != 0 && dir == 0) {
			level.rooms[roomX + (roomY - 1) * level.width].setPlayer((Player) this);
			level.rooms[roomX + roomY * level.width].setPlayer(null);
			roomY--;
			x = 256;
			y = 256;
			level.rooms[roomX + roomY * level.width].getPlayer().setAllowDoors(false);			
		}
		if (roomX != level.width && dir == 1) { 
			level.rooms[(roomX + 1) + roomY * level.width].setPlayer((Player) this);
			level.rooms[roomX + roomY * level.width].setPlayer(null);
			roomX++;
			x = 32;
			y = 144;
			level.rooms[roomX + roomY * level.width].getPlayer().setAllowDoors(false);
		}
		if (roomY != level.height && dir == 2) {
			level.rooms[roomX + (roomY + 1) * level.width].setPlayer((Player) this);
			level.rooms[roomX + roomY * level.width].setPlayer(null);
			roomY++;
			x = 256;
			y = 32;
			level.rooms[roomX + roomY * level.width].getPlayer().setAllowDoors(false);
		}
		if (roomX != 0 && dir ==3) { 
			level.rooms[(roomX - 1) + roomY * level.width].setPlayer((Player) this);
			level.rooms[roomX + roomY * level.width].setPlayer(null);
			roomX--;
			x = 480;
			y = 144;
			level.rooms[roomX + roomY * level.width].getPlayer().setAllowDoors(false);
		}
	}

	protected void checkDead() {
        if (this.health == 0) {
			level.rooms[roomX + roomY * level.width].entities.remove(this);
			Player p = level.rooms[roomX + roomY * level.width].getPlayer();
			p.setScore(p.getScore() + 1);
        }
	}

	protected void updateProjectiles() {
		for (int i = 0; i < level.rooms[roomX + roomY * level.width].getProjectiles().size(); i++) {
			Projectile p = level.rooms[roomX + roomY * level.width].getProjectiles().get(i);
			
			if (p.isRemoved()) level.rooms[roomX + roomY * level.width].getProjectiles().remove(i);
		}
	}

	protected void updateShooting() {
		if (fireRate > 0) return;
        Player target = level.rooms[roomX + roomY * level.width].getPlayer();
        double angle = Math.atan2(target.getY() - y, target.getX() - x);
        List<Mob> temp = new ArrayList<Mob>();
        temp.add(target);
		shoot(x, y, angle, temp);
		fireRate = rateOfFire;
	}

}

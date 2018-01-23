package com.teddyc28.game.entity.projectile;

import com.teddyc28.game.entity.spawner.ParticleSpawner;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;

public class BallProjectile extends Projectile {

	public static final int FIRE_RATE = 10;
    
  public BallProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 20;
		sprite = Sprite.projectile_ball;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
  }
    
  public void update() {
		move();
		if (level.rooms[roomX + roomY * level.width].tileCollision(x, y, nx, ny, 7)) { 
			level.rooms[roomX + roomY * level.width].add(new ParticleSpawner((int) x, (int) y, 44, 50, level, roomX, roomY));
			remove();
		}
		if (distance() > range) remove();

	}
	
	protected void move() {
		if (!level.rooms[roomX + roomY * level.width].tileCollision(x, y, nx, ny, 7)) 
		x += nx;
		y += ny;
	}

	private double distance() {
		double dist = Math.sqrt(Math.abs(Math.pow((xOrigin - x), 2) + Math.pow((yOrigin - y), 2)));
		return dist;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x - 12, (int) y - 3, this);
    }
    
}
package com.teddyc28.game.entity.projectile;

import com.teddyc28.game.entity.mob.Mob;
import com.teddyc28.game.entity.spawner.ParticleSpawner;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import java.util.List;

public class BallProjectile extends Projectile {

	public static final int FIRE_RATE = 10;
    
  public BallProjectile(double x, double y, double dir, List<Mob> targets) {
		super(x, y, dir, targets);
		range = 200;
		speed = 4;
		damage = 20;
		sprite = Sprite.projectile_ball;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
  }
    
  public void update() {
		move();
		if (level.rooms[roomX + roomY * level.width].tileCollision(x + nx,y + ny, 7, 5, 4)) {
			collision();
		}
		if (distance() > range) remove();
		for (int i = 0; i < targets.size(); i++) {
			Mob target = targets.get(i);
			if (level.rooms[roomX + roomY * level.width].mobCollision(x, y, 7, 5, 4, target)) {
				target.setHealth(target.getHealth() - 1);
				collision();
			}
		}
	}
	
	protected void move() {
		if (!level.rooms[roomX + roomY * level.width].tileCollision(x + nx,y + ny, 7, 5, 4)) 
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
	
	private void collision() {
		level.rooms[roomX + roomY * level.width].add(new ParticleSpawner((int) x, (int) y, 44, 50, level, roomX, roomY));
		remove();
	}

}
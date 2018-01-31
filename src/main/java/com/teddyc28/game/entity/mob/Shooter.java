package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.level.Level;

public class Shooter extends Mob {
    

    public Shooter(int x, int y, Level level, int rx, int ry) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = Sprite.dummy;
        initLevel(level);
        initRoom(rx, ry);
        this.rateOfFire = 100;
    }

	public void update() {
        checkDead();
        if (fireRate > 0) fireRate--;

		updateShooting();
		
		updateProjectiles();
    }

	public void render(Screen screen) {
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
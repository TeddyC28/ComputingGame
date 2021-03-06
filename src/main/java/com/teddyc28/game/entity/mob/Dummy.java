package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.AnimatedSprite;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.graphics.SpriteSheet;
import com.teddyc28.game.level.Level;

public class Dummy extends Mob {

    private int xa = 0, ya = 0;

    private int time = 0;
    
    private AnimatedSprite up = new AnimatedSprite(32, SpriteSheet.dummy_up, 3);
	private AnimatedSprite down = new AnimatedSprite(32, SpriteSheet.dummy_down, 3);
	private AnimatedSprite left = new AnimatedSprite(32, SpriteSheet.dummy_left, 3);
	private AnimatedSprite right = new AnimatedSprite(32, SpriteSheet.dummy_right, 3);
    private AnimatedSprite animSprite = down;
    
    public Dummy(int x, int y, Level level, int rx, int ry) {
        this.x = x << 4;
        this.y = y << 4;
        this.sprite = Sprite.dummy;
        initLevel(level);
        initRoom(rx, ry);
        this.rateOfFire = 50;
    }
    
    public void update() {
        checkDead();
        time++;
        if (fireRate > 0) fireRate--;
        if (time % (random.nextInt(50) + 30) == 0) {
            xa = random.nextInt(3) - 1;
            ya = random.nextInt(3) - 1;
            if (random.nextInt(5) == 0) {
                xa = 0;
                ya = 0;
            }
        }
        if (walking) animSprite.update();
        else animSprite.setFrame(0);
        if (ya < 0) {
            animSprite = up;
            dir = Direction.UP;
		} else if (ya > 0) {
            animSprite = down;
            dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
            dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
            dir = Direction.RIGHT;
        }

		if (xa != 0 || ya != 0) {
			move(xa, ya, false);
			walking = true;
		} else {
			walking = false;
        }
        

		updateShooting();
		
		updateProjectiles();
    }
    
    public void render(Screen screen) {
        screen.renderMob((int) (x - 16), (int) (y - 16), animSprite.getSprite());
    }
}
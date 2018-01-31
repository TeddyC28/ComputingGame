package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.AnimatedSprite;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.graphics.SpriteSheet;
import com.teddyc28.game.level.Level;

public class Chaser extends Mob {

    private AnimatedSprite up = new AnimatedSprite(32, SpriteSheet.dummy_up, 3);
	private AnimatedSprite down = new AnimatedSprite(32, SpriteSheet.dummy_down, 3);
	private AnimatedSprite left = new AnimatedSprite(32, SpriteSheet.dummy_left, 3);
	private AnimatedSprite right = new AnimatedSprite(32, SpriteSheet.dummy_right, 3);
    private AnimatedSprite animSprite = down;

    private Player target;

    private double xa = 0, ya = 0;
    private double speed = 0.8;

    public Chaser(int x, int y, Level level, int rx, int ry) {
        this.x = x << 4;
        this.y = y << 4;
        this.sprite = Sprite.dummy;
        initLevel(level);
        initRoom(rx, ry);
    }
    
    private void move() {
        xa = 0;
        ya = 0;
        target = level.rooms[roomX + roomY * level.width].getPlayer();
        if (!(level.rooms[roomX + roomY * level.width].getEntities(target, 30).size() > 0)) {
            if ((int) x < (int) target.getX()) xa += speed;
            if ((int) x > (int) target.getX()) xa -= speed;
            if ((int) y < (int) target.getY()) ya += speed;
            if ((int) y > (int) target.getY()) ya -= speed;
        }
		if (xa != 0 || ya != 0) {
			move(xa, ya, false);
			walking = true;
		} else {
			walking = false;
		}
    }

	public void update() {
        checkDead();
        move();
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
    }

	public void render(Screen screen) {
        sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
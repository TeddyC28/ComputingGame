package com.teddyc28.game.entity.mob;

import java.util.List;

import com.teddyc28.game.graphics.AnimatedSprite;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.graphics.SpriteSheet;
import com.teddyc28.game.level.Level;
import com.teddyc28.game.level.Node;
import com.teddyc28.game.util.Vector2i;

public class Follower extends Mob {

    private AnimatedSprite up = new AnimatedSprite(32, SpriteSheet.dummy_up, 3);
	private AnimatedSprite down = new AnimatedSprite(32, SpriteSheet.dummy_down, 3);
	private AnimatedSprite left = new AnimatedSprite(32, SpriteSheet.dummy_left, 3);
	private AnimatedSprite right = new AnimatedSprite(32, SpriteSheet.dummy_right, 3);
    private AnimatedSprite animSprite = down;

    private Player target;

    private double xa = 0, ya = 0;
    private double speed = 1;
    private List<Node> path = null;
    private int time = 0;

    public Follower(int x, int y, Level level, int rx, int ry) {
        this.x = x << 4;
        this.y = y << 4;
        this.sprite = Sprite.dummy;
        initLevel(level);
        initRoom(rx, ry);
        this.rateOfFire = 50;
    }
    
    private void move() {
        xa = 0;
        ya = 0;
        target = level.rooms[roomX + roomY * level.width].getPlayer();
        if (!(level.rooms[roomX + roomY * level.width].getEntities(target, 30).size() > 0)) {
            int px = (int) level.rooms[roomX + roomY * level.width].getPlayer().getX();
            int py = (int) level.rooms[roomX + roomY * level.width].getPlayer().getY();
            Vector2i start = new Vector2i((int) x >> 4, (int) y >> 4);
            Vector2i destination = new Vector2i(px >> 4, py >> 4);
            path = level.rooms[roomX + roomY * level.width].findPath(start, destination);
            if (path != null) {
                if (path.size() > 0) {
                    Vector2i vec = path.get(path.size() - 1).location;
                    if (x < (vec.getX() << 4)) xa += speed;
                    if (x > (vec.getX() << 4)) xa -= speed;
                    if (y < (vec.getY() << 4)) ya += speed;
                    if (y > (vec.getY() << 4)) ya -= speed;
                }
            }
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
        time++;
        move();
        if (fireRate > 0) fireRate--;
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
        

		updateShooting();
		
		updateProjectiles();
    }
    

	public void render(Screen screen) {
        sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
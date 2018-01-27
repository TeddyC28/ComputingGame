package com.teddyc28.game.entity.mob;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.level.Level;

public class Dummy extends Mob {

    private int xm, ym;

    public Dummy(int x, int y, Level level, int rx, int ry) {
        this.x = x << 4;
        this.y = y << 4;
        this.xm = 1;
        this.ym = 1;
        this.sprite = Sprite.player_forward;
        initLevel(level);
        initRoom(rx, ry);
    }

    public void update() {
        if (random.nextInt(100) % 20 ==0) {
            xm *= -1;
        }
        if (random.nextInt(100) % 50 ==0) {
            ym *= -1;
        }
        move(xm, ym, false); 
    }
    
    public void render(Screen screen) {
        screen.renderMob(x - 16, y - 16, this);
    }
}
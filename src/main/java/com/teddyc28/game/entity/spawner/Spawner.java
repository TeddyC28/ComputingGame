package com.teddyc28.game.entity.spawner;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.Level;


public class Spawner extends Entity {

    public enum Type {
        MOB, PARTICLE;
    }

    private Type type;

    public Spawner(int x, int y, Type type, int amount, Level level, int rx, int ry) {
        initLevel(level);
        initRoom(rx, ry);
        this.x = x;
        this.y = y;
        this.type = type;
        
    }

    public void render(Screen screen) {
    }

}
package com.teddyc28.game.entity;

import java.util.Random;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.Level;

public abstract class Entity {

    public int x, y;
    public int roomX, roomY;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();

    public void update() {
    }

    public void render(Screen screen) {
    }

    public void remove() {
        /* Remove from room. */
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void initLevel(Level level) {
        this.level = level;
    }
    
    public void initRoom(int rx, int ry) {
        this.roomX = rx;
        this.roomY = ry;
    }
}
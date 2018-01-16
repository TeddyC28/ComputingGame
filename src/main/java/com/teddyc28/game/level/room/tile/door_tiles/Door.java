package com.teddyc28.game.level.room.tile.door_tiles;

import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.level.room.tile.Tile;

public class Door extends Tile {

    public Door(Sprite sprite) {
        super(sprite);
    }

    public boolean throughDoor() {
        return false;
    }

}
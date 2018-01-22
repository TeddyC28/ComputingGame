package com.teddyc28.game.level.room.tile.door_tiles;

import com.teddyc28.game.graphics.Sprite;

public class LeftDoorTile extends Door {

    public LeftDoorTile(Sprite sprite) {
        super(sprite);
    }

    public int doorDirection() {
        return 3;
    }
}
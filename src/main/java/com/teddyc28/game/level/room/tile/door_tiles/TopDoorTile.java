package com.teddyc28.game.level.room.tile.door_tiles;

import com.teddyc28.game.graphics.Sprite;

public class TopDoorTile extends Door {

    public TopDoorTile(Sprite sprite) {
        super(sprite);
    }

    public int doorDirection() {
        return 0;
    }
}
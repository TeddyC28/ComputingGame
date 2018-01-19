package com.teddyc28.game.level.room.tile.door_tiles;

import com.teddyc28.game.graphics.Sprite;

public class BottomDoorTile extends Door {

    public BottomDoorTile(Sprite sprite) {
        super(sprite);
        name = "bdt";
    }

    public int doorDirection() {
        return 2;
    }
}
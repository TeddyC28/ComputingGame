package com.teddyc28.game.level.room.tile;

import com.teddyc28.game.graphics.Sprite;

public class WallEdgeTile extends Tile {

    public WallEdgeTile(Sprite sprite) {
        super(sprite);
    }

    public boolean solid() {
        return true;
    }

}
package com.teddyc28.game.level.room.tile;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;

public class FloorTile extends Tile
{
    public FloorTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x, y, this);
    }

}
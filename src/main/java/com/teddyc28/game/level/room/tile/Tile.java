package com.teddyc28.game.level.room.tile;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
//import com.teddyc28.game.level.room.tile.FloorTile;

public class Tile 
{
    public Sprite sprite;

    public static Tile floor = new Tile(Sprite.floor);
    public static Tile wallEdge = new Tile(Sprite.wallEdge);
    public static Tile wallCorner = new Tile(Sprite.wallCorner);
    public static Tile topDoorLeft = new Tile(Sprite.topDoorLeft);
    public static Tile topDoorRight = new Tile(Sprite.topDoorRight);
    public static Tile bottomDoorLeft = new Tile(Sprite.bottomDoorLeft);
    public static Tile bottomDoorRight = new Tile(Sprite.bottomDoorRight);
    public static Tile rightDoorTop = new Tile(Sprite.rightDoorTop);
    public static Tile rightDoorBottom = new Tile(Sprite.rightDoorBottom);
    public static Tile leftDoorTop = new Tile(Sprite.leftDoorTop);
    public static Tile leftDoorBottom = new Tile(Sprite.leftDoorBottom);
    public static Tile voidTile = new Tile(Sprite.voidSprite);

    
    
    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x, y, this);
    }

    public boolean solid() {
        return false;
    }
}
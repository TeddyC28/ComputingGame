package com.teddyc28.game.level.room.tile;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;
import com.teddyc28.game.level.room.tile.FloorTile;
import com.teddyc28.game.level.room.tile.door_tiles.*;

public class Tile 
{
    public Sprite sprite;
    public String name = "dwa";

    public static Tile floor = new FloorTile(Sprite.floor);
    public static Tile wallEdge = new WallEdgeTile(Sprite.wallEdge);
    public static Tile topDoorLeft = new TopDoorTile(Sprite.topDoorLeft);
    public static Tile topDoorRight = new TopDoorTile(Sprite.topDoorRight);
    public static Tile bottomDoorLeft = new BottomDoorTile(Sprite.bottomDoorLeft);
    public static Tile bottomDoorRight = new BottomDoorTile(Sprite.bottomDoorRight);
    public static Tile rightDoorTop = new RightDoorTile(Sprite.rightDoorTop);
    public static Tile rightDoorBottom = new RightDoorTile(Sprite.rightDoorBottom);
    public static Tile leftDoorTop = new LeftDoorTile(Sprite.leftDoorTop);
    public static Tile leftDoorBottom = new LeftDoorTile(Sprite.leftDoorBottom);
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

    public int doorDirection() {
        return -1;
    }
}
package com.teddyc28.game.level.room;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.room.tile.Tile;

public class Room {

    protected int[] tiles;

    public static Room spawnRoom = new Room("/textures/rooms/spawn_room.png");

    public Room() {

    }

    public Room(String path) {
        loadRoom(path);
    }
    
    protected void generateRoom() {
        for (int y = 0; y < Screen.ROOM_HEIGHT; y++) {
            for (int x = 0; x < Screen.ROOM_WIDTH; x++) {
                getTile(x, y);  
            }
        }
    }

    protected void loadRoom(String path) {
        try {
            System.out.println(path);
            BufferedImage image = ImageIO.read(Room.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            tiles = new int[w * h];
            image.getRGB(0, 0, w, h, tiles, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read room file");
        }
        for (int i = 0; i < tiles.length; i++) {
            System.out.println(tiles[i]);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render(Screen screen) {
        for (int y = 0; y < Screen.ROOM_HEIGHT; y++) {
            for (int x = 0; x < Screen.ROOM_WIDTH; x++) {
                screen.renderTile(x, y, getTile(x, y));
            }
        }
    }

    public Tile getTile(int x, int y) {
        System.out.println(tiles[x + y * Screen.ROOM_WIDTH]);
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0x3CFF35) return Tile.floor;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xFF0800) return Tile.wallEdge;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0x000000) return Tile.leftDoorTop;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0x4F3FFF) return Tile.leftDoorBottom;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0x8ED2FF) return Tile.topDoorLeft;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0x5EFFF9) return Tile.topDoorRight;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0x47FFB2) return Tile.rightDoorTop;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xD0FF4F) return Tile.rightDoorBottom;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xFFAF4F) return Tile.bottomDoorLeft;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xFF566D) return Tile.bottomDoorRight;
        return Tile.voidTile;
    }

}
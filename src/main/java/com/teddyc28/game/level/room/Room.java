package com.teddyc28.game.level.room;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.room.tile.Tile;

public class Room {

    protected int[] tiles;

    public static Room spawnRoom = new Room("/textures/rooms/spawn_room.png");
    
    public static Room topLeftCornerRoom = new Room("/textures/rooms/top_left_room.png");
    public static Room topRighttCornerRoom = new Room("/textures/rooms/top_right_room.png");
    public static Room bottomLeftCornerRoom = new Room("/textures/rooms/bottom_left_room.png");
    public static Room bottomRightCornerRoom = new Room("/textures/rooms/bottom_right_room.png");

    public static Room leftEdgeRoom = new Room("/textures/rooms/left_edge_room.png");
    public static Room rightEdgeRoom = new Room("/textures/rooms/right_edge_room.png");
    public static Room topEdgeRoom = new Room("/textures/rooms/top_edge_room.png");
    public static Room bottomEdgeRoom = new Room("/textures/rooms/bottom_edge_room.png");

    public static Room voidRoom = new Room();

    public Room(String path) {
        tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        loadRoom(path);
    }

    public Room() {
        tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = 0xff000074;
        }
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
            BufferedImage image = ImageIO.read(Room.class.getResource(path));
            tiles = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), tiles, 0, image.getWidth());
        } catch (IOException e) {
            System.out.println("Failed to read room file");
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
		if (x < 0 || y < 0 || x >= Screen.ROOM_WIDTH || y >= Screen.ROOM_HEIGHT) return Tile.voidTile;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xff3CFF35) return Tile.floor;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xffFF0800) return Tile.wallEdge;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xff000000) return Tile.leftDoorTop;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xff4F3FFF) return Tile.leftDoorBottom;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xff8ED2FF) return Tile.topDoorLeft;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xff5EFFF9) return Tile.topDoorRight;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xff47FFB2) return Tile.rightDoorTop;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xffD0FF4F) return Tile.rightDoorBottom;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xffFFAF4F) return Tile.bottomDoorLeft;
        if (tiles[x + y * Screen.ROOM_WIDTH] == 0xffFF566D) return Tile.bottomDoorRight;
        return Tile.voidTile;
    }

}
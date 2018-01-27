package com.teddyc28.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.room.Room;

public class Level {

    public int width, height;
    protected int[] roomsImg;
    public Room[] rooms;

    public static Level spawnLevel = new Level("/textures/levels/default_level.png");

    public Level(String path) {
        loadLevel(path);
        generateLevel();
    }

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        roomsImg = new int[width * height];
        for (int i = 0; i < roomsImg.length; i++) {
            roomsImg[i] = 0xff3CFF35;
        }
        generateLevel();
    }

    private void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rooms[x + y * Screen.ROOM_HEIGHT] = new Room(getRoom(x, y));
            }
        }
    }

    public void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            roomsImg = new int[width * height];
            rooms = new Room[width * height];
            image.getRGB(0, 0, width, height, roomsImg, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String getRoom(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        if (roomsImg[x + y * width] == 0xffFF0800) return "/textures/rooms/top_left_room.png";
        if (roomsImg[x + y * width] == 0xff000000) return "/textures/rooms/top_right_room.png";
        if (roomsImg[x + y * width] == 0xff4F3FFF) return "/textures/rooms/bottom_left_room.png";
        if (roomsImg[x + y * width] == 0xff8ED2FF) return "/textures/rooms/bottom_right_room.png";
        if (roomsImg[x + y * width] == 0xff5EFFF9) return "/textures/rooms/left_edge_room.png";
        if (roomsImg[x + y * width] == 0xffFFAF4F) return "/textures/rooms/right_edge_room.png";
        if (roomsImg[x + y * width] == 0xff47FFB2) return "/textures/rooms/top_edge_room.png";
        if (roomsImg[x + y * width] == 0xffD0FF4F) return "/textures/rooms/bottom_edge_room.png";
        if (roomsImg[x + y * width] == 0xff3CFF35) return "/textures/rooms/spawn_room.png";
        return null;
    }

}
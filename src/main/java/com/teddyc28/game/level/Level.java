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
                rooms[x + y * Screen.ROOM_HEIGHT] = getRoom(x, y);
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
    
    private Room getRoom(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return Room.voidRoom;
        if (roomsImg[x + y * width] == 0xffFF0800) return Room.topLeftCornerRoom;
        if (roomsImg[x + y * width] == 0xff000000) return Room.topRighttCornerRoom;
        if (roomsImg[x + y * width] == 0xff4F3FFF) return Room.bottomLeftCornerRoom;
        if (roomsImg[x + y * width] == 0xff8ED2FF) return Room.bottomRightCornerRoom;
        if (roomsImg[x + y * width] == 0xff5EFFF9) return Room.leftEdgeRoom;
        if (roomsImg[x + y * width] == 0xffFFAF4F) return Room.rightEdgeRoom;
        if (roomsImg[x + y * width] == 0xff47FFB2) return Room.topEdgeRoom;
        if (roomsImg[x + y * width] == 0xffD0FF4F) return Room.bottomEdgeRoom;
        if (roomsImg[x + y * width] == 0xff3CFF35) return Room.spawnRoom;
        return Room.voidRoom;
    }

}
package com.teddyc28.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.teddyc28.game.level.room.Room;

public class Level {

    public int width, height;
    protected int[] rooms;

    public static Level spawnLevel = new Level("/textures/levels/default_level.png");

    public Level(String path) {
        loadLevel(path);
    }

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        rooms = new int[width * height];
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = 0xff3CFF35;
        }
    }

    public void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            rooms = new int[width * height];
            image.getRGB(0, 0, width, height, rooms, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//FF0800
    public Room getRoom(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return Room.voidRoom;
        if (rooms[x + y * width] == 0xffFF0800) return Room.topLeftCornerRoom;
        if (rooms[x + y * width] == 0xff000000) return Room.topRighttCornerRoom;
        if (rooms[x + y * width] == 0xff4F3FFF) return Room.bottomLeftCornerRoom;
        if (rooms[x + y * width] == 0xff8ED2FF) return Room.bottomRightCornerRoom;
        if (rooms[x + y * width] == 0xff5EFFF9) return Room.leftEdgeRoom;
        if (rooms[x + y * width] == 0xffFFAF4F) return Room.rightEdgeRoom;
        if (rooms[x + y * width] == 0xff47FFB2) return Room.topEdgeRoom;
        if (rooms[x + y * width] == 0xffD0FF4F) return Room.bottomEdgeRoom;
        if (rooms[x + y * width] == 0xff3CFF35) return Room.spawnRoom;
        return Room.voidRoom;
    }

}
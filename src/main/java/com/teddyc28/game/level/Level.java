package com.teddyc28.game.level;

import com.teddyc28.game.level.room.Room;

public class Level {

    protected int width, height;
    protected int[] rooms;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        rooms = new int[width * height];
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = 0xff3CFF35;
        }
    }

    public Room getRoom(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Room.voidRoom;
        if (rooms[x + y * width] == 0xff3CFF35) return Room.spawnRoom;
        return Room.voidRoom;
    }

}
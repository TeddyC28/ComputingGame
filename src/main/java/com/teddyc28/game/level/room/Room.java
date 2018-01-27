package com.teddyc28.game.level.room;

import java.util.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.entity.particle.Particle;
import com.teddyc28.game.entity.projectile.Projectile;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.room.tile.Tile;

public class Room {

    protected int[] tiles;

    public List<Entity> entities;
    public List<Projectile> projectiles;
    public List<Particle> particles;
    
    public static Room spawnRoom = new Room("/textures/rooms/spawn_room.png");
    
    public static Room topLeftCornerRoom = new Room("/textures/rooms/top_left_room.png");
    public static Room topRightCornerRoom = new Room("/textures/rooms/top_right_room.png");
    public static Room bottomLeftCornerRoom = new Room("/textures/rooms/bottom_left_room.png");
    public static Room bottomRightCornerRoom = new Room("/textures/rooms/bottom_right_room.png");

    public static Room leftEdgeRoom = new Room("/textures/rooms/left_edge_room.png");
    public static Room rightEdgeRoom = new Room("/textures/rooms/right_edge_room.png");
    public static Room topEdgeRoom = new Room("/textures/rooms/top_edge_room.png");
    public static Room bottomEdgeRoom = new Room("/textures/rooms/bottom_edge_room.png");

    public static Room voidRoom = new Room(null);

    public Room(String path) {
        tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        this.entities = new ArrayList<Entity>();
        this.projectiles = new ArrayList<Projectile>();
        this.particles = new ArrayList<Particle>();
        if (path != null) loadRoom(path);
        else {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = 0xff000074;
            }
        }
    }

    //public Room() {
      //  tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        //this.entities = new ArrayList<Entity>();
        //this.projectiles = new ArrayList<Projectile>();
        //this.particles = new ArrayList<Particle>();
    //}

    public List<Entity> getEntities() {
        return entities;
    }
    public List<Projectile> getProjectiles() {
        return projectiles;
    }
    public List<Particle> getParticles() {
        return particles;
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

    public void update() {
		for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }

        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update();
        }

        remove();
    }
    
    private void remove() {
		for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isRemoved()) entities.remove(i);
        }

        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved()) projectiles.remove(i);
        }

        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isRemoved()) particles.remove(i);
        }
    }
    
    public boolean tileCollision(double x, double y, double xa, double ya, int size) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) x + (int) xa) + c % 2 * size * 2 - 12 /*10 - 7*/) / 16;
			int yt = (((int) y + (int) ya) + c / 2 * size + 2 /*15*/) / 16;
			if (getTile( xt, yt).solid()) solid = true;
		}
		return solid;
	}

    public void render(Screen screen) {
        for (int y = 0; y < Screen.ROOM_HEIGHT; y++) {
            for (int x = 0; x < Screen.ROOM_WIDTH; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
        }

        for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).render(screen);
        }
    }

    public void add(Entity e) {
        if (e instanceof Particle) {
            particles.add((Particle) e);
        } else if (e instanceof Projectile) {
            projectiles.add((Projectile) e);
        } else {
            entities.add(e);
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
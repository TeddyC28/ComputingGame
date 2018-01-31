package com.teddyc28.game.level.room;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.entity.mob.Mob;
import com.teddyc28.game.entity.mob.Player;
import com.teddyc28.game.entity.particle.Particle;
import com.teddyc28.game.entity.projectile.Projectile;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.level.Node;
import com.teddyc28.game.level.room.tile.Tile;
import com.teddyc28.game.util.Vector2i;

public class Room {

    protected int[] tiles;
    private Player player;
    Random random = new Random();

    public List<Entity> entities;
    public List<Projectile> projectiles;
    public List<Particle> particles;
    
    private Comparator<Node> nodeSorter = new Comparator<Node>() {
        public int compare(Node node0, Node node1) {
            if (node1.fCost < node0.fCost) return 1;
            if (node1.fCost > node0.fCost) return -1;
            return 0;
        }
    };

    public Room(String path) {
        tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        this.entities = new ArrayList<Entity>();
        this.projectiles = new ArrayList<Projectile>();
        this.particles = new ArrayList<Particle>();
        if (path == null) {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = 0xff000074;
            }
        }
        else {
            loadRoom(path);
            //while (true) {
              //  for (int i = 0; i < tiles.length; i++) {
                //    if (tiles[i] == 0xff3CFF35 && random.nextInt(10) == 0) tiles[i] = 0xffFF0800;
                //}

                //List<Node> check = findPath(new Vector2i(1, 1), new Vector2i(30, 16));
                //if (check != null) break;
            //}
        }
    }

    public Room() {
        this.entities = new ArrayList<Entity>();
        this.projectiles = new ArrayList<Projectile>();
        this.particles = new ArrayList<Particle>();
        tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        while (true) {
            for (int i = 0; i < tiles.length; i++) {
                if (random.nextInt(5) == 0) tiles[i] = 0xffFF0800;
                else tiles[i] = 0xff3CFF35;
            }
            List<Node> check = findPath(new Vector2i(), new Vector2i(31, 17));
            if (check != null) break;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //public Room() {
      //  tiles = new int[Screen.ROOM_WIDTH * Screen.ROOM_HEIGHT];
        //this.entities = new ArrayList<Entity>();
        //this.projectiles = new ArrayList<Projectile>();
        //this.particles = new ArrayList<Particle>();
    //}

    public List<Node> findPath(Vector2i start, Vector2i goal) {
        //initialisation of search
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);
        //Beginning the search
        while (openList.size() > 0) {
            //sorting openList in order of fCost using comparator above
            Collections.sort(openList, nodeSorter);

            //set current to first item in openList
            current = openList.get(0);

            //condition for success
            if (current.location.equals(goal)) {
                //reconstructing the path to the finish
                List<Node> path = new ArrayList<Node>();
                //while we are not at the starting node
                while (current.parent != null) {
                    //add the current node to the path
                    path.add(current);
                    //set the current node to the previous step in the path
                    current = current.parent;
                }
                //clearing the lists from memory
                openList.clear();
                closedList.clear();
                //returning the path (back to front)
                return path;
            }
            //moving current from openlist to closedList if it is not the goal
            openList.remove(current);
            closedList.add(current);

            //checking adjacencies
            //creating for loop to go around the current grid (current being central) and check if the next node is closer to the goal
            for (int i = 0; i < 9; i++) {
                //skipping check for current node (i will always be 4 for this)
                if (i == 4) continue;
                //getting x and y location of current node
                int x = current.location.getX();
                int y = current.location.getY();
                //getting x directional value (-1 = backwards, 0 = stationary and 1 = forwards)
                int xd = (i % 3) - 1;
                //getting y directional value (-1 = backwards, 0 = stationary and 1 = forwards)
                int yd = (i / 3) - 1;
                //getting object of current tile
                Tile nextTile = getTile(x + xd, y + yd);
                //if we couldnt get the tile we are trying to
                if (nextTile == null) continue;
                //checking if the tile is solid (cannot use or because currentTile would cause nullpointer exception when checking for solid() -> crash)
                if (nextTile.solid()) continue;
            //is the nextTile viable
                //creating new vector for nextTile location (lct = location of next tile)
                Vector2i lnt = new Vector2i(x + xd, y + yd);
                //calculating gCost
                double gCost = current.gCost + (getDistance(current.location, lnt) == 1 ? 1 : 0.95);
                //calculating hCost
                double hCost = getDistance(lnt, goal);
                //Creating Node for the location we could move to
                Node nextNode = new Node(lnt, current, gCost, hCost);
                //have we already been to the location and the gCost of the current node is greater than the gCost of the next node
                if (vecInList(closedList, lnt) && gCost >= nextNode.gCost) continue;
                //if the location of the nextNode is not in the openList or the gCost of the current node is less than the gCost of the next node then add the nextNode to the openList 
                if (!vecInList(openList, lnt) || gCost < nextNode.gCost) openList.add(nextNode);
            }
        }
        //clearing closedList for memory management
        closedList.clear();
        //returning null (no path found)
        return null;
    }

    private boolean vecInList(List<Node> list, Vector2i vector) {
        for (Node n: list) {
            if (n.location.equals(vector)) return true;
        }
        return false;
    }

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Entity> getEntities(Entity e, int radius) {
        List<Entity> result = new ArrayList<Entity>();
        int ex = (int) e.getX(), ey = (int) e.getY();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            int x = (int) entity.getX(), y = (int) entity.getY();
            double distance = Math.sqrt(Math.pow(x-ex, 2) + Math.pow(y-ey, 2));
            if (distance <= radius) result.add(entity);
        }
        return result;
    }
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
    
    public boolean tileCollision(double x, double y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * size - xOffset) / 16;
			double yt = (y + c / 2 * size - yOffset) / 16;
			if (getTile((int) xt,(int) yt).solid()) solid = true;
		}
		return solid;
	}

    
    public boolean mobCollision(double x, double y, int size, int xOffset, int yOffset, Mob target) {
        boolean hit = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * size - xOffset) / 16;
            double yt = (y + c / 2 * size - yOffset) / 16;
			if ((int) (target.getX() / 16) == (int) xt && (int) (target.getY() / 16) == (int) yt) hit = true;
		}
		return hit;
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
        player.render(screen);
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
package com.teddyc28.game.level;

import com.teddyc28.game.util.Vector2i;

public class Node {
    
    public Vector2i location;
    public Node parent;
    public double fCost, gCost, hCost;

    public Node(Vector2i location, Node parent, double gCost, double hCost) {
        this.location = location;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = this.gCost + this.hCost;
    }

}
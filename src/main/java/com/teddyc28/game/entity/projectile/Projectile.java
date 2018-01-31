package com.teddyc28.game.entity.projectile;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.entity.mob.Mob;
import com.teddyc28.game.graphics.Sprite;
import java.util.List;

public abstract class Projectile extends Entity {

    protected final double xOrigin, yOrigin;
    protected double angle;
    protected Sprite sprite;
    protected double nx, ny;
    protected double distance;
    protected double speed, range, damage;
    protected List<Mob> targets;

    public Projectile(double x, double y, double dir, List<Mob> targets) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
        this.y = y;
        this.targets = targets;
    }

    public Sprite getSprite() {
        return sprite;
    }
    
    public int getSpriteSize() {
        return sprite.getSize();
    }

    protected void move() {
    }
}
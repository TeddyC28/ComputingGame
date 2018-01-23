package com.teddyc28.game.entity.particle;

import java.util.List;
import java.util.ArrayList;

import com.teddyc28.game.entity.Entity;
import com.teddyc28.game.graphics.Screen;
import com.teddyc28.game.graphics.Sprite;

public class Particle extends Entity {

    private static List<Particle> particles = new ArrayList<Particle>();
    private Sprite sprite;

    private int life;
    private int time = 0;

    protected double xx, yy, zz;
    protected double xa, ya, za;

    public Particle(int x, int y, int life) {
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = life + (random.nextInt(30) - 15);
        sprite = Sprite.particle_normal;

        this.xa = random.nextGaussian() + 2.0;
        if (this.xa < 0) xa = 0.1;
        this.ya = random.nextGaussian();
        this.zz = random.nextFloat() + 2.0;
    }

    public Particle(int x, int y, int life, int amount) {
        this(x, y, life);
        for (int i = 0; i < amount - 1; i++) {
            particles.add(new Particle(x, y, life));
        }
        particles.add(this);
    }

    public void update() {
        time++;
        if (time >= 5000) time = 0;
        if (time > life) remove();
        za -= 0.1;

        if (zz < 0) {
            zz = 0;
            za *= -0.5;
            xa *= 0.2;
            ya *= 0.1;
        }

        this.xx += xa;
        this.yy += ya;
        this.zz += za;
    }

    public void render(Screen screen) {
        screen.renderSprite((int) xx - 16, (int) yy - (int) zz, sprite);
    }

}
package com.teddyc28.game.entity.spawner;

import com.teddyc28.game.entity.particle.Particle;
import com.teddyc28.game.level.Level;

public class ParticleSpawner extends Spawner {

    public ParticleSpawner(int x, int y, int life, int amount, Level level, int rx, int ry) {
        super(x, y, Type.PARTICLE, amount, level, rx, ry);
        for (int i = 0; i < amount; i++) {
            level.rooms[roomX + roomY * level.width].add(new Particle(x, y, life));
        }
        this.remove();
    }

}
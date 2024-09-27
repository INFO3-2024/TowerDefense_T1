package io.github.INFO32024.TowerDefense_T1.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Enemy;

public class PirateCaptain extends Enemy{

    public PirateCaptain(Vector2 position, Vector2 size, Queue<Vector2> wayPoints) {
        super(position, size, wayPoints);
        super.dropedCoins = 20;
        super.velocity = 60.f; 
        super.life = 6.f;
        super.maxLife = super.life;
    }
    
}

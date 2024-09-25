package towerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import towerDefense.GameObjects.base.Enemy;

public class ZombiePirate extends Enemy{

    public ZombiePirate(Vector2 position, Vector2 size, Queue<Vector2> wayPoints) {
        super(position, size, wayPoints);
        super.velocity = 40.f; 
        super.life = 3.f;
        super.maxLife = super.life;
    }
    
}

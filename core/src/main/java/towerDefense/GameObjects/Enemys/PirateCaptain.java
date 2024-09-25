package towerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import towerDefense.GameObjects.base.Enemy;

public class PirateCaptain extends Enemy{

    public PirateCaptain(Vector2 position, Vector2 size, Queue<Vector2> wayPoints) {
        super(position, size, wayPoints);
        super.velocity = 60.f; 
        super.life = 6.f;
        super.maxLife = super.life;
    }
    
}

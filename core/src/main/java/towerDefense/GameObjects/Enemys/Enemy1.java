package towerDefense.GameObjects.Enemys;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.math.Vector2;

import towerDefense.GameObjects.base.Enemy;

public class Enemy1 extends Enemy {
    public Enemy1(Vector2 position, Vector2 size, Queue<Vector2> wayPoints) {
        super(position, size, wayPoints);
    }
}
package towerDefense.GameObjects.base;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import towerDefense.GameObjects.Enemys.Pirate;

public class Wave {
    private float timeBetween = 0.500f;
    private int enemyQuantity = 5;

    private float timeSinceLastSpawn = 0.f;

    private ArrayList<Enemy> enimiesArray;
    private Queue<Vector2> enemyWay;

    public Wave(ArrayList<Enemy> enemies, Queue<Vector2> enemyWay) {
        enimiesArray = enemies;
        this.enemyWay = enemyWay;
    }

    public void update(float deltaTime) {
        if (timeBetween <= timeSinceLastSpawn && enemyQuantity > 0) {
            enemyQuantity--;
            timeSinceLastSpawn = 0;
            enimiesArray.add(new Pirate(new Vector2(-16, Gdx.graphics.getHeight() / 2), new Vector2(16, 16), enemyWay));
        }
        timeSinceLastSpawn += deltaTime;
    }
}

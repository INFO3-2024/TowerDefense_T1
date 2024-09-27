package io.github.INFO32024.TowerDefense_T1.GameObjects.base;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import io.github.INFO32024.TowerDefense_T1.GameObjects.Enemys.Pirate;

public class Wave {
    private float timeBetween = 0.500f;
    private int enemyQuantity = 5;

    private float timeSinceLastSpawn = 0.f;

    private ArrayList<Enemy> enimiesArray;
    private ArrayList<Queue<Vector2>> enemyWay;

    public Wave(ArrayList<Enemy> enemies, ArrayList<Queue<Vector2>> enemyWay) {
        enimiesArray = enemies;
        this.enemyWay = enemyWay;
    }

    public void update(float deltaTime, int textureOffset, int path) {
        if (timeBetween <= timeSinceLastSpawn && enemyQuantity > 0) {
            enemyQuantity--;
            timeSinceLastSpawn = 0;
            enimiesArray.add(new Pirate(new Vector2(-textureOffset, Gdx.graphics.getHeight() / 2), new Vector2(textureOffset, textureOffset), enemyWay.get(path)));
        }
        timeSinceLastSpawn += deltaTime;
    }
}

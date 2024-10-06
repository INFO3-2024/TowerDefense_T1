package TowerDefense.GameObjects.base;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.GameObjects.Enemys.Pirate;

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

    public void update(float deltaTime, int textureOffset) {
        int path = (new Random()).nextInt(enemyWay.size());
        
        if (timeBetween <= timeSinceLastSpawn && enemyQuantity > 0) {
            enemyQuantity--;
            timeSinceLastSpawn = 0;
            enimiesArray.add(new Pirate(new Vector2(textureOffset, textureOffset), enemyWay.get(path)));
        }
        timeSinceLastSpawn += deltaTime;
    }
}

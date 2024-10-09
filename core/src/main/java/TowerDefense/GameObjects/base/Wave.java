package TowerDefense.GameObjects.base;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.GameObjects.Enemys.Boss;
import TowerDefense.GameObjects.Enemys.Pirate;
import TowerDefense.GameObjects.Enemys.PirateCaptain;
import TowerDefense.GameObjects.Enemys.ZombiePirate;

public class Wave {
    private float timeBetween = 0.5f;

    private int waveDelay = 20;

    private float timeSinceLastSpawn = 0.f;

    private ArrayList<Enemy> enimiesArray;
    private ArrayList<Queue<Vector2>> enemyWay;
    private Stack<Enemy> enemiesToBeAdded;
    private JsonValue jWaves;
    private int textureOffset;

    public Wave(ArrayList<Enemy> enemies, ArrayList<Queue<Vector2>> enemyWay, JsonValue waves, int textureOffset) {
        this.enemiesToBeAdded = new Stack<Enemy>();
        enimiesArray = enemies;
        this.enemyWay = enemyWay;
        this.jWaves = waves;
        this.textureOffset = textureOffset;

        this.loadEnemies();
    }

    private void loadEnemies() {
        int path = (new Random()).nextInt(enemyWay.size());

        for (int i = 0; i < jWaves.getInt("boss"); i++) {
            path = (new Random()).nextInt(enemyWay.size());
            enemiesToBeAdded.add(new Boss(new Vector2(textureOffset, textureOffset),
                    enemyWay.get(path)));
        }

        for (int i = 0; i < jWaves.getInt("zombiePirate"); i++) {
            path = (new Random()).nextInt(enemyWay.size());
            enemiesToBeAdded.add(new ZombiePirate(new Vector2(textureOffset, textureOffset),
                    enemyWay.get(path)));
        }

        for (int i = 0; i < jWaves.getInt("pirateCaptain"); i++) {
            path = (new Random()).nextInt(enemyWay.size());
            enemiesToBeAdded.add(new PirateCaptain(new Vector2(textureOffset, textureOffset),
                    enemyWay.get(path)));
        }

        for (int i = 0; i < jWaves.getInt("pirates"); i++) {
            path = (new Random()).nextInt(enemyWay.size());
            enemiesToBeAdded.add(new Pirate(new Vector2(textureOffset, textureOffset),
                    enemyWay.get(path)));
        }
    }

    public void update(float deltaTime) {
        if (timeBetween <= timeSinceLastSpawn && !enemiesToBeAdded.empty()) {
            timeSinceLastSpawn = 0;
            enimiesArray.add(enemiesToBeAdded.pop());
        }
        timeSinceLastSpawn += deltaTime;

        if (waveDelay <= timeSinceLastSpawn && this.jWaves.next() != null) {
            this.jWaves = this.jWaves.next();
            this.loadEnemies();
        }
    }

    public boolean waveConcluded() {
        if (enemiesToBeAdded.empty()) {
            return true;
        }
        return false;
    }

    public float antecipateWave() {
        if (enemiesToBeAdded.empty()) {
            this.jWaves = this.jWaves.next();
            this.loadEnemies();
            return this.waveDelay - this.timeSinceLastSpawn;
        }
        return 0;
    }
}

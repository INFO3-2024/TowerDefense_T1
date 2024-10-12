package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import java.util.ArrayList;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Enemy;

public class Boss extends Enemy {
    private ArrayList<Enemy> enimiesArray;
    private float timeSinceLastWave = 0.f;
    private Queue<Vector2> originalWayPoints;
    private int spawnedEnemies = 0;

    public Boss(Vector2 size, Queue<Vector2> wayPoints, ArrayList<Enemy> enimiesArray) {
        super(size, wayPoints);
        super.dropedCoins = 30;
        super.originalVelocity = 20.f;
        super.velocity = originalVelocity;
        super.life = 300.f;
        super.maxLife = super.life;
        this.enimiesArray = enimiesArray;

        textureRegions = AssetsControl.getTextureRegions("basicEnemy");
        animation = AssetsControl.getAnimation(textureRegions, 3, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);

        this.originalWayPoints = new Queue<Vector2>();
        this.originalWayPoints.clear();

        for (Vector2 wayPoint : wayPoints) {
            this.originalWayPoints.addLast(wayPoint);
        }
    }

    @Override
    public void powerUp(float deltaTime) {
        this.timeSinceLastWave += deltaTime;

        if (timeSinceLastWave >= 8) {
            if (spawnedEnemies <= 3 && timeSinceLastWave >= 8 + 0.5f * spawnedEnemies) {
                enimiesArray.add(new Pirate(super.size, this.originalWayPoints));
                spawnedEnemies += 1;
            }
            if (spawnedEnemies >= 3) {
                spawnedEnemies = 0;
                this.timeSinceLastWave = 0;
            }
        }
    }
}

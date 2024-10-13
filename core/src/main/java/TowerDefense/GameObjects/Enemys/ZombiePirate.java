package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Enemy;

public class ZombiePirate extends Enemy {
    float timeSinceLastHeal = 0.f;

    public ZombiePirate(Vector2 size, Queue<Vector2> wayPoints) {
        super(size, wayPoints);
        super.dropedCoins = 15;
        super.originalVelocity = 60.f;
        super.velocity = originalVelocity;
        super.life = 30.f;
        super.maxLife = super.life;

        textureRegions = AssetsControl.getTextureRegions("basicEnemy");
        animation = AssetsControl.getAnimation(textureRegions, 2, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }

    @Override
    public void powerUp(float deltaTime) {
        this.timeSinceLastHeal += deltaTime;

        if (timeSinceLastHeal >= 10) {
            this.timeSinceLastHeal = 0;
            this.life = Math.min(maxLife, life += 5);
        }
    }
}

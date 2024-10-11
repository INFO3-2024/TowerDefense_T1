package TowerDefense.GameObjects.Enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Enemy;

public class Pirate extends Enemy {

        public Pirate(Vector2 size, Queue<Vector2> wayPoints) {
                super(size, wayPoints);
                super.dropedCoins = 10;
                super.velocity = 60.f;
                super.life = 10.f;
                super.maxLife = super.life;

                textureRegions = AssetsManager.getTextureRegions("basicEnemy");
                animation = AssetsManager.getAnimation(textureRegions, 0, 0.15f);
                this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
        }

}

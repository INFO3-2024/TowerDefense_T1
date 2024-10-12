package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Enemy;

public class Pirate extends Enemy {

        public Pirate(Vector2 size, Queue<Vector2> wayPoints) {
                super(size, wayPoints);
                super.dropedCoins = 10;
                super.originalVelocity = 50.f;
                super.velocity = originalVelocity;
                super.life = 10.f;
                super.maxLife = super.life;

                textureRegions = AssetsControl.getTextureRegions("basicEnemy");
                animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
                this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
        }

}

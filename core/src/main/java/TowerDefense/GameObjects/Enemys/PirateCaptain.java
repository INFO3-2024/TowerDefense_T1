package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Enemy;

public class PirateCaptain extends Enemy {

    public PirateCaptain(Vector2 size, Queue<Vector2> wayPoints) {
        super(size, wayPoints);
        super.dropedCoins = 20;
        super.velocity = 60.f;
        super.life = 6.f;
        super.maxLife = super.life;

        textureRegions = AssetsControl.getTextureRegions("basicEnemy");
        animation = AssetsControl.getAnimation(textureRegions, 4, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }

}

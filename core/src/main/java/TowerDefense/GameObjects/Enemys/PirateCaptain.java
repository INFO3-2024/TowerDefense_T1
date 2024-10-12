package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Enemy;

public class PirateCaptain extends Enemy {
    float shieldLife = 20;
    float maxShield = 20;

    public PirateCaptain(Vector2 size, Queue<Vector2> wayPoints) {
        super(size, wayPoints);
        super.dropedCoins = 20;
        super.originalVelocity = 40.f;
        super.velocity = originalVelocity;
        super.life = 100.f;
        super.maxLife = super.life;

        textureRegions = AssetsControl.getTextureRegions("basicEnemy");
        animation = AssetsControl.getAnimation(textureRegions, 4, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }

    @Override
    public void powerUp(float deltaTime) {
        if (this.life < this.maxLife && shieldLife > 0) {
            this.shieldLife -= this.maxLife - this.life;
            this.shieldLife = Math.max(0, this.shieldLife);
            this.life = maxLife;
        }
    }

    @Override
    public void drawLifeBar(ShapeRenderer render) {
        render.begin(ShapeType.Filled);
        render.setColor(Color.BLACK);
        render.rect(this.position.x - 1, this.position.y - 4, this.size.x + 2, 4);
        render.end();

        render.begin(ShapeType.Filled);
        render.setColor(Color.RED);
        render.rect(this.position.x, this.position.y - 3, this.size.x * this.life / this.maxLife, 2);
        render.end();

        render.begin(ShapeType.Filled);
        render.setColor(Color.CYAN);
        render.rect(this.position.x, this.position.y - 3, this.size.x * this.shieldLife / this.maxShield, 2);
        render.end();
    }
}

package TowerDefense.GameObjects.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import TowerDefense.GameObjects.base.Bullet;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.PowerUp;

public class GreenPowerup extends PowerUp {
    private Texture venonTexture;
    private TextureRegion venonTextureRegion;
    private float venonTime = 5.f;
    private float timeSinceLastDamageTick;
    private Enemy poisonedEnemy;
    private Mermaid tower;
    private Bullet poisonBullet;

    public GreenPowerup(Mermaid tower) {
        this.venonTexture = new Texture(Gdx.files.internal("PowerUps/PowerUp.png"));
        this.venonTextureRegion = new TextureRegion(venonTexture, 3 * 16, 16,
                16,
                16);
        this.powerUpDelay = 7;
        this.tower = tower;
    }

    @Override
    public void update(float deltaTime) {
        this.updateTime(deltaTime);

        this.timeSinceLastDamageTick += deltaTime;

        if (canShoot && !this.tower.getBullets().isEmpty()) {
            this.use();
            this.poisonBullet = this.tower.getBullets().get(0);
        }

        if (poisonBullet != null) {
            if (poisonBullet.hit()) {
                this.poisonedEnemy = this.poisonBullet.getBulletTarget();
                this.poisonBullet = null;
            }
        }

        if (poisonedEnemy != null && this.timeSinceLastDamageTick >= 1
                && this.timeSinceLastActivation < this.venonTime) {
            this.timeSinceLastDamageTick = 0;
            this.poisonedEnemy.damage(5);
        }

        if (venonTime <= this.timeSinceLastActivation) {
            this.poisonedEnemy = null;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (poisonedEnemy != null) {
            batch.draw(venonTextureRegion, this.poisonedEnemy.getPosition().x + 13,
                    this.poisonedEnemy.getPosition().y + 18,
                    32,
                    32);
            if (this.poisonedEnemy.getLife() <= 0) {
                this.poisonedEnemy = null;
            }
        }
    }
}

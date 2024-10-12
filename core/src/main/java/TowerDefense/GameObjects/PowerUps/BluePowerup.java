package TowerDefense.GameObjects.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import TowerDefense.GameObjects.base.Bullet;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.PowerUp;

public class BluePowerup extends PowerUp {
    private Texture iceTexture;
    private TextureRegion iceTextureRegion;
    private float iceTime = 2.f;
    private Enemy freezeEnemy;
    private Mermaid tower;
    private Bullet freezeBullet;

    public BluePowerup(Mermaid tower) {
        this.iceTexture = new Texture(Gdx.files.internal("PowerUps/PowerUp.png"));
        this.iceTextureRegion = new TextureRegion(iceTexture, 4 * 16, 16,
                16,
                16);
        this.powerUpDelay = 7;
        this.tower = tower;
    }

    public void update(float deltaTime) {
        this.updateTime(deltaTime);

        if (canShoot && !this.tower.getBullets().isEmpty()) {
            this.use();
            this.freezeBullet = this.tower.getBullets().get(0);
        }

        if (freezeBullet != null) {
            if (freezeBullet.hit()) {
                this.freezeEnemy = this.freezeBullet.getBulletTarget();
                this.freezeEnemy.setVelocity(0);
                this.freezeBullet = null;
            }
        }

        if (iceTime <= this.timeSinceLastActivation && freezeEnemy != null) {
            this.freezeEnemy.setVelocity(this.freezeEnemy.getOriginalvelocity());
            this.freezeEnemy = null;
        }
    }

    public void draw(SpriteBatch batch) {
        if (freezeEnemy != null && freezeEnemy.getLife() > 0) {
            batch.draw(iceTextureRegion, this.freezeEnemy.getPosition().x + 13, this.freezeEnemy.getPosition().y + 18,
                    32, 32);
        }
    }
}

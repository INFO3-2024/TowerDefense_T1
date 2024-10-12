package TowerDefense.GameObjects.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import TowerDefense.GameObjects.base.Bullet;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.PowerUp;

public class PurplePowerup extends PowerUp {
    private Texture buffTexture;
    private TextureRegion buffTextureRegion;
    private float buffTime = 5.f;
    private Mermaid tower;
    private Bullet bullet;
    private Enemy targetEnemy;

    public PurplePowerup(Mermaid tower) {
        this.buffTexture = new Texture(Gdx.files.internal("PowerUps/PowerUp.png"));
        this.buffTextureRegion = new TextureRegion(buffTexture, 2 * 16, 16,
                16,
                16);
        this.powerUpDelay = 7;
        this.tower = tower;
    }

    @Override
    public void update(float deltaTime) {
        this.updateTime(deltaTime);

        if (canShoot && this.tower.canShoot()) {
            this.use();
        }

        if (bullet == null && !this.tower.getBullets().isEmpty())
            this.bullet = this.tower.getBullets().get(0);

        if (this.buffTime >= this.timeSinceLastActivation && !this.tower.getBullets().isEmpty()) {
            if (bullet.hit()) {
                this.targetEnemy = this.bullet.getBulletTarget();
                this.targetEnemy.damage((float) (this.tower.getDamage() * 0.3));
                this.bullet = null;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (this.buffTime >= this.timeSinceLastActivation) {
            batch.draw(buffTextureRegion, this.tower.getPosition().x + 7,
                    this.tower.getPosition().y,
                    50,
                    50);
        }
    }
}

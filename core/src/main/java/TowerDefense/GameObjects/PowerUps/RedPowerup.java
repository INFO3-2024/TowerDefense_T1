package TowerDefense.GameObjects.PowerUps;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import TowerDefense.GameObjects.base.Bullet;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.PowerUp;

public class RedPowerup extends PowerUp {
    private Texture buffTexture;
    private TextureRegion buffTextureRegion;
    private float buffTime = 3.f;
    private Mermaid tower;
    private Bullet bullet;
    private ArrayList<Enemy> effectedEnemies;
    private Texture fire;
    private TextureRegion fireRegion;
    private Circle circle;
    private float timeSinceLastTickDamage;

    public RedPowerup(Mermaid tower) {
        this.buffTexture = new Texture(Gdx.files.internal("PowerUps/PowerUp.png"));
        this.buffTextureRegion = new TextureRegion(buffTexture, 1 * 16, 16,
                16,
                16);
        this.fire = new Texture(Gdx.files.internal("PowerUps/Effects.png"));
        this.fireRegion = new TextureRegion(fire, 0 * 16, 0,
                16,
                16);
        this.powerUpDelay = 10;
        this.tower = tower;

        this.effectedEnemies = new ArrayList<Enemy>();
    }

    @Override
    public void update(float deltaTime) {
        this.updateTime(deltaTime);
        this.timeSinceLastTickDamage += deltaTime;

        if (canShoot && !this.tower.getBullets().isEmpty()) {
            this.use();
            this.bullet = this.tower.getBullets().get(0);
        }

        if (bullet != null) {
            if (bullet.hit()) {
                this.circle = new Circle(bullet.getPosition().x, bullet.getPosition().y, 32);
                this.bullet = null;
            }
        }

        if (circle != null) {
            for (Enemy enemy : PowerUp.getEnemies()) {
                if (circle.contains(enemy.getPosition())) {
                    if (!effectedEnemies.contains(enemy)) {
                        effectedEnemies.add(enemy);
                    }
                    enemy.redTowerDebuffReset();
                }
            }
        }

        if (this.timeSinceLastActivation >= this.buffTime) {
            this.circle = null;
        }

        if (this.timeSinceLastTickDamage >= 1 && !effectedEnemies.isEmpty()) {
            this.timeSinceLastTickDamage = 0;

            for (int i = 0; i < effectedEnemies.size(); i++) {
                Enemy enemy = effectedEnemies.get(i);
                enemy.damage(4);
                if (enemy.getRedTowerDebuff() >= this.buffTime || enemy.getLife() <= 0) {
                    effectedEnemies.remove(i);
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (Enemy enemy : effectedEnemies) {
            if (enemy.getLife() > 0) {
                batch.draw(buffTextureRegion, enemy.getPosition().x + 13, enemy.getPosition().y + 18, 32, 32);
            }
        }

        if (circle != null) {
            batch.draw(fireRegion, this.circle.x, this.circle.y, 64, 64);
        }
    }
}

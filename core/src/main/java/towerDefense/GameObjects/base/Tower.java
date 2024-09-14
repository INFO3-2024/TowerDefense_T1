package towerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public abstract class Tower extends GameObject {
    protected Enemy currenteTarget;
    protected float range;

    protected float bulletSpeed; // Frames/Second
    protected float bulletDelay;
    protected float timeFromLastBullet = 9999999999.f;
    protected ArrayList<Bullet> bullets;

    protected float damage = 1.f;
    // Motivo de ser um ArrayList é que em alguns casos, muito especificos, uma
    // torre pode ter duas+ balas ao mesmo tempo, pra evitar dor de cebeça no
    // futuro, utiliza-se um array

    public Tower(Vector2 position, Vector2 size) {
        super(position, size);

        this.range = 5;
        this.bullets = new ArrayList<Bullet>();
    }

    public float getRange() {
        return this.range;
    }

    public void setCurrentTarget(Enemy enemy) {
        this.currenteTarget = enemy;
    }

    public boolean inRange(Vector2 pos) {
        if (Math.pow(range * 16, 2) >= Math.pow(pos.x - this.position.x, 2) + Math.pow(pos.y - this.position.y, 2)) {
            return true;
        }
        return false;
    }

    private void shoot(float deltaTime) {
        if (bulletDelay <= timeFromLastBullet) {
            float distanceToEnemy = (float) Math.sqrt(
                    Math.pow(currenteTarget.position.x - this.position.x, 2)
                            + Math.pow(currenteTarget.position.y - this.position.y, 2));

            float angle = (float) Math.atan2(currenteTarget.position.y - this.position.y,
                    currenteTarget.position.x - this.position.x);
            bullets.add(new Bullet(new Vector2(this.position), new Vector2(this.size), bulletSpeed, angle,
                    distanceToEnemy / bulletSpeed, currenteTarget));
            timeFromLastBullet = 0;
        }
        timeFromLastBullet += deltaTime;
    }

    @Override
    public void update(float deltaTime) {
        if (currenteTarget != null) {
            shoot(deltaTime);
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (bullet.hit()) {
                bullet.getBulletTarget().damage(this.damage);
                bullets.remove(i);
            }
        }
    }

    public void draw(TextureRegion tRegionTower, TextureRegion tRegionBullets, SpriteBatch batch) {
        super.draw(tRegionTower, batch);
        for (Bullet bullet : bullets) {
            bullet.draw(tRegionBullets, batch);
        }
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

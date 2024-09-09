package towerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public abstract class Tower extends GameObject {
    protected Vector2 position;
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

    public Tower(int initPosX, int initPosY, int sizeX, int sizeY) {
        super(initPosX, initPosY, sizeX, sizeY);
        super.sprite = new Sprite(super.texture, 16, 0, sizeX, sizeY);
        super.sprite.setPosition(initPosX, initPosY);

        this.position = new Vector2(initPosX, initPosY);
        this.range = 5;

        this.bullets = new ArrayList<Bullet>();
    }

    public Vector2 getPosition() {
        return this.position;
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
                    Math.pow(currenteTarget.getPosition().x - this.position.x, 2)
                            + Math.pow(currenteTarget.getPosition().y - this.position.y, 2));

            float angle = (float) Math.atan2(currenteTarget.getPosition().y - this.position.y,
                    currenteTarget.getPosition().x - this.position.x);

            bullets.add(new Bullet(this.position, bulletSpeed, angle, distanceToEnemy / bulletSpeed));
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
                bullets.remove(i);
                if (currenteTarget != null) {
                    currenteTarget.damage(this.damage);
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.sprite.draw(batch);
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

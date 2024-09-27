package TowerDefense.GameObjects.base;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends GameObject {
    protected float angle;
    protected float bulletSpeed;

    private float timeUntilImpact;
    private float timeAlive;

    private boolean hit = false;

    private Enemy bulletTarget;

    public Bullet(Vector2 position, Vector2 size, float bulletSpeed, float angle, float timeUntilImpact,
            Enemy bulletTarget) {
        super(position, size);
        this.bulletSpeed = bulletSpeed;
        this.angle = angle;
        this.timeUntilImpact = timeUntilImpact;
        this.bulletTarget = bulletTarget;
    }

    public boolean hit() {
        return this.hit;
    }

    public Enemy getBulletTarget() {
        return this.bulletTarget;
    }

    @Override
    public void update(float deltaTime) {
        this.position.y += Math.sin(this.angle) * bulletSpeed * deltaTime;
        this.position.x += Math.cos(this.angle) * bulletSpeed * deltaTime;

        // this.sprite.setPosition(this.position.x, this.position.y);

        this.timeAlive += deltaTime;

        if (timeAlive >= timeUntilImpact) {
            this.hit = true;
        }
    }
}

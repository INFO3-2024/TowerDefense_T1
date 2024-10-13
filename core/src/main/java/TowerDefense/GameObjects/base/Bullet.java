package TowerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        this.position.x += 8;
        this.position.y -= 20;
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

    public void setTexture(TextureRegion texture) {
        this.currentTRegion = texture;
    }

    @Override
    public void update(float deltaTime) {
        this.position.y += Math.sin(this.angle) * bulletSpeed * deltaTime;
        this.position.x += Math.cos(this.angle) * bulletSpeed * deltaTime;

        this.timeAlive += deltaTime;

        if (timeAlive >= timeUntilImpact) {
            this.hit = true;
        }
    }
}

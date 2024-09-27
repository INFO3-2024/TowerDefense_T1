package towerDefense.GameObjects.base;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends GameObject {
    protected float angle;
    protected float bulletSpeed;

    private float timeUntilImpact;
    private float timeAlive;

    private boolean hit = false;

    public Bullet(Vector2 position, Vector2 size, float bulletSpeed, float angle, float timeUntilImpact) {
        super(position, size); // TODO - Avaliar GAME OBJECT, parece só encher o saco por enquanto
        this.bulletSpeed = bulletSpeed;
        this.angle = angle;
        this.timeUntilImpact = timeUntilImpact;

        //this.bulletTexture = new Texture(Gdx.files.internal("Asset.png"));
        //this.sprite = new Sprite(bulletTexture, 16, 16, 16, 16);
        //this.sprite.setPosition(position.x, position.y);
        //this.sprite.setRotation((float) Math.toDegrees(angle));
    }

    public boolean hit() {
        return this.hit;
    }

    @Override
    public void update(float deltaTime) {
        this.position.y += Math.sin(this.angle) * bulletSpeed * deltaTime;
        this.position.x += Math.cos(this.angle) * bulletSpeed * deltaTime;

        //this.sprite.setPosition(this.position.x, this.position.y);

        this.timeAlive += deltaTime;

        if (timeAlive >= timeUntilImpact) {
            this.hit = true;
        }
    }
}

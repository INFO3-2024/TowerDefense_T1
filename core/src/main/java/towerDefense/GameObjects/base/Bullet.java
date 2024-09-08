package towerDefense.GameObjects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// SIMPLISMENTE A CLASSE MAIS *** POSSIVEL, só pra testar msm

public class Bullet extends GameObject {
    protected float angle;
    protected float bulletSpeed;
    protected Vector2 position;

    private float timeUntilImpact;
    private float timeAlive;

    private boolean hit = false;

    private Texture bulletTexture;
    private Sprite sprite;

    public Bullet(Vector2 position, float bulletSpeed, float angle, float timeUntilImpact) {
        super(0, 0, 0, 0); // TODO - Avaliar GAME OBJECT, parece só encher o saco por enquanto
        this.position = new Vector2(position);
        this.bulletSpeed = bulletSpeed;
        this.angle = angle;
        this.timeUntilImpact = timeUntilImpact;

        this.bulletTexture = new Texture(Gdx.files.internal("Asset.png"));
        this.sprite = new Sprite(bulletTexture, 16, 16, 16, 16);
        this.sprite.setPosition(position.x, position.y);
        this.sprite.setRotation((float) Math.toDegrees(angle));
    }

    public boolean hit() {
        return this.hit;
    }

    @Override
    public void update(float deltaTime) {
        this.position.y += Math.sin(this.angle) * bulletSpeed * deltaTime;
        this.position.x += Math.cos(this.angle) * bulletSpeed * deltaTime;

        this.sprite.setPosition(this.position.x, this.position.y);

        this.timeAlive += deltaTime;

        if (timeAlive >= timeUntilImpact) {
            this.hit = true;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }
}

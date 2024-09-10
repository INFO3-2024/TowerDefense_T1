package towerDefense.GameObjects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends GameObject {
    private float velocity;
    private float life = 3.f;

    public Enemy(Vector2 position, Vector2 size) {
        super(position, size);
    }

    private void move(float deltaTime) {
        this.position.x += this.velocity * deltaTime;

        // A partir daqui, codigo provisorio, remover o quanto antes :)
        if (this.position.x >= Gdx.graphics.getWidth()) {
            this.position.x = 0;
        }
    }

    public float getVelocity() {
        return this.velocity;
    }

    public void setVelocity(float vel) {
        this.velocity = vel;
    }

    public void damage(float damage) {
        this.life -= damage;
    }

    public float getLife() {
        return this.life;
    }

    @Override
    public void update(float deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

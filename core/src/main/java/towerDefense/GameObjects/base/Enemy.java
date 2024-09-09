package towerDefense.GameObjects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends GameObject {
    private float velocity;
    private float life = 3.f;
    private Vector2 position;

    public Enemy(int initPosX, int initPosY, int sizeX, int sizeY) {
        super(initPosX, initPosY, sizeX, sizeY);
        super.sprite = new Sprite(super.texture, 0, 0, sizeX, sizeY);
        super.sprite.setPosition(initPosX, initPosY);

        this.position = new Vector2(initPosX, initPosY);
    }

    private void move(float deltaTime) {
        this.position.x += this.velocity * deltaTime;
        super.sprite.setPosition(this.position.x, this.position.y);

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

    public Vector2 getPosition() {
        return this.position;
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

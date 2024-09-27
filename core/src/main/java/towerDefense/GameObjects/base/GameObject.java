package towerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    protected Vector2 position;
    protected Vector2 size;


    public GameObject(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public void draw(TextureRegion textureRegion, SpriteBatch batch) {
        batch.draw(textureRegion, this.position.x, this.position.y, this.size.x, this.size.y);
    }

    public abstract void update(float deltaTime);

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void dispose() {
        /* Nothing to dispose */
    }
}
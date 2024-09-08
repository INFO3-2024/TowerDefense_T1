package towerDefense.GameObjects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
    // protected Rectangle rect;
    protected Texture texture;
    protected Sprite sprite;

    public GameObject(int initPosX, int initPosY, int sizeX, int sizeY) {
        // this.rect = new Rectangle();

        // rect.x = initPosX;
        // rect.y = initPosY;
        // rect.width = sizeX;
        // rect.height = sizeY;

        texture = new Texture(Gdx.files.internal("Asset.png"));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract void update(float deltaTime); // Abstract method with abstract

    public void dispose() {
        /* Nothing to dispose */
    }
}
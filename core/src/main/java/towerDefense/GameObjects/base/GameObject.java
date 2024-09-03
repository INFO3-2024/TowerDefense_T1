package towerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected Rectangle rect;
    protected TextureRegion texture;
    // protected Texture texture;

    public GameObject(int initPosX, int initPosY, int sizeX, int sizeY, TextureRegion textureRegion) {
        this.rect = new Rectangle();

        rect.x = initPosX;
        rect.y = initPosY;
        rect.width = sizeX;
        rect.height = sizeY;

        this.texture = textureRegion;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rect.x, rect.y);
    }

    public void update() {
        /* Abstract method without 'abstract' rsrs */}

    public void dispose() {
        /* Nothing to dispose */}
}
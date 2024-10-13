package TowerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;

public abstract class GameObject {
    protected Vector2 position;
    protected Vector2 size;
    protected TextureRegion[][] textureRegions;
    protected Animation<TextureRegion> animation;
    protected TextureRegion currentTRegion;

    public GameObject(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentTRegion, this.position.x - 17, this.position.y, (int) (this.size.x * 1.45),
                (int) (this.size.y * 1.45));
    }

    public void update(float deltaTime) {
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public TextureRegion getCurrentTRegion() {
        return this.currentTRegion;
    }

    public void dispose() {
        /* Nothing to dispose */
    }
}
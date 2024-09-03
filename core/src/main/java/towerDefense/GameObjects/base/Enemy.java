package towerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Enemy extends GameObject {

    public Enemy(int initPosX, int initPosY, int sizeX, int sizeY, TextureRegion textureRegion) {
        super(initPosX, initPosY, sizeX, sizeY, textureRegion);
    }

    private void move() {
        // Enemy movement
    }

    @Override
    public void update() {
        this.move();

        // Enemy update
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */}
}

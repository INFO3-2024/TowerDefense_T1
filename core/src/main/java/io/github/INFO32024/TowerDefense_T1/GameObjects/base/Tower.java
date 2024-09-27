package io.github.INFO32024.TowerDefense_T1.GameObjects.base;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tower extends GameObject {
    
    public Tower(int initPosX, int initPosY, int sizeX, int sizeY, TextureRegion textureRegion){
        super(initPosX, initPosY, sizeX, sizeY, textureRegion);
    }

    @Override
    public void update() {
        // Tower update
    }

    @Override
    public void dispose() {/* Nothing to dipose also */}
}

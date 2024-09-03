package towerDefense.GameObjects;

import towerDefense.GameObjects.base.Tower;
import towerDefense.GameObjects.utils.Textures;

public class TowerExample extends Tower {
    public TowerExample(int initPosX, int initPosY, int sizeX, int sizeY) {
        super(initPosX, initPosY, sizeX, sizeY, Textures.getTexture("TowerExample"));
    }
}

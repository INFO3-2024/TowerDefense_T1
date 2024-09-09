package towerDefense.GameObjects.Towers;

import towerDefense.GameObjects.base.Tower;

public class TowerExample extends Tower {
    public TowerExample(int initPosX, int initPosY, int sizeX, int sizeY) {
        super(initPosX, initPosY, sizeX, sizeY);
        super.bulletDelay = 0.500f;
        super.bulletSpeed = 250.f;
    }
}

package io.github.INFO32024.TowerDefense_T1.GameObjects;

import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Tower;
import io.github.INFO32024.TowerDefense_T1.GameObjects.utils.Textures;

public class TowerExample extends Tower{
    public TowerExample(int initPosX, int initPosY, int sizeX, int sizeY){
        super(initPosX, initPosY, sizeX, sizeY, Textures.getTexture("TowerExample"));
    }
}

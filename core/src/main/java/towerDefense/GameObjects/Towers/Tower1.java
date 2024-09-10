package towerDefense.GameObjects.Towers;

import com.badlogic.gdx.math.Vector2;

import towerDefense.GameObjects.base.Tower;

public class Tower1 extends Tower {
    public Tower1(Vector2 position, Vector2 size) {
        super(position, size);
        super.bulletDelay = 0.500f;
        super.bulletSpeed = 250.f;
    }
}

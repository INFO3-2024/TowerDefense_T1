package TowerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public interface InterfaceMenu {
    abstract boolean handleClick(Vector2 mousePos);

    abstract void draw(ShapeRenderer render);

    abstract Vector2 getTowerPos();
}

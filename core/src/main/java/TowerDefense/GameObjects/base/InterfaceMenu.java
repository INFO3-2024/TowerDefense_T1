package TowerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface InterfaceMenu {
    abstract boolean handleClick(Vector2 mousePos);
    abstract boolean handleMouseOver(Vector2 mousePos);

    abstract void draw(SpriteBatch batch);

    abstract Vector2 getTowerPos();
}

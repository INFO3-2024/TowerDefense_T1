package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.GameObjects.base.Mermaid;

public class GreenMermaid extends Mermaid {
    public GreenMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.25f;
        super.range = 2;
        super.bulletDelay = 0.5f;
        super.price = 100;
    }
}

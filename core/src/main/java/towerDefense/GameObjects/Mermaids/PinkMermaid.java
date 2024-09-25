package towerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;
import towerDefense.GameObjects.base.Mermaid;

public class PinkMermaid extends Mermaid {
    public PinkMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.f;
        super.range = 2;
        super.bulletDelay = 1.f;
        super.price = 50;
    }
}

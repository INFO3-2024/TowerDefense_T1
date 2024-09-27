package io.github.INFO32024.TowerDefense_T1.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Mermaid;

public class RedMermaid extends Mermaid {
    public RedMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.5f;
        super.range = 2;
        super.bulletDelay = 1.f;
        super.price = 100;
    }
}

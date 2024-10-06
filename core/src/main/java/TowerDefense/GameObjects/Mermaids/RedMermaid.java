package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Mermaid;

public class RedMermaid extends Mermaid {
    public RedMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.5f;
        super.range = 2;
        super.bulletDelay = 1.f;
        super.price = 100;

        textureRegions = AssetsControl.getTextureRegions("redMermaid");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }
}

package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Tower;

public class RedMermaid extends Tower {
    public RedMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.5f;
        super.range = 2;
        super.bulletDelay = 1.f;
        super.price = 100;

        textureRegions = AssetsManager.getTextureRegions("redMermaid");
        animation = AssetsManager.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
    }
}

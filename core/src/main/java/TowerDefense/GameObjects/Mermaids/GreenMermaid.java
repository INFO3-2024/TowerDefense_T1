package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Tower;

public class GreenMermaid extends Tower {
    public GreenMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.25f;
        super.range = 2;
        super.bulletDelay = 0.5f;
        super.price = 100;

        textureRegions = AssetsManager.getTextureRegions("greenMermaid");
        animation = AssetsManager.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
    }
}

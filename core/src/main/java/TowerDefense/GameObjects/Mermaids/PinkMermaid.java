package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Tower;

public class PinkMermaid extends Tower {
    public PinkMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.f;
        super.range = 2;
        super.bulletDelay = 1.f;
        super.price = 50;

        textureRegions = AssetsManager.getTextureRegions("pinkMermaid");
        animation = AssetsManager.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
    }
}

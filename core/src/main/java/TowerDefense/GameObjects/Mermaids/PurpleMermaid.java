package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Tower;

public class PurpleMermaid extends Tower {
    public PurpleMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.f;
        super.range = 4;
        super.bulletDelay = 1.f;
        super.price = 100;

        textureRegions = AssetsManager.getTextureRegions("purpleMermaid");
        animation = AssetsManager.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
    }
}

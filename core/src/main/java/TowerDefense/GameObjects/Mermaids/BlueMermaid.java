package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Tower;

public class BlueMermaid extends Tower {
    public BlueMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.5f;
        super.range = 4;
        super.bulletDelay = 0.5f;
        super.price = 250;

        textureRegions = AssetsManager.getTextureRegions("blueMermaid");
        animation = AssetsManager.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
    }
}

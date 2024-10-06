package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Mermaid;

public class PurpleMermaid extends Mermaid {
    public PurpleMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.f;
        super.range = 4;
        super.bulletDelay = 1.f;
        super.price = 100;

        textureRegions = AssetsControl.getTextureRegions("purpleMermaid");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }
}

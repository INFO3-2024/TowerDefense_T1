package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Mermaid;

public class BlueMermaid extends Mermaid {
    public BlueMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 1.5f;
        super.range = 4;
        super.bulletDelay = 0.5f;
        super.price = 250;

        textureRegions = AssetsControl.getTextureRegions("blueMermaid");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }
}

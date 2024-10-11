package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Mermaid;

public class GreenMermaid extends Mermaid {
    public GreenMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 6.f;
        super.range = 2;
        super.bulletDelay = 0.5f;
        super.price = 100;

        textureRegions = AssetsControl.getTextureRegions("greenMermaid");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }
}

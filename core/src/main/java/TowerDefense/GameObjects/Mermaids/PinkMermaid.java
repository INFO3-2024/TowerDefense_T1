package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.PowerUps.PinkPowerup;
import TowerDefense.GameObjects.base.Mermaid;

public class PinkMermaid extends Mermaid {
    public PinkMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 5.f;
        super.range = 2;
        super.bulletDelay = 1.f;
        super.price = 50;

        textureRegions = AssetsControl.getTextureRegions("pinkMermaid");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }

    @Override
    public void addPowerUp() {
        this.powerUp = new PinkPowerup(this);
    }
}

package TowerDefense.GameObjects.Mermaids;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.PowerUps.PurplePowerup;
import TowerDefense.GameObjects.base.Mermaid;

public class PurpleMermaid extends Mermaid {
    public PurpleMermaid(Vector2 position, Vector2 size) {
        super(position, size);
        super.damage = 5.f;
        super.range = 3;
        super.bulletDelay = 1.f;
        super.price = 100;

        textureRegions = AssetsControl.getTextureRegions("purpleMermaid");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
    }

    @Override
    public void addPowerUp() {
        this.powerUp = new PurplePowerup(this);
    }
}

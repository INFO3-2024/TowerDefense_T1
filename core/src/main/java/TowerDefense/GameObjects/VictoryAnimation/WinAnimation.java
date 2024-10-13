package TowerDefense.GameObjects.VictoryAnimation;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.GameObject;

public class WinAnimation extends GameObject{
    public WinAnimation(Vector2 position, Vector2 size) {
        super(position, size);
        textureRegions = AssetsControl.getTextureRegions("Win", new Vector2(80f, 58f) );
        animation = AssetsControl.getAnimation(textureRegions, 4, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
               
        } 
}

package TowerDefense.GameObjects.Bubbles;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;

import TowerDefense.GameObjects.base.GameObject;

public class Bubble extends GameObject{
	private float space;
    public Bubble(Vector2 position, Vector2 size) {
    	super(position, size);
        this.position = position;
        this.size = size;
        textureRegions = AssetsControl.getTextureRegions("bubblesLobby");
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.15f);
        this.currentTRegion = AssetsControl.getCurrentTRegion(animation);
        this.space = MathUtils.random(30f,70f);
    }
    
	public void setPosition(Vector2 pos) {
		this.position = pos;
	}

	public float getSpace() {
		return space;
	}

	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		setPosition(new Vector2(position.x, position.y + 1));
		
	}



}

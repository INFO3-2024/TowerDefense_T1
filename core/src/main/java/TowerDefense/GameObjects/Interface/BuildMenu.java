package TowerDefense.GameObjects.Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.Cannons.Cannon;
import TowerDefense.GameObjects.Mermaids.BlueMermaid;
import TowerDefense.GameObjects.Mermaids.GreenMermaid;
import TowerDefense.GameObjects.Mermaids.PinkMermaid;
import TowerDefense.GameObjects.Mermaids.PurpleMermaid;
import TowerDefense.GameObjects.Mermaids.RedMermaid;
import TowerDefense.GameObjects.base.GameObject;
import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Tower;

public class BuildMenu extends GameObject implements InterfaceMenu {
    private Vector2 turrretPos;

    private int textureOffset;
    private int mermaidNumber = -1;

    public BuildMenu(Vector2 turretPos, int textureOffset) {
        super(new Vector2(turretPos.x + 64, turretPos.y), new Vector2(192, 80));
        this.turrretPos = turretPos;
        this.textureOffset = textureOffset;

        this.currentTRegion = new TextureRegion(AssetsManager.getTexture("buildMenu"), 0, 0, (int)this.size.x, (int)this.size.y);
    
        this.size.x *= 2;
        this.size.y *= 2;

        // Para garantir que nunca seja desenhado fora da tela
        if(this.position.x > Gdx.graphics.getWidth() - this.size.x){
            this.position.x = Gdx.graphics.getWidth() - this.size.x;
        }
        if(this.position.y > Gdx.graphics.getHeight() - this.size.y){
            this.position.y = Gdx.graphics.getHeight() - this.size.y;
        }
    }

    public Vector2 getTowerPos() {
        return this.turrretPos;
    }

    public Tower getMermaid() {
        switch (mermaidNumber) {
            case 0:
                return new PinkMermaid(this.turrretPos, new Vector2(this.textureOffset, this.textureOffset));
            case 1:
                return new RedMermaid(this.turrretPos, new Vector2(this.textureOffset, this.textureOffset));
            case 2:
                return new PurpleMermaid(this.turrretPos, new Vector2(this.textureOffset, this.textureOffset));
            case 3:
                return new GreenMermaid(this.turrretPos, new Vector2(this.textureOffset, this.textureOffset));
            case 4:
            return new BlueMermaid(this.turrretPos, new Vector2(this.textureOffset, this.textureOffset));
            case 5:
                return new Cannon(this.turrretPos, new Vector2(this.textureOffset, this.textureOffset));
            default:
                return null;
        }
    }

    public boolean handleClick(Vector2 mousePos) {
        for (int i = 0; i <= 5; i++) {
            if(
                (mousePos.x >= this.position.x + 32 + 58 * i && mousePos.x < this.position.x + 64 + 58 * i) &&
                (mousePos.y >= this.position.y + 86 && mousePos.y < this.position.y + 150)
            ){
                mermaidNumber = i;
                return true;
            }
        }
        return false;
    }

    public boolean handleMouseOver(Vector2 mousePos){
        return handleClick(mousePos);
    }

    @Override
    public void update(float deltaTime){/* pass */}

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.currentTRegion, (int)this.position.x , (int)this.position.y, (int)this.size.x, (int)this.size.y);
    }
}

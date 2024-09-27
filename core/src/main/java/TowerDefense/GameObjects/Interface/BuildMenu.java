package TowerDefense.GameObjects.Interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.GameObjects.Mermaids.BlueMermaid;
import TowerDefense.GameObjects.Mermaids.GreenMermaid;
import TowerDefense.GameObjects.Mermaids.PinkMermaid;
import TowerDefense.GameObjects.Mermaids.PurpleMermaid;
import TowerDefense.GameObjects.Mermaids.RedMermaid;
import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Mermaid;

public class BuildMenu implements InterfaceMenu {
    private Vector2 turrretPos;
    private Rectangle backgroundRect;
    private Rectangle towerRect;

    private int textureOffset;
    private int mermaidNumber = 99;

    public BuildMenu(Vector2 turretPos) {
        this.turrretPos = turretPos;
        this.backgroundRect = new Rectangle(turretPos.x - 56, turretPos.y + 16, 128, 32);
        this.towerRect = new Rectangle(backgroundRect.x + 8, backgroundRect.y + 8, 16, 16);
    }

    public Vector2 getTowerPos() {
        return this.turrretPos;
    }

    public Mermaid getMermaid() {
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
            default:
                return null;
        }
    }

    public boolean handleClick(Vector2 mousePos) {
        for (int i = 0; i < 5; i++) {
            if (mousePos.x >= towerRect.x + i * (16 + 8) && mousePos.x < towerRect.x + i * (16 + 8) + 16
                    && mousePos.y >= towerRect.y
                    && mousePos.y <= towerRect.y + 16) {
                mermaidNumber = i;
                return true;
            }
        }
        return false;
    }

    public void draw(ShapeRenderer render) {
        Color[] colors = { Color.PINK, Color.RED, Color.PURPLE, Color.GREEN, Color.BLUE };
        render.begin(ShapeType.Filled);
        render.setColor(Color.WHITE);
        render.rect(backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);

        // Se tiver um jeito melhor de fazer isso aq, plmds, ta mt feio
        for (int i = 0; i < colors.length; i++) {
            render.setColor(colors[i]);
            render.rect(towerRect.x + i * (16 + 8), towerRect.y, towerRect.width, towerRect.height);
        }

        render.end();
    }
}

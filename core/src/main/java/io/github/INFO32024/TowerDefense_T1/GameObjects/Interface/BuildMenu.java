package io.github.INFO32024.TowerDefense_T1.GameObjects.Interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.INFO32024.TowerDefense_T1.GameObjects.Mermaids.BlueMermaid;
import io.github.INFO32024.TowerDefense_T1.GameObjects.Mermaids.GreenMermaid;
import io.github.INFO32024.TowerDefense_T1.GameObjects.Mermaids.PinkMermaid;
import io.github.INFO32024.TowerDefense_T1.GameObjects.Mermaids.PurpleMermaid;
import io.github.INFO32024.TowerDefense_T1.GameObjects.Mermaids.RedMermaid;
import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Mermaid;

public class BuildMenu {
    private Vector2 turrretPos;
    private Rectangle backgroundRect;
    private Rectangle towerRect;
    private int textureOffset;

    public BuildMenu(Vector2 turretPos,int textureOffset) {
        this.turrretPos = turretPos;
        this.backgroundRect = new Rectangle(turretPos.x - 56, turretPos.y + 16, 128, 32);
        this.towerRect = new Rectangle(backgroundRect.x + 8, backgroundRect.y + 8, 16, 16);
        this.textureOffset = textureOffset;
    }

    public Vector2 getTowerPos() {
        return this.turrretPos;
    }

    public Mermaid handleClick(Vector2 mousePos) {
        int mermaidNumber = 99;
        for (int i = 0; i < 5; i++) {
            if (mousePos.x >= towerRect.x + i * (textureOffset + textureOffset / 2) && mousePos.x < towerRect.x + i * (textureOffset + textureOffset / 2) + textureOffset
                    && mousePos.y >= towerRect.y
                    && mousePos.y <= towerRect.y + textureOffset) {
                mermaidNumber = i;
                break;
            }
        }

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

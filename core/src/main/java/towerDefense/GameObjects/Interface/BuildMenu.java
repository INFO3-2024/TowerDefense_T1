package towerDefense.GameObjects.Interface;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import towerDefense.GameObjects.Mermaids.BlueMermaid;
import towerDefense.GameObjects.Mermaids.GreenMermaid;
import towerDefense.GameObjects.Mermaids.PinkMermaid;
import towerDefense.GameObjects.Mermaids.PurpleMermaid;
import towerDefense.GameObjects.Mermaids.RedMermaid;
import towerDefense.GameObjects.base.Mermaid;

public class BuildMenu {
    private Vector2 turrretPos;
    private Rectangle backgroundRect;
    private Rectangle towerRect;

    public BuildMenu(Vector2 turretPos) {
        this.turrretPos = turretPos;
        this.backgroundRect = new Rectangle(turretPos.x - 56, turretPos.y + 16, 128, 32);
        this.towerRect = new Rectangle(backgroundRect.x + 8, backgroundRect.y + 8, 16, 16);
    }

    public Vector2 getTowerPos() {
        return this.turrretPos;
    }

    public Mermaid handleClick(Vector2 mousePos) {
        int mermaidNumber = 99;
        for (int i = 0; i < 5; i++) {
            if (mousePos.x >= towerRect.x + i * (16 + 8) && mousePos.x < towerRect.x + i * (16 + 8) + 16
                    && mousePos.y >= towerRect.y
                    && mousePos.y <= towerRect.y + 16) {
                mermaidNumber = i;
                break;
            }
        }

        switch (mermaidNumber) {
            case 0:
                return new BlueMermaid(this.turrretPos, new Vector2(16, 16));
            case 1:
                return new GreenMermaid(this.turrretPos, new Vector2(16, 16));
            case 2:
                return new PinkMermaid(this.turrretPos, new Vector2(16, 16));
            case 3:
                return new PurpleMermaid(this.turrretPos, new Vector2(16, 16));
            case 4:
                return new RedMermaid(this.turrretPos, new Vector2(16, 16));
            default:
                return null;
        }

    }

    public void draw(ShapeRenderer render) {
        Color[] colors = { Color.BLUE, Color.GREEN, Color.PINK, Color.PURPLE, Color.RED };
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

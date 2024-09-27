package TowerDefense.GameObjects.Interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Mermaid;

public class UpgradeMenu implements InterfaceMenu {
    private Mermaid turret;
    private Rectangle backgroundRect;
    private Rectangle towerRect;

    private int mermaidUpdate = 99;

    public UpgradeMenu(Mermaid turrent) {
        this.turret = turrent;
        this.backgroundRect = new Rectangle(turrent.getPosition().x - 56, turrent.getPosition().y + 16, 128, 32);
        this.towerRect = new Rectangle(backgroundRect.x + 8, backgroundRect.y + 8, 16, 16);
    }

    public boolean handleClick(Vector2 mousePos) {
        for (int i = 0; i < 5; i++) {
            if (mousePos.x >= towerRect.x + i * (16 + 8) && mousePos.x < towerRect.x + i * (16 + 8) + 16
                    && mousePos.y >= towerRect.y
                    && mousePos.y <= towerRect.y + 16) {
                mermaidUpdate = i;
                return true;
            }
        }
        return false;
    }

    public int upgrade(int coins) {
        switch (mermaidUpdate) {
            case 0:
                if (coins >= turret.getDamageUpgradePrice()) {
                    turret.upgradeDamage();
                    return coins - turret.getDamageUpgradePrice();
                }
            case 1:
                if (coins >= turret.getBulletDelayUpgradePrice()) {
                    turret.upgradeBulletDelay();
                    return coins - turret.getBulletDelayUpgradePrice();
                }
            case 2:
                if (coins >= turret.getBulletSpeedUpgradePrice()) {
                    turret.upgradeBulletSpeed();
                    return coins - turret.getBulletSpeedUpgradePrice();
                }
            case 3:
                if (coins >= turret.getRangeUpgradePrice()) {
                    turret.upgradeRange();
                    return coins - turret.getRangeUpgradePrice();
                }
            case 4:
                if (coins >= turret.getLevelUpPrice()) {
                    turret.levelUp();
                    return coins - turret.getLevelUpPrice();
                }
            default:
                return coins;
        }
    }

    public Vector2 getTowerPos() {
        return turret.getPosition();
    }

    public void draw(ShapeRenderer render) {
        Color[] colors = { Color.GREEN, Color.PURPLE, Color.RED, Color.SKY, Color.GOLD };
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

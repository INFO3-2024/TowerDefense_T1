package TowerDefense.GameObjects.Interface;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.GameObject;
import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Mermaid;

public class UpgradeMenu extends GameObject implements InterfaceMenu {
    private Mermaid turret;

    private int mermaidUpdate, lastOver = -1;

    TextureRegion buttonsTRegion;

    ArrayList<TextureRegion> splitedBtnsTRegion, buttonsNormal, buttonsOver, numbersFont;

    public UpgradeMenu(Mermaid turrent) {
        super(new Vector2(turrent.getPosition().x + 64, turrent.getPosition().y), new Vector2(48, 32));
        this.turret = turrent;

        this.currentTRegion = new TextureRegion(AssetsControl.getTexture("upgradeMenu"), 0, 0, (int) this.size.x,
                (int) this.size.y);
        this.buttonsTRegion = new TextureRegion(AssetsControl.getTexture("upgradeMenu"), (int) this.size.x, 0, 64, 24);

        this.buttonsNormal = new ArrayList<TextureRegion>();
        this.buttonsOver = new ArrayList<TextureRegion>();
        this.numbersFont = new ArrayList<TextureRegion>();

        for (int i = 0; i < 3; i++) {
            buttonsNormal.add(new TextureRegion(this.buttonsTRegion, 0, i * 8, 32, 8));
        }

        for (int i = 0; i < 3; i++) {
            buttonsOver.add(new TextureRegion(this.buttonsTRegion, 32, i * 8, 32, 8));
        }

        for (int i = 0; i <= 10; i++) {
            numbersFont.add(
                    new TextureRegion(
                            new TextureRegion(AssetsControl.getTexture("upgradeMenu"), (int) this.size.x,
                                    (int) this.size.y - 5, 30, 5),
                            i * 3,
                            0,
                            3,
                            5));
        }

        this.splitedBtnsTRegion = new ArrayList<TextureRegion>(buttonsNormal);

        this.size.x *= 3;
        this.size.y *= 3;

        // Para garantir que nunca seja desenhado fora da tela
        if (this.position.x > Gdx.graphics.getWidth() - this.size.x) {
            this.position.x = Gdx.graphics.getWidth() - this.size.x;
        }
        if (this.position.y > Gdx.graphics.getHeight() - this.size.y) {
            this.position.y = Gdx.graphics.getHeight() - this.size.y;
        }
    }

    private int buttonColide(Vector2 mousePos) {
        for (int i = 0; i < this.splitedBtnsTRegion.size(); i++) {
            if ((mousePos.x >= this.position.x + (2 * 3) && mousePos.x < this.position.x + (32 - 4) * 3) &&
                    (mousePos.y >= this.position.y + (3 + i * 9) * 3
                            && mousePos.y < this.position.y + (3 + i * 9 + 8) * 3)) {
                return i;
            }
        }

        return -1;
    }

    public boolean handleClick(Vector2 mousePos) {
        mermaidUpdate = buttonColide(mousePos);

        return mermaidUpdate == -1 ? false : true;
    }

    public boolean handleMouseOver(Vector2 mousePos) {
        int buttonPressed = buttonColide(mousePos);

        if (buttonPressed == -1) {
            if (lastOver != buttonPressed) {
                lastOver = buttonPressed;
                this.splitedBtnsTRegion = new ArrayList<TextureRegion>(buttonsNormal);
            }
            return false;
        }

        this.splitedBtnsTRegion.set(buttonPressed, buttonsOver.get(buttonPressed));

        lastOver = buttonPressed;
        return true;
    }

    public int upgrade(int coins) {
        switch (mermaidUpdate) {
            case 0:
                if (turret.canUpgrade() && turret.getBulletSpeedUpgradePrice() < coins) {
                    int returnCoins = coins - turret.getBulletSpeedUpgradePrice();
                    turret.upgradeBulletSpeed();
                    return returnCoins;
                }
                break;
            case 1:
                if (turret.canUpgrade() && turret.getRangeUpgradePrice() < coins) {
                    int returnCoins = coins - turret.getRangeUpgradePrice();
                    turret.upgradeRange();
                    return returnCoins;
                }
                break;
            case 2:
                if (turret.canUpgrade() && turret.getDamageUpgradePrice() < coins) {
                    int returnCoins = coins - turret.getDamageUpgradePrice();
                    turret.upgradeDamage();
                    return returnCoins;
                }
                break;
        }
        return coins;
    }

    @Override
    public void update(float deltaTime) {
        /* pass */}

    public Vector2 getTowerPos() {
        return turret.getPosition();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.currentTRegion, (int) this.position.x, (int) this.position.y, (int) this.size.x,
                (int) this.size.y);

        for (int i = 0; i < this.splitedBtnsTRegion.size(); i++) {
            batch.draw(this.splitedBtnsTRegion.get(i), (int) this.position.x, (int) this.position.y + (3 + i * 9) * 3,
                    32 * 3, 8 * 3);

            batch.draw(
                    this.numbersFont.get(turret.getUpdates(i)),
                    (int) this.position.x + 43 * 3,
                    (int) this.position.y + (3 + 11 * i) * 3,
                    3 * 3,
                    5 * 3);
        }
    }
}

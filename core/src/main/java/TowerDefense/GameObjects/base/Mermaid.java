package TowerDefense.GameObjects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;

import java.util.ArrayList;

public abstract class Mermaid extends GameObject {
    protected int price; // Moedas
    protected float range;
    protected float bulletSpeed; // Frames/Second
    protected float bulletDelay;

    protected Enemy currenteTarget;
    protected float timeFromLastBullet = 9999999999.f;
    protected float damage;

    protected int level = 1;
    protected int[] upgrades = { 0, 0, 0 };
    protected int mermaidType = 0; // Usado para pegar a textura certa
    protected int sumUpgrades = 0;

    protected ArrayList<Bullet> bullets;
    // Motivo de ser um ArrayList é que em alguns casos, muito especificos, uma
    // torre pode ter duas+ balas ao mesmo tempo, pra evitar dor de cebeça no
    // futuro, utiliza-se um array

    protected PowerUp powerUp;

    public Mermaid(Vector2 position, Vector2 size) {
        super(position, size);
        this.bulletSpeed = 250.f;
        this.bullets = new ArrayList<Bullet>();
    }

    public float getRange() {
        return this.range;
    }

    public int getPrice() {
        return this.price;
    }

    public void setCurrentTarget(Enemy enemy) {
        this.currenteTarget = enemy;
    }

    public boolean inRange(Vector2 pos) {
        if (Math.pow(range * 64, 2) >= Math.pow(pos.x - this.position.x, 2) + Math.pow(pos.y - this.position.y, 2)) {
            return true;
        }
        return false;
    }

    protected void shoot(float deltaTime) {
        if (bulletDelay <= timeFromLastBullet) {
            float distanceToEnemy = (float) Math.sqrt(
                    Math.pow(currenteTarget.position.x - this.position.x, 2)
                            + Math.pow(currenteTarget.position.y - this.position.y, 2));

            float angle = (float) Math.atan2(currenteTarget.position.y - this.position.y,
                    currenteTarget.position.x - this.position.x);
            bullets.add(new Bullet(new Vector2(this.position), new Vector2(this.size), bulletSpeed, angle,
                    distanceToEnemy / bulletSpeed, currenteTarget));
            timeFromLastBullet = 0;
        }
        timeFromLastBullet += deltaTime;
    }

    public boolean canShoot() {
        if (this.currenteTarget == null) {
            return false;
        }
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (powerUp != null) {
            this.powerUp.update(deltaTime);
        }

        if (currenteTarget != null) {
            shoot(deltaTime);
        }

        if (currenteTarget != null && mermaidType % 2 == 0) {
            mermaidType += 1;
            this.animation = AssetsControl.getAnimation(textureRegions, mermaidType, 0.15f);
        } else if (currenteTarget == null && mermaidType % 2 == 1) {
            mermaidType -= 1;
            this.animation = AssetsControl.getAnimation(textureRegions, mermaidType, 0.15f);
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (bullet.hit()) {
                bullet.getBulletTarget().damage(this.damage);
                bullets.remove(i);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);

        for (Bullet bullet : bullets) {
            bullet.currentTRegion = new TextureRegion(new Texture(Gdx.files.internal("Mermaids/Power.png")),
                    (level - 1) * 64, 0,
                    64, 64);
            bullet.draw(batch);
        }
        if (powerUp != null) {
            this.powerUp.draw(batch);
        }
    }

    public void drawPowerUpLoading(ShapeRenderer shape) {
        if (powerUp != null) {
            powerUp.drawPowerUpLoading(this.position, new Vector2(this.size.x, 2), shape);
        }
    }

    public float getDamage() {
        return this.damage;
    }

    // Upgrade functions
    public int getDamageUpgradePrice() {
        return (this.upgrades[2] + 1) * 50;
    }

    public int getRangeUpgradePrice() {
        return (this.upgrades[1] + 1) * 50;
    }

    public int getBulletSpeedUpgradePrice() {
        return (this.upgrades[0] + 1) * 50;
    }

    public boolean canUpgrade() {
        if (sumUpgrades >= 9 || level >= 3)
            return false;
        return true;
    }

    public boolean upgradeDamage() {
        if (sumUpgrades >= 9 || level >= 3)
            return false;

        this.upgrades[2]++;
        this.sumUpgrades++;
        this.damage = this.damage * 1.1f;

        levelUp();

        return true;
    }

    public boolean upgradeRange() {
        if (sumUpgrades >= 9 || level >= 3)
            return false;

        this.upgrades[1]++;
        this.sumUpgrades++;
        this.range = this.range * 1.5f;

        levelUp();
        return true;
    }

    public boolean upgradeBulletSpeed() {
        if (sumUpgrades >= 9 || level >= 3)
            return false;

        this.upgrades[0]++;
        this.sumUpgrades++;
        this.bulletSpeed = this.bulletSpeed * 1.2f;
        this.bulletDelay = this.bulletDelay * 0.9f;

        levelUp();

        return true;
    }

    public int getUpdates(int i) {
        return this.upgrades[i];
    }

    protected void levelUp() {
        if (sumUpgrades < this.level * 3) {
            return;
        }

        // Sim, cada nivel que ela aumenta tem que aumentar 2 aqui
        // se eu fiz assim é pq é assim.

        this.level += 1;
        this.mermaidType += 2;
        this.animation = AssetsControl.getAnimation(textureRegions, mermaidType, 0.15f);
        if (this.level >= 3) {
            this.addPowerUp();
        }
    }

    public void addPowerUp() {
    }

    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

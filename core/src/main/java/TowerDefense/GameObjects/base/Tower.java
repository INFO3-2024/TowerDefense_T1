package TowerDefense.GameObjects.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;

import java.util.ArrayList;

public abstract class Tower extends GameObject {
    protected int price; // Moedas
    protected float range;
    protected float bulletSpeed; // Frames/Second
    protected float bulletDelay;

    protected Enemy currenteTarget;
    protected float timeFromLastBullet = 9999999999.f;
    protected float damage;

    protected int level = 1;
    protected int[] upgrades = {0, 0, 0};
    protected int mermaidType = 0; // Usado para pegar a textura certa
    protected int sumUpgrades = 0;

    TextureRegion bulletTexture;

    protected ArrayList<Bullet> bullets;
    // Motivo de ser um ArrayList é que em alguns casos, muito especificos, uma
    // torre pode ter duas+ balas ao mesmo tempo, pra evitar dor de cebeça no
    // futuro, utiliza-se um array

    public Tower(Vector2 position, Vector2 size, int power) {
        super(position, size);
        this.bulletSpeed = 250.f;
        this.bullets = new ArrayList<Bullet>();

        bulletTexture = new TextureRegion(AssetsManager.getTexture("towersPower"), power * 64, 0, 64, 64);
    }

    public Tower(Vector2 position, Vector2 size) {
        this(position, size, 0);
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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        
        if (currenteTarget != null) {
            shoot(deltaTime);

        }

        if(currenteTarget != null && mermaidType % 2 == 0) {
            mermaidType += 1;
            this.animation = AssetsManager.getAnimation(textureRegions, mermaidType, 0.15f);
        } else if(currenteTarget == null && mermaidType % 2 == 1) {
            mermaidType -= 1;
            this.animation = AssetsManager.getAnimation(textureRegions, mermaidType, 0.15f);
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

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentTRegion, this.position.x - 17, this.position.y, (int)(this.size.x * 1.45), (int)(this.size.y * 1.45));


        for (Bullet bullet : bullets) {
            bullet.currentTRegion = this.bulletTexture;
            bullet.draw(batch);
        }
    }

    // Upgrade functions
    public int getDamageUpgradePrice() {
        return (this.upgrades[2] + 1) * 3;
    }

    public int getRangeUpgradePrice() {
        return (this.upgrades[1] + 1) * 1;
    }

    public int getBulletSpeedUpgradePrice() {

        return (this.upgrades[0] + 1) * 2;
    }

    public boolean upgradeDamage() {
        if(sumUpgrades >= 9 || level >= 3) 
            return false;

        this.upgrades[2]++;
        this.sumUpgrades++;
        this.damage = this.damage * 1.1f;

        levelUp();

        return true;
    }

    public boolean upgradeRange() {
        if(sumUpgrades >= 9 || level >= 3) 
            return false;

        this.upgrades[1]++;
        this.sumUpgrades++;
        this.range = this.range * 1.5f;

        levelUp();
        return true;
    }

    public boolean upgradeBulletSpeed() {
        if(sumUpgrades >= 9 || level >= 3) 
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
        if(sumUpgrades < this.level * 3 ){
            return;
        }

        // Sim, cada nivel que ela aumenta tem que aumentar 2 aqui 
        // se eu fiz assim é pq é assim.

        this.level += 1;
        this.mermaidType += 2;
        this.animation = AssetsManager.getAnimation(textureRegions, mermaidType, 0.15f); 
    
        bulletTexture = new TextureRegion(AssetsManager.getTexture("towersPower"), (this.level - 1) * 64, 0, 64, 64);
    }

    public ArrayList<Bullet> getBullets(){
        return this.bullets;
    }
    
    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

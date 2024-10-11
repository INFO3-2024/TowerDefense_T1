package TowerDefense.GameObjects.Cannons;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.base.Bullet;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Mermaid;

public class Cannon extends Mermaid {

    protected ArrayList<Enemy> enemies;
    protected int bulletRange;

    public Cannon(Vector2 position, Vector2 size) {
        super(new Vector2(position), new Vector2(80, 80));
        super.damage = 6f;
        super.range = 2;
        super.bulletDelay = 2.f;
        super.price = 250;
        this.bulletRange = 64;

        textureRegions = AssetsControl.getTextureRegions("cannon", this.size);
        animation = AssetsControl.getAnimation(textureRegions, 0, 0.8f);
        this.currentTRegion = textureRegions[0][0];

        this.enemies = new ArrayList<Enemy>();

        this.size.x = size.x;
        this.size.y = size.y;
    }

    public void setEnemies(ArrayList<Enemy> e) {
        this.enemies.clear();

        for (Enemy enemy : e) {
            if (Math.sqrt(Math.pow(currenteTarget.getPosition().x - enemy.getPosition().x, 2)
                    + Math.pow(currenteTarget.getPosition().y - enemy.getPosition().y, 2)) <= this.bulletRange) {
                this.enemies.add(enemy);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentTRegion, this.position.x, this.position.y, (int) (this.size.x), (int) (this.size.y));
    }

    @Override
    public void update(float deltaTime) {
        if (currenteTarget != null) {
            shoot(deltaTime);

            this.currentTRegion = AssetsControl.getCurrentTRegion(animation);

        } else if (this.currentTRegion == textureRegions[level - 1][1]) {
            this.currentTRegion = textureRegions[level - 1][0];
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (bullet.hit()) {
                for (Enemy enemy : enemies) {
                    enemy.damage(this.damage);
                }
                bullets.remove(i);
            }
        }
    }

    @Override
    protected void levelUp() {
        if (sumUpgrades < this.level * 3) {
            return;
        }

        this.level += 1;
        this.animation = AssetsControl.getAnimation(textureRegions, (this.level - 1), 0.8f);
        this.currentTRegion = textureRegions[level - 1][0];
    }
}
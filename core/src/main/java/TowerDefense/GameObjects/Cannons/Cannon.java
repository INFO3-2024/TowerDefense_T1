package TowerDefense.GameObjects.Cannons;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.base.Bullet;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Tower;

public class Cannon extends Tower {

    protected ArrayList<Enemy> enemies;
    protected float bulletRange;

    public Cannon(Vector2 position, Vector2 size) {
        super(new Vector2(position), new Vector2(80, 80), 3);
        super.damage = 1.f;
        super.range = 2;
        super.bulletDelay = 2.f;
        super.price = 250;
        this.bulletRange = 1.5f * this.size.x;

        this.textureRegions = AssetsManager.getTextureRegions("cannon", this.size);
        this.animation = AssetsManager.getAnimation(textureRegions, 0, 0.8f);
        this.currentTRegion = textureRegions[0][0];

        this.enemies = new ArrayList<Enemy>();
        
        this.size.x = size.x;
        this.size.y = size.y;
    }

    public void setEnemies(ArrayList<Enemy> e) {
        this.enemies.clear();

        for(Enemy enemy : e) {
            if(Math.sqrt( Math.pow(currenteTarget.getPosition().x - enemy.getPosition().x, 2) + Math.pow(currenteTarget.getPosition().y - enemy.getPosition().y, 2)) <= this.bulletRange) {
                this.enemies.add(enemy);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if (currenteTarget != null) {
            shoot(deltaTime);

            this.currentTRegion = AssetsManager.getCurrentTRegion(animation);
            
        } else if(this.currentTRegion == textureRegions[level - 1][1]){
            this.currentTRegion = textureRegions[level - 1][0];
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (bullet.hit()) {
                for(Enemy enemy : enemies) {
                     enemy.damage(this.damage);
                }

                currenteTarget.damage(this.damage); // O inimigo que está na mira recebe mais dano

                bullets.remove(i);
            }
        }
    }

    @Override
    protected void levelUp() {
        if(sumUpgrades < this.level * 3 ){
            return;
        }

        this.level += 1;
        this.animation = AssetsManager.getAnimation(textureRegions, (this.level - 1), 0.8f);
        this.currentTRegion = textureRegions[level - 1][0];
    }
}
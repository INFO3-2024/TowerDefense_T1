package TowerDefense.GameObjects.PowerUps;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.PowerUp;

public class PinkPowerup extends PowerUp {
    private Texture tornadoTexture;
    private float slowDuration = 2.f;
    private float tornadoAnimation = 100;
    private Mermaid tower;
    private ArrayList<Enemy> effectedEnemies;

    public PinkPowerup(Mermaid tower) {
        this.tornadoTexture = new Texture(Gdx.files.internal("PowerUps/Tornado.png"));
        this.powerUpDelay = 10;
        this.tower = tower;
        effectedEnemies = new ArrayList<Enemy>();
    }

    public void update(float deltaTime) {
        super.updateTime(deltaTime);

        if (tornadoAnimation < 1) {
            this.tornadoAnimation += deltaTime;
        }

        if (canShoot && this.tower.canShoot()) {
            this.use();
            tornadoAnimation = 0;

            for (Enemy enemy : PowerUp.getEnemies()) {
                if (tower.inRange(enemy.getPosition())) {
                    effectedEnemies.add(enemy);
                    enemy.setVelocity(enemy.getOriginalvelocity() * 0.5f);
                    enemy.pinkTowerDebuffReset();
                }
            }
        }

        for (Enemy enemy : effectedEnemies) {
            if (enemy.getPinkTowerDebuff() >= this.slowDuration) {
                enemy.setVelocity(enemy.getOriginalvelocity());
            }
        }

    }

    public void draw(SpriteBatch batch) {
        if (this.tornadoAnimation < 1) {
            batch.draw(tornadoTexture,
                    tower.getPosition().x + tower.getSize().x / 2
                            - this.tornadoAnimation * (tower.getRange() * 128) / 2,
                    tower.getPosition().y + tower.getSize().y / 2
                            - this.tornadoAnimation * (tower.getRange() * 128) / 2,
                    this.tornadoAnimation * (tower.getRange() * 128),
                    this.tornadoAnimation * (tower.getRange() * 128));
        }
    }
}

package TowerDefense.GameObjects.base;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public abstract class PowerUp {
    private static ArrayList<Enemy> enemies;
    protected float powerUpDelay;
    protected float timeSinceLastActivation;
    protected boolean canShoot = false;

    public static void setEnemies(ArrayList<Enemy> enemiesArray) {
        enemies = enemiesArray;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    protected void updateTime(float deltaTime) {
        this.timeSinceLastActivation = Math.min(powerUpDelay, timeSinceLastActivation + deltaTime);
        if (timeSinceLastActivation >= powerUpDelay) {
            this.canShoot = true;
            return;
        }
    }

    public void update(float deltaTime) {
    }

    public void use() {
        if (canShoot) {
            this.canShoot = false;
            this.timeSinceLastActivation = 0;
        }
    }

    public void drawPowerUpLoading(Vector2 pos, Vector2 size, ShapeRenderer shape) {
        shape.begin(ShapeType.Filled);
        shape.setColor(Color.BLACK);
        shape.rect(pos.x - 1, pos.y - 1, size.x + 2, size.y + 2);
        shape.end();

        shape.begin(ShapeType.Filled);
        shape.setColor(Color.GREEN);
        shape.rect(pos.x, pos.y, timeSinceLastActivation / powerUpDelay * size.x, size.y);
        shape.end();
    }

    public void draw(SpriteBatch batch) {
    }
}

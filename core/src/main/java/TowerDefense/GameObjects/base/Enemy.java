package TowerDefense.GameObjects.base;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends GameObject {
    protected float originalVelocity;
    protected float velocity;
    protected float life;
    protected float maxLife;
    protected Queue<Vector2> wayPoints;
    protected boolean fullPath = false;
    protected int dropedCoins;
    protected float distanceWalked;
    protected float[] badEffectTime = { 0.f, 0.f, 0.f, 0.f }; // ROSA, VERDE, VERMELHA, AZUL

    public Enemy(Vector2 size, Queue<Vector2> wayPoints) {
        super(new Vector2(wayPoints.first().x, wayPoints.first().y), size);

        this.wayPoints = new Queue<Vector2>();
        this.setWayPoints(wayPoints);
    }

    public void setWayPoints(Queue<Vector2> wayPoints) {
        this.wayPoints.clear();

        for (Vector2 wayPoint : wayPoints) {
            this.wayPoints.addFirst(wayPoint);
        }
    }

    private void move(float deltaTime) {
        if (this.wayPoints.isEmpty()) {
            this.fullPath = true;
            return;
        }

        Vector2 nextVector = this.wayPoints.last();
        Vector2 diffToNextVector = new Vector2(nextVector.x - this.position.x, nextVector.y - this.position.y);

        this.position.x += this.velocity * deltaTime * Math.signum(diffToNextVector.x);
        this.position.y += this.velocity * deltaTime * Math.signum(diffToNextVector.y);

        this.distanceWalked += (float) Math.abs(this.velocity * deltaTime * Math.signum(diffToNextVector.x))
                + (float) Math.abs(this.velocity * deltaTime * Math.signum(diffToNextVector.y));

        if (Math.signum(diffToNextVector.x) * this.position.x > Math.signum(diffToNextVector.x) * nextVector.x) {
            float leftOverDistance = Math.abs(this.position.x - nextVector.x);
            this.position.x = nextVector.x;
            if (this.wayPoints.isEmpty()) {
                this.fullPath = true;
                return;
            }
            this.position.y += Math.signum(this.wayPoints.last().y - this.position.y) * leftOverDistance;
        }

        if (Math.signum(diffToNextVector.y) * this.position.y > Math.signum(diffToNextVector.y) * nextVector.y) {
            float leftOverDistance = Math.abs(this.position.y - nextVector.y);
            this.position.y = nextVector.y;
            if (this.wayPoints.isEmpty()) {
                this.fullPath = true;
                return;
            }
            this.position.x += Math.signum(this.wayPoints.last().x - this.position.x) * leftOverDistance;
        }

        if (this.position.equals(this.wayPoints.last())) {
            this.wayPoints.removeLast();
        }
    }

    public boolean getPassedState() {
        return this.fullPath;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentTRegion, this.position.x - 17, this.position.y - 14, (int) (this.size.x * 1.45),
                (int) (this.size.y * 1.45));
    }

    public float getVelocity() {
        return this.velocity;
    }

    public float getOriginalvelocity() {
        return this.originalVelocity;
    }

    public void setVelocity(float vel) {
        this.velocity = vel;
    }

    public void damage(float damage) {
        this.life -= damage;
    }

    public float getLife() {
        return this.life;
    }

    public float getDistanceWalked() {
        return this.distanceWalked;
    }

    public void drawLifeBar(ShapeRenderer render) {
        render.begin(ShapeType.Filled);
        render.setColor(Color.BLACK);
        render.rect(this.position.x - 1, this.position.y - 4, this.size.x + 2, 4);
        render.end();

        render.begin(ShapeType.Filled);
        render.setColor(Color.RED);
        render.rect(this.position.x, this.position.y - 3, this.size.x * this.life / this.maxLife, 2);
        render.end();
    }

    public int dropCoins() {
        return this.dropedCoins;
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < badEffectTime.length; i++) {
            badEffectTime[i] += deltaTime;
        }
        super.update(deltaTime);
        this.move(deltaTime);
        this.powerUp(deltaTime);
    }

    public void powerUp(float deltaTime) {
    }

    public void pinkTowerDebuffReset() {
        this.badEffectTime[0] = 0.f;
    }

    public void greenTowerDebuffReset() {
        this.badEffectTime[1] = 0.f;
    }

    public void redTowerDebuffReset() {
        this.badEffectTime[2] = 0.f;
    }

    public void blueTowerDebuffReset() {
        this.badEffectTime[3] = 0.f;
    }

    public float getPinkTowerDebuff() {
        return this.badEffectTime[0];
    }

    public float getGreenTowerDebuff() {
        return this.badEffectTime[1];
    }

    public float getRedTowerDebuff() {
        return this.badEffectTime[2];
    }

    public float getBlueTowerDebuff() {
        return this.badEffectTime[3];
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

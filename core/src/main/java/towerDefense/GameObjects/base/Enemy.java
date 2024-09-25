package towerDefense.GameObjects.base;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends GameObject {
    protected float velocity;
    protected float life;
    protected float maxLife;
    protected Queue<Vector2> wayPoints;
    protected boolean fullPath = false;

    public Enemy(Vector2 position, Vector2 size, Queue<Vector2> wayPoints) {
        super(position, size);

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
        /*
         * OK, HÁ UMA SÉRIE DE PROBLEMAS AQUI, TAMBÉM ME DÓI, JURO QUE ISSO É
         * TEMPORÁRIO (OU NÃO RSRS
         * - Rlx, vou fazer ser temporario
         */

        if (this.wayPoints.isEmpty()) {
            this.fullPath = true;
            return;
        }

        Vector2 nextVector = this.wayPoints.first();
        Vector2 diffToNextVector = new Vector2(nextVector.x - this.position.x, nextVector.y - this.position.y);
        // Essa biblioteca é tão util que não tem a m3rd4 de uma sobrecarga de operador
        // pra somar vetor. OBRIGADO LIBGDX

        this.position.x += this.velocity * deltaTime * Math.signum(diffToNextVector.x);
        this.position.y += this.velocity * deltaTime * Math.signum(diffToNextVector.y);

        if (Math.signum(diffToNextVector.x) * this.position.x > Math.signum(diffToNextVector.x) * nextVector.x) {
            float leftOverDistance = Math.abs(this.position.x - nextVector.x);
            this.position.x = nextVector.x;
            this.wayPoints.removeFirst();
            if (this.wayPoints.isEmpty()) {
                this.fullPath = true;
                return;
            }
            this.position.y += Math.signum(this.wayPoints.first().y - this.position.y) * leftOverDistance;
        }

        if (Math.signum(diffToNextVector.y) * this.position.y > Math.signum(diffToNextVector.y) * nextVector.y) {
            float leftOverDistance = Math.abs(this.position.y - nextVector.y);
            this.position.y = nextVector.y;
            this.wayPoints.removeFirst();
            if (this.wayPoints.isEmpty()) {
                this.fullPath = true;
                return;
            }
            this.position.x += Math.signum(this.wayPoints.first().x - this.position.x) * leftOverDistance;
        }

        if (this.position.equals(this.wayPoints.first())) {
            this.wayPoints.removeFirst();
        }
    }

    public float getVelocity() {
        return this.velocity;
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

    public void drawLifeBar(ShapeRenderer render) {
        render.begin(ShapeType.Filled);
        render.setColor(Color.BLUE);
        render.rect(this.position.x, this.position.y - 3, this.size.x, 2);
        render.end();

        render.begin(ShapeType.Filled);
        render.setColor(Color.YELLOW);
        render.rect(this.position.x, this.position.y - 3, this.size.x * this.life / this.maxLife, 2);
        render.end();
    }

    @Override
    public void update(float deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

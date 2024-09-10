package towerDefense.GameObjects.base;

import com.badlogic.gdx.utils.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends GameObject {
    private float velocity;
    private float life = 3.f;
    private Queue<Vector2> wayPoints;

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

    private void move(float deltaTime) { // OK, HÁ UMA SÉRIE DE PROBLEMAS AQUI, TAMBÉM ME DÓI, JURO QUE ISSO É TEMPORÁRIO (OU NÃO RSRS
        if(!this.wayPoints.notEmpty()){
            return;
        }

        if (this.position.x < this.wayPoints.first().x) {
            this.position.x += 1;/*(int)(this.velocity * deltaTime);*/
        } else if (this.position.x > this.wayPoints.first().x) {
            this.position.x -= 1;/*(int)(this.velocity * deltaTime);*/
        }

        if (this.position.y < this.wayPoints.first().y) {
            this.position.y += 1;/*(int)(this.velocity * deltaTime);*/
        } else if (this.position.y > this.wayPoints.first().y) {
            this.position.y -= 1;/*(int)(this.velocity * deltaTime);*/
        }

        if(this.position.equals(this.wayPoints.first())) {
            this.wayPoints.removeFirst();
        }

        // QUANTO IF KK

        // A partir daqui, codigo provisorio, remover o quanto antes :)
        if (this.position.x >= Gdx.graphics.getWidth()) {
            this.position.x = 0;
        } else if (this.position.y >= Gdx.graphics.getWidth()) {
            this.position.y = 500;
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

    @Override
    public void update(float deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void dispose() {
        /* Nothing to dipose also */
    }
}

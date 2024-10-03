package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.GameObjects.base.Enemy;

public class ZombiePirate extends Enemy {

    public ZombiePirate(Vector2 size, Queue<Vector2> wayPoints) {
        super(size, wayPoints);
        super.dropedCoins = 15;
        super.velocity = 40.f;
        super.life = 3.f;
        super.maxLife = super.life;
    }

}

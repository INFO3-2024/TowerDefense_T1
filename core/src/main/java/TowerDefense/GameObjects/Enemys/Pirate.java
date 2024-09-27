package TowerDefense.GameObjects.Enemys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import TowerDefense.GameObjects.base.Enemy;

public class Pirate extends Enemy {

        public Pirate(Vector2 position, Vector2 size, Queue<Vector2> wayPoints) {
                super(position, size, wayPoints);
                super.dropedCoins = 10;
                super.velocity = 80.f;
                super.life = 1.25f;
                super.maxLife = super.life;
        }

}

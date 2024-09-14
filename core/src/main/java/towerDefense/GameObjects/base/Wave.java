package towerDefense.GameObjects.base;

// Mais cru que isso só 2 disso
// Pensamentos aqui para o futuro, colocar isso dentro de uma classe: EnemyController/EnemyHandler

public class Wave {
    // private int goldRecived = 50;
    private float timeBetween = 0.500f;
    private int enemyQuantity = 5;

    private float timeSinceLastSpawn = 0.f;

    public boolean canSpawn(float deltaTime) { // Update
        if (timeBetween <= timeSinceLastSpawn && enemyQuantity > 0) {
            enemyQuantity--;
            timeSinceLastSpawn = 0;
            return true;
        }
        timeSinceLastSpawn += deltaTime;
        return false;
    }
}

package TowerDefense.GameObjects.Fish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import TowerDefense.AssetsManager.AssetsControl;

public class Fish {
    private Texture texture;
    private TextureRegion fishTextureRegion;
    private float xPosition;
    private float yPosition;

    private Random random;
    private List<Rectangle> fishRegions;

    public Fish(Texture texture, float xPosition, float yPosition) {
        this.texture = texture;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
      
        this.random = new Random(); 
        this.fishRegions = new ArrayList<>();
        inicializarPosicoes();
    
        // Sorteia uma região logo no início para inicializar fishTextureRegion
        this.fishTextureRegion = sortearPosicao();
    
        // Agora que fishTextureRegion está inicializada, podemos sortear a altura
        this.yPosition = sortearAltura();
    }
    
    // Inicializa as posições possíveis dos peixes na textura
    private void inicializarPosicoes(){
        // Adiciona várias regiões de peixe dentro da textura (exemplo de múltiplas posições)
        fishRegions.add(new Rectangle(0, 0, 48, 48));   // Posição de um peixe na textura
        fishRegions.add(new Rectangle(48, 0, 48, 48));  // Posição de outro peixe
        fishRegions.add(new Rectangle(96, 0, 48, 48));  // Posição de mais um peixe
        fishRegions.add(new Rectangle(48, 144, 48, 48)); // Mais uma posição
        fishRegions.add(new Rectangle(96, 144, 48, 48)); // Outra posição de peixe
        fishRegions.add(new Rectangle(0, 192, 48, 48)); // Outra posição de peixe
        fishRegions.add(new Rectangle(48, 240, 48, 48)); // Outra posição de peixe
        // Adicione mais regiões conforme necessário, com base na sua textura
    }

    private TextureRegion sortearPosicao() {
        int index = random.nextInt(fishRegions.size()); // Sorteia um índice da lista
        Rectangle selectedRegion = fishRegions.get(index); // Obtém a região sorteada
        return new TextureRegion(texture, 
                                 (int) selectedRegion.x, 
                                 (int) selectedRegion.y, 
                                 (int) selectedRegion.width, 
                                 (int) selectedRegion.height);
    }

    // Método para obter a textura
    public TextureRegion getFishTextureRegion() {
        return fishTextureRegion;
    }

    // Métodos para obter as posições
    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    // Método para atualizar a lógica do peixe
    public void update(float delta) {
        // Atualize a lógica do peixe, como movimentação
        xPosition += 60 * delta; // Movimenta o peixe na horizontal com base na velocidade

        // Verifica se o peixe saiu da tela e reaparece do lado oposto
        if (xPosition > Gdx.graphics.getWidth()) {
            xPosition = -fishTextureRegion.getRegionWidth(); // Reaparece do lado esquerdo
            yPosition = sortearAltura(); // Sorteia nova altura
            fishTextureRegion = sortearPosicao(); // Sorteia uma nova região de peixe
        }
    }

    private float sortearAltura() {
        // Sorteia uma altura aleatória dentro da altura da tela
        return (float) (Math.random() * (Gdx.graphics.getHeight() - fishTextureRegion.getRegionHeight()));
    }

    public List<Fish> getFishList(AssetsControl assetsControl) {
        return assetsControl.fishList;
    }
}

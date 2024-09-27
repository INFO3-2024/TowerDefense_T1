package com.testeassets.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TesteAssets extends ApplicationAdapter {
    private static TesteAssets instanciaUnica;

    private AssetManager assetManager;
    private SpriteBatch batch;
    private Texture MapOne, alga, bolha, jellyfishes1, jellyfishes2, jellyfishes3, jellyfishes4, jellyfishes5, jellyfishes6;
    private Texture enemy1, enemy2, enemy3, enemy4, enemy5;
    private Music musica;
    private AssetsAnimation  algaAimation, bolhaAnimation, jellyAnimation1, jellyAnimation2, jellyAnimation3, jellyAnimation4, jellyAnimation5, jellyAnimation6  ;
    private AssetsAnimation enemyAnimation1, enemyAnimation2, enemyAnimation3, enemyAnimation4, enemyAnimation5;
    // Armazenamento das animações por sereia e nível
    private Map<String, Map<Integer, AssetsAnimation[]>> mermaidAnimations;

    private TesteAssets() {}

    public static synchronized TesteAssets getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new TesteAssets();
        }
        return instanciaUnica;
    }

    @Override
    public void create() {
        assetManager = new AssetManager();
        batch = new SpriteBatch();

        // Carregamento dos assets
        assetManager.load("Maps1.png", Texture.class);

        // Carregando todas as texturas de ataque e idle para níveis 1, 2 e 3 de todas as sereias
        carregarTexturas();

        assetManager.load("041 Goldenrod City.mp3", Music.class);
        assetManager.finishLoading();

        MapOne = assetManager.get("Maps1.png", Texture.class);
       

        criarAnimacoes();

        // Música de fundo
        musica = assetManager.get("041 Goldenrod City.mp3", Music.class);
        musica.setLooping(true);
        musica.setVolume(0.1f);
        musica.play();
    }

    private void carregarTexturas() {
        // Carregar texturas para PinkMermaid
        assetManager.load("PinkMermaid-attack-lvl1.png", Texture.class);
        assetManager.load("PinkMermaid-idle-lvl1.png", Texture.class);
        assetManager.load("PinkMermaid-attack-lvl2.png", Texture.class);
        assetManager.load("PinkMermaid-idle-lvl2.png", Texture.class);
        assetManager.load("PinkMermaid-attack-lvl3.png", Texture.class);
        assetManager.load("PinkMermaid-idle-lvl3.png", Texture.class);

        // Carregar texturas para BlueMermaid
        assetManager.load("BlueMermaid-attack-lvl1.png", Texture.class);
        assetManager.load("BlueMermaid-idle-lvl1.png", Texture.class);
        assetManager.load("BlueMermaid-attack-lvl2.png", Texture.class);
        assetManager.load("BlueMermaid-idle-lvl2.png", Texture.class);
        assetManager.load("BlueMermaid-attack-lvl3.png", Texture.class);
        assetManager.load("BlueMermaid-idle-lvl3.png", Texture.class);

        // Carregar texturas para GreenMermaid
        assetManager.load("GreenMermaid-attack-lvl1.png", Texture.class);
        assetManager.load("GreenMermaid-idle-lvl1.png", Texture.class);
        assetManager.load("GreenMermaid-attack-lvl2.png", Texture.class);
        assetManager.load("GreenMermaid-idle-lvl2.png", Texture.class);
        assetManager.load("GreenMermaid-attack-lvl3.png", Texture.class);
        assetManager.load("GreenMermaid-idle-lvl3.png", Texture.class);

        // Carregar texturas para PurpleMermaid
        assetManager.load("PurpleMermaid-attack-lvl1.png", Texture.class);
        assetManager.load("PurpleMermaid-idle-lvl1.png", Texture.class);
        assetManager.load("PurpleMermaid-attack-lvl2.png", Texture.class);
        assetManager.load("PurpleMermaid-idle-lvl2.png", Texture.class);
        assetManager.load("PurpleMermaid-attack-lvl3.png", Texture.class);
        assetManager.load("PurpleMermaid-idle-lvl3.png", Texture.class);

        // Carregar texturas Scenery
        assetManager.load("alga.png", Texture.class);
        assetManager.load("Bolhas.png", Texture.class);

        // Carregar texturas jellyfishes
        assetManager.load("jellyfishes 1.png", Texture.class);
        assetManager.load("jellyfishes 2.png", Texture.class);
        assetManager.load("jellyfishes 3.png", Texture.class);
        assetManager.load("jellyfishes 4.png", Texture.class);
        assetManager.load("jellyfishes 5.png", Texture.class);
        assetManager.load("jellyfishes 6.png", Texture.class);

        // Carregar texturas Enemys
        assetManager.load("BasicEnemy1.png", Texture.class);
        assetManager.load("BasicEnemy2.png", Texture.class);
        assetManager.load("BasicEnemy3.png", Texture.class);
        assetManager.load("BasicEnemy4.png", Texture.class);
        assetManager.load("BasicEnemy5.png", Texture.class);
        
    }

    private void criarAnimacoes() {
        mermaidAnimations = new HashMap<>();

        // Criar animações para PinkMermaid
        Map<Integer, AssetsAnimation[]> pinkAnimations = new HashMap<>();
        pinkAnimations.put(1, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("PinkMermaid-attack-lvl1.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("PinkMermaid-idle-lvl1.png", Texture.class), 4, 1, 0.17f)
        });
        pinkAnimations.put(2, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("PinkMermaid-attack-lvl2.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("PinkMermaid-idle-lvl2.png", Texture.class), 4, 1, 0.17f)
        });
        pinkAnimations.put(3, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("PinkMermaid-attack-lvl3.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("PinkMermaid-idle-lvl3.png", Texture.class), 4, 1, 0.17f)
        });
        mermaidAnimations.put("PinkMermaid", pinkAnimations);

        // Criar animações para BlueMermaid
        Map<Integer, AssetsAnimation[]> blueAnimations = new HashMap<>();
        blueAnimations.put(1, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("BlueMermaid-attack-lvl1.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("BlueMermaid-idle-lvl1.png", Texture.class), 4, 1, 0.17f)
        });
        blueAnimations.put(2, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("BlueMermaid-attack-lvl2.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("BlueMermaid-idle-lvl2.png", Texture.class), 4, 1, 0.17f)
        });
        blueAnimations.put(3, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("BlueMermaid-attack-lvl3.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("BlueMermaid-idle-lvl3.png", Texture.class), 4, 1, 0.17f)
        });
        mermaidAnimations.put("BlueMermaid", blueAnimations);

        // Criar animações para GreenMermaid
        Map<Integer, AssetsAnimation[]> greenAnimations = new HashMap<>();
        greenAnimations.put(1, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("GreenMermaid-attack-lvl1.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("GreenMermaid-idle-lvl1.png", Texture.class), 4, 1, 0.17f)
        });
        greenAnimations.put(2, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("GreenMermaid-attack-lvl2.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("GreenMermaid-idle-lvl2.png", Texture.class), 4, 1, 0.17f)
        });
        greenAnimations.put(3, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("GreenMermaid-attack-lvl3.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("GreenMermaid-idle-lvl3.png", Texture.class), 4, 1, 0.17f)
        });
        mermaidAnimations.put("GreenMermaid", greenAnimations);

        // Criar animações para PurpleMermaid
        Map<Integer, AssetsAnimation[]> purpleAnimations = new HashMap<>();
        purpleAnimations.put(1, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("PurpleMermaid-attack-lvl1.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("PurpleMermaid-idle-lvl1.png", Texture.class), 4, 1, 0.17f)
        });
        purpleAnimations.put(2, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("PurpleMermaid-attack-lvl2.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("PurpleMermaid-idle-lvl2.png", Texture.class), 4, 1, 0.17f)
        });
        purpleAnimations.put(3, new AssetsAnimation[] {
            new AssetsAnimation(assetManager.get("PurpleMermaid-attack-lvl3.png", Texture.class), 4, 1, 0.15f),
            new AssetsAnimation(assetManager.get("PurpleMermaid-idle-lvl3.png", Texture.class), 4, 1, 0.17f)
        });
        mermaidAnimations.put("PurpleMermaid", purpleAnimations);

        // pegando texturas carregadas
        alga = assetManager.get("alga.png", Texture.class);
        bolha = assetManager.get("Bolhas.png", Texture.class);

        jellyfishes1 = assetManager.get("jellyfishes 1.png", Texture.class);
        jellyfishes2 = assetManager.get("jellyfishes 2.png", Texture.class);
        jellyfishes3 = assetManager.get("jellyfishes 3.png", Texture.class);
        jellyfishes4 = assetManager.get("jellyfishes 4.png", Texture.class);
        jellyfishes5 = assetManager.get("jellyfishes 5.png", Texture.class);
        jellyfishes6 = assetManager.get("jellyfishes 6.png", Texture.class);
        
        enemy1 = assetManager.get("BasicEnemy1.png", Texture.class);
        enemy2 = assetManager.get("BasicEnemy2.png", Texture.class);
        enemy3 = assetManager.get("BasicEnemy3.png", Texture.class);
        enemy4 = assetManager.get("BasicEnemy4.png", Texture.class);
        enemy5 = assetManager.get("BasicEnemy5.png", Texture.class);

        // Criar animações para SceneryAnimals
        algaAimation = new AssetsAnimation(alga, 3, 1, 0.18f);
        bolhaAnimation = new AssetsAnimation(bolha, 4, 1, 0.15f);

        // Criar animações para Jellyfishes
        jellyAnimation1 = new AssetsAnimation(jellyfishes1, 3 , 1 , 0.3f);
        jellyAnimation2 = new AssetsAnimation(jellyfishes2, 3 , 1 , 0.3f);
        jellyAnimation3 = new AssetsAnimation(jellyfishes3, 3 , 1 , 0.3f);
        jellyAnimation4 = new AssetsAnimation(jellyfishes4, 3 , 1 , 0.3f);
        jellyAnimation5 = new AssetsAnimation(jellyfishes5, 3 , 1 , 0.3f);
        jellyAnimation6 = new AssetsAnimation(jellyfishes6, 3 , 1 , 0.3f);
        
        //Criar animações para Basic Enemys
        enemyAnimation1 = new AssetsAnimation(enemy1, 4, 1, 0.15f);
        enemyAnimation2 = new AssetsAnimation(enemy2, 4, 1, 0.15f);
        enemyAnimation3 = new AssetsAnimation(enemy3, 4, 1, 0.15f);
        enemyAnimation4 = new AssetsAnimation(enemy4, 4, 1, 0.15f);
        enemyAnimation5 = new AssetsAnimation(enemy5, 4, 1, 0.15f);
        
        // Inicializar a batch para desenhar as texturas
        batch = new SpriteBatch();

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        float delta = Gdx.graphics.getDeltaTime();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        // // Quantidade de sereias e níveis
        int numMermaids = 4;  // 4 sereias
        int numLevels = 3;    // 3 níveis (1, 2, 3)
        
        // Cálculos para uma exibição responsiva
        float mermaidWidth = screenWidth / numMermaids;
        float mermaidHeight = screenHeight / (numLevels * 2);  // Para garantir espaço para idle e ataque
        
        batch.begin();
        // Desenhar o fundo
        batch.draw(MapOne, 0, 0, screenWidth, screenHeight);
    
        // Para cada sereia, desenhar suas animações de ataque e idle por nível
        for (int level = 1; level <= numLevels; level++) {
            float yPosAttack = screenHeight - (level * 2 * mermaidHeight); // Linha para ataque
            float yPosIdle = screenHeight - (level * 2 * mermaidHeight) + mermaidHeight; // Linha para idle
            
            // Desenhar as animações de ataque e idle de cada sereia em cada nível
            batch.draw(mermaidAnimations.get("PinkMermaid").get(level)[0].getCurrentFrame(delta, true), 0, yPosAttack);
            batch.draw(mermaidAnimations.get("PinkMermaid").get(level)[1].getCurrentFrame(delta, true), 0, yPosIdle);
            
            batch.draw(mermaidAnimations.get("BlueMermaid").get(level)[0].getCurrentFrame(delta, true), mermaidWidth, yPosAttack);
            batch.draw(mermaidAnimations.get("BlueMermaid").get(level)[1].getCurrentFrame(delta, true), mermaidWidth, yPosIdle);
            
            batch.draw(mermaidAnimations.get("GreenMermaid").get(level)[0].getCurrentFrame(delta, true), mermaidWidth * 2, yPosAttack);
            batch.draw(mermaidAnimations.get("GreenMermaid").get(level)[1].getCurrentFrame(delta, true), mermaidWidth * 2, yPosIdle);
            
            batch.draw(mermaidAnimations.get("PurpleMermaid").get(level)[0].getCurrentFrame(delta, true), mermaidWidth * 3, yPosAttack);
            batch.draw(mermaidAnimations.get("PurpleMermaid").get(level)[1].getCurrentFrame(delta, true), mermaidWidth * 3, yPosIdle);
            
        }

        // Desenhar a alga e a bolha

        TextureRegion frameBolha= bolhaAnimation.getCurrentFrame(delta, true);
        TextureRegion frameAlga = algaAimation.getCurrentFrame(delta, true);
        TextureRegion frameJelly1= jellyAnimation1.getCurrentFrame(delta, true);
        TextureRegion frameJelly2 = jellyAnimation2.getCurrentFrame(delta, true);
        TextureRegion frameJelly3 = jellyAnimation3.getCurrentFrame(delta, true);
        TextureRegion frameJelly4 = jellyAnimation4.getCurrentFrame(delta, true);
        TextureRegion frameJelly5 = jellyAnimation5.getCurrentFrame(delta, true);
        TextureRegion frameJelly6 = jellyAnimation6.getCurrentFrame(delta, true);
        TextureRegion frameEnemy1 = enemyAnimation1.getCurrentFrame(delta, true);
        TextureRegion frameEnemy2 = enemyAnimation2.getCurrentFrame(delta, true);
        TextureRegion frameEnemy3 = enemyAnimation3.getCurrentFrame(delta, true);
        TextureRegion frameEnemy4 = enemyAnimation4.getCurrentFrame(delta, true);
        TextureRegion frameEnemy5 = enemyAnimation5.getCurrentFrame(delta, true);
        

        batch.draw(frameAlga, 100, 100, 90, 100);
        batch.draw(frameBolha, 150, 100, 90, 100);
        batch.draw(frameJelly1, 300, 100, 90, 100);
        batch.draw(frameJelly2, 550, 100, 90, 100);
        batch.draw(frameJelly3, 650, 100, 90, 100);
        batch.draw(frameJelly4, 250, 100, 90, 100);
        batch.draw(frameJelly5, 400, 100, 90, 100);
        batch.draw(frameJelly6, 200, 200, 90, 100);
        batch.draw(frameEnemy1, 350, 200, 90, 100);
        batch.draw(frameEnemy2, 500, 200, 90, 100);
        batch.draw(frameEnemy3, 600, 200, 90, 100);
        batch.draw(frameEnemy4, 450, 200, 90, 100);
        batch.draw(frameEnemy5, 550, 200, 90, 100);
    
    
        batch.end();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        musica.dispose();
        assetManager.dispose();
    }
}


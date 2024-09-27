package com.testeassets.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AssetsAnimation {
    private final Animation<TextureRegion> animation;
    private float stateTime;

    // Construtor genérico para animações, tanto de ataque quanto de idle
    public AssetsAnimation(Texture texture, int frameCols, int frameRows, float frameDuration) {
        // Divide a textura em frames com base no número de colunas e linhas
        TextureRegion[][] tmpFrames = TextureRegion.split(texture, texture.getWidth() / frameCols, texture.getHeight() / frameRows);
        TextureRegion[] animationFrames = new TextureRegion[frameCols * frameRows];

        // Copia os frames para um array unidimensional
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }

       
        animation = new Animation<>(frameDuration, animationFrames);
        stateTime = 0f;
    }

    // Atualiza e retorna o frame atual
    public TextureRegion getCurrentFrame(float deltaTime, boolean looping) {
        stateTime += deltaTime;
        return animation.getKeyFrame(stateTime, looping);
    }

    // Reseta a animação
    public void reset() {
        stateTime = 0f;
    }
}
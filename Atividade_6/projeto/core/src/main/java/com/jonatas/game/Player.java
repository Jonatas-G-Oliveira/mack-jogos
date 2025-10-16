package com.jonatas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

//Classe que representa o jogador
//Ela herda do GameObject e implementa a detecção do teclado
public class Player extends GameObject {
    
    private boolean alive = true;
    private Vector2 posicaoNave;
    private Viewport viewport;

    public Player(Texture texture, float x, float y, float largura, float altura, Viewport viewport){
        super(texture, x, y, largura, altura);
        // sprite.setRotation(90);
        sprite.setSize(largura * 0.7f, altura * 0.7f);
        sprite.setOriginCenter();  // Define o ponto de origem para o centro do sprite
        sprite.setRotation(90);    // Rotaciona 90 graus
        this.viewport = viewport;
        this.posicaoNave = new Vector2();
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill(){
        alive = false;
    }
    @Override
    public void update(float dt){
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float spaceshipWidth = sprite.getWidth();

        //Movimento do teclado
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            sprite.translateX(speed * dt);
        } 
        if(Gdx.input.isKeyPressed((Input.Keys.LEFT))){
            sprite.translateX(-speed * dt);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sprite.translateY(speed * dt);
        }
         if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sprite.translateY(-speed * dt);
         }
        //Movimento do Mouse
        if(Gdx.input.isTouched()){
            posicaoNave.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(posicaoNave);
            sprite.setCenterX(posicaoNave.x);
        }

        //Garantindo que  jogado não saia da tela
        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - spaceshipWidth));
    }
}

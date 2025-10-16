package com.jonatas.game;

import com.badlogic.gdx.graphics.Texture;

public class Meteoro extends GameObject{

    public Meteoro(Texture texture, float x, float y, float largura, float altura){
        super(texture, x, y, largura, altura);
    }

    @Override  
    public void update(float dt){
        sprite.translateY(-2f * dt);
    }

}
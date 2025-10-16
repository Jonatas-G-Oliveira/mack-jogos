package com.jonatas.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// ----- Classe pai -
public abstract class GameObject {

    protected Sprite sprite;
    protected Rectangle colisao;

    //Construtor
    public GameObject(Texture texture, float x, float y, float largura, float altura){
        this.sprite = new Sprite(texture);
        this.sprite.setSize(largura, altura);
        this.sprite.setPosition(x, y);
        
        this.colisao = new Rectangle(x, y, largura, altura);
    }

    public abstract void update(float dt);

    public void draw(SpriteBatch sequencia){
        sprite.draw(sequencia);
    }

    public Rectangle getLimites(){
        //O retangulo tem o exato local da sprite 
        colisao.setPosition(sprite.getX(), sprite.getY());
        return colisao;
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x, y);
    }


}
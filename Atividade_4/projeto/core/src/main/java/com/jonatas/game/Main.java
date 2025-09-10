package com.jonatas.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
   
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Sprite spaceshipSprite;

    Texture spaceshipTexture;
    Texture backgroundTexture;
    Texture meteoroTexture;
    
    Vector2 touchPos;

    float meteoroTimer;
    Array<Sprite> meteorosAleatorios;

    @Override
    public void create() {
        spaceshipTexture = new Texture("spaceship.png");
        backgroundTexture = new Texture("bg.png");
        meteoroTexture = new Texture("meteoro.png");
        
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        spaceshipSprite = new Sprite(spaceshipTexture);
        spaceshipSprite.setSize(1, 1);

        touchPos = new Vector2();
        meteorosAleatorios = new Array<>();

        
        createMeteoro();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        viewport.update(width, height, true);
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        // Draw your application here.
        input();
        logic();
        draw();
    }

    private void input(){
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            spaceshipSprite.translateX(speed * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            spaceshipSprite.translateX(-speed * delta);
        }
         if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            spaceshipSprite.translateY(speed * delta);
        }
         if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            spaceshipSprite.translateY(-speed * delta);
        }
       if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            spaceshipSprite.setCenterX(touchPos.x);
       }
    }
    private void logic(){
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float spaceshipWidth = spaceshipSprite.getWidth();
        float spaceshipHeight = spaceshipSprite.getHeight();
        spaceshipSprite.setX(MathUtils.clamp(spaceshipSprite.getX(), 0, worldWidth - spaceshipWidth));
        spaceshipSprite.setY(MathUtils.clamp(spaceshipSprite.getY(), 0, worldHeight - spaceshipHeight));

        float delta = Gdx.graphics.getDeltaTime();

        for (Sprite meteoro: meteorosAleatorios){
            meteoro.translateY(-2f * delta);
        }

        meteoroTimer += delta;
        if(meteoroTimer > 1f){
            meteoroTimer = 0;
            createMeteoro();
        }
        
    }


    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spaceshipSprite.draw(spriteBatch);

        for (Sprite meteoro : meteorosAleatorios){
            meteoro.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    private void createMeteoro(){
        float meteoroWidth = 1;
        float meteoroHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //Como vamos mover a gota vamos deixar ela como sprite
        //Sprite guarda a posição tamanho e etc
        Sprite meteoroSprite = new Sprite(meteoroTexture);
        meteoroSprite.setSize(meteoroWidth, meteoroHeight);
        meteoroSprite.setX(MathUtils.random(0f,worldWidth - meteoroWidth));
        meteoroSprite.setY(worldHeight);
        meteorosAleatorios.add(meteoroSprite);
    }
    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}
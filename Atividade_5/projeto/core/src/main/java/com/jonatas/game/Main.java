package com.jonatas.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Music music;
    Sound meteoroSound;

    SpriteBatch spriteBatch;
    FitViewport viewport;
    
    boolean NAVE_VIVA;
    Sprite spaceshipSprite;
    Texture spaceshipTexture;

    Texture backgroundTexture;
    Texture meteoroTexture;
    
    Vector2 touchPos;

    float meteoroTimer;
    Array<Sprite> meteorosAleatorios;

    Rectangle spaceshipRectangle;
    Rectangle meteoroRectangle;

    

    @Override
    public void create() {
        meteoroSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic((Gdx.files.internal("music.mp3")));

        spaceshipTexture = new Texture("spaceship.png");
        backgroundTexture = new Texture("bg.png");
        meteoroTexture = new Texture("meteoro.png");
        
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        NAVE_VIVA = true;
        spaceshipSprite = new Sprite(spaceshipTexture);
        spaceshipSprite.setSize(1, 1);

        touchPos = new Vector2();
        meteorosAleatorios = new Array<>();

        spaceshipRectangle =  new Rectangle();
        meteoroRectangle = new Rectangle();    
        
        createMeteoro();
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        if(width <= 0 || height <= 0) return;
    }

    @Override
    public void render() {
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

        spaceshipRectangle.set(spaceshipSprite.getX(), spaceshipSprite.getY(), spaceshipWidth, spaceshipWidth);

        for (int i = meteorosAleatorios.size -1; i >=0 ;i--){
            Sprite meteoroSprite = meteorosAleatorios.get(i);
            float meteoroWidth = meteoroSprite.getWidth();
            float meteoroHeight = meteoroSprite.getHeight();

            meteoroSprite.translateY(-2f * delta);

            meteoroRectangle.set(meteoroSprite.getX(), meteoroSprite.getY(), meteoroWidth, meteoroHeight);
            
            if(meteoroSprite.getY() < -meteoroHeight) meteorosAleatorios.removeIndex(i);
            else if(meteoroRectangle.overlaps(spaceshipRectangle)){
                this.NAVE_VIVA = false;
                meteoroSound.play();
            }

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
        
        if(NAVE_VIVA) spaceshipSprite.draw(spriteBatch);

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
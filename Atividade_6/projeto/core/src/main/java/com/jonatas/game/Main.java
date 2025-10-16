package com.jonatas.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main implements ApplicationListener {
    Texture spaceshipTexture;
    Texture backgroundTexture;
    Texture meteoroTexture;
    Texture specialTexture;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Player player;
    Array<GameObject> metoroObjects;

    Sound meteoroSound;
    Music music;
    
    float meteoroTimer;
    
    //Pontuacao
    private int score;
    private float gameTime;
    
    //Elementos de UI
    private Stage uiStage;
    private Label scoreLabel;
    private Label timeLabel;
    private BitmapFont font;

    
    

    @Override
    public void create() {
        //Texturas
        spaceshipTexture = new Texture("spaceship.png");
        backgroundTexture = new Texture("bg.png");
        meteoroTexture = new Texture("meteoro.png");
        specialTexture = new Texture("diamante.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        //Novo ----
        player = new Player(spaceshipTexture, 3.5f, 0.5f, 1,1,viewport);
        metoroObjects =  new Array<>();
        
        // Musica
        meteoroSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic((Gdx.files.internal("music.mp3")));

        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
        

        //Inicializacao da UI
        score = 0;
        gameTime = 0;
        uiStage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        scoreLabel = new Label("Score: " + score, style);
        scoreLabel.setPosition(10,10); // Posição na tela, não no mundo do jogo

        timeLabel = new Label("Time: 0", style);
        timeLabel.setPosition(10, 40);
        
        uiStage.addActor(scoreLabel);
        uiStage.addActor(timeLabel);
       
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        //Atualiza o tempo do jogo

        gameTime += dt;
        if(player.isAlive()){
            updateGameObjects(dt);
            handleCollisions();
            spawnDroplets(dt);

            drawGameObjects();
            drawUI();
        }
    }

    
     private void updateGameObjects(float dt) {
        player.update(dt);

        for(int i = metoroObjects.size - 1; i >=0; i--){
            GameObject meteoro = metoroObjects.get(i);
            meteoro.update(dt);
            if(meteoro.getLimites().y < -meteoro.getLimites().height){
                metoroObjects.removeIndex(i);
            }
        }
     }

     private void handleCollisions(){
        for(int i = metoroObjects.size - 1; i >=0; i--){
            GameObject meteoro = metoroObjects.get(i);
            if(player.getLimites().overlaps(meteoro.getLimites()))  {
                metoroObjects.removeIndex(i);
                meteoroSound.play();

              

                //Lógica de pontuação com polimorfismo
                if (meteoro instanceof Special) {
                    score += 5; // Diamente vale 1 ponto!
                }else{
                      player.kill();
                }
                scoreLabel.setText("Score " + score);
            }
        }
     }


    private void spawnDroplets(float dt){
        meteoroTimer += dt;
        if (meteoroTimer > 1f) {
            meteoroTimer = 0;
            float worldWidth = viewport.getWorldWidth();
            float dropWidth = 1;
        
        //Implementar as gotas especiasi
        GameObject newMeteoro;
        float randomX = MathUtils.random(0f, worldWidth - dropWidth);

         if (MathUtils.random(0, 9) == 0) { // 10% de chance de ser uma gota especial
                newMeteoro = new Special(specialTexture, randomX , viewport.getWorldHeight(), dropWidth, 1);
            } else {
                newMeteoro = new Meteoro(meteoroTexture, randomX, viewport.getWorldHeight(), dropWidth, 1);
            

            }
            System.out.println("X aleatório: " + randomX);


            metoroObjects.add(newMeteoro);
        }
        
    }

    private void drawGameObjects(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
            spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            if(player.isAlive()){
                player.draw(spriteBatch);
            }

            for( GameObject meteoro : metoroObjects){
                meteoro.draw(spriteBatch);
            }
        spriteBatch.end();
    }

    // Método para desenhar a UI
    private void drawUI() {
        scoreLabel.setText("Score" + score);
        timeLabel.setText("Time: " + (int)gameTime);
        uiStage.act();
        uiStage.draw();
        
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
        spriteBatch.dispose();
        spaceshipTexture.dispose();
        backgroundTexture.dispose();
        meteoroTexture.dispose();
        // specialDropTexture.dispose(); // NOVIDADE
        meteoroSound.dispose();
        music.dispose();
        font.dispose(); // NOVIDADE
        uiStage.dispose(); // NOVIDADE
    }
}
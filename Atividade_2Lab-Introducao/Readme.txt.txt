Rodar o jogo
./gradlew run

Criar um jogo

   treeckoTexture = new Texture("treecko.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

Caso a janela seja redimensionada
 viewport.update(width, height, true);

Desenhando a sprite
private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(treeckoTexture, 0, 0, 1, 1);
        spriteBatch.end();
    }

O SpriteBatch é como a libGDX combina essas chamadas de desenho.
. A lógica do seu jogo não deve saber nada sobre pixels.
Um Sprite é capaz de fazer tudo isso e manter o estado ,é pra ajudar a lib pra gente redimensionar a imagem

Resumo - 
	Texture é estatica
	Sprite é dinâmica
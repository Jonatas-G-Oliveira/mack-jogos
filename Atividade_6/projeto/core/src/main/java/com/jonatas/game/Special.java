package com.jonatas.game;

import com.badlogic.gdx.graphics.Texture;

public class Special extends Meteoro {
    
    public Special(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void update(float dt) {
        // Gota especial cai mais rápido (o dobro da velocidade)
        sprite.translateY(-4f * dt);
    }
}

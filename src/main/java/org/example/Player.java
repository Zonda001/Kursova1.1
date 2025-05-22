package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import untitled4.util.CustomImageLoader;

public class Player implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .build();
    }

    public void LoadAnimatedTexture(){
        AnimationChannel animIdle = CustomImageLoader.loadAnimationChannel(CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Idle.png"), 4, 160, 260, 200);
        AnimatedTexture mainTexture = new AnimatedTexture(animIdle);
        mainTexture.loopAnimationChannel(animIdle);
        
    }



}

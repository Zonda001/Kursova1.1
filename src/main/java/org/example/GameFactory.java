package org.example;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.scene.image.ImageView;
import untitled4.util.CustomImageLoader;

public class GameFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new ImageView(FXGL.image("player/Idle.png")))
                .build();
    }



}

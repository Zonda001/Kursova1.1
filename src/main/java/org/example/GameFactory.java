package org.example;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.image.ImageView;

public class GameFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)

                .view(new ImageView(FXGL.image("player/Idle.png")))
                .build();
    }
}

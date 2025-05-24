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

<<<<<<< HEAD
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
=======
    // Константи для назв анімацій
    public static final String ANIM_IDLE = "IDLE";
    public static final String ANIM_WALK = "WALK";
    public static final String ANIM_RUN = "RUN";
    public static final String ANIM_ATTACK = "ATTACK";

    // Зберігаємо анімаційні канали як статичні поля для доступу ззовні
    private static AnimationChannel animIdle;
    private static AnimationChannel animWalk;
    private static AnimationChannel animRun;
    private static AnimationChannel animAttack;

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        // Створюємо анімовану текстуру
        AnimatedTexture playerTexture = createPlayerAnimatedTexture();

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .view(playerTexture)
                .build();
    }

    private AnimatedTexture createPlayerAnimatedTexture() {
        // Завантажуємо всі анімаційні канали
        animIdle = CustomImageLoader.loadAnimationChannel(
                CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Idle.png"),
                6, 128, 128, 200
        );

        animWalk = CustomImageLoader.loadAnimationChannel(
                CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Walk.png"),
                9, 128, 128, 150
        );

        animRun = CustomImageLoader.loadAnimationChannel(
                CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Run.png"),
                8, 128, 128, 500
        );

        animAttack = CustomImageLoader.loadAnimationChannel(
                CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Attack_1.png"),
                4, 128, 128, 120
        );

        // Створюємо анімовану текстуру тільки з Idle каналом (за замовчуванням)
        AnimatedTexture mainTexture = new AnimatedTexture(animIdle);
        mainTexture.loopAnimationChannel(animIdle);

        return mainTexture;
    }

    // Статичний метод для зміни анімації гравця
    public static void setPlayerAnimation(Entity player, String animationName) {
        AnimatedTexture texture = (AnimatedTexture) player.getViewComponent().getChildren().get(0);
        if (texture != null) {
            switch (animationName) {
                case ANIM_IDLE:
                    texture.loopAnimationChannel(animIdle);
                    break;
                case ANIM_WALK:
                    texture.loopAnimationChannel(animWalk);
                    break;
                case ANIM_RUN:
                    texture.loopAnimationChannel(animRun);
                    break;
                case ANIM_ATTACK:
                    texture.playAnimationChannel(animAttack);
                    break;
            }
        }
    }

    // Цей метод тепер не потрібен, але залишаю для довідки
    public void LoadAnimatedTexture(){
        AnimationChannel animIdle = CustomImageLoader.loadAnimationChannel(CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Idle.png"), 4, 128, 128, 200);
        AnimatedTexture mainTexture = new AnimatedTexture(animIdle);
        mainTexture.loopAnimationChannel(animIdle);
    }
}
>>>>>>> origin/master

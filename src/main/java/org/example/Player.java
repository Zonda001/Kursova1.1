package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import untitled4.util.CustomImageLoader;

public class Player implements EntityFactory {

    // Константи для назв анімацій
    public static final String ANIM_IDLE = "IDLE";
    public static final String ANIM_WALK = "WALK";
    public static final String ANIM_RUN = "RUN";
    public static final String ANIM_ATTACK = "ATTACK";

    // Статичні анімаційні канали (ініціалізуються один раз)
    private static AnimationChannel animIdle;
    private static AnimationChannel animWalk;
    private static AnimationChannel animRun;
    private static AnimationChannel animAttack;
    private static boolean animationsLoaded = false;

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        // Ініціалізуємо анімації тільки один раз
        if (!animationsLoaded) {
            loadAnimations();
            animationsLoaded = true;
        }

        // Створюємо анімовану текстуру з idle анімацією
        AnimatedTexture playerTexture = new AnimatedTexture(animIdle);
        playerTexture.loopAnimationChannel(animIdle);

        // Створюємо компонент для зберігання посилання на AnimatedTexture
        PlayerAnimationComponent animComponent = new PlayerAnimationComponent(playerTexture);

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .view(playerTexture)
                .with(animComponent)
                .build();
    }

    private static void loadAnimations() {
        System.out.println("🔄 Завантажуємо анімації гравця...");

        // Завантажуємо спрайт-шіти
        Image idleSheet = CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Idle.png");
        Image walkSheet = CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Walk.png");
        Image runSheet = CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Run.png");
        Image attackSheet = CustomImageLoader.loadSpriteSheet("src/main/resources/assets/textures/player/Attack_1.png");

        if (idleSheet == null || walkSheet == null || runSheet == null || attackSheet == null) {
            System.err.println("❌ Помилка завантаження спрайт-шітів!");
            return;
        }

        // Створюємо анімаційні канали (тривалість в СЕКУНДАХ!)
        animIdle = CustomImageLoader.loadAnimationChannel(idleSheet, 6, 128, 128, 2.0);    // 2 секунди
        animWalk = CustomImageLoader.loadAnimationChannel(walkSheet, 9, 128, 128, 1.2);    // 1.2 секунди
        animRun = CustomImageLoader.loadAnimationChannel(runSheet, 8, 128, 128, 0.8);      // 0.8 секунди
        animAttack = CustomImageLoader.loadAnimationChannel(attackSheet, 4, 128, 128, 0.6); // 0.6 секунди

        System.out.println("✅ Анімації завантажено успішно!");
    }

    // Статичний метод для зміни анімації гравця
    public static void setPlayerAnimation(Entity player, String animationName) {
        if (!animationsLoaded) {
            System.err.println("❌ Анімації ще не завантажені!");
            return;
        }

        // Отримуємо компонент анімації
        PlayerAnimationComponent animComponent = player.getComponent(PlayerAnimationComponent.class);
        if (animComponent == null) {
            System.err.println("❌ Компонент анімації не знайдено!");
            return;
        }

        AnimatedTexture texture = animComponent.getAnimatedTexture();
        if (texture == null) {
            System.err.println("❌ AnimatedTexture не знайдено!");
            return;
        }

        // Змінюємо анімацію
        switch (animationName) {
            case ANIM_IDLE:
                texture.loopAnimationChannel(animIdle);
                System.out.println("🧍 Анімація: Idle");
                break;
            case ANIM_WALK:
                texture.loopAnimationChannel(animWalk);
                System.out.println("🚶 Анімація: Walk");
                break;
            case ANIM_RUN:
                texture.loopAnimationChannel(animRun);
                System.out.println("🏃 Анімація: Run");
                break;
            case ANIM_ATTACK:
                texture.playAnimationChannel(animAttack);
                System.out.println("⚔️ Анімація: Attack");
                break;
            default:
                System.err.println("❌ Невідома анімація: " + animationName);
        }
    }

    // Компонент для зберігання посилання на AnimatedTexture
    public static class PlayerAnimationComponent extends Component {
        private final AnimatedTexture animatedTexture;

        public PlayerAnimationComponent(AnimatedTexture animatedTexture) {
            this.animatedTexture = animatedTexture;
        }

        public AnimatedTexture getAnimatedTexture() {
            return animatedTexture;
        }
    }
}
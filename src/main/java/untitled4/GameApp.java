package untitled4;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import org.example.Player;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameApp extends GameApplication {

    private Entity player;
    private boolean isMoving = false;
    private boolean isRunning = false;

    // Флаги для відстеження натиснених клавіш
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Owl's Blood");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        // Білий фон кімнати
        FXGL.getGameScene().setBackgroundColor(Color.WHITE);

        // Створення кімнати (наприклад, 800x600 білий прямокутник)
        FXGL.entityBuilder()
                .view(new Rectangle(800, 600, Color.WHITE))
                .buildAndAttach();

        FXGL.getGameWorld().addEntityFactory(new Player());
        player = FXGL.spawn("player", 300, 300);
    }

    @Override
    protected void initGameVars(java.util.Map<String, Object> vars) {
        // Можна сюди додати змінні гри, якщо потрібно
    }

    @Override
    protected void initInput() {
        // Рух вверх (W)
        FXGL.getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                wPressed = true;
                movePlayer(0, -5);
            }

            @Override
            protected void onActionEnd() {
                wPressed = false;
                stopMoving();
            }
        }, KeyCode.W);

        // Рух вниз (S)
        FXGL.getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                sPressed = true;
                movePlayer(0, 5);
            }

            @Override
            protected void onActionEnd() {
                sPressed = false;
                stopMoving();
            }
        }, KeyCode.S);

        // Рух вліво (A)
        FXGL.getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                aPressed = true;
                movePlayer(-5, 0);
            }

            @Override
            protected void onActionEnd() {
                aPressed = false;
                stopMoving();
            }
        }, KeyCode.A);

        // Рух вправо (D)
        FXGL.getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                dPressed = true;
                movePlayer(5, 0);
            }

            @Override
            protected void onActionEnd() {
                dPressed = false;
                stopMoving();
            }
        }, KeyCode.D);

        // Біг (Left Shift замінено на E)
        FXGL.getInput().addAction(new UserAction("Run") {
            @Override
            protected void onAction() {
                isRunning = true;
                updateAnimation();
            }

            @Override
            protected void onActionEnd() {
                isRunning = false;
                updateAnimation();
            }
        }, KeyCode.E);

        // Атака (Space)
        FXGL.getInput().addAction(new UserAction("Attack") {
            @Override
            protected void onActionBegin() {
                attack();
            }
        }, KeyCode.SPACE);
    }

    private void movePlayer(double dx, double dy) {
        if (player != null) {
            // Збільшуємо швидкість якщо біжимо
            double speed = isRunning ? 2.0 : 1.0;

            double newX = player.getX() + dx * speed;
            double newY = player.getY() + dy * speed;

            // Обмежуємо рух в межах екрану
            newX = Math.max(0, Math.min(800 - 128, newX)); // 128 - розмір спрайту
            newY = Math.max(0, Math.min(600 - 128, newY));

            player.setPosition(newX, newY);

            if (!isMoving) {
                isMoving = true;
                updateAnimation();
            }
        }
    }

    private void stopMoving() {
        // Перевіряємо чи не натиснуті інші клавіші руху
        if (!wPressed && !sPressed && !aPressed && !dPressed) {
            isMoving = false;
            updateAnimation();
        }
    }

    private void updateAnimation() {
        if (player != null) {
            if (isMoving) {
                if (isRunning) {
                    Player.setPlayerAnimation(player, Player.ANIM_RUN);
                } else {
                    Player.setPlayerAnimation(player, Player.ANIM_WALK);
                }
            } else {
                Player.setPlayerAnimation(player, Player.ANIM_IDLE);
            }
        }
    }

    private void attack() {
        if (player != null) {
            Player.setPlayerAnimation(player, Player.ANIM_ATTACK);

            // Через деякий час повертаємося до попередньої анімації
            FXGL.getGameTimer().runOnceAfter(() -> {
                updateAnimation();
            }, javafx.util.Duration.millis(500)); // 500ms для анімації атаки
        }
    }

    @Override
    protected void initUI() {
        // UI можна буде додати пізніше
    }

    @Override
    protected void initPhysics() {
        // Поки що фізики не потрібно
    }

    public static void main(String[] args) {
        launch(args);
    }
}
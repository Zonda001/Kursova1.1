package untitled4;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import org.example.Player;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameApp extends GameApplication {

    private Entity player;

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

        FXGL.spawn("player", 300, 300);
    }

    @Override
    protected void initGameVars(java.util.Map<String, Object> vars) {
        // Можна сюди додати змінні гри, якщо потрібно
    }

    @Override
    protected void initInput() {
        // Пізніше додамо input, зараз тільки структура
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

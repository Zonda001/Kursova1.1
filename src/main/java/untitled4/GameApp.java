package untitled4;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Owl's Blood");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        FXGL.getGameScene().addUINode(new Text("Welcome to Owl's Blood") {{
            setFill(Color.WHITE);
            setTranslateX(100);
            setTranslateY(100);
        }});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
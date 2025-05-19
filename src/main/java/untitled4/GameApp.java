package untitled4;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import component.PlayerComponent;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameApp extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Owl's Blood");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.WHITE);

        player = entityBuilder()
                .at(300, 300)
                .viewWithBBox(texture("player.png"))
                .with(new PlayerComponent())
                .buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayer().up();
            }
        }, KeyCode.W);

        FXGL.getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayer().down();
            }
        }, KeyCode.S);

        FXGL.getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayer().left();
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayer().right();
            }
        }, KeyCode.D);
    }

    private PlayerComponent getPlayer() {
        return FXGL.getGameWorld()
                .getEntitiesByComponent(PlayerComponent.class)
                .get(0)
                .getComponent(PlayerComponent.class);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
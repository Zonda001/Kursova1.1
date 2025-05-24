package component;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class PlayerComponent extends Component {

    private Point2D velocity = Point2D.ZERO;
    private static final double SPEED = 150;

    public void up() {
        velocity = new Point2D(0, -1);
    }

    public void down() {
        velocity = new Point2D(0, 1);
    }

    public void left() {
        velocity = new Point2D(-1, 0);
    }

    public void right() {
        velocity = new Point2D(1, 0);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translate(velocity.multiply(SPEED * tpf));
        velocity = Point2D.ZERO;
    }
}

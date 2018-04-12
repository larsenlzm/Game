import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;

public class Bullet extends GameObjects {

    Bullet(double centerX, double centerY, double radius, Color c, double x, double y, Pane root, Player player) {
        super(new Circle(centerX,centerY,radius,c));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
        setVelocity(new Point2D(Math.cos(Math.toRadians(player.getRotate()))*5,Math.sin(Math.toRadians(player.getRotate()))*5));
    }
    public void RemoveBullet(Pane root) {
        root.getChildren().remove(getView());
    }
}

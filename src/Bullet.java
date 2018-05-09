import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;

public class Bullet extends GameObjects {

    Bullet(String sprite, double x, double y, Pane root, Player player, double rotate) {
        super(new ImageView(sprite));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
        setVelocity(Math.cos(Math.toRadians(player.getRotate()))*10,Math.sin(Math.toRadians(player.getRotate()))*10);
        getView().setRotate(rotate);
    }
    public void RemoveBullet(Pane root) {
        root.getChildren().remove(getView());
    }
}

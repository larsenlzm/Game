import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;

public class Bullet extends GameObjects {
    Bullet() {
        super(new Circle(5,5,5, Color.BROWN));
    }
    public void addBullet(double x, double y, Pane root) {
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
    }
    public void RemoveBullet(Pane root) {
        root.getChildren().remove(getView());
    }
}

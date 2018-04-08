import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends GameObjects {
    Bullet() {
        super(new Circle(5,5,5, Color.BROWN));
    }
    public void addBullet(double x, double y) {
        getView().setTranslateX(x);
        getView().setTranslateY(y);
    }
}

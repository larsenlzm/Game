import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends GameObjects {
    public Wall(double w, double h, Color c, double x, double y) {
        super(new Rectangle(w,h,c));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
    }
}

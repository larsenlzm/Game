import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends GameObjects {
    public Wall(double w, double h, Color c) {
        super(new Rectangle(w,h,c));
    }
    public void addWall(double x, double y) {
        getView().setTranslateX(x);
        getView().setTranslateY(y);
    }
}

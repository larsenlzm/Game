import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class Wall extends GameObjects {
    public Wall(double w, double h, Color c, double x, double y, Pane root) {
        super(new Rectangle(w,h,c));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
    }
}

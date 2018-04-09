import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class Wall extends GameObjects {
    public Wall(double width, double heigth, Color color, double x, double y, Pane pane) {
        super(new Rectangle(width,heigth,color));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        pane.getChildren().add(getView());
    }
}

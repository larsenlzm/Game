import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class Wall extends GameObjects {

    private Pane pane;

    public Wall(double width, double heigth, Color color, double x, double y, Pane pane) {
        super(new Rectangle(width,heigth,color));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        this.pane = pane;
    }
    public void addPane(){
        pane.getChildren().add(getView());
    }
    public void removePane(){
        pane.getChildren().remove(getView());
    }
}

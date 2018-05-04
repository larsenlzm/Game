import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import java.awt.*;

public class Wall extends GameObjects {

    private Pane pane;
    private Rectangle squareboi;
    private Image wallsprite;

    Wall(String sprite, double x, double y, Pane pane) {
        super(new ImageView(sprite));
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

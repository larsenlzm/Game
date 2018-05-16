import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import java.awt.*;

/**
 * Class for the wall objects.
 *
 * @author s325919, s325894
 */

public class Wall extends GameObjects {

    private Pane pane;

    /**
     *
     * Constructs the walls to the pane
     *
     * @param sprite image for the walls.
     * @param x x-coordinates for the walls.
     * @param y y-coordinates for the walls.
     * @param pane the pane it's placed on.
     */

    Wall(String sprite, double x, double y, Pane pane) {
        super(new ImageView(String.valueOf(Bullet.class.getResource(sprite))));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        this.pane = pane;
    }

    /**
     * adds the objects to the pane.
     */
    void addPane(){
        pane.getChildren().add(getView());
    }

    /**
     * Removes the objects from the pane.
     */
    void removePane(){
        pane.getChildren().remove(getView());
    }
}

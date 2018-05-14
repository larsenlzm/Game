import javafx.geometry.Point2D;
import javafx.scene.Node;

public class GameObjects {

    private Node view;
    private Point2D velocity;

    GameObjects(Node view) {
        this.view = view;
    }
    void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }
    void setVelocity(double x, double y) {
        velocity = new Point2D(x,y);
    }
    Node getView() {
        return view;
    }
    double getRotate() {
        return view.getRotate();
    }
    void rotateRight() {
        view.setRotate(view.getRotate() + 3);
    }
    void rotateLeft() {
        view.setRotate(view.getRotate() - 3);
    }
    boolean isColliding(GameObjects other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }
    double getWidth() {
        return getView().getLayoutBounds().getWidth();
    }
    double getHeigth() {
         return getView().getLayoutBounds().getHeight();
    }
    double getMaxX() {
        return getView().getBoundsInParent().getMaxX();
    }
    double getMinX() {
        return getView().getBoundsInParent().getMinX();
    }
    double getMaxY() {
        return getView().getBoundsInParent().getMaxY();
    }
    double getMinY() {
        return getView().getBoundsInParent().getMinY();
    }
}

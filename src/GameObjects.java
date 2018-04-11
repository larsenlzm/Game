import javafx.geometry.Point2D;
import javafx.scene.Node;

public class GameObjects {

    private Node view;
    private Point2D velocity;

    private boolean alive = true;

    public GameObjects(Node view) {
        this.view = view;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + 1);
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 1);
    }

    public boolean isColliding(GameObjects other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }
    public double getWidth() {
        return getView().getLayoutBounds().getWidth();
    }
    public double getHeigth() {
         return getView().getLayoutBounds().getHeight();
    }
    public double getMaxX() {
        return getView().getBoundsInParent().getMaxX();
    }
    public double getMinX() {
        return getView().getBoundsInParent().getMinX();
    }
    public double getMaxY() {
        return getView().getBoundsInParent().getMaxY();
    }
    public double getMinY() {
        return getView().getBoundsInParent().getMinY();
    }
}

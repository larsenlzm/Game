import javafx.scene.Node;

/**
 * Class for the interactive parts of the game.
 *
 * @author s325919, s325894
 */

public class GameObjects {

    private Node view;
    private double speedX;
    private double speedY;

    /**
     * Initializes the view
     *
     * @param view the view.
     */

    public GameObjects(Node view) {
        this.view = view;
    }

    /**
     * Updates the game.
     */
    public void update() {
        view.setTranslateX(view.getTranslateX() + speedX);
        view.setTranslateY(view.getTranslateY() + speedY);
    }

    /**
     * Sets the velocity in x and y direction.
     *
     * @param x how many x-coordinates per frame.
     * @param y how many y-coordinates per frame.
     */
    public void setVelocity(double x, double y) {
        speedX = x;
        speedY = y;
    }

    /**
     * Gets the view.
     *
     * @return returns the view
     */
    public Node getView() {
        return view;
    }

    /**
     * gets the rotation of the object.
     *
     * @return returns the rotation of the object.
     */
    public double getRotate() {
        return view.getRotate();
    }

    /**
     * Rotates the object to the right.
     */
    public void rotateRight() {
        view.setRotate(view.getRotate() + 3);
    }

    /**
     * Rotates the object left.
     */
    public void rotateLeft() {
        view.setRotate(view.getRotate() - 3);
    }

    /**
     * Checks if a objects is colliding with another object.
     *
     * @param other is another object to check.
     * @return returns the view of objects colliding.
     */
    public boolean isColliding(GameObjects other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    /**
     * Gets the width of an object.
     *
     * @return returns the width.
     */
    public double getWidth() {
        return getView().getLayoutBounds().getWidth();
    }

    /**
     * Gets the height of an object.
     *
     * @return returns the height.
     */
    public double getHeigth() {
         return getView().getLayoutBounds().getHeight();
    }

    /**
     * Gets the maximum x-value of
     * an object.
     *
     * @return returns the maximum x-value
     */
    public double getMaxX() {
        return getView().getBoundsInParent().getMaxX();
    }

    /**
     * Gets the minimum x-value
     * of an object.
     *
     * @return returns the minimum x-value.
     */
    public double getMinX() {
        return getView().getBoundsInParent().getMinX();
    }

    /**
     * Gets the maximum y-value of
     * an object.
     *
     * @return returns the maximum y-value.
     */
    public double getMaxY() {
        return getView().getBoundsInParent().getMaxY();
    }

    /**
     * Gets the minimum y-value of
     * an object.
     *
     * @return returns the minimum y-value
     */
    public double getMinY() {
        return getView().getBoundsInParent().getMinY();
    }
}

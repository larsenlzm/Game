import javafx.scene.Node;

/**
 * Class for the interactive parts of the game.
 *
 * @author s325919, s325894
 */

class GameObjects {

    private Node view;
    private double speedX;
    private double speedY;

    GameObjects(Node view) {
        this.view = view;
    }
    void update() {
        view.setTranslateX(view.getTranslateX() + speedX);
        view.setTranslateY(view.getTranslateY() + speedY);
    }
    void setVelocity(double x, double y) {
        speedX = x;
        speedY = y;
    }
    void setSpeedX(double speedX){
        this.speedX = speedX;
    }
    void setSpeedY(double speedY){
        this.speedY = speedY;
    }
    double getSpeedX(){
        return speedX;
    }
    double getSpeedY(){
        return speedY;
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

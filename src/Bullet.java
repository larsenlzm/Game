import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Class for the bullets.
 *
 * @author s325919, s325894
 */

class Bullet extends GameObjects {

    /**
     *
     * @param sprite image for the bullet.
     * @param x x-coordinates for the bullets.
     * @param y y-coordinates for the bullets.
     * @param root Which pane the bullets are going to.
     * @param player Which player the bullet is shot out from.
     * @param rotate Which way the player is facing.
     */

    Bullet(String sprite, double x, double y, Pane root, Player player, double rotate){
        super(new ImageView(String.valueOf(Bullet.class.getResource(sprite))));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
        setVelocity(Math.cos(Math.toRadians(player.getRotate()))*10,Math.sin(Math.toRadians(player.getRotate()))*10);
        getView().setRotate(rotate);
    }

    /**
     *
     * Method that removes the bullet
     * from the pane.
     *
     * @param root Which pane that needs to be edited.
     */
    void RemoveBullet(Pane root) {
        root.getChildren().remove(getView());
    }
}

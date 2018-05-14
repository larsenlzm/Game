import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

class Bullet extends GameObjects {

    Bullet(String sprite, double x, double y, Pane root, Player player, double rotate){
        super(new ImageView(String.valueOf(Bullet.class.getResource(sprite))));
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
        setVelocity(Math.cos(Math.toRadians(player.getRotate()))*10,Math.sin(Math.toRadians(player.getRotate()))*10);
        getView().setRotate(rotate);
    }
    void RemoveBullet(Pane root) {
        root.getChildren().remove(getView());
    }
}

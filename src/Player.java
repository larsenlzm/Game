import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends GameObjects {

    private int hp;

    Player(String sprite, int hp, double x, double y, Pane root) {
        super(new ImageView(sprite));
        this.hp = hp;
        getView().setTranslateX(x);
        getView().setTranslateY(y);
        root.getChildren().add(getView());
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp){
        this.hp = hp;
    }
    public double getX() {
        return getView().getTranslateX();
    }
    public double getY() {
        return getView().getTranslateY();
    }
}
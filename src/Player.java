import javafx.scene.image.ImageView;

public class Player extends GameObjects {

    private int hp;

    Player(String sprite, int hp, double x, double y) {
        super(new ImageView(sprite));
        this.hp = hp;
        getView().setTranslateX(x);
        getView().setTranslateY(y);
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
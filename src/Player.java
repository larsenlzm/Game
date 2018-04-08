import javafx.scene.image.ImageView;

public class Player extends GameObjects {

    private int hp;

    Player(String sprite, int hp) {
        super(new ImageView(sprite));
        this.hp = hp;
    }
    public void addPlayer(double x, double y) {
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
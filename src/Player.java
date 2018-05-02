import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends GameObjects {

    private int hp;
    private int lifePoints;
    private double speedMultiplier;

    Player(String sprite, int hp,int lifePoints, double x, double y, Pane root) {
        super(new ImageView(sprite));
        this.hp = hp;
        this.lifePoints = lifePoints;
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
    public int getLifePoints() {
        return lifePoints;
    }
    public void setLifePoints(int lifePoints){
        this.lifePoints = lifePoints;
    }
    public double getX() {
        return getView().getTranslateX();
    }
    public double getY() {
        return getView().getTranslateY();
    }
    public void setSpeedMultiplier(double speed) {
        this.speedMultiplier = speed;}
    public double getSpeedMultiplier() {
        return speedMultiplier;
    }
}
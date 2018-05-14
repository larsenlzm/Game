import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class has all the information about the player and enemy.
 *
 * @author s25919, s325894
 */

public class Player extends GameObjects {

    private int hp;
    private int lifePoints;
    private double speedMultiplier;
    private int score;
    private boolean exploded;

    /**
     *
     * @param sprite String that contains the sprite/image.
     * @param hp int variable that contains the number of hp a player has.
     * @param x double variable that cointains the x-position
     * @param y
     * @param root
     */
    Player(String sprite, int hp, Pane root) {
        super(new ImageView(String.valueOf(Bullet.class.getResource(sprite))));
        this.hp = hp;
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
    public int getScore() {
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public boolean getExploded(){
        return exploded;
    }
    public void setExploded(boolean b){
        exploded = b;
    }
}
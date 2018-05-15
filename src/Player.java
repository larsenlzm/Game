import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class has all the information about the players.
 *
 * @author s25919, s325894
 */

class Player extends GameObjects {

    private int hp;
    private double speedMultiplier;
    private int score;
    private boolean exploded;

    /**
     * Constructor that gets used to make player and enemy objects
     *
     * @param sprite String that contains the sprite/image.
     * @param hp int variable that contains the number of hp a player has.
     * @param root Pane that the player gets placed on.
     */
    Player(String sprite, int hp, Pane root) {
        super(new ImageView(String.valueOf(Bullet.class.getResource(sprite))));
        this.hp = hp;
        root.getChildren().add(getView());
    }

    /**
     * Gets the hp.
     *
     * @return return hp
     */
    int getHp() {
        return hp;
    }

    /**
     * Sets the HP
     *
     * @param hp Lifepoints for player.
     */
    void setHp(int hp){
        this.hp = hp;
    }

    /**
     * Gets the X-coordinate.
     *
     * @return returns the x-coordinate
     */
    double getX() {
        return getView().getTranslateX();
    }

    /**
     * Gets the y-coordinate.
     *
     * @return returns the y-coordinate
     */
    double getY() {
        return getView().getTranslateY();
    }

    /**
     * Sets the speed.
     *
     * @param speed How fast a object moves
     */
    void setSpeedMultiplier(double speed) {
        this.speedMultiplier = speed;
    }

    /**
     * Gets the speed.
     *
     * @return returns the speed.
     */
    double getSpeedMultiplier() {
        return speedMultiplier;
    }

    /**
     * Gets the score.
     *
     * @return returns the score.
     */
    int getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score what the score is.
     */
    void setScore(int score){
        this.score = score;
    }

    /**
     * Returns if the object has exploded
     * or not.
     *
     * @return returns the exploded boolean.
     */
    boolean getExploded(){
        return exploded;
    }

    /**
     * Sets if exploded is true or false.
     *
     * @param b sets exploded as b.
     */
    void setExploded(boolean b){
        exploded = b;
    }
}
/**
 * Class that saves the score and map.
 *
 * @author s325919, s325894
 */

public class Save implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int scoreP;
    private int scoreE;
    private int currentMap;

    /**
     * Constructor that gets used to save important data.
     *
     * @param scoreP player's score.
     * @param scoreE enemy's score.
     * @param currentMap Which map is getting saved.
     */

    public Save(int scoreP, int scoreE, int currentMap){
        this.scoreP = scoreP;
        this.scoreE = scoreE;
        this.currentMap = currentMap;
    }

    /**
     * Gets the player score.
     *
     * @return returns the player score.
     */
     public int getScoreP(){
        return scoreP;
    }

    /**
     * Gets the enemy score.
     *
     * @return returns the enemy score.
     */
    public int getScoreE(){
        return scoreE;
    }

    /**
     * Gets which map it is.
     *
     * @return returns the current map.
     *
     */
    public int getCurrentMap(){
        return currentMap;
    }
}

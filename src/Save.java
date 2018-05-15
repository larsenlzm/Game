/**
 * Class that saves the score and map.
 *
 * @author s325919, s325894
 */

class Save implements java.io.Serializable {

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

    Save(int scoreP, int scoreE, int currentMap){
        this.scoreP = scoreP;
        this.scoreE = scoreE;
        this.currentMap = currentMap;
    }

    int getScoreP(){
        return scoreP;
    }
    int getScoreE(){
        return scoreE;
    }
    int getCurrentMap(){
        return currentMap;
    }
}

public class Save implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int scoreP;
    private int scoreE;
    private int currentMap;

    Save(int scoreP, int scoreE, int currentMap){
        this.scoreP = scoreP;
        this.scoreE = scoreE;
        this.currentMap = currentMap;
    }

    public int getScoreP(){
        return scoreP;
    }
    public int getScoreE(){
        return scoreE;
    }
    public int getCurrentMap(){
        return currentMap;
    }
}

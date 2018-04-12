public class Save implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int scoreP;
    private int scoreE;

    Save(int scoreP, int scoreE){
        this.scoreP = scoreP;
        this.scoreE = scoreP;
    }

    public int getScoreP(){
        return scoreP;
    }
    public int getScoreE(){
        return scoreE;
    }
}

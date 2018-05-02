import java.util.ArrayList;
import java.util.List;

public class Level {

    private List<Wall> Walls = new ArrayList<>();
    private String mapBG;

    public void addWalls(Wall wall){
        Walls.add(wall);
    }
    public List<Wall> getWalls(){
        return Walls;
    }
    public Wall getWall(int i){
        return Walls.get(i);
    }

}

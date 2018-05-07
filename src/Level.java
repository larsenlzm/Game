import java.util.ArrayList;
import java.util.List;

public class Level {

    private List<Wall> Walls = new ArrayList<>();
    private double spawnPX;
    private double spawnPY;
    private double spawnEX;
    private double spawnEY;
    private String mapBG;

    public Level(double spawnPX, double spawnPY,double spawnEX, double spawnEY){
        this.spawnPX = spawnPX;
        this.spawnPY = spawnPY;
        this.spawnEX = spawnEX;
        this.spawnEY = spawnEY;

    }

    public void addWalls(Wall wall){
        Walls.add(wall);
    }
    public List<Wall> getWalls(){
        return Walls;
    }
    public Wall getWall(int i){
        return Walls.get(i);
    }
    public double getSpawnPX() {
        return spawnPX;
    }
    public double getSpawnPY() {
        return spawnPY;
    }
    public double getSpawnEX() {
        return spawnEX;
    }
    public double getSpawnEY() {
        return spawnEY;
    }
}

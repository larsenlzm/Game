import java.util.ArrayList;
import java.util.List;

/**
 * Class for levels
 *
 * @author s325919, s325894
 */

public class Level {

    private List<Wall> Walls = new ArrayList<>();
    private double spawnPX;
    private double spawnPY;
    private double spawnEX;
    private double spawnEY;
    private String mapBg;

    /**
     * Constructer that initiates the spawn loactions and background image
     *
     * @param spawnPX spawn x-coordinate for player.
     * @param spawnPY spawn y-coordinate for player.
     * @param spawnEX spawn x-coordinate for enemy.
     * @param spawnEY spawn y-coordinate for enemy.
     * @param mapBg Location of the background image.
     */

    public Level(double spawnPX, double spawnPY,double spawnEX, double spawnEY, String mapBg){
        this.spawnPX = spawnPX;
        this.spawnPY = spawnPY;
        this.spawnEX = spawnEX;
        this.spawnEY = spawnEY;
        this.mapBg = mapBg;
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
    public String getMapBg() {
        return mapBg;
    }
}

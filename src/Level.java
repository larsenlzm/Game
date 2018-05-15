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

    /**
     * Adds the correct wall
     * to the game.
     *
     * @param wall Which wall to add from the ArrayList.
     */
    public void addWalls(Wall wall){
        Walls.add(wall);
    }

    /**
     * Gets walls from the ArrayList.
     *
     * @return returns the walls from the ArrayList.
     */
    public List<Wall> getWalls(){
        return Walls;
    }

    /**
     * Gets the player spawn x-coordinate.
     *
     * @return returns the player spawn x-coordinate.
     */
    public double getSpawnPX() {
        return spawnPX;
    }

    /**
     * Gets the player spawn y-coordinate
     *
     * @return returns the player spawn y-coordinate.
     */
    public double getSpawnPY() {
        return spawnPY;
    }

    /**
     * Gets the enemy spawn x-coordinate
     *
     * @return returns the enemy spawn x-coordinate.
     */
    public double getSpawnEX() {
        return spawnEX;
    }

    /**
     * Gets the enemy spawn y-coordinate
     *
     * @return returns the enemy spawn y-coordinate.
     */
    public double getSpawnEY() {
        return spawnEY;
    }

    /**
     * Gets the map background.
     *
     * @return returns the map background
     */
    public String getMapBg() {
        return mapBg;
    }
}

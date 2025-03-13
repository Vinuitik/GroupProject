
/**
 * Write a description of class ObstacleFactory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ObstacleFactory
{
    
    private static ObstacleFactory instance;

    
    private ObstacleFactory() {
    }

    
    public static ObstacleFactory getInstance() {
        if (instance == null) {
            instance = new ObstacleFactory();
        }
        return instance;
    }

    Obstacle generateObstacle(String obstacleType){
        if(obstacleType.toLowerCase().equals("Cactus")){
            return new Cactus();
        }
        if(obstacleType.toLowerCase().equals("Bird")){
            return new Bird();
        }
        if(obstacleType.toLowerCase().equals("Cactuses")){
            return new Cactuses();
        }
        return null;
    }
}

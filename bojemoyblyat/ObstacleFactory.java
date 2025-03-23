import java.util.Random;

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

    public Obstacle generateObstacle(int x, int y, double score,String obstacleType){
        if(obstacleType.toLowerCase().equals("bush")){
            return new Bush(x,y,score);
        }
        if(obstacleType.toLowerCase().equals("bird")){
            return new Bird(x,y,score);
        }
        if(obstacleType.toLowerCase().equals("bushes")){
            return new Bushes(x,y,score);
        }
        return null;
    }
    
    public Obstacle generateRandomObstacle(int x, int y, double speed){
        int chosenType = randomType();
        if(chosenType==0){
            return new Bush(x,y,speed);
        }
        if(chosenType==1){
            return new Bird(x,y,speed);
        }
        if(chosenType==2){
            return new Bushes(x,y,speed);
        }
        return null;
    }
    
    private static int randomType() {
        return new Random().nextInt(3); // Generates 0, 1, 2, or 3
    }
}

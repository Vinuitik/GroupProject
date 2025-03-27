import java.util.Random;
import java.util.List;

/**
 * Write a description of class ObstacleFactory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ObstacleFactory
{
    
    private static ObstacleFactory instance;
    
    private List<Obstacle> list;

    
    private ObstacleFactory(List<Obstacle> list) {
        this.list = list;
    }

    
    public static ObstacleFactory getInstance(List<Obstacle> list) {
        if (instance == null) {
            instance = new ObstacleFactory(list);
        }
        return instance;
    }
    
    public static ObstacleFactory getInstance() {
        return instance;
    }

    public Obstacle generateObstacle(int x, int y, int finalX, double score,String obstacleType){
        Obstacle obstacle = null;
        if(obstacleType.toLowerCase().equals("bush")){
            obstacle = new Bush(x,y,finalX,score,list);
            
        }
        else if(obstacleType.toLowerCase().equals("bird")){
            obstacle = new Bird(x,y,finalX,score,list);
        }
        else if(obstacleType.toLowerCase().equals("bushes")){
            obstacle = new Bushes(x,y,finalX,score,list);
        }
        list.add(obstacle);
        return obstacle;
    }
    
    public Obstacle generateRandomObstacle(int x, int y,int finalX, double speed){
        int chosenType = randomType();
        Obstacle obstacle = null;
        if(chosenType==0){
            obstacle = new Bush(x,y,finalX,speed,list);
        }
        else if(chosenType==1){
            obstacle = new Bird(x,y,finalX,speed,list);
        }
        else if(chosenType==2){
            obstacle = new Bushes(x,y,finalX,speed,list);
        }
        list.add(obstacle);
        return obstacle;
    }
    
    private static int randomType() {
        return new Random().nextInt(3); // Generates 0, 1 or 2
    }
}

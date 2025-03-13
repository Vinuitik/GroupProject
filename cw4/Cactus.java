import javafx.util.Pair;
import java.awt.Point;
/**
 * Write a description of class Cactus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cactus extends Obstacle
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Cactus
     */
    public Cactus()
    {
        // initialise instance variables
        x = 0;
    }

    @Override
    public void start(){}
    @Override
    public void spawn(){}
    @Override
    public void move(){}
    @Override
    public Pair<Point,Point> getBoundingBox(){
        return null;
    }
}

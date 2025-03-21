import javafx.util.Pair;
import java.awt.Point;
/**
 * Write a description of class Bird here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bird extends Obstacle
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Bird
     */
    public Bird()
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

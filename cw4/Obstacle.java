import javafx.util.Pair;
import java.awt.Point;

/**
 * Write a description of class Obstacle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Obstacle
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Obstacle
     */
    public Obstacle()
    {
    }

    protected abstract void start();
    protected abstract void spawn();
    protected abstract void move();
    protected abstract Pair<Point,Point> getBoundingBox();
}

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.geometry.BoundingBox;

/**
 * Abstract class for game obstacles
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public abstract class Obstacle {
    // Position coordinates
    protected double x;
    protected double y;
    // Width and height for collision detection
    protected double width;
    protected double height;
    // JavaFX Group for rendering the obstacle
    protected Group obstacleGroup;
    
    /**
     * Constructor for the Obstacle class
     */
    public Obstacle() {
        obstacleGroup = new Group();
    }
    
    /**
     * Get the JavaFX Node for this obstacle
     * @return Group containing obstacle graphics
     */
    public Group getNode() {
        return obstacleGroup;
    }
    
    /**
     * Update the obstacle position
     * @param delta The amount to move the obstacle
     */
    public void update(double delta) {
        x -= delta; 
        obstacleGroup.setLayoutX(x);
    }
    
    // Getters and setters
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
        obstacleGroup.setLayoutX(x);
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
        obstacleGroup.setLayoutY(y);
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    /**
     * Get a bounding box for collision detection
     * @return BoundingBox for this obstacle
     */
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, width, height);
    }
    
    /**
     * Check if this obstacle collides with given coordinates and dimensions
     * @param otherX X coordinate
     * @param otherY Y coordinate
     * @param otherWidth Width
     * @param otherHeight Height
     * @return true if collision detected
     */
    public boolean collidesWith(double otherX, double otherY, double otherWidth, double otherHeight) {
        return x < otherX + otherWidth &&
               x + width > otherX &&
               y < otherY + otherHeight &&
               y + height > otherY;
    }
    
    /**
     * Initialize the obstacle
     */
    public abstract void initialize();
    
}
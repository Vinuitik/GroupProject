import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.geometry.BoundingBox;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.Pane;
import java.util.List;
import javafx.scene.shape.*;

/**
 * Abstract class for game obstacles
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public abstract class Obstacle {
    
    protected static final int futureOffset = 138;
    protected static final double minTime = 2.5;
    protected static final int timeNumerator = 320; 
    
    protected double finalX;
    
    // Position coordinates
    protected double x;
    protected double y;
    // Width and height for collision detection
    protected double width;
    protected double height;
    protected double score;
    // JavaFX Group for rendering the obstacle
    protected Group obstacleGroup;
    protected List<Obstacle> obstaclesList;
    
    protected Random random;
    
    protected TranslateTransition moveAnimation;
    
    /**
     * Constructor for the Obstacle class
     */
    public Obstacle(double score, double finalX,List<Obstacle> list) {
        obstacleGroup = new Group();
        this.score = score;
        random = new Random();
        
        this.obstaclesList = list;
        
        this.finalX = finalX;
    }
        
    public void delete(){
        clear();
        //obstacleGroup = null; // Allow garbage collection
    }
    public void clear(){
        if (obstacleGroup.getParent() != null) {
            ((Pane) obstacleGroup.getParent()).getChildren().remove(obstacleGroup);
        }
        obstaclesList.remove(this);
    }
    
    /**
     * Настраивает анимацию плавного вертикального движения
     */
    protected void setupMoveAnimation() {
        moveAnimation = new TranslateTransition(Duration.seconds(  timeNumerator / (score+futureOffset) + minTime  ), obstacleGroup);
        moveAnimation.setFromX(x);
        moveAnimation.setToX(finalX-x); // Move off screen
        moveAnimation.setCycleCount(1); // Stops after moving off screen
        
        moveAnimation.setOnFinished(event -> {
            clear();
        });
        
        moveAnimation.play();
    }
    
    /**
     * Get the JavaFX Node for this obstacle
     * @return Group containing obstacle graphics
     */
    public Group getNode() {
        return obstacleGroup;
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
    
    public void stopAnimation() {
        if (moveAnimation != null) {
            moveAnimation.stop();
        }
    }
    
    /**
     * Initialize the obstacle
     */
    public abstract void initialize();
    
}
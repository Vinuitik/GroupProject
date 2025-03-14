import javafx.util.Pair;
import java.awt.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 * Write a description of class Cactus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Bush/Cactus obstacle class for the Dinosaur Game
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bush extends Obstacle {
    private double speed;
    private int type; // 0 = small, 1 = medium, 2 = large
    
    /**
     * Constructor for the Bush class
     */
    public Bush() {
        super();
        this.type = 0; // Default to small cactus
        this.speed = 3.0;
        initialize();
    }
    
    /**
     * Constructor with custom position and type
     * @param startX Starting X position
     * @param startY Starting Y position
     * @param type The type of cactus (0=small, 1=medium, 2=large)
     */
    public Bush(double startX, double startY, int type) {
        super();
        this.type = type;
        this.speed = 3.0;
        initialize();
        setX(startX);
        setY(startY);
    }
    
    /**
     * Initialize the bush/cactus
     */
    @Override
    public void initialize() {
        buildBush();
    }
    
    /**
     * Build the bush/cactus graphics based on type
     */
    private void buildBush() {
        // Clear any existing children
        obstacleGroup.getChildren().clear();
        
        switch (type) {
            case 0: // Small cactus
                createSmallCactus();
                this.width = 40;
                this.height = 40;
                break;
            case 1: // Medium cactus (taller)
                createMediumCactus();
                this.width = 30;
                this.height = 60;
                break;
            case 2: // Large cactus (wider)
                createLargeCactus();
                this.width = 80;
                this.height = 40;
                break;
            default:
                createSmallCactus();
                this.width = 40;
                this.height = 40;
        }
        
        // Set default position (will be overridden when placed in game)
        this.x = 600;
        this.y = 250; // Adjust to match ground level in your game
        obstacleGroup.setLayoutX(x);
        obstacleGroup.setLayoutY(y);
    }
    
    /**
     * Create a small cactus
     */
    private void createSmallCactus() {
        Circle leaf1 = new Circle(0, 0, 20);
        Circle leaf2 = new Circle(20, 0, 20);
        Circle leaf3 = new Circle(10, -20, 20);
        
        leaf1.setFill(Color.GREEN);
        leaf2.setFill(Color.GREEN);
        leaf3.setFill(Color.GREEN);
        
        leaf1.setStroke(Color.DARKGREEN);
        leaf2.setStroke(Color.DARKGREEN);
        leaf3.setStroke(Color.DARKGREEN);
        
        obstacleGroup.getChildren().addAll(leaf1, leaf2, leaf3);
    }
    
    /**
     * Create a medium (taller) cactus
     */
    private void createMediumCactus() {
        Circle leaf1 = new Circle(0, 0, 15);
        Circle leaf2 = new Circle(0, -30, 15);
        Circle leaf3 = new Circle(0, -60, 15);
        
        leaf1.setFill(Color.GREEN);
        leaf2.setFill(Color.GREEN);
        leaf3.setFill(Color.GREEN);
        
        leaf1.setStroke(Color.DARKGREEN);
        leaf2.setStroke(Color.DARKGREEN);
        leaf3.setStroke(Color.DARKGREEN);
        
        obstacleGroup.getChildren().addAll(leaf1, leaf2, leaf3);
    }
    
    /**
     * Create a large (wider) cactus
     */
    private void createLargeCactus() {
        Circle leaf1 = new Circle(0, 0, 20);
        Circle leaf2 = new Circle(30, 0, 20);
        Circle leaf3 = new Circle(60, 0, 20);
        
        leaf1.setFill(Color.GREEN);
        leaf2.setFill(Color.GREEN);
        leaf3.setFill(Color.GREEN);
        
        leaf1.setStroke(Color.DARKGREEN);
        leaf2.setStroke(Color.DARKGREEN);
        leaf3.setStroke(Color.DARKGREEN);
        
        obstacleGroup.getChildren().addAll(leaf1, leaf2, leaf3);
    }
    
    /**
     * Update bush position
     * @param delta The speed modifier
     */
    @Override
    public void update(double delta) {
        // Move bush left
        x -= (speed * delta);
        obstacleGroup.setLayoutX(x);
    }
    
    
    /**
     * Set the speed of the bush
     * @param speed The new speed value
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

/**
 * Bushes obstacle class for multiple cacti in a row
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bushes extends Obstacle {
    private double speed;
    private int numberOfBushes;
    private Random random;
    
    /**
     * Constructor for the Bushes class
     */
    public Bushes() {
        super();
        random = new Random();
        this.numberOfBushes = random.nextBoolean() ? 2 : 3;
        this.speed = 3.0;
        initialize();
    }
    
    /**
     * Constructor with custom position
     * @param startX Starting X position
     * @param startY Starting Y position
     */
    public Bushes(double startX, double startY) {
        super();
        random = new Random();
        this.numberOfBushes = random.nextBoolean() ? 2 : 3;
        this.speed = 3.0;
        initialize();
        setX(startX);
        setY(startY);
    }
    
    /**
     * Constructor with custom position and number of bushes
     * @param startX Starting X position
     * @param startY Starting Y position
     * @param numberOfBushes Number of bushes to create
     */
    public Bushes(double startX, double startY, int numberOfBushes) {
        super();
        this.numberOfBushes = numberOfBushes;
        this.speed = 3.0;
        initialize();
        setX(startX);
        setY(startY);
    }
    
    /**
     * Initialize the bushes
     */
    @Override
    public void initialize() {
        buildBushes();
    }
    
    /**
     * Build multiple bushes/cacti in a row
     */
    private void buildBushes() {
        // Clear any existing children
        obstacleGroup.getChildren().clear();
        
        double offsetX = 0;
        double totalWidth = 0;
        
        for (int i = 0; i < numberOfBushes; i++) {
            // Each bush consists of three circles
            Circle leaf1 = new Circle(offsetX + 0, 0, 20);
            Circle leaf2 = new Circle(offsetX + 20, 0, 20);
            Circle leaf3 = new Circle(offsetX + 10, -20, 20);
            
            leaf1.setFill(Color.GREEN);
            leaf2.setFill(Color.GREEN);
            leaf3.setFill(Color.GREEN);
            
            leaf1.setStroke(Color.DARKGREEN);
            leaf2.setStroke(Color.DARKGREEN);
            leaf3.setStroke(Color.DARKGREEN);
            
            obstacleGroup.getChildren().addAll(leaf1, leaf2, leaf3);
            
            // Move to next bush position
            offsetX += 60;
            totalWidth = offsetX;
        }
        
        // Set collision box to cover all bushes
        this.width = totalWidth;
        this.height = 40;
        
        // Set default position (will be overridden when placed in game)
        this.x = 600;
        this.y = 250; // Adjust to match ground level in your game
        obstacleGroup.setLayoutX(x);
        obstacleGroup.setLayoutY(y);
    }
    
    /**
     * Update bushes position
     * @param delta The speed modifier
     */
    @Override
    public void update(double delta) {
        // Move bushes left
        x -= (speed * delta);
        obstacleGroup.setLayoutX(x);
    }
    
    /**
     * Set the speed of the bushes
     * @param speed The new speed value
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    /**
     * Get the number of bushes
     * @return Number of bushes
     */
    public int getNumberOfBushes() {
        return numberOfBushes;
    }
}
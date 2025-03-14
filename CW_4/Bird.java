import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

/**
 * Bird obstacle class
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bird extends Obstacle {
    private Timeline flapAnimation;
    private double speed;
    private double altitude;
    
    /**
     * Constructor for the Bird class
     */
    public Bird() {
        super();
        this.width = 20;
        this.height = 20;
        this.speed = 3.0;
        initialize();
    }
    
    /**
     * Constructor with custom position
     * @param startX Starting X position
     * @param startY Starting Y position
     */
    public Bird(double startX, double startY) {
        super();
        this.width = 20;
        this.height = 20;
        this.speed = 3.0;
        initialize();
        setX(startX);
        setY(startY);
    }
    
    /**
     * Initialize the bird
     */
    @Override
    public void initialize() {
        buildBird();
        setupFlapAnimation();
    }
    
    /**
     * Build the bird graphics
     */
    private void buildBird() {
        // Clear any existing children
        obstacleGroup.getChildren().clear();
        
        // Create bird body
        Circle body = new Circle(0, 0, 10);
        body.setFill(Color.DODGERBLUE);
        
        // Create bird wing
        Polygon wing = new Polygon(
            -5.0, 0.0,
            -10.0, -5.0,
            -2.0, -3.0
        );
        wing.setFill(Color.WHITE);
        
        // Create bird beak
        Polygon beak = new Polygon(
            10.0, 0.0,
            15.0, 0.0,
            10.0, 5.0
        );
        beak.setFill(Color.ORANGE);
        
        // Add all parts to the group
        obstacleGroup.getChildren().addAll(body, wing, beak);
        
        // Set default position (will be overridden when placed in game)
        this.x = 600;
        this.y = 150;
        obstacleGroup.setLayoutX(x);
        obstacleGroup.setLayoutY(y);
    }
    
    /**
     * Setup wing flapping animation
     */
    private void setupFlapAnimation() {
        // Only proceed if we have a wing
        if (obstacleGroup.getChildren().size() < 2) return;
        
        // Get the wing and set up animation
        Polygon wing = (Polygon) obstacleGroup.getChildren().get(1);
        
        // Create timeline for wing flapping
        flapAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> wing.setRotate(0)),
            new KeyFrame(Duration.millis(100), e -> wing.setRotate(20)),
            new KeyFrame(Duration.millis(200), e -> wing.setRotate(0))
        );
        flapAnimation.setCycleCount(Timeline.INDEFINITE);
        flapAnimation.play();
    }
    
    /**
     * Update bird position
     * @param delta The speed modifier
     */
    @Override
    public void update(double delta) {
        // Move bird left
        x -= (speed * delta);
        obstacleGroup.setLayoutX(x);
        
        // Optional: Add slight vertical movement for more natural flight
        // y += Math.sin(x * 0.05) * 0.5;
        // obstacleGroup.setLayoutY(y);
    }
    
    
    /**
     * Stop animations when bird is no longer needed
     */
    public void cleanup() {
        if (flapAnimation != null) {
            flapAnimation.stop();
        }
    }
}
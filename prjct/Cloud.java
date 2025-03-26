import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.Pane;
import java.util.List;

/**
 * Cloud class for background decoration in the game
 * Creates minimalistic cloud shapes that slowly move across the sky
 * 
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Cloud {
    private Group cloudGroup;
    private TranslateTransition moveAnimation;
    private Random random;
    private Pane parentPane;
    private double width;
    List<Cloud> clouds;
    
    // Cloud position
    private double x;
    private double y;
    
    /**
     * Constructor for Cloud objects
     * 
     * @param startX starting X position
     * @param startY starting Y position
     * @param width width of the game window (for animation)
     * @param parentPane the parent pane to add/remove cloud from
     */
    public Cloud(double startX, double startY, double width, Pane parentPane, List<Cloud> clouds) {
        random = new Random();
        cloudGroup = new Group();
        this.parentPane = parentPane;
        this.width = width;
        
        this.clouds = clouds;
        
        this.x = startX;
        this.y = startY;
        
        buildCloud();
        setupMoveAnimation();
    }
    
    /**
     * Build the cloud graphics
     */
    private void buildCloud() {
        // Random cloud size (scaled together)
        double scale = 0.7 + random.nextDouble() * 0.6;
        
        // Create a group of overlapping circles to form a cloud
        double baseRadius = 20 * scale;
        
        // Center cluster of circles
        Circle mainCircle = new Circle(0, 0, baseRadius);
        Circle topCircle = new Circle(-baseRadius * 0.5, -baseRadius * 0.3, baseRadius * 0.7);
        Circle rightCircle = new Circle(baseRadius * 0.8, 0, baseRadius * 0.9);
        Circle leftCircle = new Circle(-baseRadius * 0.8, baseRadius * 0.1, baseRadius * 0.8);
        
        // Add some randomness to each cloud
        if (random.nextBoolean()) {
            Circle extraCircle = new Circle(
                baseRadius * 0.4,
                -baseRadius * 0.5,
                baseRadius * 0.6
            );
            extraCircle.setFill(Color.WHITE);
            cloudGroup.getChildren().add(extraCircle);
        }
        
        // Set all circles to white with slight transparency
        Color cloudColor = new Color(1, 1, 1, 0.8);
        mainCircle.setFill(cloudColor);
        topCircle.setFill(cloudColor);
        rightCircle.setFill(cloudColor);
        leftCircle.setFill(cloudColor);
        
        // Add circles to the cloud group
        cloudGroup.getChildren().addAll(mainCircle, topCircle, rightCircle, leftCircle);
        
        // Set layout position
        cloudGroup.setLayoutX(x);
        cloudGroup.setLayoutY(y);
    }
    
    public void delete(){
        if (cloudGroup.getParent() != null) {
            parentPane.getChildren().remove(cloudGroup);
        }
        clouds.remove(this);
    }
    
    /**
     * Setup the cloud movement animation
     */
    private void setupMoveAnimation() {
        // Calculate a very slow speed (between 40-60 seconds to cross the screen)
        double duration = 40 + random.nextDouble() * 20;
        
        moveAnimation = new TranslateTransition(Duration.seconds(duration), cloudGroup);
        moveAnimation.setFromX(0);
        moveAnimation.setToX(-width - 200); // Move past the left edge of screen
        moveAnimation.setCycleCount(1);
        
        // When animation completes, remove this cloud and create a new one from the right
        moveAnimation.setOnFinished(event -> {
            try {
                
                delete();
                // Create a new cloud at the right edge
                createNewCloud();
            } catch (Exception e) {
                // Handle potential issues
            }
        });
        
        moveAnimation.play();
    }
    
    /**
     * Create a new cloud when this one disappears off screen
     */
    private void createNewCloud() {
        try {
            // Random position for the new cloud
            double newY = 50 + random.nextDouble() * 100; // Clouds between y=50 and y=150
            Cloud newCloud = new Cloud(width + 50, newY, width, parentPane,clouds);
            clouds.add(newCloud);
            parentPane.getChildren().add(newCloud.getNode());
        } catch (Exception e) {
            // Handle errors when creating new cloud
        }
    }
    
    public void stopAnimation() {
        if (moveAnimation != null) {
            moveAnimation.stop();
        }
    }
    
    /**
     * Get the cloud node for adding to the scene
     * 
     * @return Group containing cloud graphics
     */
    public Group getNode() {
        return cloudGroup;
    }
}
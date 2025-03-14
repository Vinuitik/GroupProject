import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;


/**
 * Bushes obstacle class for multiple bushes in a row
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bushes extends Obstacle {
    private double speed;
    private int numberOfBushes;
    private Random random;
    private static final Color[] FLOWER_COLORS = {
        Color.PINK, Color.YELLOW, Color.LIGHTSKYBLUE, 
        Color.LIGHTCORAL, Color.LAVENDER, Color.WHITE
    };
    
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
        random = new Random();
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
     * Build multiple bushes in a row
     */
    private void buildBushes() {
        if (obstacleGroup == null) {
            obstacleGroup = new Group(); // Ensure it is initialized
        }

        double offsetX = 0; // Start from the first bush
        
        Random random1 = new Random();

        for (int i = 0; i < numberOfBushes; i++) {
            double scale = 0.7 + random1.nextDouble() * 0.6; // Scale variation (0.7 to 1.3)

            // Create bush components
            Rectangle stem = new Rectangle(offsetX - (3 * scale), 0, 6 * scale, 25 * scale);
            stem.setFill(Color.SADDLEBROWN);

            Circle base = new Circle(offsetX, 0, 20 * scale);
            base.setFill(Color.FORESTGREEN);

            Circle leftSide = new Circle(offsetX - 15 * scale, -5 * scale, 15 * scale);
            leftSide.setFill(Color.FORESTGREEN);

            Circle rightSide = new Circle(offsetX + 15 * scale, -5 * scale, 15 * scale);
            rightSide.setFill(Color.FORESTGREEN);

            Circle top = new Circle(offsetX, -18 * scale, 14 * scale);
            top.setFill(Color.FORESTGREEN);

            // Add elements to obstacleGroup
            obstacleGroup.getChildren().addAll(stem, base, leftSide, rightSide, top);

            // Add highlights and flowers
            addHighlights(offsetX, scale);
            addFlowers(offsetX, scale);

            // Offset for next bush
            offsetX += 50 * scale;
        }

        // Set obstacle properties using Obstacle methods
        setX(600);  // Starting X position (off-screen)
        setY(250);  // Ground level

        this.width = offsetX;  // Total width of all bushes
        this.height = 40;      // Approximate height

        // Apply position to JavaFX node
        obstacleGroup.setLayoutX(getX());
        obstacleGroup.setLayoutY(getY());
    }

    /**
     * Add highlights to the bushes
     */
    private void addHighlights(double offsetX, double scale) {
        int highlights = random.nextInt(3) + 2; // 2-4 highlights
        
        for (int i = 0; i < highlights; i++) {
            double hx = offsetX + (random.nextDouble() * 30 - 15) * scale;
            double hy = (random.nextDouble() * 15 - 15) * scale;
            double hr = (5 + random.nextDouble() * 7) * scale;
            
            Circle highlight = new Circle(hx, hy, hr);
            highlight.setFill(Color.LIMEGREEN);
            
            obstacleGroup.getChildren().add(highlight);
        }
    }

    /**
     * Add flowers to the bushes
     */
    private void addFlowers(double offsetX, double scale) {
        int flowers = random.nextInt(4) + 1; // 1-4 flowers
        Random random1 = new Random();
        
        for (int i = 0; i < flowers; i++) {
            double fx = offsetX + (random1.nextDouble() * 40 - 20) * scale;
            double fy = (random1.nextDouble() * 20 - 25) * scale;
            double fr = (3 + random1.nextDouble() * 3) * scale;
            
            // Choose a random flower color from the list
            Color flowerColor = FLOWER_COLORS[random.nextInt(FLOWER_COLORS.length)];
            
            Circle flower = new Circle(fx, fy, fr);
            flower.setFill(flowerColor);
            
            // Flower center
            Circle flowerCenter = new Circle(fx, fy, fr * 0.4);
            flowerCenter.setFill(Color.YELLOW);
            
            obstacleGroup.getChildren().addAll(flower, flowerCenter);
        }
    }

    /**
     * Update bushes position
     * @param delta The speed modifier
     */
    @Override
    public void update(double delta) {
        // Move bushes to the left
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

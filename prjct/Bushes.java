import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import java.util.List;
import javafx.scene.shape.*;
import javafx.scene.Node;


/**
 * Bushes obstacle class for multiple bushes in a row
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bushes extends Obstacle {
    private int numberOfBushes;
    private static final Color[] FLOWER_COLORS = {
        Color.PINK, Color.YELLOW, Color.LIGHTSKYBLUE, 
        Color.LIGHTCORAL, Color.LAVENDER, Color.WHITE
    };
    
    
    /**
     * Constructor with custom position
     * @param startX Starting X position
     * @param startY Starting Y position
     */
    

    public Bushes(double startX, double startY, double finalX, double score, List<Obstacle> obstacles) {
        super(score, finalX,obstacles);
        this.numberOfBushes = randomBushCount();
        setX(startX);
        setY(startY+15);
        initialize();
    }

    private static int randomBushCount() {
        return new Random().nextBoolean() ? 2 : 3;
    }
    
    /**
     * Initialize the bushes
     */
    @Override
    public void initialize() {
        buildBushes();
        setupMoveAnimation();
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
            offsetX += 35 * scale;
        }
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
        
        for (int i = 0; i < flowers; i++) {
            double fx = offsetX + (random.nextDouble() * 40 - 20) * scale;
            double fy = (random.nextDouble() * 20 - 25) * scale;
            double fr = (3 + random.nextDouble() * 3) * scale;
            
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
     * Get the number of bushes
     * @return Number of bushes
     */
    public int getNumberOfBushes() {
        return numberOfBushes;
    }
}

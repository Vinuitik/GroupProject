import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.List;
import javafx.scene.shape.*;
import javafx.scene.Node;

/**
 * Bush/Cactus obstacle class for the Dinosaur Game
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bush extends Obstacle {
    private int type; // 0 = small, 1 = medium, 2 = large

    /**
     * Constructor with custom position and type
     * @param startX Starting X position
     * @param startY Starting Y position
     * @param type The type of cactus (0=small, 1=medium, 2=large)
     */
    public Bush(double startX, double startY,double finalX, double score, List<Obstacle> obstacles) {
        super(score,finalX, obstacles);
        this.type = randomType();
        setX(startX);
        setY(startY);
        initialize();
    }
    
    private static int randomType() {
        return new Random().nextInt(4); // Generates 0, 1, 2, or 3
    }

    /**
     * Initialize the bush/cactus
     */
    @Override
    public void initialize() {
        buildBush();
        setupMoveAnimation();
    }

    /**
     * Build the bush/cactus graphics based on type
     */
    private void buildBush() {
        // Bush Stem
        Rectangle stem = new Rectangle(-5, 0, 10, 30);
        stem.setFill(Color.SADDLEBROWN);

        // Main bush parts
        Circle base = new Circle(0, 0, 25);
        base.setFill(Color.FORESTGREEN);

        Circle leftSide = new Circle(-18, -5, 20);
        leftSide.setFill(Color.FORESTGREEN);

        Circle rightSide = new Circle(18, -5, 20);
        rightSide.setFill(Color.FORESTGREEN);

        Circle top = new Circle(0, -20, 18);
        top.setFill(Color.FORESTGREEN);

        // Highlights
        Circle highlight1 = new Circle(-12, -12, 8);
        highlight1.setFill(Color.LIMEGREEN);

        Circle highlight2 = new Circle(12, -12, 10);
        highlight2.setFill(Color.LIMEGREEN);

        Circle highlight3 = new Circle(0, -5, 7);
        highlight3.setFill(Color.LIMEGREEN);

        // Flowers
        Circle flower1 = new Circle(-15, -20, 5);
        flower1.setFill(Color.PINK);

        Circle flower2 = new Circle(15, -25, 4);
        flower2.setFill(Color.YELLOW);

        Circle flower3 = new Circle(5, -30, 3);
        flower3.setFill(Color.LIGHTSKYBLUE);

        // Flower centers
        Circle flowerCenter1 = new Circle(-15, -20, 2);
        flowerCenter1.setFill(Color.YELLOW);

        Circle flowerCenter2 = new Circle(15, -25, 1.5);
        flowerCenter2.setFill(Color.ORANGE);

        Circle flowerCenter3 = new Circle(5, -30, 1);
        flowerCenter3.setFill(Color.WHITE);

        // Add elements to the group
        obstacleGroup.getChildren().addAll(
            stem, base, leftSide, rightSide, top, 
            highlight1, highlight2, highlight3,
            flower1, flower2, flower3,
            flowerCenter1, flowerCenter2, flowerCenter3
        );
    }

    
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Scene class for the Dinosaur Game
 * Manages the game background, including ground, clouds, and game elements
 * 
 * @author Dima, Daria, David
 * @version 1.0
 */
public class GameScene extends Application {
    
    // Window dimensions
    private final int WIDTH = 800;
    private final int HEIGHT = 400;
    
    // Root pane
    private Pane root;
    
    // Game elements
    private Bird bird;
    private Bush smallCactus;
    private Bush mediumCactus;
    private Bushes multipleCacti;
    
    public GameScene() {
        // Constructor remains empty as setup is done in start() method
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize window
        primaryStage.setTitle("Dinosaur Game");
        
        // Create a simple pane
        root = new Pane();
        
        // Initialize and add game elements
        initializeGameElements();
        
        // Create scene and add to stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        // Show the stage
        primaryStage.show();
    }
    
    /**
     * Initialize game elements and add them to the scene
     */
    private void initializeGameElements() {
        // Create a bird obstacle
        bird = new Bird(700, 150);
        
        // Create a small cactus
        smallCactus = new Bush(500, 250, 0);
        
        // Create a medium (taller) cactus
        mediumCactus = new Bush(300, 250, 1);
        
        // Create a set of multiple cacti
        multipleCacti = new Bushes(100, 250, 3);
        
        // Add all obstacles to the root pane
        root.getChildren().addAll(
            bird.getNode(),
            smallCactus.getNode(),
            mediumCactus.getNode(),
            multipleCacti.getNode()
        );
    }
    
    /**
     * Main method to launch the application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
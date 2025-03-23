import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    
    ObstacleFactory factory;
    
    public GameScene() {
        // Constructor remains empty as setup is done in start() method
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize window
        primaryStage.setTitle("Dinosaur Game");
        
        factory = ObstacleFactory.getInstance();
        
        // Create a simple pane
        root = new Pane();
        
        
        
        // Create scene and add to stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        // Show the stage
        primaryStage.show();
        
        // Initialize and add game elements
        initializeGameElements();
    }
    
    /**
     * Initialize game elements and add them to the scene
     */
    private void initializeGameElements() {
       
        
        new Thread(() -> {
            while(true) {
                javafx.application.Platform.runLater(() -> 
                    root.getChildren().add(factory.generateRandomObstacle(500, 200, 60).getNode())
                );
                try {
                    Thread.sleep(2700);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
        
        
        
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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import java.util.Random;


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
    private final Random random = new Random();
    
    private final int spawnObstacleX = 500;
    private final int spawnCharacterX = 50;
    private final int spawnY = 270;
    private final int finalX = -200;
    private AtomicInteger score;
    
    private Label scoreLabel;
    
    // Root pane
    private Pane root;
    
    private Kitten kitten;
    
    private ObstacleFactory factory;
    
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

        // Create score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: gray;");
        scoreLabel.setLayoutX(20);  // Position on the screen
        scoreLabel.setLayoutY(20);
        root.getChildren().add(scoreLabel); // Add label to root
        
        kitten = new Kitten(spawnCharacterX,spawnY);
        root.getChildren().add(kitten.getNode());

        // Create scene and add to stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP) {
                kitten.jump();  // Trigger jump when spacebar or up arrow is pressed
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show the stage
        primaryStage.show();
        
        

        score = new AtomicInteger(0);

        // Initialize and add game elements
        initializeGameElements();
        startCountingScore();
        
    }
    
    /**
     * Initialize game elements and add them to the scene
     */
    private void initializeGameElements() {
       
        
        new Thread(() -> {
            while(true) {
                int scoreVal = score.get();
                javafx.application.Platform.runLater(() -> 
                    root.getChildren().add(factory.generateRandomObstacle(spawnObstacleX, spawnY,finalX, scoreVal).getNode())
                );
                try {
                    Thread.sleep((123000 / (scoreVal + 80)) + 410 + random.nextInt(120)  );
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
    }
    
    /**
     * Initialize game elements and add them to the scene
     */
    private void startCountingScore() {
       
        
        new Thread(() -> {
            while(true) {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
                int newScore = score.addAndGet(1);
                Platform.runLater(() -> scoreLabel.setText("Score: " + newScore));
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
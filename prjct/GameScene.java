import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import java.util.Collections;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import java.util.List;
import javafx.animation.AnimationTimer;
import java.util.Iterator;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;


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
    
    private List<Obstacle> obstacles;
    private List<Cloud> clouds;
    
    private final int spawnObstacleX = 550;
    private final int spawnCharacterX = 50;
    private final int spawnY = 270;
    private final int finalX = -200;
    private AtomicInteger score;
    private AtomicBoolean crushed;
    
    private AnimationTimer collisionTimer;
    
    private Label scoreLabel;
    private Pane gameOverPane;
    
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
        
        obstacles = new ArrayList<>();
        clouds = new ArrayList<>();

        factory = ObstacleFactory.getInstance(obstacles);
        

        // Create a simple pane
        root = new Pane();
        
        // Set sky background color (light blue)
        root.setStyle("-fx-background-color: #87CEEB;");
        
        // Create clouds in the background
        try {
            createClouds();
        } catch (Exception e) {
            // Continue without clouds if there's an error
            System.err.println("Could not create clouds: " + e.getMessage());
        }
        
        // Create the ground
        createGround();

        // Create score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: gray;");
        scoreLabel.setLayoutX(20);  // Position on the screen
        scoreLabel.setLayoutY(20);
        root.getChildren().add(scoreLabel); // Add label to root
        
        // Create game over pane
        createGameOverPane();
        
        kitten = new Kitten(spawnCharacterX,spawnY);
        root.getChildren().add(kitten.getNode());

        // Create scene and add to stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP) {
                kitten.jump();  // Trigger jump when spacebar or up arrow is pressed
            }
            /*if (event.getCode() == KeyCode.F) {
                stopALL();
                showGameOver();
            }*/
            if (event.getCode() == KeyCode.R) {
                reset();
                hideGameOver();
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show the stage
        primaryStage.show();

        score = new AtomicInteger(0);
        crushed = new AtomicBoolean(false);

        // Initialize and add game elements
        initializeGameElements();
        startCountingScore();
        checkCollisions();
    }
    
    /**
     * Creates the game over screen with retry and quit buttons
     */
    private void createGameOverPane() {
        gameOverPane = new Pane();
        gameOverPane.setPrefSize(WIDTH, HEIGHT);
        gameOverPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background
        gameOverPane.setVisible(false);
        
        // Game Over message
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(new Font("Arial Bold", 48));
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setLayoutX(WIDTH / 2 - 150);
        gameOverLabel.setLayoutY(HEIGHT / 2 - 100);
        
        // Create buttons
        Button retryButton = createButton("Retry", 120, 50);
        retryButton.setOnAction(e -> {
            reset();
            hideGameOver();
        });
        
        Button quitButton = createButton("Quit", 120, 50);
        quitButton.setOnAction(e -> System.exit(0));
        
        // Create horizontal box for buttons
        HBox buttonBox = new HBox(20); // 20 pixels spacing
        buttonBox.getChildren().addAll(retryButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setLayoutX(WIDTH / 2 - 130);
        buttonBox.setLayoutY(HEIGHT / 2);
        
        // Add elements to game over pane
        gameOverPane.getChildren().addAll(gameOverLabel, buttonBox);
        
        // Add game over pane to root
        root.getChildren().add(gameOverPane);
    }
    
    /**
     * Helper method to create styled buttons
     */
    private Button createButton(String text, int width, int height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(new Font("Arial Bold", 16));
        
        // Style the button
        button.setStyle("-fx-background-color: #3c7fb1; " +
                         "-fx-text-fill: white; " +
                         "-fx-background-radius: 5;");
        
        // Add hover effect
        button.setOnMouseEntered(e -> 
            button.setStyle("-fx-background-color: #5a9fd4; " +
                           "-fx-text-fill: white; " +
                           "-fx-background-radius: 5;"));
        
        button.setOnMouseExited(e -> 
            button.setStyle("-fx-background-color: #3c7fb1; " +
                           "-fx-text-fill: white; " +
                           "-fx-background-radius: 5;"));
        
        // Add shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0, 0, 0, 0.3));
        button.setEffect(shadow);
        
        return button;
    }
    
    /**
     * Shows the game over screen
     */
    private void showGameOver() {
        gameOverPane.setVisible(true);
        gameOverPane.toFront();
    }
    
    /**
     * Hides the game over screen
     */
    private void hideGameOver() {
        gameOverPane.setVisible(false);
    }
    
    private void stopALL(){
        kitten.stopAnimation();
        for(Obstacle obstacle : obstacles){
            obstacle.stopAnimation();
        }
        for(Cloud cloud : clouds){
            cloud.stopAnimation();
        }
        crushed.set(true);
    }
    
    private void reset(){
        kitten.reset();
        
        // Create a copy to avoid modification while iterating
        List<Obstacle> obstaclesCopy = new ArrayList<>(obstacles);
        for (Obstacle obstacle : obstaclesCopy) {
            obstacle.delete(); // This will remove it from the original list
        }
        
        List<Cloud> cloudsCopy = new ArrayList<>(clouds);
        for (Cloud cloud : cloudsCopy) {
            cloud.delete(); // This will remove it from the original list
        }
        try {
            createClouds();
        } catch (Exception e) {
            // Continue without clouds if there's an error
            System.err.println("Could not create clouds: " + e.getMessage());
        }
        score.set(0);
        crushed.set(false);
    }
    
    /**
     * Create the clouds in the background
     */
    private void createClouds() {
        // Create several clouds at different positions
        int numClouds = 4 + random.nextInt(3); // 4-6 clouds
        
        for (int i = 0; i < numClouds; i++) {
            // Random position for each cloud - starting from different positions along the width
            double cloudX = random.nextDouble() * WIDTH;
            double cloudY = 50 + random.nextDouble() * 100; // Clouds between y=50 and y=150
            
            Cloud cloud = new Cloud(cloudX, cloudY, WIDTH, root, clouds);
            clouds.add(cloud);
            root.getChildren().add(cloud.getNode());
        }
    }
    
    /**
     * Create the ground on which the kitten runs and obstacles are placed
     */
    private void createGround() {
        // Main ground rectangle
        Rectangle ground = new Rectangle(0, spawnY + 30, WIDTH, HEIGHT - spawnY - 30);
        
        // Create a gradient for more natural looking ground
        Stop[] stops = new Stop[] {
            new Stop(0, Color.rgb(139, 69, 19)), // Dark brown at top
            new Stop(0.3, Color.rgb(160, 82, 45)), // Medium brown
            new Stop(1, Color.rgb(210, 180, 140))  // Light brown at bottom
        };
        
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops
        );
        
        ground.setFill(gradient);
        
        // Add a thin grass layer on top of the ground
        Rectangle grassLayer = new Rectangle(0, spawnY + 30, WIDTH, 5);
        grassLayer.setFill(Color.rgb(50, 150, 50)); // Green grass
        
        // Add ground to the scene
        root.getChildren().addAll(ground, grassLayer);
    }
    
    /**
     * Initialize game elements and add them to the scene
     */
    private void initializeGameElements() {
       
        
        new Thread(() -> {
            while(true) {
                if(!crushed.get()){
                    int scoreVal = score.get();
                    javafx.application.Platform.runLater(() -> 
                        root.getChildren().add(factory.generateRandomObstacle(spawnObstacleX, spawnY,finalX, scoreVal).getNode())
                    );
                    try {
                        Thread.sleep((123000 / (scoreVal + 80)) + 430 + random.nextInt(120)+scoreVal/100  );
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
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
                if(!crushed.get()){
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
            }
        }).start();
    }
    
    /**
     * Initialize game elements and add them to the scene
     */
    private void checkCollisions() {
        collisionTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!crushed.get() && !obstacles.isEmpty()) {
                    int limit = Math.min(obstacles.size(), 4); // Determine the number of obstacles to check
                    for (int i = 0; i < limit; i++) {
                        Obstacle obstacle = obstacles.get(i);
                        if (kitten.check(obstacle)) {
                            stopALL();
                            showGameOver();
                        }
                    }
                }
            }
        };
        collisionTimer.start();
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Ellipse;
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

    private Timeline floatAnimation;
    private Polygon wing;
    private Polygon beak;
    private double originalY;
    
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
        buildBird();
        setupFlapAnimation();
        setupFloatAnimation();
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
         // Тело птицы - более детализированное, используем эллипс
        Ellipse body = new Ellipse(0, 0, 15, 10);
        body.setFill(Color.ROYALBLUE);
        
        // Голова птицы
        Circle head = new Circle(-12, -5, 8);
        head.setFill(Color.ROYALBLUE);
        
        // Клюв
        beak = new Polygon(
            -22.0, -7.0,
            -15.0, -3.0,
            -15.0, -7.0
        );
        beak.setFill(Color.ORANGE);
        
        // Глаз
        Circle eye = new Circle(-15, -7, 2);
        eye.setFill(Color.WHITE);
        
        Circle pupil = new Circle(-15, -7, 1);
        pupil.setFill(Color.BLACK);
        
        // Крыло
        wing = new Polygon(
            -5.0, 0.0,
            5.0, 0.0,
            10.0, -10.0,
            0.0, -5.0
        );
        wing.setFill(Color.CORNFLOWERBLUE);
        
        // Хвост
        Polygon tail = new Polygon(
            15.0, 0.0,
            25.0, -5.0,
            25.0, 5.0
        );
        tail.setFill(Color.CORNFLOWERBLUE);
        
        // Лапки
        Polygon leftLeg = new Polygon(
            -5.0, 10.0,
            -5.0, 15.0,
            -10.0, 15.0
        );
        leftLeg.setFill(Color.ORANGE);
        
        Polygon rightLeg = new Polygon(
            5.0, 10.0,
            5.0, 15.0,
            0.0, 15.0
        );
        rightLeg.setFill(Color.ORANGE);
        
        obstacleGroup.getChildren().addAll(
            body, head, beak, eye, pupil, wing, tail, leftLeg, rightLeg
        );
        
        // Начальное положение
        this.x = 600;
        this.y = 150;
        originalY = y;
        obstacleGroup.setLayoutX(x);
        obstacleGroup.setLayoutY(y);
    }
    
    private void setupFlapAnimation() {
        flapAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                wing.setRotate(0);
                wing.setLayoutY(0);
            }),
            new KeyFrame(Duration.seconds(0.25), e -> {
                wing.setRotate(-30);
                wing.setLayoutY(-5);
            }),
            new KeyFrame(Duration.seconds(0.5), e -> {
                wing.setRotate(0);
                wing.setLayoutY(0);
            })
        );
        flapAnimation.setCycleCount(Timeline.INDEFINITE);
        flapAnimation.play();
        
        // Добавляем анимацию открывания клюва
        Timeline beakAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> beak.setRotate(0)),
            new KeyFrame(Duration.seconds(1), e -> beak.setRotate(15)),
            new KeyFrame(Duration.seconds(1.2), e -> beak.setRotate(0))
        );
        beakAnimation.setCycleCount(Timeline.INDEFINITE);
        beakAnimation.play();
    }
    
    /**
     * Настраивает анимацию плавного вертикального движения
     */
    private void setupFloatAnimation() {
        floatAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                setY(originalY);
            }),
            new KeyFrame(Duration.seconds(1), e -> {
                setY(originalY - 15);
            }),
            new KeyFrame(Duration.seconds(2), e -> {
                setY(originalY);
            })
        );
        floatAnimation.setCycleCount(Timeline.INDEFINITE);
        floatAnimation.play();
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
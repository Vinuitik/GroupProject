import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Ellipse;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.*;

/**
 * Bird obstacle class
 *
 * @author Dima, Daria, David
 * @version 1.0
 */
public class Bird extends Obstacle {
    private Timeline flapAnimation;
    private Timeline beakAnimation;
    private Timeline floatAnimation;
    
    private double altitude;

    
    private Polygon wing;
    private Polygon beak;
    private double originalY;
    
    
    
    /**
     * Constructor with custom position
     * @param startX Starting X position
     * @param startY Starting Y position
     */
    public Bird(double startX, double startY, double finalX, double score, List<Obstacle> obstacles) {
        super(score,finalX,obstacles);
        this.width = 20;
        this.height = 20;
        setX(startX);
        setY(startY);
        originalY=startY;
        initialize();
    }
    
    /**
     * Initialize the bird
     */
    @Override
    public void initialize() {
        buildBird();
        setupMoveAnimation();
    }
    
    @Override
    protected void setupMoveAnimation() {
        moveAnimation = new TranslateTransition(Duration.seconds(  nummerator / (score+futureOffset) + minTime  ), obstacleGroup);
        moveAnimation.setFromX(x);
        moveAnimation.setToX(finalX-x); // Move off screen
        moveAnimation.setCycleCount(1); // Stops after moving off screen
        
        setupFlapAnimation();
        setupFloatAnimation();
        
        moveAnimation.setOnFinished(event -> {
            if(flapAnimation!=null){
                flapAnimation.stop();
            }
            if(floatAnimation!=null){
                floatAnimation.stop();
            }
            if(beakAnimation!=null){
                beakAnimation.stop();
            }
            if (obstacleGroup.getParent() != null) {
                ((Pane) obstacleGroup.getParent()).getChildren().remove(obstacleGroup);
            }
            obstaclesList.remove(this);
            obstacleGroup = null; // Allow garbage collection
            flapAnimation = null;
            moveAnimation = null;
            beakAnimation = null;
            floatAnimation = null;
        });
        
        moveAnimation.play();
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
        beakAnimation = new Timeline(
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
    
    @Override
    public void stopAnimation(){
        if (moveAnimation != null) {
            moveAnimation.stop();
        }
        if (floatAnimation != null) {
            floatAnimation.stop();
        }
        if (beakAnimation != null) {
            beakAnimation.stop();
        }
        if (flapAnimation != null) {
            flapAnimation.stop();
        }
    }
    
}
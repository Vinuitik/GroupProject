import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.animation.KeyValue;
import javafx.animation.Interpolator;



public class Kitten extends Charachter {

    public enum State { RUNNING, JUMPING, CROUCHING, GAME_OVER }

    private Group kittenGroup;
    private State currentState;
    private Timeline runningAnimation;
    private Timeline jumpAnimation;
    
    // Components for kitten's body
    private Rectangle frontLeg;
    private Rectangle backLeg;
    private Group bodyGroup; // Group for everything except legs
    
    // Jump physics
    private double jumpHeight = 120;
    private double initialY;
    private boolean isJumping = false;
    
    private Rectangle hitbox;
    
    public Kitten(double x, double y) {
        kittenGroup = new Group();
        currentState = State.RUNNING;
        buildKitten();
        setupRunningAnimation();
        setupJumpAnimation();
        initialY = kittenGroup.getLayoutY();
        
        kittenGroup.setLayoutX(x);
        kittenGroup.setLayoutY(y);
    }
    
    // Creates a refined kitten design with smoother proportions and better visuals
    private void buildKitten() {
        bodyGroup = new Group();

        // More rounded kitten body with a bigger shape for cuteness
        Ellipse body = new Ellipse(30, 20, 40, 20);
        body.setFill(Color.SANDYBROWN);

        // Head with a more proportional design
        Circle head = new Circle(45, -10, 22);
        head.setFill(Color.SANDYBROWN);

        // Ears with better detail and tilt for a cute look
        Polygon earLeft = new Polygon(35.0, -25.0, 30.0, -5.0, 40.0, -5.0);
        earLeft.setFill(Color.SIENNA);
        Polygon earRight = new Polygon(55.0, -25.0, 50.0, -5.0, 60.0, -5.0);
        earRight.setFill(Color.SIENNA);
        
        // Eyes to make the kitten expressive
        Circle eyeLeft = new Circle(40, -10, 5);
        eyeLeft.setFill(Color.WHITE);
        Circle eyeRight = new Circle(50, -10, 5);
        eyeRight.setFill(Color.WHITE);
        
        // Pupils for a more emotional look
        Circle pupilLeft = new Circle(41, -10, 2);
        pupilLeft.setFill(Color.BLACK);
        Circle pupilRight = new Circle(51, -10, 2);
        pupilRight.setFill(Color.BLACK);
        
        // Nose and whiskers for added cuteness
        Polygon nose = new Polygon(45.0, 0.0, 42.0, 3.0, 48.0, 3.0);
        nose.setFill(Color.PINK);
        
        Rectangle whiskerLeft1 = new Rectangle(30, 0, 15, 1);
        Rectangle whiskerLeft2 = new Rectangle(30, 4, 15, 1);
        Rectangle whiskerRight1 = new Rectangle(50, 0, 15, 1);
        Rectangle whiskerRight2 = new Rectangle(50, 4, 15, 1);
        
        // Tail with a more dynamic look
        Ellipse tail = new Ellipse(-5, 15, 12, 7);
        tail.setFill(Color.SANDYBROWN);
        tail.setRotate(45);

        // Front and back legs with better animation possibilities
        frontLeg = new Rectangle(35, 30, 10, 20);
        frontLeg.setArcWidth(10);
        frontLeg.setArcHeight(10);
        frontLeg.setFill(Color.SANDYBROWN);

        backLeg = new Rectangle(15, 30, 10, 20);
        backLeg.setArcWidth(10);
        backLeg.setArcHeight(10);
        backLeg.setFill(Color.SANDYBROWN);

        // Add all the components to the body group
        bodyGroup.getChildren().addAll(body, head, earLeft, earRight, eyeLeft, eyeRight, pupilLeft, pupilRight, nose, whiskerLeft1, whiskerLeft2, whiskerRight1, whiskerRight2, tail);
        kittenGroup.getChildren().addAll(bodyGroup, frontLeg, backLeg);

        // Hitbox for collision detection
        hitbox = new Rectangle(0, -20, 50, 50);
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.TRANSPARENT);

        kittenGroup.setLayoutX(50);
        kittenGroup.setLayoutY(250);
    }
    
    // Setting up the smooth running animation with leg movement
    private void setupRunningAnimation() {
        runningAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                frontLeg.setRotate(15);
                backLeg.setRotate(-15);
            }),
            new KeyFrame(Duration.seconds(0.2), e -> {
                frontLeg.setRotate(-15);
                backLeg.setRotate(15);
            }),
            new KeyFrame(Duration.seconds(0.4), e -> {
                frontLeg.setRotate(15);
                backLeg.setRotate(-15);
            })
        );
        runningAnimation.setCycleCount(Timeline.INDEFINITE);
    }

    // Smoother jump animation using ease-in and ease-out for the vertical movement
    private void setupJumpAnimation() {
        KeyValue startValue = new KeyValue(kittenGroup.translateYProperty(), 0, Interpolator.LINEAR);
        KeyValue peakValue = new KeyValue(kittenGroup.translateYProperty(), -jumpHeight, Interpolator.LINEAR);
        KeyValue endValue = new KeyValue(kittenGroup.translateYProperty(), 0, Interpolator.LINEAR);
    
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, startValue);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1.4), peakValue);
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1.9), endValue);
    
        jumpAnimation = new Timeline(keyFrame1, keyFrame2, keyFrame3);
        jumpAnimation.setCycleCount(1);
    
        // Optionally, set an onFinished event to handle post-jump actions
        jumpAnimation.setOnFinished(e -> {
            isJumping = false;
            if (currentState == State.JUMPING) {
                setState(State.RUNNING);
            }
        });
    }





    // Performs the jump if the kitten is not already jumping
    public void jump() {
        if (!isJumping && currentState != State.GAME_OVER) {
            setState(State.JUMPING);
            jumpAnimation.playFromStart();
        }
    }

    // Switches to crouch state and modifies the kitten's posture
    public void crouch() {
        if (!isJumping && currentState != State.GAME_OVER) {
            setState(State.CROUCHING);
            bodyGroup.setScaleY(0.5);
            bodyGroup.setLayoutY(15);
            frontLeg.setScaleY(0.5);
            frontLeg.setLayoutY(15);
            backLeg.setScaleY(0.5);
            backLeg.setLayoutY(15);
            hitbox.setHeight(25);
            hitbox.setY(-10);
        }
    }

    // Returns the kitten to standing position
    public void standUp() {
        if (currentState == State.CROUCHING) {
            setState(State.RUNNING);
            bodyGroup.setScaleY(1);
            bodyGroup.setLayoutY(0);
            frontLeg.setScaleY(1);
            frontLeg.setLayoutY(0);
            backLeg.setScaleY(1);
            backLeg.setLayoutY(0);
            hitbox.setHeight(50);
            hitbox.setY(-20);
        }
    }

    // Sets the kitten's state
    public void setState(State newState) {
        if (currentState == newState) return;

        State previousState = currentState;
        currentState = newState;

        switch (newState) {
            case RUNNING:
                runningAnimation.play();
                break;
            case JUMPING:
                runningAnimation.stop();
                break;
            case CROUCHING:
                runningAnimation.stop();
                break;
            case GAME_OVER:
                runningAnimation.stop();
                jumpAnimation.stop();
                bodyGroup.setRotate(90);  // Kitten falls down in game over
                frontLeg.setRotate(0);
                backLeg.setRotate(0);
                break;
        }
    }

    public State getState() {
        return currentState;
    }

    // Returns the kitten as a Group for rendering
    public Group getNode() {
        return kittenGroup;
    }

    // Returns the hitbox for collision detection
    public Rectangle getHitbox() {
        Rectangle actualHitbox = new Rectangle(
            kittenGroup.getLayoutX() + hitbox.getX(),
            kittenGroup.getLayoutY() + hitbox.getY(),
            hitbox.getWidth(),
            hitbox.getHeight()
        );
        return actualHitbox;
    }
}

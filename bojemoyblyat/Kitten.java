
/**
 * Write a description of class Dinosaur here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

// Класс котёнка наследуется от базового класса персонажей (Charachter)
public class Kitten extends Charachter {
    
    // Перечисление состояний котёнка
    public enum State { RUNNING, JUMPING, CROUCHING, GAME_OVER }
    
    private Group kittenGroup;
    private State currentState;
    private Timeline runningAnimation;
    
    // Для простоты создаем два прямоугольника как "лапки", которые будем анимировать
    private Rectangle frontLeg;
    private Rectangle backLeg;
    
    public Kitten() {
        kittenGroup = new Group();
        currentState = State.RUNNING;
        buildKitten();
        setupRunningAnimation();
    }
    
    // Метод отрисовки котёнка с нужными цветами:
    // - Тело и голова – светло-коричневые (SANDYBROWN, подходит для рыжего оттенка)
    // - Ушки – темно-коричневые (BROWN)
    // - Глаза – черные
    private void buildKitten() {
        // Тело котёнка
        Rectangle body = new Rectangle(0, 0, 50, 30);
        body.setArcWidth(20);
        body.setArcHeight(20);
        body.setFill(Color.SANDYBROWN);
        
        // Голова котёнка
        Circle head = new Circle(25, -15, 15);
        head.setFill(Color.SANDYBROWN);
        
        // Ушки – два треугольника
        Polygon earLeft = new Polygon(
            15.0, -30.0,
            10.0, -15.0,
            20.0, -15.0
        );
        earLeft.setFill(Color.BROWN);
        
        Polygon earRight = new Polygon(
            35.0, -30.0,
            30.0, -15.0,
            40.0, -15.0
        );
        earRight.setFill(Color.BROWN);
        
        // Глаза – маленькие черные круги
        Circle eyeLeft = new Circle(20, -17, 2);
        eyeLeft.setFill(Color.BLACK);
        Circle eyeRight = new Circle(30, -17, 2);
        eyeRight.setFill(Color.BLACK);
        
        // Лапки, которые будем анимировать:
        frontLeg = new Rectangle(10, 30, 5, 15);
        frontLeg.setFill(Color.SANDYBROWN);
        backLeg = new Rectangle(35, 30, 5, 15);
        backLeg.setFill(Color.SANDYBROWN);
        
        kittenGroup.getChildren().addAll(body, head, earLeft, earRight, eyeLeft, eyeRight, frontLeg, backLeg);
    }
    
    // Настройка анимации бега: с помощью Timeline изменяем угол наклона лапок, чтобы имитировать шаг
    private void setupRunningAnimation() {
        runningAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                frontLeg.setRotate(0);
                backLeg.setRotate(0);
            }),
            new KeyFrame(Duration.seconds(0.2), e -> {
                frontLeg.setRotate(15);
                backLeg.setRotate(-15);
            }),
            new KeyFrame(Duration.seconds(0.4), e -> {
                frontLeg.setRotate(0);
                backLeg.setRotate(0);
            })
        );
        runningAnimation.setCycleCount(Timeline.INDEFINITE);
        runningAnimation.play();
    }
    
    // Метод для получения узла (Group), который можно добавить на сцену
    public Group getNode() {
        return kittenGroup;
    }
    
    // Метод для переключения состояний котёнка
    public void setState(State newState) {
        if (currentState == newState) return;
        currentState = newState;
        
        // Если не в режиме бега – останавливаем анимацию
        if (currentState != State.RUNNING) {
            runningAnimation.stop();
        } else {
            runningAnimation.play();
        }
        // Здесь можно добавить дополнительные анимации для прыжка, пригибания или состояния проигрыша
    }
}
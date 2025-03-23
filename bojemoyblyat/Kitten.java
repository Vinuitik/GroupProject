import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/**
 * Класс Kitten представляет игрового персонажа-котенка.
 * Котенок может бегать, прыгать и пригибаться.
 */
public class Kitten extends Character {
    
    // Перечисление состояний котёнка
    public enum State { RUNNING, JUMPING, CROUCHING, GAME_OVER }
    
    private Group kittenGroup;
    private State currentState;
    private Timeline runningAnimation;
    private Timeline jumpAnimation;
    
    // Компоненты тела котенка для анимации
    private Rectangle frontLeg;
    private Rectangle backLeg;
    private Group bodyGroup; // Группа для всего тела кроме ног
    
    // Физические параметры для прыжка
    private double jumpHeight = 100;
    private double initialY;
    private double gravity = 9.8;
    private boolean isJumping = false;
    
    // Для обнаружения столкновений
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
    
    /**
     * Создает дизайн котенка со всеми деталями
     */
    private void buildKitten() {
        bodyGroup = new Group();
        
        // Тело котёнка - более округлое, используем эллипс
        Ellipse body = new Ellipse(25, 15, 30, 15);
        body.setFill(Color.SANDYBROWN);
        
        // Голова котёнка
        Circle head = new Circle(40, -5, 20);
        head.setFill(Color.SANDYBROWN);
        
        // Ушки - более детализированные
        Polygon earLeft = new Polygon(
            30.0, -25.0,
            25.0, -5.0,
            35.0, -5.0
        );
        earLeft.setFill(Color.SIENNA);
        
        Polygon earRight = new Polygon(
            50.0, -25.0,
            45.0, -5.0,
            55.0, -5.0
        );
        earRight.setFill(Color.SIENNA);
        
        // Внутренние части ушей
        Polygon earLeftInner = new Polygon(
            30.0, -22.0,
            27.0, -8.0,
            33.0, -8.0
        );
        earLeftInner.setFill(Color.ROSYBROWN);
        
        Polygon earRightInner = new Polygon(
            50.0, -22.0,
            47.0, -8.0,
            53.0, -8.0
        );
        earRightInner.setFill(Color.ROSYBROWN);
        
        // Глаза - большие выразительные
        Circle eyeLeft = new Circle(35, -5, 4);
        eyeLeft.setFill(Color.WHITE);
        Circle eyeRight = new Circle(45, -5, 4);
        eyeRight.setFill(Color.WHITE);
        
        // Зрачки
        Circle pupilLeft = new Circle(36, -5, 2);
        pupilLeft.setFill(Color.BLACK);
        Circle pupilRight = new Circle(46, -5, 2);
        pupilRight.setFill(Color.BLACK);
        
        // Нос
        Polygon nose = new Polygon(
            40.0, 0.0,
            37.0, 3.0,
            43.0, 3.0
        );
        nose.setFill(Color.PINK);
        
        // Усы
        Rectangle whiskerLeft1 = new Rectangle(20, 0, 15, 1);
        Rectangle whiskerLeft2 = new Rectangle(20, 3, 15, 1);
        Rectangle whiskerRight1 = new Rectangle(45, 0, 15, 1);
        Rectangle whiskerRight2 = new Rectangle(45, 3, 15, 1);
        
        // Хвост
        Ellipse tail = new Ellipse(-5, 10, 10, 5);
        tail.setFill(Color.SANDYBROWN);
        tail.setRotate(45);
        
        // Лапки
        frontLeg = new Rectangle(35, 30, 8, 20);
        frontLeg.setArcWidth(8);
        frontLeg.setArcHeight(8);
        frontLeg.setFill(Color.SANDYBROWN);
        
        backLeg = new Rectangle(5, 30, 8, 20);
        backLeg.setArcWidth(8);
        backLeg.setArcHeight(8);
        backLeg.setFill(Color.SANDYBROWN);
        
        // Добавляем все элементы в группу тела
        bodyGroup.getChildren().addAll(
            tail, body, head, 
            earLeft, earRight, earLeftInner, earRightInner,
            eyeLeft, eyeRight, pupilLeft, pupilRight,
            nose, whiskerLeft1, whiskerLeft2, whiskerRight1, whiskerRight2
        );
        
        // Добавляем тело и лапки в основную группу
        kittenGroup.getChildren().addAll(bodyGroup, frontLeg, backLeg);
        
        // Создаем хитбокс для обнаружения столкновений
        hitbox = new Rectangle(0, -20, 50, 50);
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.TRANSPARENT);
        
        // Настраиваем начальное положение
        kittenGroup.setLayoutX(50);
        kittenGroup.setLayoutY(250);
    }
    
    /**
     * Настраивает анимацию бега
     */
    private void setupRunningAnimation() {
        runningAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                frontLeg.setRotate(15);
                backLeg.setRotate(-15);
            }),
            new KeyFrame(Duration.seconds(0.15), e -> {
                frontLeg.setRotate(-15);
                backLeg.setRotate(15);
            }),
            new KeyFrame(Duration.seconds(0.3), e -> {
                frontLeg.setRotate(15);
                backLeg.setRotate(-15);
            })
        );
        runningAnimation.setCycleCount(Timeline.INDEFINITE);
    }
    
    /**
     * Настраивает анимацию прыжка
     */
    private void setupJumpAnimation() {
        jumpAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                isJumping = true;
                frontLeg.setRotate(0);
                backLeg.setRotate(0);
            }),
            new KeyFrame(Duration.seconds(0.5), e -> {
                kittenGroup.setLayoutY(initialY - jumpHeight);
            }),
            new KeyFrame(Duration.seconds(1.0), e -> {
                kittenGroup.setLayoutY(initialY);
                isJumping = false;
                if (currentState == State.JUMPING) {
                    setState(State.RUNNING);
                }
            })
        );
        jumpAnimation.setCycleCount(1);
    }
    
    /**
     * Выполняет прыжок, если котенок не в прыжке
     */
    public void jump() {
        if (!isJumping && currentState != State.GAME_OVER) {
            setState(State.JUMPING);
            jumpAnimation.playFromStart();
        }
    }
    
    /**
     * Переключает котенка в режим пригибания
     */
    public void crouch() {
        if (!isJumping && currentState != State.GAME_OVER) {
            setState(State.CROUCHING);
            // Изменяем положение тела для пригибания
            bodyGroup.setScaleY(0.5);
            bodyGroup.setLayoutY(15);
            frontLeg.setScaleY(0.5);
            frontLeg.setLayoutY(15);
            backLeg.setScaleY(0.5);
            backLeg.setLayoutY(15);
            // Обновляем хитбокс
            hitbox.setHeight(25);
            hitbox.setY(-10);
        }
    }
    
    /**
     * Возвращает котенка в нормальное положение после пригибания
     */
    public void standUp() {
        if (currentState == State.CROUCHING) {
            setState(State.RUNNING);
            // Восстанавливаем нормальное положение
            bodyGroup.setScaleY(1);
            bodyGroup.setLayoutY(0);
            frontLeg.setScaleY(1);
            frontLeg.setLayoutY(0);
            backLeg.setScaleY(1);
            backLeg.setLayoutY(0);
            // Обновляем хитбокс
            hitbox.setHeight(50);
            hitbox.setY(-20);
        }
    }
    
    /**
     * Переключает состояние котенка
     */
    public void setState(State newState) {
        if (currentState == newState) return;
        
        // Сохраняем предыдущее состояние для возврата при необходимости
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
                // Анимация проигрыша - котенок падает
                bodyGroup.setRotate(90);
                frontLeg.setRotate(0);
                backLeg.setRotate(0);
                break;
        }
    }
    
    /**
     * Возвращает текущее состояние котенка
     */
    public State getState() {
        return currentState;
    }
    
    /**
     * Возвращает Node для добавления на сцену
     */
    public Group getNode() {
        return kittenGroup;
    }
    
    /**
     * Возвращает хитбокс для обнаружения столкновений
     */
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
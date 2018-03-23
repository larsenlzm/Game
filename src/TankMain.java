


// Må endre en del av koden så den ikke er lik som forskjellige tutorials på nett ofc, men det fikser vi.


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class TankMain extends Application {

    private Pane root;

    private List<GameObjects> bullets = new ArrayList<>();


    private GameObjects player;
    private GameObjects enemy;

    public BitSet keyboardBitSet = new BitSet();

    private void addInputControls(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyboardBitSet.set(event.getCode().ordinal(), true);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            keyboardBitSet.set(event.getCode().ordinal(), false);
        });
    }

    private void addBullet(GameObjects bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet,x,y);
    }

    private Parent createContent() {

        root = new Pane();
        root.setPrefSize(600,600);

        player = new Player();
        player.setVelocity(new Point2D(1,0));
        addGameObject(player,300,300);

        enemy = new Enemy();
        enemy.setVelocity(new Point2D(1,0));
        addGameObject(enemy,100,300);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addGameObject(GameObjects object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private static class Player extends GameObjects {
        Player() {
            super(new ImageView("/tank1.png"));
        }
    }

    private static class Enemy extends GameObjects {
        Enemy() {
            super(new ImageView("/tank2.png"));
        }
    }

    private static class Bullet extends GameObjects {
        Bullet() {
            super(new Circle(5,5,5,Color.BROWN));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(new Scene(createContent()));

        addInputControls(stage.getScene());


        stage.show();

    }

    private void onUpdate() {


        boolean isAPressed = keyboardBitSet.get(KeyCode.A.ordinal());
        boolean isDPressed = keyboardBitSet.get(KeyCode.D.ordinal());
        boolean isLeftPressed = keyboardBitSet.get(KeyCode.LEFT.ordinal());
        boolean isRightPressed = keyboardBitSet.get(KeyCode.RIGHT.ordinal());
        boolean isSpacePressed = keyboardBitSet.get(KeyCode.SPACE.ordinal());

            if (isLeftPressed && !isRightPressed) {
                player.rotateLeft();
            }

            else if ( !isLeftPressed && isRightPressed) {
                player.rotateRight();
            }

            if ( isAPressed && !isDPressed) {
                enemy.rotateLeft();
            }

            else if ( !isAPressed && isDPressed) {
                enemy.rotateRight();
            }

            if ( isSpacePressed) {
                Bullet bullet = new Bullet();
                Bullet bullet2 = new Bullet();
                // Setter bullet velocity til 5 ganger så mye som player
                bullet.setVelocity(player.getVelocity().normalize().multiply(5));
                bullet2.setVelocity(enemy.getVelocity().normalize().multiply(5));
                //Adder bulleten til gameworld og posisjonen er da samme som player
                addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());


                // Denne ødelegger for enemy-objektet
                addBullet(bullet2, enemy.getView().getTranslateX(), enemy.getView().getTranslateY());


            }

        for (GameObjects bullet : bullets) {
            if (bullet.isColliding(enemy)) {
                bullet.setAlive(false);
                enemy.setAlive(false);

                //remover bullet og enemy når de blir hit av bullet

            }

        }

        bullets.removeIf(GameObjects::isDead);

        bullets.forEach(GameObjects::update);

        player.update();
        enemy.update();
        //Slett denne for å fjerne Røde dotter

    }

    public static void main(String[] args) {
        launch(args);
    }
}

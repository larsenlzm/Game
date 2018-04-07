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
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class TankMain extends Application {

    private Pane root;

    private List<GameObjects> bullets = new ArrayList<>();
    private List<GameObjects> bullets2 = new ArrayList<>();

    private GameObjects player;
    private GameObjects enemy;
    private GameObjects wall1;
    private GameObjects wall2;
    private GameObjects wall3;
    private GameObjects wall4;

    double pistollader = 10; //skudd per antall frames
    double pistolladerteller = pistollader;
    double pistolladertellerDelta = 1;

    double playerHP = 5; //spiller liv
    double enemyHP = 5; //spiller2 liv

    double scenewidth = 600;
    double sceneheigth = 600;

    public BitSet keyboardBitSet = new BitSet();

    private void addInputControls(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyboardBitSet.set(event.getCode().ordinal(), true);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            keyboardBitSet.set(event.getCode().ordinal(), false);
        });
    }

    private Parent createContent() {

        root = new Pane();
        root.setPrefSize(scenewidth,sceneheigth);

        player = new Player();
        player.setVelocity(new Point2D(1,0));
        addGameObject(player,100,100);

        enemy = new Enemy();
        enemy.setVelocity(new Point2D(1,0));
        addGameObject(enemy,100,500);

        wall1 = new Wall();
        addGameObject(wall1,150,100);

        wall2 = new Wall();
        addGameObject(wall2,425,100);

        wall3 = new Wall();
        addGameObject(wall3,150,375);

        wall4 = new Wall();
        addGameObject(wall4,425,375);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };

        timer.start();

        return root;
    }

    private void addBullet(GameObjects bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet,x,y);
    }
    private void addBullet2(GameObjects bullet, double x, double y) {
        bullets2.add(bullet);
        addGameObject(bullet,x,y);
    }

    private void addGameObject(GameObjects object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }


    private static class Bullet extends GameObjects {
        Bullet() {
            super(new Circle(5,5,5,Color.BROWN));
        }
    }

    private static class Wall extends GameObjects {
        Wall() {
            super(new Rectangle(25,100,Color.ORANGE));
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
        boolean isWPressed = keyboardBitSet.get(KeyCode.W.ordinal());
        boolean isLeftPressed = keyboardBitSet.get(KeyCode.LEFT.ordinal());
        boolean isRightPressed = keyboardBitSet.get(KeyCode.RIGHT.ordinal());
        boolean isUpPressed = keyboardBitSet.get(KeyCode.UP.ordinal());

        double maxX = scenewidth - (player.getView().getLayoutBounds().getHeight() / 2);
        double minX = 0 - (player.getView().getLayoutBounds().getHeight() / 2);
        double maxY = sceneheigth -(player.getView().getLayoutBounds().getHeight() / 2);
        double minY = 0 - (player.getView().getLayoutBounds().getHeight() / 2);

        double playerX = player.getView().getTranslateX();
        double playerY = player.getView().getTranslateY();
        double enemyX = enemy.getView().getTranslateX();
        double enemyY = enemy.getView().getTranslateY();

        pistolladerteller += pistolladertellerDelta;
        if( pistolladerteller > pistollader) {
            pistolladerteller = pistollader;
        }

        boolean isPistolLadet = pistolladerteller >= pistollader;

        if (isUpPressed && isPistolLadet) {
            Bullet bullet = new Bullet();

            // Setter bullet velocity til 5 ganger så mye som player
            bullet.setVelocity(new Point2D(Math.cos(Math.toRadians(player.getRotate()))*5,Math.sin(Math.toRadians(player.getRotate()))*5));

            //Adder bulleten til gameworld og posisjonen er da samme som player
            addBullet(bullet, playerX, playerY);
            pistolladerteller = 0;
        }
        if (isWPressed && isPistolLadet) {
            Bullet bullet2 = new Bullet();

            // Setter bullet velocity til 5 ganger så mye som enemy
            bullet2.setVelocity(new Point2D(Math.cos(Math.toRadians(enemy.getRotate()))*5,Math.sin(Math.toRadians(enemy.getRotate()))*5));

            //Adder bulleten til gameworld og posisjonen er da samme som enemy
            addBullet2(bullet2, enemyX, enemyY);
            pistolladerteller = 0;
        }

        if (isLeftPressed && !isRightPressed) {
            player.rotateLeft();
        } else if ( !isLeftPressed && isRightPressed) {
            player.rotateRight();
        }

        if ( isAPressed && !isDPressed) {
            enemy.rotateLeft();
        } else if ( !isAPressed && isDPressed) {
            enemy.rotateRight();
        }
        //behandler kulekollisjon
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(enemy)) {
                root.getChildren().remove(bullets.get(i).getView());
                bullets.remove(i);
                if (enemyHP != 0) {
                    enemyHP = enemyHP - 1;
                } else {
                    enemyHP = 5;
                    enemy.getView().setTranslateX(100);
                    enemy.getView().setTranslateY(500);
                }
            } //sjekker om kulene treffer veggene
            else if(bullets.get(i).isColliding(wall1)) {
                root.getChildren().remove(bullets.get(i).getView());
            } else if(bullets.get(i).isColliding(wall2)) {
                root.getChildren().remove(bullets.get(i).getView());
            } else if(bullets.get(i).isColliding(wall3)) {
                root.getChildren().remove(bullets.get(i).getView());
            } else if(bullets.get(i).isColliding(wall4)) {
                root.getChildren().remove(bullets.get(i).getView());
            } else if (bullets.get(i).getView().getTranslateY() <= minY  || bullets.get(i).getView().getTranslateY() >= maxY) {
                root.getChildren().remove(bullets.get(i).getView());
            } else if (bullets.get(i).getView().getTranslateX() <= minX  || bullets.get(i).getView().getTranslateX() >= maxX) {
                root.getChildren().remove(bullets.get(i).getView());
            }
        }
        //behandler kulekollisjon
        for (int i = 0; i < bullets2.size(); i++){
            if(bullets2.get(i).isColliding(player)) {
                root.getChildren().remove(bullets2.get(i).getView());
                bullets2.remove(i);
                if(playerHP != 0){
                    playerHP = playerHP - 1;
                } else {
                    playerHP = 5;
                    player.getView().setTranslateX(100);
                    player.getView().setTranslateY(100);
                }
            } //sjekker om kulene treffer veggene
            else if(bullets2.get(i).isColliding(wall1)) {
                root.getChildren().remove(bullets2.get(i).getView());
            } else if(bullets2.get(i).isColliding(wall2)) {
                root.getChildren().remove(bullets2.get(i).getView());
            } else if(bullets2.get(i).isColliding(wall3)) {
                root.getChildren().remove(bullets2.get(i).getView());
            } else if(bullets2.get(i).isColliding(wall4)) {
                root.getChildren().remove(bullets2.get(i).getView());
            } else if (bullets2.get(i).getView().getTranslateY() <= minY  || bullets2.get(i).getView().getTranslateY() >= maxY) {
                root.getChildren().remove(bullets2.get(i).getView());
            } else if (bullets2.get(i).getView().getTranslateX() <= minX  || bullets2.get(i).getView().getTranslateX() >= maxX) {
                root.getChildren().remove(bullets2.get(i).getView());
            }
        }

        //hitbox for vegg1 for spiller 1
        if(playerX >= 110 && playerX <= 111 && playerY >= 60 && playerY <= 200){
            player.getView().setTranslateX(110);
        } else if(playerX >= 175 && playerX <= 176 && playerY >= 60 && playerY <= 200){
            player.getView().setTranslateX(176);
        } else if(playerX >= 110 && playerX <= 176 && playerY >= 60 && playerY <= 61){
            player.getView().setTranslateY(60);
        } else if(playerX >= 110 && playerX <= 176 && playerY >= 199 && playerY <= 200){
            player.getView().setTranslateY(200);
        }
        if(enemyX >= 110 && enemyX <= 111 && enemyY >= 60 && enemyY <= 200){
            enemy.getView().setTranslateX(110);
        } else if(enemyX >= 175 && enemyX <= 176 && enemyY >= 60 && enemyY <= 200){
            enemy.getView().setTranslateX(176);
        } else if(enemyX >= 110 && enemyX <= 176 && enemyY >= 60 && enemyY <= 61){
            enemy.getView().setTranslateY(60);
        } else if(enemyX >= 110 && enemyX <= 176 && enemyY >= 199 && enemyY <= 200){
            enemy.getView().setTranslateY(200);
        }

        // går for langt til høyre eller venstre så kommer du ut på andre siden
        if(playerX >= maxX) {
            player.getView().setTranslateX(minX);
        } else if (playerX <= minX) {
            player.getView().setTranslateX(maxX);
        }
        // går for langt opp eller ned så kommer du ut på andre siden
        if(playerY >= maxY) {
            player.getView().setTranslateY(minY);
        } else if (playerY <= minY) {
            player.getView().setTranslateY(maxY);
        }

        // går for langt til høyre eller venstre så kommer du ut på andre siden
        if(enemyX >= maxX) {
            enemy.getView().setTranslateX(minX);
        } else if (enemyX <= minX) {
            enemy.getView().setTranslateX(maxX);
        }
        // går for langt opp eller ned så kommer du ut på andre siden
        if(enemyY >= maxY) {
            enemy.getView().setTranslateY(minY);
        } else if (enemyY <= minY) {
            enemy.getView().setTranslateY(maxY);
        }

        //oppdaterer shiten
        bullets.forEach(GameObjects::update);
        bullets2.forEach(GameObjects::update);

        player.update();
        enemy.update();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

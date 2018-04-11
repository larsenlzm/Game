import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class TankMain extends Application {

    private Pane root;
    private Pane overLayer;
    private Pane pauseLayer;
    private Label hpLabel;
    private Label hpLabel2;
    private Label score;
    private Label finishLabel;
    private Button resume;
    private Button save;
    private Button load;

    private AnimationTimer timer;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bullets2 = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private Player player;
    private Player enemy;

    private boolean isPaused = true;

    private double lader = 10; //skudd per antall frames
    private double laderTeller = lader;
    private double laderTellerDelta = 1;
    private int scoreP = 0;
    private int scoreE = 0;

    private double scenewidth = 600;
    private double sceneheigth = 600;

    public BitSet keyboardBitSet = new BitSet();

    private void addInputControls(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyboardBitSet.set(event.getCode().ordinal(), true);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            keyboardBitSet.set(event.getCode().ordinal(), false);
        });
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Parent root = FXMLLoader.load(getClass().getResource("fuxml.fxml"));

        //Scene scene = new Scene(root, scenewidth, sceneheigth);

        stage.setScene(new Scene(createContent()));

        addInputControls(stage.getScene());

        stage.show();
    }

    private Parent createContent() {

        root = new Pane();
        overLayer = new Pane();
        pauseLayer = new Pane();

        root.setPrefSize(scenewidth,sceneheigth);

        player = new Player("tank1.png", 10,3, 50,50, root);
        player.setVelocity(new Point2D(0,0));

        enemy = new Player("tank2.png", 10,3, 500,500, root);
        enemy.setVelocity(new Point2D(0,0));
        enemy.getView().setRotate(180);

        Wall vegg = new Wall(25,100,Color.ORANGE,125,100, root);
        walls.add(vegg);
        Wall vegg2 = new Wall(25,100,Color.ORANGE,450,100, root);
        walls.add(vegg2);
        Wall vegg3 = new Wall(25,100,Color.ORANGE,125,375, root);
        walls.add(vegg3);
        Wall vegg4 = new Wall(25,100,Color.ORANGE,450,375, root);
        walls.add(vegg4);
        Wall vegg5 = new Wall(75,25,Color.ORANGE,150,100, root);
        walls.add(vegg5);
        Wall vegg6 = new Wall(75,25,Color.ORANGE,375,100, root);
        walls.add(vegg6);
        Wall vegg7 = new Wall(75,25,Color.ORANGE,150,450, root);
        walls.add(vegg7);
        Wall vegg8 = new Wall(75,25,Color.ORANGE,375,450, root);
        walls.add(vegg8);
        Wall vegg9 = new Wall(25,25,Color.ORANGE,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, root);
        walls.add(vegg9);

        root.getChildren().add(overLayer);
        root.getChildren().add(pauseLayer);

        hpLabel = new Label();
        hpLabel.setTextFill(Color.BLACK);
        overLayer.getChildren().add(hpLabel);

        hpLabel2 = new Label();
        hpLabel2.setTextFill(Color.BLACK);
        overLayer.getChildren().add(hpLabel2);

        finishLabel = new Label();
        finishLabel.setTextFill(Color.BLACK);
        overLayer.getChildren().add(finishLabel);

        resume = new Button("RESUME");
        resume.setTextFill(Color.BLACK);
        pauseLayer.getChildren().add(resume);

        save = new Button("SAVE");
        save.setTextFill(Color.BLACK);
        pauseLayer.getChildren().add(save);

        load = new Button("LOAD");
        resume.setTextFill(Color.BLACK);
        pauseLayer.getChildren().add(load);

        pauseLayer.setVisible(false);

        score = new Label();
        score.setTextFill(Color.BLACK);
        overLayer.getChildren().add(score);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };

        timer.start();

        return root;
    }

    private void onUpdate() {

        boolean isWPressed = keyboardBitSet.get(KeyCode.W.ordinal());
        boolean isSPressed = keyboardBitSet.get(KeyCode.S.ordinal());
        boolean isAPressed = keyboardBitSet.get(KeyCode.A.ordinal());
        boolean isDPressed = keyboardBitSet.get(KeyCode.D.ordinal());
        boolean isVPressed = keyboardBitSet.get(KeyCode.V.ordinal());
        boolean isUpPressed = keyboardBitSet.get(KeyCode.UP.ordinal());
        boolean isDownPressed = keyboardBitSet.get(KeyCode.DOWN.ordinal());
        boolean isLeftPressed = keyboardBitSet.get(KeyCode.LEFT.ordinal());
        boolean isRightPressed = keyboardBitSet.get(KeyCode.RIGHT.ordinal());
        boolean isPeriodPressed = keyboardBitSet.get(KeyCode.PERIOD.ordinal());
        boolean isSpacePressed = keyboardBitSet.get(KeyCode.SPACE.ordinal());

        double maxX = scenewidth - (player.getWidth() / 2);
        double minX = 0 - (player.getWidth() / 2);
        double maxY = sceneheigth -(player.getHeigth() / 2);
        double minY = 0 - (player.getHeigth() / 2);

        laderTeller += laderTellerDelta;
        if( laderTeller > lader) {
            laderTeller = lader;
        }
        boolean isPistolLadet = laderTeller >= lader;

        //Pause
        if (isSpacePressed){
            timer.stop();
            //gi muligheter får å resume, save, load, exit
            pauseLayer.setVisible(true);
            resume.setOnAction(e -> {
                timer.start();
                pauseLayer.setVisible(false);
            });
            save.setOnAction(e -> {
                System.out.println("SAVING");
            });
            load.setOnAction(e -> {
                System.out.println("LOADING SAVE");
            });
        }

        if (isPeriodPressed && isPistolLadet) {
            Bullet bullet = new Bullet();
            // Setter bullet velocity til 5 ganger så mye som player
            bullet.setVelocity(new Point2D(Math.cos(Math.toRadians(player.getRotate()))*5,Math.sin(Math.toRadians(player.getRotate()))*5));
            //Adder bulleten til gameworld og posisjonen er da samme som player
            bullets.add(bullet);
            bullet.addBullet(player.getX(),player.getY(),root);
            //resetter pistolklokka
            laderTeller = 0;
        }

        if (isVPressed && isPistolLadet) {
            Bullet bullet2 = new Bullet();
            // Setter bullet velocity til 5 ganger så mye som enemy
            bullet2.setVelocity(new Point2D(Math.cos(Math.toRadians(enemy.getRotate()))*5,Math.sin(Math.toRadians(enemy.getRotate()))*5));
            //Adder bulleten til gameworld og posisjonen er da samme som enemy
            bullets2.add(bullet2);
            bullet2.addBullet(enemy.getX(),enemy.getY(), root);
            //resetter pistolklokka
            laderTeller = 0;
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
        if(isWPressed){
            enemy.setVelocity(new Point2D(Math.cos(Math.toRadians(enemy.getRotate())), Math.sin(Math.toRadians(enemy.getRotate()))));
        }else{
            enemy.setVelocity(new Point2D(0,0));
        }

        if(isUpPressed){
            player.setVelocity(new Point2D(Math.cos(Math.toRadians(player.getView().getRotate())), Math.sin(Math.toRadians(player.getView().getRotate()))));
        } else  if(isDownPressed){
            player.setVelocity(new Point2D(-Math.cos(Math.toRadians(player.getView().getRotate())), -Math.sin(Math.toRadians(player.getView().getRotate()))));
        }else{
            player.setVelocity(new Point2D(0,0));
        }

        if(isWPressed){
            enemy.setVelocity(new Point2D(Math.cos(Math.toRadians(enemy.getRotate())), Math.sin(Math.toRadians(enemy.getRotate()))));
        } else if(isSPressed){
            enemy.setVelocity(new Point2D(-Math.cos(Math.toRadians(enemy.getView().getRotate())), -Math.sin(Math.toRadians(enemy.getView().getRotate()))));
        }else{
            enemy.setVelocity(new Point2D(0,0));
        }
        //behandler kulekollisjon med person og utkant
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(enemy)) {
                bullets.get(i).RemoveBullet(root);
                bullets.remove(i);
                if (enemy.getHp() != 1) {
                    enemy.setHp(enemy.getHp() - 1);
                } else if(enemy.getLifePoints() != 1){
                    enemy.setHp(10);
                    enemy.getView().setTranslateX(500);// flytter spiller2 til spawn
                    enemy.getView().setTranslateY(500);
                    player.getView().setTranslateX(50);//flytter spiller til spawn
                    player.getView().setTranslateY(50);
                    enemy.setLifePoints(enemy.getLifePoints() - 1);
                    scoreP++;
                } else {
                    scoreP++;
                    root.getChildren().remove(enemy.getView());
                    enemy.setHp(enemy.getHp() - 1);
                    enemy.setLifePoints(enemy.getLifePoints() - 1);
                    finishLabel.setText("PLAYER 1 WON!");
                }
            } //sjekker om kulene treffer utkant av kartet
            else if (bullets.get(i).getView().getTranslateY() <= 0  || bullets.get(i).getView().getTranslateY() >= sceneheigth+25) {
                bullets.get(i).RemoveBullet(root);
                bullets.remove(i);
            } else if (bullets.get(i).getView().getTranslateX() <= 0  || bullets.get(i).getView().getTranslateX() >= scenewidth+25) {
                bullets.get(i).RemoveBullet(root);
                bullets.remove(i);
            } else {
                for(int j = 0; j < walls.size(); j++) {
                    if (bullets.get(i).isColliding(walls.get(j))){
                        bullets.get(i).RemoveBullet(root);
                        bullets.remove(i);
                    }
                }
            }
        }
        //behandler kulekollisjon med person og utkant
        for (int i = 0; i < bullets2.size(); i++){
            if(bullets2.get(i).isColliding(player)) {
                bullets2.get(i).RemoveBullet(root);
                bullets2.remove(i);
                if(player.getHp() != 1){
                    player.setHp(player.getHp() - 1);
                } else if(player.getLifePoints() != 1) {
                    player.setHp(10);
                    player.getView().setTranslateX(50); //flytter spiller tilbake til spawn
                    player.getView().setTranslateY(50);
                    enemy.getView().setTranslateX(500); //flytter spiller2 tilbake til spawn
                    enemy.getView().setTranslateY(500);
                    player.setLifePoints(player.getLifePoints() - 1);
                    scoreE++;
                } else {
                    scoreE++;
                    root.getChildren().remove(player.getView());
                    player.setHp(player.getHp() - 1);
                    player.setLifePoints(player.getLifePoints() - 1);
                    finishLabel.setText("PLAYER 2 WON!");
                }
            } //sjekker om kulene treffer utkant av kartet
            else if (bullets2.get(i).getView().getTranslateY() <= 0  || bullets2.get(i).getView().getTranslateY() >= sceneheigth+25) {
                bullets2.get(i).RemoveBullet(root);
                bullets2.remove(i);
            } else if (bullets2.get(i).getView().getTranslateX() <= 0  || bullets2.get(i).getView().getTranslateX() >= scenewidth+25) {
                bullets2.get(i).RemoveBullet(root);
                bullets2.remove(i);
            } else {
                for(int j = 0; j < walls.size(); j++) {
                    if (bullets2.get(i).isColliding(walls.get(j))){
                        bullets2.get(i).RemoveBullet(root);
                        bullets2.remove(i);
                    }
                }
            }
        }
        //kollisjon med veggene spiller 1
        for(int i = 0; i<walls.size(); i++) {
            if (player.getX() >= walls.get(i).getMinX() - player.getWidth() && player.getX() <= walls.get(i).getMinX() - player.getWidth() + 1 && player.getY() >= walls.get(i).getMinY() - player.getWidth() && player.getY() <= walls.get(i).getMaxY()) {
                player.getView().setTranslateX(walls.get(i).getMinX() - player.getWidth());
            } else if (player.getX() >= walls.get(i).getMaxX() - 1 && player.getX() <= walls.get(i).getMaxX() && player.getY() >= walls.get(i).getMinY() - player.getWidth() && player.getY() <= walls.get(i).getMaxY()) {
                player.getView().setTranslateX(walls.get(i).getMaxX());
            } else if (player.getX() >= walls.get(i).getMinX() - player.getWidth() && player.getX() <= walls.get(i).getMaxX() && player.getY() >= walls.get(i).getMinY() - player.getWidth() && player.getY() <= walls.get(i).getMinY() - player.getWidth() + 1) {
                player.getView().setTranslateY(walls.get(i).getMinY() - player.getWidth());
            } else if (player.getX() >= walls.get(i).getMinX() - player.getWidth() && player.getX() <= walls.get(i).getMaxX() && player.getY() >= walls.get(i).getMaxY() - 1 && player.getY() <= walls.get(i).getMaxY()) {
                player.getView().setTranslateY(walls.get(i).getMaxY());
            }
        }
        //kollisjon med veggene spiller 2
        for(int i = 0; i<walls.size(); i++) {
            if (enemy.getX() >= walls.get(i).getMinX() - enemy.getWidth() && enemy.getX() <= walls.get(i).getMinX() - enemy.getWidth() + 1 && enemy.getY() >= walls.get(i).getMinY() - enemy.getWidth() && enemy.getY() <= walls.get(i).getMaxY()) {
                enemy.getView().setTranslateX(walls.get(i).getMinX() - enemy.getWidth());
            } else if (enemy.getX() >= walls.get(i).getMaxX() - 1 && enemy.getX() <= walls.get(i).getMaxX() && enemy.getY() >= walls.get(i).getMinY() - enemy.getWidth() && enemy.getY() <= walls.get(i).getMaxY()) {
                enemy.getView().setTranslateX(walls.get(i).getMaxX());
            } else if (enemy.getX() >= walls.get(i).getMinX() - enemy.getWidth() && enemy.getX() <= walls.get(i).getMaxX() && enemy.getY() >= walls.get(i).getMinY() - enemy.getWidth() && enemy.getY() <= walls.get(i).getMinY() - enemy.getWidth() + 1) {
                enemy.getView().setTranslateY(walls.get(i).getMinY() - enemy.getWidth());
            } else if (enemy.getX() >= walls.get(i).getMinX() - enemy.getWidth() && enemy.getX() <= walls.get(i).getMaxX() && enemy.getY() >= walls.get(i).getMaxY() - 1 && enemy.getY() <= walls.get(i).getMaxY()) {
                enemy.getView().setTranslateY(walls.get(i).getMaxY());
            }
        }
        // går for langt til høyre eller venstre så kommer du ut på andre siden
        if(player.getX() >= maxX) {
            player.getView().setTranslateX(maxX);
        } else if (player.getX() <= minX) {
            player.getView().setTranslateX(minX);
        }
        // går for langt opp eller ned så kommer du ut på andre siden
        if(player.getY() >= maxY) {
            player.getView().setTranslateY(maxY);
        } else if (player.getY() <= minY) {
            player.getView().setTranslateY(minY);
        }
        // går for langt til høyre eller venstre så kommer du ut på andre siden
        if(enemy.getX() >= maxX) {
            enemy.getView().setTranslateX(maxX);
        } else if (enemy.getX() <= minX) {
            enemy.getView().setTranslateX(minX);
        }
        // går for langt opp eller ned så kommer du ut på andre siden
        if(enemy.getY() >= maxY) {
            enemy.getView().setTranslateY(maxY);
        } else if (enemy.getY() <= minY) {
            enemy.getView().setTranslateY(minY);
        }

        hpLabel.setText("PLAYER 1 HP: " + player.getHp());
        hpLabel.setTranslateX(10);
        hpLabel.setTranslateY(10);

        hpLabel2.setText("PLAYER 2 HP: " + enemy.getHp());
        hpLabel2.setTranslateX(scenewidth - hpLabel2.getBoundsInParent().getWidth()-10);
        hpLabel2.setTranslateY(10);

        finishLabel.setTranslateX(scenewidth/2 - finishLabel.getWidth()/2);
        finishLabel.setTranslateY(sceneheigth/2 - 75);
        finishLabel.setFont(new Font(40));

        score.setText(scoreP + " : " + scoreE);
        score.setTranslateX(scenewidth/2 - score.getWidth()/2);
        score.setTranslateY(10);
        score.setFont(new Font(20));

        resume.setTranslateX(scenewidth/2 - resume.getWidth()/2);
        resume.setTranslateY(125);

        save.setTranslateX(scenewidth/2 -save.getWidth()/2);
        save.setTranslateY(125+resume.getHeight()+10);

        load.setTranslateX(scenewidth/2 -load.getWidth()/2);
        load.setTranslateY(125+(resume.getHeight()+10)*2);

        //oppdaterer posisjon
        bullets.forEach(Bullet::update);
        bullets2.forEach(Bullet::update);
        player.update();
        enemy.update();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

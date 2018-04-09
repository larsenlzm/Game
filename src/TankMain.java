import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class TankMain extends Application {

    private Pane root;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bullets2 = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private Player player;
    private Player enemy;

    double pistollader = 10; //skudd per antall frames
    double pistolladerteller = pistollader;
    double pistolladertellerDelta = 1;

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

        player = new Player("tank1.png", 5, 50,50, root);
        player.setVelocity(new Point2D(1,0));

        enemy = new Player("tank2.png", 5, 50,500, root);
        enemy.setVelocity(new Point2D(1,0));

        Wall vegg = new Wall(25,100,Color.ORANGE,150,100, root);
        walls.add(vegg);
        Wall vegg2 = new Wall(25,100,Color.ORANGE,425,100, root);
        walls.add(vegg2);
        Wall vegg3 = new Wall(25,100,Color.ORANGE,150,375, root);
        walls.add(vegg3);
        Wall vegg4 = new Wall(25,100,Color.ORANGE,425,375, root);
        walls.add(vegg4);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };

        timer.start();

        return root;
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

        double maxX = scenewidth - (player.getWidth() / 2);
        double minX = 0 - (player.getWidth() / 2);
        double maxY = sceneheigth -(player.getHeigth() / 2);
        double minY = 0 - (player.getHeigth() / 2);

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
            bullets.add(bullet);
            bullet.addBullet(player.getX(),player.getY(),root);
            //resetter pistolklokka
            pistolladerteller = 0;
        }

        if (isWPressed && isPistolLadet) {
            Bullet bullet2 = new Bullet();
            // Setter bullet velocity til 5 ganger så mye som enemy
            bullet2.setVelocity(new Point2D(Math.cos(Math.toRadians(enemy.getRotate()))*5,Math.sin(Math.toRadians(enemy.getRotate()))*5));
            //Adder bulleten til gameworld og posisjonen er da samme som enemy
            bullets2.add(bullet2);
            bullet2.addBullet(enemy.getX(),enemy.getY(), root);
            //resetter pistolklokka
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
        //behandler kulekollisjon med person og utkant
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(enemy)) {
                bullets.get(i).RemoveBullet(root);
                bullets.remove(i);
                if (enemy.getHp() != 0) {
                    enemy.setHp(enemy.getHp() - 1);
                } else {
                    enemy.setHp(5);
                    enemy.getView().setTranslateX(100);
                    enemy.getView().setTranslateY(500);
                }
            } //sjekker om kulene treffer utkant av kartet
            else if (bullets.get(i).getView().getTranslateY() <= minY  || bullets.get(i).getView().getTranslateY() >= maxY) {
                bullets.get(i).RemoveBullet(root);
            } else if (bullets.get(i).getView().getTranslateX() <= minX  || bullets.get(i).getView().getTranslateX() >= maxX) {
                bullets.get(i).RemoveBullet(root);
            } else {
                for(int j = 0; j < walls.size(); j++) {
                    if (bullets.get(i).isColliding(walls.get(j))){
                        bullets.get(i).RemoveBullet(root);
                    }
                }
            }
        }
        //behandler kulekollisjon med person og utkant
        for (int i = 0; i < bullets2.size(); i++){
            if(bullets2.get(i).isColliding(player)) {
                bullets2.get(i).RemoveBullet(root);
                bullets2.remove(i);
                if(player.getHp() != 0){
                    player.setHp(player.getHp() - 1);
                } else {
                    player.setHp(5);
                    player.getView().setTranslateX(100);
                    player.getView().setTranslateY(100);
                }
            } //sjekker om kulene treffer utkant av kartet
            else if (bullets2.get(i).getView().getTranslateY() <= minY  || bullets2.get(i).getView().getTranslateY() >= maxY) {
                bullets2.get(i).RemoveBullet(root);
            } else if (bullets2.get(i).getView().getTranslateX() <= minX  || bullets2.get(i).getView().getTranslateX() >= maxX) {
                bullets2.get(i).RemoveBullet(root);
            } else {
                for(int j = 0; j < walls.size(); j++) {
                    if (bullets2.get(i).isColliding(walls.get(j))){
                        bullets2.get(i).RemoveBullet(root);
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
            player.getView().setTranslateX(minX);
        } else if (player.getX() <= minX) {
            player.getView().setTranslateX(maxX);
        }
        // går for langt opp eller ned så kommer du ut på andre siden
        if(player.getY() >= maxY) {
            player.getView().setTranslateY(minY);
        } else if (player.getY() <= minY) {
            player.getView().setTranslateY(maxY);
        }
        // går for langt til høyre eller venstre så kommer du ut på andre siden
        if(enemy.getX() >= maxX) {
            enemy.getView().setTranslateX(minX);
        } else if (enemy.getX() <= minX) {
            enemy.getView().setTranslateX(maxX);
        }
        // går for langt opp eller ned så kommer du ut på andre siden
        if(enemy.getY() >= maxY) {
            enemy.getView().setTranslateY(minY);
        } else if (enemy.getY() <= minY) {
            enemy.getView().setTranslateY(maxY);
        }
        //kulekollisjon med vegg


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

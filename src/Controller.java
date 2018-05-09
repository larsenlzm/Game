import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.net.*;
import java.io.*;

public class Controller {

    private Stage stage;

    private Pane overLayer;
    private Label hpLabel, hpLabel2, score, finishLabel;
    private AnimationTimer timer;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bullets2 = new ArrayList<>();
    private List<Level> maps = new ArrayList<>();

    private Player player;
    private Player enemy;

    private Clip ooh;

    private double lader = 10; //skudd per antall frames
    private double laderTeller = lader;
    private double laderTellerDelta = 1;

    private int scoreP;
    private int scoreE;
    private int currentLevel;

    private File saveFile;

    private double scenewidth = 1280;
    private double sceneheigth = 720;

    public Button saveButton, startButton, resumeButton, loadButton, exitButton, back, saveSave;
    public TextField saveText;
    public Label errorLabel, loadLabel, victoryLabelWinner, victoryLabelScore;
    public Pane gameRoot;
    public AnchorPane main, saveP, errorP, loadP, mainPane, gamePaused, victoryP;
    public ImageView background;

    private BitSet keyboardBitSet = new BitSet();

    private void switchPane(Pane from,Pane to){
        from.setDisable(true);
        from.setVisible(false);
        to.setDisable(false);
        to.setVisible(true);
    }
    private void createContent(int P, int E, int L) {

        stage = (Stage) mainPane.getScene().getWindow();

        scoreP = P;
        scoreE = E;
        currentLevel = L;

        String sprite1 = "res/wall1.png";
        String sprite2 = "res/wall1.png";
        String sprite3 = "res/wall1.png";
        String sprite4 = "res/wall1.png";
        String sprite5 = "res/wall1.png";

        deleteImages();

        overLayer = new Pane();

        gameRoot.setPrefSize(scenewidth,sceneheigth);

        player = new Player("res/tankBlue.png", 10,3, 50,50, gameRoot);
        player.setVelocity(0,0);
        player.setSpeedMultiplier(3);

        enemy = new Player("res/tankRed.png", 10,3, 1210,650, gameRoot);
        enemy.setVelocity(0,0);
        enemy.getView().setRotate(180);
        enemy.setSpeedMultiplier(3);

        // bane1
        maps.add(new Level(50,650,1210,50, "res/spillbg1.png"));
        maps.get(0).addWalls(new Wall(sprite1,150,100, gameRoot)); //oppe til venstre
        maps.get(0).addWalls(new Wall(sprite1,150,150, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,150,200, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,200,100, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,250,100, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1100,100, gameRoot)); // oppe til høyre
        maps.get(0).addWalls(new Wall(sprite1,1050,100, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1000,100, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1100,150, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1100,200, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,150,550, gameRoot)); // nede til venstre
        maps.get(0).addWalls(new Wall(sprite1,150,500, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,150,450, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,200,550, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,250,550, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1100,550, gameRoot));//nede til høyre
        maps.get(0).addWalls(new Wall(sprite1,1100,500, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1100,450, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1050,550, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,1000,550, gameRoot));
        maps.get(0).addWalls(new Wall(sprite1,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, gameRoot)); //midten

        //bane2
        maps.add(new Level(50,650,1210,50,"res/spillbg2.png"));
        maps.get(1).addWalls(new Wall(sprite2,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,1165,545, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,1070,58, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,590,200, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,330,340, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,130,560, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,790,640, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,920,280, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,135,100, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,640,75, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,540,575, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,960,460, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,737,360, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,123,321, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,907,580, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,400,160, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,330,630, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,490,430, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,400,160, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,1125,345, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,830,125, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,730,495, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,360,505, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,240,184, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,340,59, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,1170,168, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,980,170, gameRoot));
        maps.get(1).addWalls(new Wall(sprite2,210,420, gameRoot));

        //bane3
        maps.add(new Level(50 ,50,1210,650,"res/spillbg1.png"));
        maps.get(2).addWalls(new Wall(sprite3,1230,550, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1180,550, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,550, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,500, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,450, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,400, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,350, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,40, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,90, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,1130,140, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,0,300, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,50,300, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,100,300, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,150,300, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,100,250, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,100,200, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,100,150, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,400,670, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,400,620, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,400,570, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,700,40, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,700,90, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,700,140, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,500,400, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,550,400, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,600,400, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,650,400, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,700,400, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,600,450, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,600,500, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,600,350, gameRoot));
        maps.get(2).addWalls(new Wall(sprite3,600,300, gameRoot));

        //bane4
        maps.add(new Level(50,650,1210,50,"res/spillbg2.png"));
        maps.get(3).addWalls(new Wall(sprite4,100,530, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,100,580, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,100,630, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,200,300, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,200,350, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,200,400, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,100,70, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,100,120, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,100,170, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,300,70, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,300,120, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,300,170, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,400,300, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,400,350, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,400,400, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,300,530, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,300,580, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,300,630, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,500,70, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,500,120, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,500,170, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,600,300, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,600,350, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,600,400, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,500,530, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,500,580, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,500,630, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,700,70, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,700,120, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,700,170, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,800,300, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,800,350, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,800,400, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,700,530, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,700,580, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,700,630, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,900,70, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,900,120, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,900,170, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1000,300, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1000,350, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1000,400, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,900,530, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,900,580, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,900,630, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1100,70, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1100,120, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1100,170, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1100,530, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1100,580, gameRoot));
        maps.get(3).addWalls(new Wall(sprite4,1100,630, gameRoot));

        //bane5
        maps.add(new Level(50,650,1210,50,"res/spillbg1.png"));
        maps.get(4).addWalls(new Wall(sprite5,1150,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1100,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1150,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1100,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1150,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1100,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1150,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1100,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1150,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1100,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1150,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,1100,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,950,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,900,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,950,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,900,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,950,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,900,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,950,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,900,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,950,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,900,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,950,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,900,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,750,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,700,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,750,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,700,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,750,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,700,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,750,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,700,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,750,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,700,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,750,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,700,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,550,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,500,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,550,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,500,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,550,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,500,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,550,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,500,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,550,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,500,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,550,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,500,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,350,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,300,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,350,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,300,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,350,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,300,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,350,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,300,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,350,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,300,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,350,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,300,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,150,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,100,180, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,150,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,100,130, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,150,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,100,380, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,150,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,100,330, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,150,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,100,580, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,150,530, gameRoot));
        maps.get(4).addWalls(new Wall(sprite5,100,530, gameRoot));

        for(Wall i : maps.get(currentLevel).getWalls()){
            i.addPane();
        }
        background.setImage(new Image(maps.get(currentLevel).getMapBg()));

        gameRoot.getChildren().add(overLayer);

        hpLabel = new Label();
        hpLabel.setTextFill(Color.BLACK);
        overLayer.getChildren().add(hpLabel);

        hpLabel2 = new Label();
        hpLabel2.setTextFill(Color.BLACK);
        overLayer.getChildren().add(hpLabel2);

        finishLabel = new Label();
        finishLabel.setTextFill(Color.BLACK);
        overLayer.getChildren().add(finishLabel);

        score = new Label();
        score.setTextFill(Color.BLACK);
        overLayer.getChildren().add(score);
    }

    private void bulletPhysics(List<Bullet> bullets, Player enemy, int nr){
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(enemy)) {
                getSound("res/sound.wav");
                bullets.get(i).RemoveBullet(gameRoot);
                bullets.remove(i);
                if (enemy.getHp() != 1) {
                    enemy.setHp(enemy.getHp() - 1);
                } else if(scoreP < 2){
                    enemy.setHp(10);
                    newRound();
                    enemy.setLifePoints(enemy.getLifePoints() - 1);
                    scoreP++;
                } else {
                    scoreP++;
                    gameRoot.getChildren().remove(enemy.getView());
                    stopContent();
                    enemy.setHp(enemy.getHp() - 1);
                    enemy.setLifePoints(enemy.getLifePoints() - 1);
                    victoryLabelScore.setText(scoreP + " : " + scoreE);
                    victoryLabelWinner.setText("PLAYER " + nr +" WON!");
                    victoryP.setVisible(true);
                    victoryP.setDisable(false);
                }
            } //sjekker om kulene treffer utkant av kartet
            else if (bullets.get(i).getView().getTranslateY() <= 45  || bullets.get(i).getView().getTranslateY() >= sceneheigth+25) {
                bullets.get(i).RemoveBullet(gameRoot);
                bullets.remove(i);
            } else if (bullets.get(i).getView().getTranslateX() <= 0  || bullets.get(i).getView().getTranslateX() >= scenewidth+25) {
                bullets.get(i).RemoveBullet(gameRoot);
                bullets.remove(i);
            } else {
                for(Wall j : maps.get(currentLevel).getWalls()) {
                    if (bullets.get(i).isColliding(j)){
                        bullets.get(i).RemoveBullet(gameRoot);
                        bullets.remove(i);
                    }
                }
            }
        }
    }

    private void collisionWalls(Player player){
        for(Wall i : maps.get(currentLevel).getWalls()) {
            //spiller kommer fra venstre
            if (player.getX() >= i.getMinX() - player.getWidth() && player.getX() <= i.getMinX() - player.getWidth() + 5 && player.getY() >= i.getMinY() - player.getWidth() && player.getY() <= i.getMaxY()) {
                player.getView().setTranslateX(i.getMinX() - player.getWidth());
            }
            //spiller kommer fra høyre
            else if (player.getX() >= i.getMaxX() - 5 && player.getX() <= i.getMaxX() && player.getY() >= i.getMinY() - player.getWidth() && player.getY() <= i.getMaxY()) {
                player.getView().setTranslateX(i.getMaxX());
            }
            //spiller kommer fra toppen
            else if (player.getX() >= i.getMinX() - player.getWidth() && player.getX() <= i.getMaxX() && player.getY() >= i.getMinY() - player.getWidth() && player.getY() <= i.getMinY() - player.getWidth() + 5) {
                player.getView().setTranslateY(i.getMinY() - player.getWidth());
            }
            //spiller kommer fra bunnen
            else if (player.getX() >= i.getMinX() - player.getWidth() && player.getX() <= i.getMaxX() && player.getY() >= i.getMaxY() - 5 && player.getY() <= i.getMaxY()) {
                player.getView().setTranslateY(i.getMaxY());
            }
        }
    }
    private void addInputControls(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            keyboardBitSet.set(e.getCode().ordinal(), true);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            keyboardBitSet.set(e.getCode().ordinal(), false);
        });
    }
    private void error(String error){
        errorLabel.setText("Error!!! \n" + error);
        errorP.setDisable(false);
        errorP.setVisible(true);
        main.setDisable(true);
        saveP.setDisable(true);
        loadP.setDisable(true);
    }
    private void newRound(){
        player.getView().setRotate(0);
        enemy.getView().setRotate(180);
        for (Bullet b : bullets){
            b.RemoveBullet(gameRoot);
        }
        for (Bullet b : bullets2){
            b.RemoveBullet(gameRoot);
        }
        bullets.clear();
        bullets2.clear();
        enemy.setHp(10);
        player.setHp(10);
        if(currentLevel+1<maps.size()) {
            for (Wall i : maps.get(currentLevel).getWalls()) {
                i.removePane();
            }
            currentLevel = currentLevel + 1;
            for (Wall i : maps.get(currentLevel).getWalls()) {
                i.addPane();
            }
        }
        background.setImage(new Image(maps.get(currentLevel).getMapBg()));
        player.getView().setTranslateX(maps.get(currentLevel).getSpawnPX());
        player.getView().setTranslateY(maps.get(currentLevel).getSpawnPY());
        enemy.getView().setTranslateX(maps.get(currentLevel).getSpawnEX());
        enemy.getView().setTranslateY(maps.get(currentLevel).getSpawnEY());
    }
    private void stopContent() {
        timer.stop();
    }
    private void resumeContent() {
        timer.start();
    }
    private void deleteImages() {
        gameRoot.getChildren().clear();
    }
    public void startGame() {
        createContent(0,0,0);
        addInputControls(mainPane.getScene());
        switchPane(main,gameRoot);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        resumeContent();
        keyboardBitSet.set(0,100,false);
    }
    public void help() {
        System.out.println("help me senpai");
    }
    public void resumeGame() {
        try {
            gamePaused.setVisible(false);
            gamePaused.setDisable(true);
            gameRoot.setDisable(false);
            background.setImage(new Image(maps.get(currentLevel).getMapBg()));
            resumeContent();
            keyboardBitSet.set(0,100,false);
            System.out.println("Resuming game ...");
        } catch(RuntimeException e){
            error("Nothing to resume");
            System.out.println("Cant resume, you sure you have anything to resume??");
        }
    }
    public void saveGame() {
        saveText.setText("");
        switchPane(main,saveP);
    }
    public void saveSave(){
        if(!saveText.getText().trim().isEmpty()){
            System.out.println(saveText.getCharacters());
            Save save = new Save(scoreP,scoreE,currentLevel);
            try {
                resourceManager.save(save,saveText.getText() + ".save"); // MIDLERTIDIG
                System.out.println("Saving game ...");
            } catch (Exception ex){
                System.out.println("FUNKER IKKE Å LAGRE " + ex.getMessage());
            }
            switchPane(saveP,gamePaused);
        } else {
            error("No name entered");
            System.out.println("Skriv inn et navn");
        }
    }
    public void loadGame() {
        switchPane(main,loadP);
    }
    public void loader(){
        System.out.println("getting file");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load .save file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Saves", "*.save");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        saveFile = fileChooser.showOpenDialog(stage);
        loadLabel.setText(saveFile.getName());
    }
    public void loadLoad(){
        if(saveFile != null) {
            try {
                Save save = (Save) resourceManager.load(saveFile.getName());
                System.out.println("Loading game ...");
                scoreP = save.getScoreP();
                scoreE = save.getScoreE();
                currentLevel = save.getCurrentMap();
                createContent(scoreP,scoreE,currentLevel);
                addInputControls(mainPane.getScene());
                switchPane(loadP,gameRoot);
                timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        onUpdate();
                    }
                };
                resumeContent();
                keyboardBitSet.set(0, 100, false);
                saveFile = null;
                loadLabel.setText("");
            } catch (Exception ex) {
                if (ex.getMessage() != null) {
                    System.out.println("KAN IKKE LOADE!: " + ex.getMessage());
                    error("cant load file");
                }
            }
        } else {
            error("cant load file, no file chosen");
        }
    }
    public void exitGame() {
        stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }
    public void goBack() {
        if(errorP.isVisible() && main.isVisible()){
            switchPane(errorP,main);
        } else if(errorP.isVisible() && saveP.isVisible()){
            switchPane(errorP,saveP);
        } else if(errorP.isVisible() && loadP.isVisible()){
            switchPane(errorP,loadP);
        } else if(errorP.isVisible() && gameRoot.isVisible()){
            switchPane(errorP,gameRoot);
        } else {
            errorP.setDisable(true);
            errorP.setVisible(false);
            loadP.setDisable(true);
            loadP.setVisible(false);
            saveP.setDisable(true);
            saveP.setVisible(false);
            gameRoot.setDisable(true);
            gameRoot.setVisible(false);
            main.setDisable(false);
            main.setVisible(true);
        }
    }
    public void goBackGame(){
        switchPane(saveP,gamePaused);
    }
    public void toMain() {
        goBack();
        gamePaused.setVisible(false);
        gamePaused.setDisable(true);
        victoryP.setVisible(false);
        victoryP.setDisable(true);
        background.setImage(new Image("res/navn.png"));
    }
    private void getSound(String fname) { //Midlertidig kode for sound

        try {
            URL url = this.getClass().getClassLoader().getResource(fname);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            ooh = AudioSystem.getClip();
            ooh.open(audioIn);
            ooh.setFramePosition(0);
            ooh.start();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void onUpdate() {
        boolean isWPressed = keyboardBitSet.get(KeyCode.W.ordinal());
        boolean isAPressed = keyboardBitSet.get(KeyCode.A.ordinal());
        boolean isSPressed = keyboardBitSet.get(KeyCode.S.ordinal());
        boolean isDPressed = keyboardBitSet.get(KeyCode.D.ordinal());
        boolean isVPressed = keyboardBitSet.get(KeyCode.V.ordinal());
        boolean isUpPressed = keyboardBitSet.get(KeyCode.UP.ordinal());
        boolean isDownPressed = keyboardBitSet.get(KeyCode.DOWN.ordinal());
        boolean isLeftPressed = keyboardBitSet.get(KeyCode.LEFT.ordinal());
        boolean isRightPressed = keyboardBitSet.get(KeyCode.RIGHT.ordinal());
        boolean isMPressed = keyboardBitSet.get(KeyCode.M.ordinal());
        boolean isSpacePressed = keyboardBitSet.get(KeyCode.SPACE.ordinal());
        boolean isEscPressed = keyboardBitSet.get(KeyCode.ESCAPE.ordinal());

        double maxX = scenewidth - (player.getWidth() / 2);
        double minX = 0 - (player.getWidth() / 2);
        double maxY = sceneheigth - (player.getWidth() / 2);
        double minY = 45;

        laderTeller += laderTellerDelta;
        if( laderTeller > lader) {
            laderTeller = lader;
        }
        boolean isPistolLadet = laderTeller >= lader;
        //Pause

        if(isSpacePressed || isEscPressed){
            stopContent();
            gamePaused.setDisable(false);
            gamePaused.setVisible(true);
            gameRoot.setDisable(true);
        }

        //skyting player2
        if (isMPressed && isPistolLadet) {
            Bullet bullet2 = new Bullet("res/bulletRed1.png",enemy.getX(),enemy.getY(), gameRoot,enemy,enemy.getRotate());
            //Adder bulleten til gameworld og posisjonen er da samme som player
            bullets2.add(bullet2);
            //resetter pistolklokka
            laderTeller = 0;
        }
        //skyting player1
        if (isVPressed && isPistolLadet) {
            Bullet bullet = new Bullet("res/bulletBlue.png",player.getX(),player.getY(), gameRoot, player, player.getRotate());
            //Adder bulleten til gameworld
            bullets.add(bullet);
            //resetter pistolklokka
            laderTeller = 0;
        }

        if (isLeftPressed && !isRightPressed) {
            enemy.rotateLeft();
        } else if ( !isLeftPressed && isRightPressed) {
            enemy.rotateRight();
        }

        if ( isAPressed && !isDPressed) {
            player.rotateLeft();
        } else if ( !isAPressed && isDPressed) {
            player.rotateRight();
        }

        if(isUpPressed){
            enemy.setVelocity(Math.cos(Math.toRadians(enemy.getView().getRotate()))*enemy.getSpeedMultiplier(), Math.sin(Math.toRadians(enemy.getView().getRotate()))*enemy.getSpeedMultiplier());
        } else  if(isDownPressed){
            enemy.setVelocity(-Math.cos(Math.toRadians(enemy.getView().getRotate()))*enemy.getSpeedMultiplier(), -Math.sin(Math.toRadians(enemy.getView().getRotate()))*enemy.getSpeedMultiplier());
        }else{
            enemy.setVelocity(0,0);
        }

        if(isWPressed){
            player.setVelocity(Math.cos(Math.toRadians(player.getRotate()))*player.getSpeedMultiplier(), Math.sin(Math.toRadians(player.getRotate()))*player.getSpeedMultiplier());
        } else if(isSPressed){
            player.setVelocity(-Math.cos(Math.toRadians(player.getView().getRotate()))*player.getSpeedMultiplier(), -Math.sin(Math.toRadians(player.getView().getRotate()))*player.getSpeedMultiplier());
        }else{
            player.setVelocity(0,0);
        }
        //behandler kulekollisjon med person og utkant
        bulletPhysics(bullets2, player, 1);
        bulletPhysics(bullets, enemy, 2);

        //kollisjon med veggene spiller 1
        collisionWalls(player);
        collisionWalls(enemy);

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

        //oppdaterer posisjon
        bullets.forEach(Bullet::update);
        bullets2.forEach(Bullet::update);
        player.update();
        enemy.update();
    }
}

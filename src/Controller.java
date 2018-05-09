import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    private int currentLevel;

    private File saveFile;

    private double scenewidth = 1280;
    private double sceneheigth = 720;

    public Button saveButton, startButton, resumeButton, loadButton, exitButton, back, saveSave;
    public TextField saveText;
    public Label errorLabel, loadLabel, victoryLabelWinner, victoryLabelScore, currentScore;
    public Pane gameP;
    public AnchorPane main, saveP, errorP, loadP, mainPane, gamePaused, victoryP, gameRoot;
    public ImageView background;
    public ProgressBar playerHp, enemyHp;

    private BitSet keyboardBitSet = new BitSet();

    private void createContent(int P, int E, int L) {

        stage = (Stage) mainPane.getScene().getWindow();

        deleteImages();

        player = new Player("res/tankBlue.png", 10,3, 50,50, gameP);
        player.setVelocity(0,0);
        player.setSpeedMultiplier(3);

        enemy = new Player("res/tankRed.png", 10,3, 1210,650, gameP);
        enemy.setVelocity(0,0);
        enemy.getView().setRotate(180);
        enemy.setSpeedMultiplier(3);

        player.setScore(P);
        enemy.setScore(E);
        currentLevel = L;

        String spriteWall = "res/wall1.png";
        String spriteWall2 = "res/wall1.png";
        String spriteWall3 = "res/wall1.png";
        String spriteWall4 = "res/wall1.png";
        String spriteWall5 = "res/wall1.png";
        String spriteBg1 = "res/spillbg1.png";
        String spriteBg2 = "res/spillbg2.png";
        String spriteBg3 = "res/spillbg1.png";
        String spriteBg4 = "res/spillbg2.png";
        String spriteBg5 = "res/spillbg1.png";

        // bane1
        maps.add(new Level(50,650,1210,50, spriteBg1));
        maps.get(0).addWalls(new Wall(spriteWall,150,100, gameP)); //oppe til venstre
        maps.get(0).addWalls(new Wall(spriteWall,150,150, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,150,200, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,200,100, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,250,100, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1100,100, gameP)); // oppe til høyre
        maps.get(0).addWalls(new Wall(spriteWall,1050,100, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1000,100, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1100,150, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1100,200, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,150,550, gameP)); // nede til venstre
        maps.get(0).addWalls(new Wall(spriteWall,150,500, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,150,450, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,200,550, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,250,550, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1100,550, gameP));//nede til høyre
        maps.get(0).addWalls(new Wall(spriteWall,1100,500, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1100,450, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1050,550, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,1000,550, gameP));
        maps.get(0).addWalls(new Wall(spriteWall,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, gameP)); //midten

        //bane2
        maps.add(new Level(50,650,1210,50,spriteBg2));
        maps.get(1).addWalls(new Wall(spriteWall2,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1165,545, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1070,58, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,590,200, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,330,340, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,130,560, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,790,640, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,920,280, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,135,100, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,640,75, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,540,575, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,960,460, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,737,360, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,123,321, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,907,580, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,400,160, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,330,630, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,490,430, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,400,160, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1125,345, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,830,125, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,730,495, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,360,505, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,240,184, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,340,59, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1170,168, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,980,170, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,210,420, gameP));

        //bane3
        maps.add(new Level(50 ,50,1210,650,spriteBg3));
        maps.get(2).addWalls(new Wall(spriteWall3,1230,550, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1180,550, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,550, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,500, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,450, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,350, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,40, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,90, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,140, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,0,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,50,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,100,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,150,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,100,250, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,100,200, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,100,150, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,400,670, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,400,620, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,400,570, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,700,40, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,700,90, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,700,140, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,500,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,550,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,650,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,700,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,450, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,500, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,350, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,300, gameP));

        //bane4
        maps.add(new Level(50,650,1210,50,spriteBg4));
        maps.get(3).addWalls(new Wall(spriteWall4,100,530, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,100,580, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,100,630, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,200,300, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,200,350, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,200,400, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,100,70, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,100,120, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,100,170, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,300,70, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,300,120, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,300,170, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,400,300, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,400,350, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,400,400, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,300,530, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,300,580, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,300,630, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,500,70, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,500,120, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,500,170, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,600,300, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,600,350, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,600,400, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,500,530, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,500,580, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,500,630, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,700,70, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,700,120, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,700,170, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,800,300, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,800,350, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,800,400, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,700,530, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,700,580, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,700,630, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,900,70, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,900,120, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,900,170, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1000,300, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1000,350, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1000,400, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,900,530, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,900,580, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,900,630, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,70, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,120, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,170, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,530, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,580, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,630, gameP));

        //bane5
        maps.add(new Level(50,650,1210,50,spriteBg5));
        maps.get(4).addWalls(new Wall(spriteWall5,1150,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1150,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1150,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1150,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1150,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1150,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,750,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,700,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,750,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,700,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,750,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,700,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,750,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,700,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,750,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,700,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,750,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,700,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,550,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,500,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,550,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,500,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,550,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,500,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,550,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,500,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,550,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,500,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,550,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,500,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,100,180, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,100,130, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,100,380, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,100,330, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,100,580, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,530, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,100,530, gameP));

        for(Wall i : maps.get(currentLevel).getWalls()){
            i.addPane();
        }

        background.setImage(new Image(maps.get(currentLevel).getMapBg()));

        currentScore.setText(player.getScore() + " : " + enemy.getScore());
    }
    private void switchPane(Pane from,Pane to){
        from.setDisable(true);
        from.setVisible(false);
        to.setDisable(false);
        to.setVisible(true);
    }
    private void boundsPlayer(Player player){
        double maxX = scenewidth - (player.getWidth() / 2);
        double minX = 0 - (player.getWidth() / 2);
        double maxY = sceneheigth - (player.getWidth() / 2);
        double minY = 45;
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
    }
    private void movePlayer(boolean forward, boolean back, Player player){
        if(forward){
            player.setVelocity(Math.cos(Math.toRadians(player.getRotate()))*player.getSpeedMultiplier(), Math.sin(Math.toRadians(player.getRotate()))*player.getSpeedMultiplier());
        } else if(back){
            player.setVelocity(-Math.cos(Math.toRadians(player.getView().getRotate()))*player.getSpeedMultiplier(), -Math.sin(Math.toRadians(player.getView().getRotate()))*player.getSpeedMultiplier());
        }else{
            player.setVelocity(0,0);
        }
    }
    private void rotatePlayer(boolean right, boolean left, Player player){
        if (right && !left) {
            player.rotateLeft();
        } else if ( !right && left) {
            player.rotateRight();
        }
    }
    private void shootPlayer(boolean shoot, Player player, boolean isPistolLadet, List<Bullet> bullets, String sprite){
        if (shoot && isPistolLadet) {
            //Adder bulleten til gameworld og posisjonen er da samme som player
            bullets.add(new Bullet(sprite,player.getX(),player.getY(), gameP, player, player.getRotate()));
            //resetter pistolklokka
            laderTeller = 0;
        }
    }
    private void bulletPhysics(List<Bullet> bullets, Player play, int nr, Player poeng){
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(play)) {
                getSound("res/sound.wav");
                bullets.get(i).RemoveBullet(gameP);
                bullets.remove(i);
                if (play.getHp() != 1) {
                    play.setHp(play.getHp() - 1);
                } else if(poeng.getScore() < 2){
                    play.setHp(10);
                    newRound();
                    play.setLifePoints(play.getLifePoints() - 1);
                    poeng.setScore(poeng.getScore()+1);
                } else {
                    poeng.setScore(poeng.getScore()+1);
                    gameP.getChildren().remove(play.getView());
                    stopContent();
                    play.setHp(play.getHp() - 1);
                    play.setLifePoints(play.getLifePoints() - 1);
                    victoryLabelScore.setText(player.getScore() + " : " + enemy.getScore());
                    victoryLabelWinner.setText("PLAYER " + nr +" WON!");
                    victoryP.setVisible(true);
                    victoryP.setDisable(false);
                }
            } //sjekker om kulene treffer utkant av kartet
            else if (bullets.get(i).getView().getTranslateY() <= 45  || bullets.get(i).getView().getTranslateY() >= sceneheigth+25) {
                bullets.get(i).RemoveBullet(gameP);
                bullets.remove(i);
            } else if (bullets.get(i).getView().getTranslateX() <= 0  || bullets.get(i).getView().getTranslateX() >= scenewidth+25) {
                bullets.get(i).RemoveBullet(gameP);
                bullets.remove(i);
            } else {
                for(Wall j : maps.get(currentLevel).getWalls()) {
                    if (bullets.get(i).isColliding(j)){
                        bullets.get(i).RemoveBullet(gameP);
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
            b.RemoveBullet(gameP);
        }
        for (Bullet b : bullets2){
            b.RemoveBullet(gameP);
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
        gameP.getChildren().clear();
    }
    public void startGame() {
        createContent(0,0,0);
        addInputControls(mainPane.getScene());
        switchPane(main, gameRoot);
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
            Save save = new Save(player.getScore(),enemy.getScore(),currentLevel);
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
                player.setScore(save.getScoreP());
                player.setScore(save.getScoreE());
                currentLevel = save.getCurrentMap();
                createContent(player.getScore(),enemy.getScore(),currentLevel);
                addInputControls(mainPane.getScene());
                switchPane(loadP, gameRoot);
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
            switchPane(errorP, gameRoot);
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
        //skyte spiller
        shootPlayer(isVPressed,player,isPistolLadet,bullets,"res/bulletBlue.png");
        shootPlayer(isMPressed,enemy,isPistolLadet,bullets2,"res/bulletRed.png");
        //rotere spiller
        rotatePlayer(isLeftPressed,isRightPressed,enemy);
        rotatePlayer(isAPressed,isDPressed,player);
        //flytte spiller
        movePlayer(isWPressed, isSPressed, player);
        movePlayer(isUpPressed, isDownPressed,enemy);
        //behandler kulekollisjon med person og utkant
        bulletPhysics(bullets2, player, 1, enemy);
        bulletPhysics(bullets, enemy, 2, player);
        //kollisjon med veggene spiller 1
        collisionWalls(player);
        collisionWalls(enemy);
        //kollisjon med kanten av banen
        boundsPlayer(player);
        boundsPlayer(enemy);

        playerHp.setProgress((double)player.getHp()/10);
        enemyHp.setProgress((double)enemy.getHp()/10);
        currentScore.setText(player.getScore() + " : " + enemy.getScore());

        //oppdaterer posisjon
        bullets.forEach(Bullet::update);
        bullets2.forEach(Bullet::update);
        player.update();
        enemy.update();
    }
}

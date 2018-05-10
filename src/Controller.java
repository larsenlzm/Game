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

    private double load = 10; //skudd per antall frames
    private double loadCount = load;

    private int currentLevel;
    private File saveFile;
    private BitSet keyboardBitSet = new BitSet();
    private double scenewidth = 1280;
    private double sceneheigth = 720;
    public TextField saveText;
    public Label errorLabel, loadLabel, victoryLabelWinner, victoryLabelScore, currentScore;
    public Pane gameP;
    public AnchorPane main, saveP, errorP, loadP, mainPane, gamePaused, victoryP, gameRoot;
    public ImageView background;
    public ProgressBar playerHp, enemyHp;

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
        timer.start();
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
            timer.start();
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
                int P = save.getScoreP();
                int E = save.getScoreE();
                int c = save.getCurrentMap();
                createContent(P,E,c);
                addInputControls(mainPane.getScene());
                switchPane(loadP, gameRoot);
                timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        onUpdate();
                    }
                };
                timer.start();
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
    private void createContent(int P, int E, int L) {

        stage = (Stage) mainPane.getScene().getWindow();

        gameP.getChildren().clear();

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

        String spriteWall = "res/wall2.png";
        String spriteWall2 = "res/wall1.png";
        String spriteWall3 = "res/wall1.png";
        String spriteWall4 = "res/wall2.png";
        String spriteWall5 = "res/wall1.png";
        String spriteBg1 = "res/spillbg1.png";
        String spriteBg2 = "res/spillbg2.png";
        String spriteBg3 = "res/spillbg3.png";
        String spriteBg4 = "res/spillbg1.png";
        String spriteBg5 = "res/spillbg2.png";

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
        //opp til venstre
        maps.get(1).addWalls(new Wall(spriteWall2,300,45, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,300,95, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,300,145, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,300,195, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,300,245, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,250,245, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,200,245, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,150,245, gameP));
        //oppe til høyre
        maps.get(1).addWalls(new Wall(spriteWall2,900,45, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,900,95, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,900,145, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,900,195, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,900,245, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,950,245, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1000,245, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1050,245, gameP));
        //nede til venstre
        maps.get(1).addWalls(new Wall(spriteWall2,350,670, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,350,620, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,350,570, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,350,520, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,350,470, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,300,470, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,250,470, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,200,470, gameP));
        //nede til høyre
        maps.get(1).addWalls(new Wall(spriteWall2,950,670, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,950,620, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,950,570, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,950,520, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,950,470, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1000,470, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1050,470, gameP));
        maps.get(1).addWalls(new Wall(spriteWall2,1100,470, gameP));

        //bane3
        maps.add(new Level(50 ,50,1210,650,spriteBg3));
        //nede til høyre'
        maps.get(2).addWalls(new Wall(spriteWall3,1230,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1180,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1080,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1030,450, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,930,570, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,880,620, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,880,670, gameP));
        //oppe til høyre
        maps.get(2).addWalls(new Wall(spriteWall3,1130,40, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,90, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,1130,140, gameP));
        //sidelengs T til venstre
        maps.get(2).addWalls(new Wall(spriteWall3,0,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,50,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,100,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,150,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,150,250, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,150,350, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,150,400, gameP));
        //
        maps.get(2).addWalls(new Wall(spriteWall3,400,670, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,400,620, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,400,570, gameP));
        //krysset
        maps.get(2).addWalls(new Wall(spriteWall3,450,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,500,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,550,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,650,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,700,400, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,450, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,500, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,350, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,300, gameP));
        maps.get(2).addWalls(new Wall(spriteWall3,600,550, gameP));

        //bane4
        maps.add(new Level(50,650,1210,50,spriteBg4));
        //oppe til venstre
        maps.get(3).addWalls(new Wall(spriteWall4,150,100, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,150,150, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,150,200, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,200,100, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,250,100, gameP));
        // oppe til høyre
        maps.get(3).addWalls(new Wall(spriteWall4,1100,100, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1050,100, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1000,100, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,150, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,200, gameP));
        // nede til venstre
        maps.get(3).addWalls(new Wall(spriteWall4,150,550, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,150,500, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,150,450, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,200,550, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,250,550, gameP));
        //nede til høyre
        maps.get(3).addWalls(new Wall(spriteWall4,1100,550, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,500, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1100,450, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1050,550, gameP));
        maps.get(3).addWalls(new Wall(spriteWall4,1000,550, gameP));
        //dritten i midten
        maps.get(3).addWalls(new Wall(spriteWall4,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, gameP)); //midten

        //bane5
        maps.add(new Level(50,650,1210,50,spriteBg5));
        maps.get(4).addWalls(new Wall(spriteWall5,scenewidth/2 - 25/2,sceneheigth/2 - 25/2, gameP));
        //opp til venstre
        maps.get(4).addWalls(new Wall(spriteWall5,300,45, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,95, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,145, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,195, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,245, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,250,245, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,200,245, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,150,245, gameP));
        //oppe til høyre
        maps.get(4).addWalls(new Wall(spriteWall5,900,45, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,95, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,145, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,195, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,900,245, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,245, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1000,245, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1050,245, gameP));
        //nede til venstre
        maps.get(4).addWalls(new Wall(spriteWall5,350,670, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,620, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,570, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,520, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,350,470, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,300,470, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,250,470, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,200,470, gameP));
        //nede til høyr
        maps.get(4).addWalls(new Wall(spriteWall5,950,670, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,620, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,570, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,520, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,950,470, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1000,470, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1050,470, gameP));
        maps.get(4).addWalls(new Wall(spriteWall5,1100,470, gameP));

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
            bullets.add(new Bullet(sprite,player.getX()+(player.getHeigth()/2),player.getY()+(player.getWidth()/2), gameP, player, player.getRotate()));
            //resetter pistolklokka
            loadCount = 0;
        }
    }
    private void bulletExplosion(Player play){
        System.out.println("boom!");
    }
    private void lifeUpdate(Player play, String name, Player pointer){
        if (play.getHp() != 1) {
            play.setHp(play.getHp() - 1);
            getSound("res/sound.wav");
        } else if(pointer.getScore() < 2){
            getSound("res/fatality.wav");
            pointer.setScore(pointer.getScore()+1);
            play.setLifePoints(play.getLifePoints() - 1);
            play.setHp(10);
            newRound();
        } else {
            getSound("res/fatality.wav");
            pointer.setScore(pointer.getScore()+1);
            play.setLifePoints(play.getLifePoints() - 1);
            play.setHp(play.getHp() - 1);
            timer.stop();
            victoryLabelScore.setText(player.getScore() + " : " + enemy.getScore());
            victoryLabelWinner.setText(name + " WON!");
            victoryP.setVisible(true);
            victoryP.setDisable(false);
        }
    }
    private void bulletPhysics(List<Bullet> bullets, Player play, String name, Player pointer){
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(play)) {
                bullets.get(i).RemoveBullet(gameP);
                bullets.remove(i);
                lifeUpdate(play, name, pointer);
                bulletExplosion(play);
            } else if (bullets.get(i).getView().getTranslateY() <= 0  || bullets.get(i).getView().getTranslateY() >= sceneheigth+25) {
                bullets.get(i).RemoveBullet(gameP);
                bullets.remove(i);
                bulletExplosion(play);
            } else if (bullets.get(i).getView().getTranslateX() <= -30  || bullets.get(i).getView().getTranslateX() >= scenewidth+25) {
                bullets.get(i).RemoveBullet(gameP);
                bullets.remove(i);
                bulletExplosion(play);
            } else {
                for(Wall j : maps.get(currentLevel).getWalls()) {
                    if(bullets.size() != 0) {
                        if (bullets.get(i).isColliding(j)) {
                            bullets.get(i).RemoveBullet(gameP);
                            bullets.remove(i);
                            bulletExplosion(play);
                        }
                    }
                }
            }
        }
    }
    private void collisionWalls(Player player){
        for(Wall i : maps.get(currentLevel).getWalls()) {
            //spiller kommer fra venstre
            if (    player.getX() >= i.getMinX() - player.getWidth() &&
                    player.getX() <= i.getMinX() - player.getWidth() + 5 &&
                    player.getY() >= i.getMinY() - player.getHeigth() &&
                    player.getY() <= i.getMaxY()) {
                player.getView().setTranslateX(i.getMinX() - player.getWidth());
            }
            //spiller kommer fra høyre
            else if(player.getX() >= i.getMaxX() - 5 &&
                    player.getX() <= i.getMaxX() &&
                    player.getY() >= i.getMinY() - player.getHeigth() &&
                    player.getY() <= i.getMaxY()) {
                player.getView().setTranslateX(i.getMaxX());
            }
            //spiller kommer fra toppen
            else if(player.getX() >= i.getMinX() - player.getWidth() &&
                    player.getX() <= i.getMaxX() &&
                    player.getY() >= i.getMinY() - player.getHeigth() &&
                    player.getY() <= i.getMinY() - player.getHeigth() + 5) {
                player.getView().setTranslateY(i.getMinY() - player.getHeigth());
            }
            //spiller kommer fra bunnen
            else if(player.getX() >= i.getMinX() - player.getWidth() &&
                    player.getX() <= i.getMaxX() &&
                    player.getY() >= i.getMaxY() -5 &&
                    player.getY() <= i.getMaxY() ) {
                player.getView().setTranslateY(i.getMaxY());
            }
        }
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
    private void getSound(String fname) { //Midlertidig kode for sound
        Clip ooh;
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

        double loadCountD = 1;
        loadCount += loadCountD;
        if( loadCount > load) {
            loadCount = load;
        }
        boolean isLoaded = loadCount >= load;
        //Pause
        if(isSpacePressed || isEscPressed){
            timer.stop();
            gamePaused.setDisable(false);
            gamePaused.setVisible(true);
            gameRoot.setDisable(true);
        }
        //behandler kulekollisjon med person og utkant
        bulletPhysics(bullets, enemy, "BLUE PLAYER", player);
        bulletPhysics(bullets2, player, "RED PLAYER ", enemy);
        //skyte spiller
        shootPlayer(isVPressed,player,isLoaded,bullets,"res/bulletBlue.png");
        shootPlayer(isMPressed,enemy,isLoaded,bullets2,"res/bulletRed.png");
        //rotere spiller
        rotatePlayer(isLeftPressed,isRightPressed,enemy);
        rotatePlayer(isAPressed,isDPressed,player);
        //flytte spiller
        movePlayer(isWPressed, isSPressed, player);
        movePlayer(isUpPressed, isDownPressed,enemy);
        //kollisjon med kanten av banen
        boundsPlayer(player);
        boundsPlayer(enemy);
        //kollisjon med veggene spiller 1
        collisionWalls(player);
        collisionWalls(enemy);

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

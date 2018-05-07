import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Scene game, lobby;

    private Pane root, overLayer;
    private Label hpLabel, hpLabel2, score, finishLabel;
    private AnimationTimer timer;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bullets2 = new ArrayList<>();
    private List<Level> maps = new ArrayList<>();

    private Player player;
    private Player enemy;

    private Clip ooh;

    private Rectangle rect = new Rectangle(50,50);

    private double lader = 10; //skudd per antall frames
    private double laderTeller = lader;
    private double laderTellerDelta = 1;

    private int scoreP = 0;
    private int scoreE = 0;
    private int currentLevel = 0;

    private File saveFile;

    private double scenewidth = 1280;
    private double sceneheigth = 720;

    public Button saveButton, startButton, resumeButton, loadButton, exitButton, back, saveSave;
    public TextField saveText;
    public Label errorLabel, loadLabel;
    public AnchorPane main, saveP, errorP, loadP;

    private BitSet keyboardBitSet = new BitSet();

    private Parent createContent() {

        root = new Pane();
        overLayer = new Pane();

        root.setPrefSize(scenewidth,sceneheigth);

        player = new Player("res/tank1.png", 10,3, 50,50, root);
        player.setVelocity(0,0);
        player.setSpeedMultiplier(3);

        enemy = new Player("res/tank2.png", 10,3, 1210,650, root);
        enemy.setVelocity(0,0);
        enemy.getView().setRotate(180);
        enemy.setSpeedMultiplier(3);

        // bane1
        maps.add(new Level());
        maps.get(0).addWalls(new Wall("res/wall1.png",150,100, root)); //oppe til venstre
        maps.get(0).addWalls(new Wall("res/wall1.png",150,150, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",150,200, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",200,100, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",250,100, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1100,100, root)); // oppe til høyre
        maps.get(0).addWalls(new Wall("res/wall1.png",1050,100, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1000,100, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1100,150, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1100,200, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",150,550, root)); // nede til venstre
        maps.get(0).addWalls(new Wall("res/wall1.png",150,500, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",150,450, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",200,550, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",250,550, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1100,550, root));//nede til høyre
        maps.get(0).addWalls(new Wall("res/wall1.png",1100,500, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1100,450, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1050,550, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",1000,550, root));
        maps.get(0).addWalls(new Wall("res/wall1.png",scenewidth/2 - 25/2,sceneheigth/2 - 25/2, root)); //midten
        for(Wall i : maps.get(0).getWalls()){
            i.addPane();
        }

        //bane2
        maps.add(new Level());
        maps.get(1).addWalls(new Wall("res/wall1.png",scenewidth/2 - 25/2,sceneheigth/2 - 25/2, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",1165,545, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",1070,58, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",590,200, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",330,340, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",130,560, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",790,640, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",920,280, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",135,100, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",640,75, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",540,575, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",960,460, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",737,360, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",123,321, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",907,580, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",400,160, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",330,630, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",490,430, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",400,160, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",1125,345, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",830,125, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",730,495, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",360,505, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",240,184, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",340,59, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",1170,168, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",980,170, root));
        maps.get(1).addWalls(new Wall("res/wall1.png",210,420, root));

        //bane3
        maps.add(new Level());
        maps.get(2).addWalls(new Wall("res/wall1.png",1230,550, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1180,550, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,550, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,500, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,450, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,400, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,350, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,0, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,50, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",1130,100, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",0,300, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",50,300, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",100,300, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",150,300, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",100,250, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",100,200, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",100,150, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",400,670, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",400,620, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",400,570, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",700,0, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",700,50, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",700,100, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",500,400, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",550,400, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",600,400, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",650,400, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",700,400, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",600,450, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",600,500, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",600,350, root));
        maps.get(2).addWalls(new Wall("res/wall1.png",600,300, root));

        //bane4
        maps.add(new Level());
        maps.get(3).addWalls(new Wall("res/wall1.png",scenewidth/2 - 25/2,sceneheigth/2 - 25/2, root));

        //bane5
        maps.add(new Level());
        maps.get(4).addWalls(new Wall("res/wall1.png",scenewidth/2 - 25/2,sceneheigth/2 - 25/2, root));

        root.getChildren().add(overLayer);

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

        return root;
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
        player.getView().setTranslateX(50); //flytter spiller tilbake til spawn
        player.getView().setTranslateY(50);
        enemy.getView().setTranslateX(1210); //flytter spiller2 tilbake til spawn
        enemy.getView().setTranslateY(650);
        player.getView().setRotate(0);
        enemy.getView().setRotate(180);
        for (Bullet b : bullets){
            b.RemoveBullet(root);
        }
        for (Bullet b : bullets2){
            b.RemoveBullet(root);
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
    }
    private void stopContent() {
        timer.stop();
    }
    private void resumeContent() {
        timer.start();
    }
    public void startGame() {
        scoreE = 0;
        scoreP = 0;
        stage = (Stage) startButton.getScene().getWindow();
        game = new Scene(createContent());
        stage.setScene(game);
        addInputControls(game);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        resumeContent();
        keyboardBitSet.set(0,100,false);
    }
    public void resumeGame() {
        try {
            stage.setScene(game);
            resumeContent();
            keyboardBitSet.set(0,100,false);
            System.out.println("Resuming game ...");
        } catch(RuntimeException e){
            error("Nothing to resume");
            System.out.println("Cant resume, you sure you have anything to resume??");
        }
    }
    public void loadGame() {
        main.setDisable(true);
        main.setVisible(false);
        loadP.setDisable(false);
        loadP.setVisible(true);
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
                stage = (Stage) startButton.getScene().getWindow();
                game = new Scene(createContent());
                stage.setScene(game);
                addInputControls(game);
                timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        onUpdate();
                    }
                };
                resumeContent();
                keyboardBitSet.set(0, 100, false);
                scoreP = save.getScoreP();
                scoreE = save.getScoreE();
                saveFile = null;
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
    public void saveGame() {

        saveText.setText("");
        main.setDisable(true);
        main.setVisible(false);
        saveP.setDisable(false);
        saveP.setVisible(true);
    }
    public void saveSave(){
        if(!saveText.getText().trim().isEmpty()){
            System.out.println(saveText.getCharacters());
            Save save = new Save(scoreP,scoreE);
            try {
                resourceManager.save(save,saveText.getText() + ".save"); // MIDLERTIDIG
                System.out.println("Saving game ...");
            } catch (Exception ex){
                System.out.println("FUNKER IKKE Å LAGRE " + ex.getMessage());
            }
            saveP.setDisable(true);
            saveP.setVisible(false);
            main.setDisable(false);
            main.setVisible(true);
        } else {
            error("No name entered");
            System.out.println("Skriv inn et navn");
        }
    }
    public void exitGame() {
        stage = (Stage) startButton.getScene().getWindow();
        stage.close();
    }
    public void goBack() {
        if(errorP.isVisible() && main.isVisible()){
            errorP.setDisable(true);
            errorP.setVisible(false);
            main.setDisable(false);
            main.setVisible(true);
        } else if(errorP.isVisible() && saveP.isVisible()){
            errorP.setDisable(true);
            errorP.setVisible(false);
            saveP.setDisable(false);
            saveP.setVisible(true);
        } else if(errorP.isVisible() && loadP.isVisible()){
            errorP.setDisable(true);
            errorP.setVisible(false);
            loadP.setDisable(false);
            loadP.setVisible(true);
        } else {
            errorP.setDisable(true);
            errorP.setVisible(false);
            loadP.setDisable(true);
            loadP.setVisible(false);
            saveP.setDisable(true);
            saveP.setVisible(false);
            main.setDisable(false);
            main.setVisible(true);
        }
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
                lobby = exitButton.getScene();
                stage.setScene(lobby);
                goBack();
                loadLabel.setText("");
        }

        //skyting player2
        if (isMPressed && isPistolLadet) {
            Bullet bullet2 = new Bullet(5,5,5,Color.RED,enemy.getX(),enemy.getY(), root,enemy);
            //Adder bulleten til gameworld og posisjonen er da samme som player
            bullets2.add(bullet2);
            //resetter pistolklokka
            laderTeller = 0;
        }
        //skyting player1
        if (isVPressed && isPistolLadet) {
            Bullet bullet = new Bullet(5,5,5,Color.RED,player.getX(),player.getY(), root, player);
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
        for (int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isColliding(enemy)) {
                getSound("res/sound.wav");
                bullets.get(i).RemoveBullet(root);
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
                    root.getChildren().remove(enemy.getView());
                    stopContent();
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
                for(Wall j : maps.get(currentLevel).getWalls()) {
                    if (bullets.get(i).isColliding(j)){
                        bullets.get(i).RemoveBullet(root);
                        bullets.remove(i);
                    }
                }
            }
        }
        //behandler kulekollisjon med person og utkant
        for (int i = 0; i < bullets2.size(); i++){
            if(bullets2.get(i).isColliding(player)) {
                getSound("res/sound.wav");
                bullets2.get(i).RemoveBullet(root);
                bullets2.remove(i);
                if(player.getHp() != 1){
                    player.setHp(player.getHp() - 1);
                } else if(scoreE < 2) {
                    newRound();
                    player.setLifePoints(player.getLifePoints() - 1);
                    scoreE++;
                } else {
                    scoreE++;
                    root.getChildren().remove(player.getView());
                    stopContent();
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
                for(Wall w : maps.get(currentLevel).getWalls()) {
                    if (bullets2.get(i).isColliding(w)){
                        bullets2.get(i).RemoveBullet(root);
                        bullets2.remove(i);
                    }
                }
            }
        }
        //kollisjon med veggene spiller 1
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
        //kollisjon med veggene spiller 2
        for(Wall i : maps.get(currentLevel).getWalls()) {
            //spiller kommer fra venstre
            if (enemy.getX() >= i.getMinX() - enemy.getWidth() && enemy.getX() <= i.getMinX() - enemy.getWidth() + 5 && enemy.getY() >= i.getMinY() - enemy.getWidth() && enemy.getY() <= i.getMaxY()) {
                enemy.getView().setTranslateX(i.getMinX() - enemy.getWidth());
            }
            //spiller kommer fra høyre
            else if (enemy.getX() >= i.getMaxX() - 5 && enemy.getX() <= i.getMaxX() && enemy.getY() >= i.getMinY() - enemy.getWidth() && enemy.getY() <= i.getMaxY()) {
                enemy.getView().setTranslateX(i.getMaxX());
            }
            //spiller fra toppen
            else if (enemy.getX() >= i.getMinX() - enemy.getWidth() && enemy.getX() <= i.getMaxX() && enemy.getY() >= i.getMinY() - enemy.getWidth() && enemy.getY() <= i.getMinY() - enemy.getWidth() + 5) {
                enemy.getView().setTranslateY(i.getMinY() - enemy.getWidth());
            }
            //spiller fra bunnen
            else if (enemy.getX() >= i.getMinX() - enemy.getWidth() && enemy.getX() <= i.getMaxX() && enemy.getY() >= i.getMaxY() - 5 && enemy.getY() <= i.getMaxY()) {
                enemy.getView().setTranslateY(i.getMaxY());
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

        //oppdaterer posisjon
        bullets.forEach(Bullet::update);
        bullets2.forEach(Bullet::update);
        player.update();
        enemy.update();
    }
}

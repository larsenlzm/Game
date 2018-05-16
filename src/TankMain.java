import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The main class for the game.
 *
 * @author s325919, s325894
 */


public class TankMain extends Application {

    /**
     * Creates the scene.
     * @param mainStage the stage.
     */
    @Override
    public void start(Stage mainStage) throws Exception {
        Font.loadFont(getClass().getResource("/res/8bit.TTF").toExternalForm(),15);
        Parent root = FXMLLoader.load(getClass().getResource("fuxml.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Style.css");
        mainStage.setTitle("TankSpillet");
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * Launches the game.
     *
     * @param args the arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

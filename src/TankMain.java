import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TankMain extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fuxml.fxml"));
        Scene scene = new Scene(root);


        //Kode for fullskjerm
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        mainStage.setX(primaryScreenBounds.getMinX());
        mainStage.setY(primaryScreenBounds.getMinY());
        mainStage.setWidth(primaryScreenBounds.getWidth());
        mainStage.setHeight(primaryScreenBounds.getHeight());
        //

        scene.getStylesheets().add("Style.css");
        mainStage.setTitle("TankSpillet");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

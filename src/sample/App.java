package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ui.OnKeyListener;
import sample.ui.UiConstants;
import sample.ui.field.FieldView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/connection_menu.fxml"));

        Parent root = loader.load();
        // set key listener

        Scene scene = new Scene(root, UiConstants.MenuField.FIELD_WIDTH, UiConstants.MenuField.FIELD_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

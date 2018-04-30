package sample.ui.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ui.UiConstants;
import sample.ui.theme.LightTheme;
import sample.ui.theme.ViewTheme;


public class MenuView {

    public MenuView() {

    }

    void changeTheme(ViewTheme viewTheme) {
    }

    // doesn't need to be called "start" any more...
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("res/connection_menu.fxml"));
        Scene scene =  new Scene(root, UiConstants.MenuField.FIELD_WIDTH,UiConstants.MenuField.FIELD_HEIGHT);
        window.setScene(scene);
        window.show();
    }



    public void showFieldView(Stage rootStage) {

    }
}

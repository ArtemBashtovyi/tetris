package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.SizeConstants;
import sample.ui.field.FieldController;

public class Main extends Application {

    public interface OnKeyListener {
        void onUp();
        void onLeft();
        void onRight();
        void onDown();
    }

    private OnKeyListener onKeyListener;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("res/field.fxml"));

        Parent root = loader.load();
        // set key listener
        onKeyListener = loader.<FieldController>getController();
        Scene scene = new Scene(root, SizeConstants.MAIN_SCREEN_WIDTH, SizeConstants.MAIN_SCREEN_HEIGHT);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP :    onKeyListener.onUp();   break;
                case LEFT :  onKeyListener.onLeft();   break;
                case RIGHT : onKeyListener.onRight();  break;
                case DOWN :  onKeyListener.onDown();   break;
            }
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
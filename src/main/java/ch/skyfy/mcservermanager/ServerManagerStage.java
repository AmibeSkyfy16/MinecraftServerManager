package ch.skyfy.mcservermanager;

import ch.skyfy.mcservermanager.view.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerManagerStage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new HomeView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

package Poker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        primaryStage.setTitle("Poker");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        Parent gameScreen = FXMLLoader.load(getClass().getResource("gameP1Preflop.fxml"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}

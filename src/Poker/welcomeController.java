package Poker;

import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class welcomeController {

    @FXML
    private TextField name1;

    @FXML
    private TextField name2;

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;

    @FXML
    void Begin(MouseEvent event) throws IOException{
        Context.getInstance().currentGame().setPlayer1Name(name1.getText());
        Context.getInstance().currentGame().setPlayer2Name(name2.getText());
        Parent gameParent = FXMLLoader.load(getClass().getResource("gameP1Preflop.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void restart(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene rootScene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
    }

}

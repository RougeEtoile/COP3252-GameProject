package Poker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class gameScoreController implements Initializable{

    @FXML
    private Label player1Cash;

    @FXML
    private Label player2Cash;

    @FXML
    private ImageView board0;

    @FXML
    private Label round;

    @FXML
    private ImageView board1;

    @FXML
    private ImageView board2;

    @FXML
    private ImageView board3;

    @FXML
    private ImageView board4;

    @FXML
    private ImageView hand0;

    @FXML
    private ImageView hand1;


    @FXML
    private Label errorMSG;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;

    @FXML
    private ImageView hand3;

    @FXML
    private ImageView hand2;

    @FXML
    private Button newHandButton;

    @FXML
    void exit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void newHand(ActionEvent event) throws IOException
    {
        if(Context.getInstance().currentGame().isDone())
        {
            System.out.println("Broke");
            errorMSG.setText("A player doesn't have the money to continue, press restart for a new game.");
        }
        else
        {
            System.out.println("New hand");
            Context.getInstance().currentGame().reset();
            Parent gameParent = FXMLLoader.load(getClass().getResource("gameP1Preflop.fxml"));
            Scene gameScene = new Scene(gameParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(gameScene);
            window.show();

        }
    }

    @FXML
    void restart(ActionEvent event) throws IOException
    {
        Context.getInstance().resetGame();
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene rootScene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {


        int button = Context.getInstance().currentGame().button;

        Context.getInstance().currentGame().initPreFlop();
        Context.getInstance().currentGame().initFlop();
        Context.getInstance().currentGame().initTurn();
        Context.getInstance().currentGame().initRiver();



        Card [] p1hand = {Context.getInstance().currentGame().players[button%2].cards.elementAt(0), Context.getInstance().currentGame().players[button%2].cards.elementAt(1), Context.getInstance().currentGame().board.elementAt(0), Context.getInstance().currentGame().board.elementAt(1), Context.getInstance().currentGame().board.elementAt(2)};
        Card [] p2hand = {Context.getInstance().currentGame().players[button+1%2].cards.elementAt(0), Context.getInstance().currentGame().players[button+1%2].cards.elementAt(1), Context.getInstance().currentGame().board.elementAt(0), Context.getInstance().currentGame().board.elementAt(1), Context.getInstance().currentGame().board.elementAt(2)};

        int p1score = Context.getInstance().currentGame().rules.score(p1hand);
        int p2score = Context.getInstance().currentGame().rules.score(p2hand);

        if(Context.getInstance().currentGame().players[button+1%2].getStatus().equals("FOLD"))   //player 2 folded
        {
            round.textProperty().bind(new SimpleStringProperty(Context.getInstance().currentGame().players[button%2].getName() +" has won the hand"));
            Context.getInstance().currentGame().players[button%2].addWinnings(Context.getInstance().currentGame().pot);
        }
        else if(p1score > p2score)                   //player 1 had a better hard
        {
            round.textProperty().bind(new SimpleStringProperty(Context.getInstance().currentGame().players[button%2].getName() +" has won the hand"));
            Context.getInstance().currentGame().players[button%2].addWinnings(Context.getInstance().currentGame().pot);

        }
        else if( Context.getInstance().currentGame().players[button%2].getStatus().equals("FOLD"))
        {
            round.textProperty().bind(new SimpleStringProperty(Context.getInstance().currentGame().players[button+1%2].getName() +" has won the hand"));
            Context.getInstance().currentGame().players[button+1%2].addWinnings(Context.getInstance().currentGame().pot);
        }
        else if(p2score > p1score)
        {
            round.textProperty().bind(new SimpleStringProperty(Context.getInstance().currentGame().players[button+1%2].getName() +" has won the hand"));
            Context.getInstance().currentGame().players[button+1%2].addWinnings(Context.getInstance().currentGame().pot);
        }
        else
        {
            round.textProperty().bind(new SimpleStringProperty("The hand was a tie"));
            Context.getInstance().currentGame().players[button%2].addWinnings(Context.getInstance().currentGame().pot/2);
            Context.getInstance().currentGame().players[button+1%2].addWinnings(Context.getInstance().currentGame().pot/2);

        }


        String boardcard1 = Context.getInstance().currentGame().board.elementAt(0).toString();
        String boardcard2 = Context.getInstance().currentGame().board.elementAt(1).toString();
        String boardcard3 = Context.getInstance().currentGame().board.elementAt(2).toString();
        String boardcard4 = Context.getInstance().currentGame().board.elementAt(3).toString();
        String boardcard5 = Context.getInstance().currentGame().board.elementAt(4).toString();
        Image card1 = new Image(getClass().getResourceAsStream(boardcard1));
        Image card2 = new Image(getClass().getResourceAsStream(boardcard2));
        Image card3 = new Image(getClass().getResourceAsStream(boardcard3));
        Image card4 = new Image(getClass().getResourceAsStream(boardcard4));
        Image card5 = new Image(getClass().getResourceAsStream(boardcard5));
        board0.setImage(card1);
        board1.setImage(card2);
        board2.setImage(card3);
        board3.setImage(card4);
        board4.setImage(card5);


        String player1card1 = Context.getInstance().currentGame().players[button%2].cards.elementAt(0).toString();
        String player1card2 = Context.getInstance().currentGame().players[button%2].cards.elementAt(1).toString();
        Image p1card1 = new Image(getClass().getResourceAsStream(player1card1));
        Image p1card2 = new Image(getClass().getResourceAsStream(player1card2));
        hand0.setImage(p1card1);
        hand1.setImage(p1card2);
        String player2card1 = Context.getInstance().currentGame().players[button+1%2].cards.elementAt(0).toString();
        String player2card2 = Context.getInstance().currentGame().players[button+1%2].cards.elementAt(1).toString();
        Image p2card1 = new Image(getClass().getResourceAsStream(player2card1));
        Image p2card2 = new Image(getClass().getResourceAsStream(player2card2));
        hand2.setImage(p2card1);
        hand3.setImage(p2card2);


        String cbhelp = "Current bet is $";
        //String cb = Integer.toString(Context.getInstance().currentGame().)
        String playerNameHelp ="Current player is ";
        String player1Name = Context.getInstance().currentGame().players[button%2].getName();
        String player2Name = Context.getInstance().currentGame().players[button+1 %2].getName();
        String plCashHelp = player1Name.concat(" has $");
        String p2CashHelp = player2Name.concat(" has $");

        String p1Cash = plCashHelp.concat(Integer.toString(Context.getInstance().currentGame().players[button%2].getCash()));
        String p2Cash = p2CashHelp.concat(Integer.toString(Context.getInstance().currentGame().players[button+1 %2].getCash()));
        StringProperty player1CashProperty = new SimpleStringProperty(p1Cash);
        StringProperty player2CashProperty = new SimpleStringProperty(p2Cash);
        StringProperty playerNameProp = new SimpleStringProperty(player2Name);
        player1Cash.textProperty().bind(player1CashProperty);
        player2Cash.textProperty().bind(player2CashProperty);



    }

}


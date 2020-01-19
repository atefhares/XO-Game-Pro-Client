package com.itijavafinalprojectteam8.view.gamewithcpu;

import com.itijavafinalprojectteam8.controller.AiLibrary;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.controller.Props;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author moham
 */
public class GameWithCpuController implements Initializable {

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label playerDetails;

    @FXML
    private Pane gameOverPane;

    boolean isGameStarted = true;

    private static GameAppView mApplicationCallback;

    public static void setApplicationCallback(GameAppView callback) {
        mApplicationCallback = callback;
    }

    @FXML
    public void clickButton(ActionEvent event) throws IOException {
        try {
            final Node source = (Node) event.getSource();
            String id = source.getId();


            char c = id.charAt(id.length() - 1);
            int idnum = Character.getNumericValue(c);
            if (!(AiLibrary.playerPosition.contains(idnum) || AiLibrary.cpuPosition.contains(idnum)) && isGameStarted) {
                AiLibrary.onPlayerMove(idnum);

                drawInGui(idnum, "X", Color.YELLOW);
                if (AiLibrary.playerPosition.size() + AiLibrary.cpuPosition.size() < 11) {
                    int cpuPosition = AiLibrary.onCpuMove();
                    drawInGui(cpuPosition, "o", Color.RED);
                }

                int result = AiLibrary.getWinner();
                System.out.println("result is " + result);

                handleGameResult(result);


            } else {
                System.out.println("this is the error");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void drawInGui(int pos, String c, Color color) {

        switch (pos) {
            case 1:
                b1.setText(c);
                b1.setTextFill(color);
                break;

            case 2:
                b2.setText(c);
                b2.setTextFill(color);
                break;

            case 3:
                b3.setText(c);
                b3.setTextFill(color);

                break;

            case 4:
                b4.setText(c);
                b4.setTextFill(color);
                break;

            case 5:
                b5.setText(c);
                b5.setTextFill(color);
                break;

            case 6:
                b6.setText(c);
                b6.setTextFill(color);
                break;

            case 7:
                b7.setText(c);
                b7.setTextFill(color);
                break;

            case 8:
                b8.setText(c);
                b8.setTextFill(color);
                break;

            case 9:
                b9.setText(c);
                b9.setTextFill(color);
                break;
        }
    }

    public void onChangeModeBtnClicked(ActionEvent actionEvent) {
        if (mApplicationCallback != null) {
            mApplicationCallback.switchToGameChooser();
        }
    }

    public void onPlayAgainClicked(ActionEvent actionEvent) {
        resetGame();
    }

    private void handleGameResult(int result) {
        switch (result) {
            case 0:
                //player won
                gameOverPane.setVisible(true);
                winnerLabel.setText("Congratulation! You won....");
                isGameStarted = false;
//                try {
//                    ClientController.sendToServer(JsonOperations.createUpdatePlayerPointsJson());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            case 1:
                //cpu won
                gameOverPane.setVisible(true);
                winnerLabel.setText("Computer Won!");
                isGameStarted = false;
                break;
            case 2:
                // draw
                gameOverPane.setVisible(true);
                winnerLabel.setText("OH !! NO Its a Draw");
                isGameStarted = false;
                break;
            default:
                isGameStarted = true;
                break;
        }
    }

    private void resetGame() {
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");
        gameOverPane.setVisible(false);
        isGameStarted = true;
        winnerLabel.setText("");
        AiLibrary.reset();

        playerDetails.setText(Props.mCurrentPlayer.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // resetGame();
    }
}
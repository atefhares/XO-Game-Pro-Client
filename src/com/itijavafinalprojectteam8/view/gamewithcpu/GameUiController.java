/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8.view.gamewithcpu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.itijavafinalprojectteam8.controller.AiLibrary;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.interfaces.GameCpuView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.exit;

/**
 * FXML Controller class
 *
 * @author moham
 */
public class GameUiController implements GameCpuView {

    /**
     * Initializes the controller class.
     */


    @FXML
    private Button dark_blue;

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
    private AnchorPane anchorPane;
    @FXML
    private Label howWinner;

    boolean flag = true;
/*

    private static GameAppView mApplicationCallback;

    public GameUiController() {
        ClientController.setGameCpuView(this);
    }

    public static void setApplicationCallback(GameAppView callback) {
        mApplicationCallback = callback;
    }

    @FXML
    private void changeScreenHyperLink(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchToGameCpuScreen();
    }


*/

    @FXML
    public void clickButton(ActionEvent event) throws IOException {

        final Node source = (Node) event.getSource();
        String id = source.getId();


            char c = id.charAt(id.length() - 1);
            int idnum = Character.getNumericValue(c);
        if(!(AiLibrary.playerPosition.contains(idnum)||AiLibrary.cpuPosition.contains(idnum))&& flag) {
            AiLibrary.onPlayerMove(idnum);

            drawInGui(idnum, 'X', Color.YELLOW);

            int cpuPosition = AiLibrary.onCpuMove();
            drawInGui(cpuPosition, 'O', Color.RED);


            int result = AiLibrary.getWinner();
            System.out.println("result is " + result);
            switch (result) {
                case 0:
                    //player won

                    howWinner.setText("Congratulation You Are Winner");
                    howWinner.setStyle("-fx-background-color:#fff;");
                    howWinner.setVisible(true);


                    break;
                case 1:
                    //cpu won
                    howWinner.setText("Computer Won");
                    howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                    break;

                case 2:
                    // draw
                    System.out.println("OH !! NO Its a Draw");


                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + result);
            }


            result = AiLibrary.getWinner();
            switch (result) {
                case 0:
                    //player won
                    howWinner.setText("Congratulation You Are Winner");
                    howWinner.setStyle("-fx-background-color:#fff;");
                    flag  = false;
                    break;
                case 1:
                    //cpu won
                    howWinner.setText("Computer Won");
                    howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                    flag  = false;
                    break;
                    case 2:
                    // draw
                    howWinner.setText("OH !! NO Its a Draw");
                    howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                        flag  = false;
                        break;
                default:
                    flag  = true;
                    break;
            }
        }
        else {System.out.println("this is the error");}

    }

    public void drawInGui(int pos, char c, Color color) {

        switch (pos) {
            case 1:
                    b1.setText(String.valueOf(c));
                    //b1.setStyle("-fx-text-fill: yellow;-fx-background-color:none;-fx-padding:10px");
                    b1.setTextFill(color);
                break;

            case 2:
                    b2.setText(String.valueOf(c));
                    b2.setTextFill(color);

                break;

            case 3:
                    b3.setText(String.valueOf(c));
                    b3.setTextFill(color);

                break;

            case 4:
                b4.setText(String.valueOf(c));
                b4.setTextFill(color);
                break;

            case 5:
                b5.setText(String.valueOf(c));
                b5.setTextFill(color);
                break;

            case 6:
                b6.setText(String.valueOf(c));
                b6.setTextFill(color);
                break;

            case 7:
                b7.setText(String.valueOf(c));
                b7.setTextFill(color);
                break;

            case 8:
                b8.setText(String.valueOf(c));
                b8.setTextFill(color);
                break;

            case 9:
                b9.setText(String.valueOf(c));
                b9.setTextFill(color);
                break;
        }
    }

}
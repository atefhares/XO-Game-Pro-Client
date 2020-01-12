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
import com.itijavafinalprojectteam8.view.interfaces.GameCpuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

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
        System.out.println("c is     " + c);
        int idnum = Character.getNumericValue(c);
        System.out.println(idnum);
        AiLibrary.onPlayerMove(c);
        drawInGui(idnum, 'X', Color.YELLOW);
        int cpuPosition = AiLibrary.cpuMove();
        drawInGui(cpuPosition, 'O', Color.RED);


        int result = AiLibrary.getWinner();
        System.out.println("result is " + result);
        switch (result) {
            case 0:
                //player won

                howWinner.setText("Congratulation You Are Winner");
                howWinner.setStyle("-fx-background-color:#fff;");
                howWinner.setVisible(true);
                return;
            case 1:
                //cpu won
                howWinner.setText("Computer Won");
                howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                return;

            case 2:
                // draw
                System.out.println("OH !! NO Its a Draw");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }

        int cpuPos = AiLibrary.onCpuMove();
        //show O on ui at pos = cpuPos

        result = AiLibrary.getWinner();
        switch (result) {
            case 0:
                //player won
                howWinner.setText("Congratulation You Are Winner");
                howWinner.setStyle("-fx-background-color:#fff;");
            case 1:
                //cpu won
                howWinner.setText("Computer Won");
                howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
            case 2:
                // draw
                //howWinner.setText("OH !! NO Its a Draw");
        }


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
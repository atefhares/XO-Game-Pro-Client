/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8.view.chat;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.view.gamewithplayer.GameWithPlayerController;
import com.itijavafinalprojectteam8.view.interfaces.ChatScreenView;
import com.itijavafinalprojectteam8.view.interfaces.GameWithPlayerView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author m_bassam94
 */
public class ChatUIController implements Initializable, ChatScreenView {

    @FXML
    private Button sendBtn;
    @FXML
    private TextArea ta;
    @FXML
    private TextField tf;

    private static String userEmail;

    public static void setUserEmail(String email) {
        userEmail = email;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameWithPlayerController.setChatScreenView(this);
    }

    public void onSendBtnClicked(ActionEvent actionEvent) {
        String str = tf.getText();
        ta.appendText("You: " + str + "\n");

        tf.setText("");
        try {
            ClientController.sendToServer(JsonOperations.createSendNewMsgJson(
                    userEmail, str
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewMessageReceived(String message) {
        Platform.runLater(() -> ta.appendText(message + " \n"));
    }
}

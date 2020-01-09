package com.itijavafinalprojectteam8.view.login;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class LoginController {

    @FXML
    private TextField emailAddressTF;
    @FXML
    private PasswordField passwordPF;

    private static View mApplicationCallback;

    public static void setApplicationCallback(View callback) {
        mApplicationCallback = callback;
    }

    @FXML
    private void changeScreenHyperLink(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchSceneToSignUpScreen();
    }

    @FXML
    private void onLoginBtnClicked(ActionEvent event) throws IOException {
        String email = emailAddressTF.getText();
        String plainPass = passwordPF.getText();

        try {
            ClientController.open();
            ClientController.sendToServer(JsonOperations.getSignInJson(email, plainPass));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

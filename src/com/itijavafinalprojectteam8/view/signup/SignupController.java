package com.itijavafinalprojectteam8.view.signup;

import com.itijavafinalprojectteam8.view.login.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField nameTF;
    @FXML
    private TextField emailAddressTF;
    @FXML
    private PasswordField passwordPF;
    @FXML
    private PasswordField confirmPasswordPF;

    private static View mApplicationCallback;

    public static void setApplicationCallback(View callback) {
        mApplicationCallback = callback;
    }

    @FXML
    public void changeScreenHyperLink(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchSceneToLoginScreen();
    }
}

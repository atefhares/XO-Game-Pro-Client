package com.itijavafinalprojectteam8.view.signup;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.controller.UserInputChecker;
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


    @FXML
    private void onSignUpBtnPressed() {
        String name = nameTF.getText();
        String inputUserEmail = emailAddressTF.getText();
        String inputUserPassword = passwordPF.getText();
        String inputUserConfirmPassword = confirmPasswordPF.getText();

        if (!UserInputChecker.isValidEmail(inputUserEmail)) {
            if (mApplicationCallback != null)
                mApplicationCallback.showToastMessage("Email is invalid");
            return;
        }

        if (!UserInputChecker.isValidPassword(inputUserPassword)) {
            if (mApplicationCallback != null)
                mApplicationCallback.showToastMessage("Password must be at least 8 digits and a mix of chars and numbers");
            return;
        }

        if (!inputUserPassword.equals(inputUserConfirmPassword)) {
            if (mApplicationCallback != null)
                mApplicationCallback.showToastMessage("Passwords don't match");
            return;
        }

        try {
            ClientController.sendToServer(JsonOperations.getSignUpJson(name, inputUserEmail, inputUserPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


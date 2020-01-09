package com.itijavafinalprojectteam8.view.signup;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
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

    public static final int PASSWORD_LENGTH = 8;

    @FXML
    private void onLoginUp() {
        String name = nameTF.getText();
        String inputUserEmail = emailAddressTF.getText();
        String inputUserPassword = passwordPF.getText();
        String inputUserConfirmPassword = confirmPasswordPF.getText();

        try {
            ClientController.open();
            ClientController.sendToServer(JsonOperations.getSignUpJson(name, inputUserEmail, inputUserPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        if (!isValidEmail(inputUserEmail)) {
//            Toast.makeText(
//                    window
//                    , "THis is a TOAST", 2000, 500, 500);
            return;
        }
        if (isValidPassword(inputUserPassword) && inputUserPassword.equals(inputUserConfirmPassword)) {
            System.out.println("Password is valid: " + inputUserPassword);
        } else {

            System.out.println("Not a valid password: " + inputUserPassword);
        }*/

    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    private boolean isNumeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    private boolean isValidPassword(String password) {
        System.out.println(password.length());
        if (password.length() < PASSWORD_LENGTH) {
            return false;
        } else {
            int charCounter = 0;
            int numCounter = 0;

            for (int i = 0; i < password.length(); i++) {

                char ch = password.charAt(i);

                if (isNumeric(ch)) {
                    numCounter++;
                } else if (isLetter(ch)) {
                    charCounter++;
                } else {
                    return false;
                }
            }

            return (charCounter >= 2 && numCounter >= 2);
        }
    }

}


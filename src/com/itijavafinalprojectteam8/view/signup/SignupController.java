package com.itijavafinalprojectteam8.view.signup;

import com.itijavafinalprojectteam8.view.others.toast.Toast;
import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

    private Parent root;
    private Scene signInScene;
    @FXML
    private TextField emailAddressTF;
    @FXML
    private PasswordField passWordF;
    @FXML
    private PasswordField confirmPassWordF;

    private  Stage window;
    @FXML
    public void changeScreen(ActionEvent event) throws IOException {
        if (root == null)
            root = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/login/login.fxml"));

        if (signInScene == null)
            signInScene = new Scene(root);

        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(signInScene);
        window.show();
    }

    public static final int PASSWORD_LENGTH = 8;

    @FXML
    private void onLoginUp() {
        String inputUserEmail = emailAddressTF.getText();
        String inputUserPassword = passWordF.getText();
        String inputUserConfirmPassword = confirmPassWordF.getText();

        if (!isValidEmail(inputUserEmail)) {
            Toast.makeText(
                    window
                    , "THis is a TOAST", 2000, 500, 500);
        }
        if (isValidPassword(inputUserPassword) && inputUserPassword.equals(inputUserConfirmPassword)) {
            System.out.println("Password is valid: " + inputUserPassword);
        } else {

            System.out.println("Not a valid password: " + inputUserPassword);
        }

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


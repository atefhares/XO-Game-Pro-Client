package com.itijavafinalprojectteam8.view.signup;

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



    @FXML
    public void changeScreen(ActionEvent event) throws IOException {
        if(root == null)
            root = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/login/login.fxml"));

        if(signInScene == null)
            signInScene= new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(signInScene);
        window.show();
    }

    @FXML
    private  void onLoginUp()
    {
        emailAddressTF.getText();
        passWordF.getText();

    }
}

package com.itijavafinalprojectteam8.view.login;

import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginController {
    private Parent root;
    private Scene signupScene;

    @FXML
    private void changeScreenHyperLink(ActionEvent event) throws IOException {
        if (root == null)
            root = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/signup/signup.fxml"));

        if (signupScene == null)
            signupScene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(signupScene);
        window.show();
    }

}

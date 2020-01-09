/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.view.login.LoginController;
import com.itijavafinalprojectteam8.view.login.View;
import com.itijavafinalprojectteam8.view.signup.SignupController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author ahares
 */
public class GameApplication extends Application implements View {

    /*=====================================================================*/

    private Scene signInScene;
    private Scene signUpScene;
    private Stage applicationStage;

    /*=====================================================================*/
    @FXML
    private TextField emailAddressTF;
    @FXML
    private PasswordField passwordPF;
    /*=========================================================================================*/

    @Override
    public void init() throws Exception {
        super.init();

        LoginController.setApplicationCallback(this);
        SignupController.setApplicationCallback(this);
        ClientController.init(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(Constants.GAME_TITLE);
        stage.setResizable(false);

        applicationStage = stage;

        showLoginScene(stage);
    }

    /*=========================================================================================*/

    private void showLoginScene(Stage stage) throws IOException {
        if (signInScene == null)
            signInScene = new Scene(
                    FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/login/login.fxml"))
            );


        stage.setScene(signInScene);
        stage.show();
    }

    private void showSignUpScene(Stage stage) throws IOException {
        if (signUpScene == null)
            signUpScene = new Scene(
                    FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/signup/signup.fxml"))
            );

        stage.setScene(signUpScene);
        stage.show();
    }
    /*=========================================================================================*/

    @Override
    public void switchSceneToSignUpScreen() {
        try {
            showSignUpScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchSceneToLoginScreen() {
        try {
            showLoginScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToastMessage(String text) {
    }

    /*=========================================================================================*/
    public static void main(String[] args) {
        System.out.println("This is the client!");
        launch(args);
    }

}

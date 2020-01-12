/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8;

import com.itijavafinalprojectteam8.view.gamechooserscreen.GameChooserController;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.login.LoginController;
import com.itijavafinalprojectteam8.view.others.Toast;
import com.itijavafinalprojectteam8.view.signup.SignupController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author ahares
 */

public class GameApplication extends Application implements GameAppView {

    /*=====================================================================*/

    private Scene signInScene;
    private Scene signUpScene;
    private Scene gameWithOtherPlayerScene;
    private Scene chooserScene;
    private Scene gameWithCpuScene;

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
        GameChooserController.setApplicationCallback(this);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(Constants.GAME_TITLE);
        stage.setResizable(false);

        applicationStage = stage;

        showLoginScene(stage);
//        showChooserScene(stage);
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

    private void showGameScene(Stage stage) throws IOException {
        if (gameWithOtherPlayerScene == null)
            gameWithOtherPlayerScene = new Scene(
                    FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/gamewithplayer/playwithotherpalyer.fxml"))
            );


        stage.setScene(gameWithOtherPlayerScene);
        stage.show();
    }

    private void showGameWithCpuScene(Stage stage) throws IOException {
        if (gameWithCpuScene == null)
            gameWithCpuScene = new Scene(
                    FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/gamewithcpu/playwithcpu.fxml"))
            );
        stage.setScene(gameWithCpuScene);
        stage.show();
    }

    private void showChooserScene(Stage stage) throws IOException {
        if (chooserScene == null)
            chooserScene = new Scene(
                    FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/gamechooserscreen/gameChooser.fxml"))

            );


        stage.setScene(chooserScene);
        stage.show();
    }

    /*=========================================================================================*/

    @Override
    public void switchToSignUpScreen() {
        try {
            showSignUpScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToLoginScreen() {
        try {
            showLoginScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToGameChooserScreen() {
        try {
            showChooserScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToGameWithOtherPlayerScreen() {
        try {
            showGameScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToGameWithCpuScreen() {
        try {
            showGameWithCpuScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToGameChooser() {
        try {
            showChooserScene(applicationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToastMessage(String text) {
        if (text != null && !text.isEmpty())
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(applicationStage, text, 2000, 500, 500);
                }
            });
    }

    /*=========================================================================================*/
    public static void main(String[] args) {
        System.out.println("This is the client!");
        launch(args);
    }

}

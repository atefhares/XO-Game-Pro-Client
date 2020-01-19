/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8;

import com.itijavafinalprojectteam8.view.gamechooserscreen.GameChooserController;
import com.itijavafinalprojectteam8.view.gamewithcpu.GameWithCpuController;
import com.itijavafinalprojectteam8.view.gamewithplayer.GameWithPlayerController;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.login.LoginController;
import com.itijavafinalprojectteam8.view.others.Toast;
import com.itijavafinalprojectteam8.view.signup.SignupController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author ahares
 */

public class GameApplication extends Application implements GameAppView {

    /*=====================================================================*/

    private Pane signInPane;
    private Pane signUpPane;
    private Pane whichPlayModePane;
    private Pane playWithCpuPane;
    private SplitPane playWithPlayerPane;
    private ArrayList<Object> panes = new ArrayList<>();

    private Scene mMainScene;
    private StackPane mRootPane;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 650;
    private Stage applicationStage;
    /*=====================================================================*/

    @Override
    public void init() throws Exception {
        super.init();

        LoginController.setApplicationCallback(this);
        SignupController.setApplicationCallback(this);
        GameChooserController.setApplicationCallback(this);
        GameWithCpuController.setApplicationCallback(this);
        GameWithPlayerController.setAppInterface(this);


        // init app scene
        mRootPane = new StackPane();
        mMainScene = new Scene(mRootPane, WIDTH, HEIGHT);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(Constants.GAME_TITLE);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setScene(mMainScene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            System.out.println("exit called");
            System.exit(0);
        });


        switchToLoginScreen();

        applicationStage = stage;
    }

    /*=========================================================================================*/

    @Override
    public void switchToSignUpScreen() {
        try {
            if (signUpPane == null) {
                signUpPane = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/signup/signup.fxml"));
                panes.add(signUpPane);
            }

            mRootPane.getChildren().removeAll(panes);
            mRootPane.getChildren().add(signUpPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToLoginScreen() {
        try {

            if (signInPane == null) {
                signInPane = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/login/login.fxml"));
//                signInPane.setPrefHeight(HEIGHT);
//                signInPane.setPrefWidth(WIDTH);
                panes.add(signInPane);
            }

            mRootPane.getChildren().removeAll(panes);
            mRootPane.getChildren().add(signInPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToGameWithOtherPlayerScreen() {
        try {

            if (playWithPlayerPane == null) {
                playWithPlayerPane = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/gamewithplayer/playwithotherpalyer.fxml"));
                panes.add(playWithPlayerPane);
            }

            mRootPane.getChildren().removeAll(panes);
            mRootPane.getChildren().add(playWithPlayerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToGameWithCpuScreen() {
        try {

            if (playWithCpuPane == null) {
                playWithCpuPane = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/gamewithcpu/playwithcpu.fxml"));
                panes.add(playWithCpuPane);
            }
            mRootPane.getChildren().removeAll(panes);
            mRootPane.getChildren().add(playWithCpuPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToGameChooser() {
        try {

            if (whichPlayModePane == null) {
                whichPlayModePane = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/gamechooserscreen/gameChooser.fxml"));
                panes.add(whichPlayModePane);
            }

            mRootPane.getChildren().removeAll(panes);
            mRootPane.getChildren().add(whichPlayModePane);

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

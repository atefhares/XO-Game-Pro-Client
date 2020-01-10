/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8;

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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


/**
 * @author ahares
 */

public class GameApplication extends Application implements GameAppView {

    /*=====================================================================*/
    @FXML
    private AnchorPane signInPane;
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private Stage applicationStage;
    @FXML
    private Scene OnlyScene;
    

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
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(Constants.GAME_TITLE);
        stage.getIcons().add(new Image(GameApplication.class.getResourceAsStream("icon.png")));
        stage.setResizable(false);

        applicationStage = stage;

        OnlyScene = new Scene(
                    FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/login/login.fxml"))
            ,820,525);


        stage.setScene(OnlyScene);
        stage.show();
    }

    /*=========================================================================================*/
    
    private void showLoginScene(Stage stage) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/login/login.fxml"));
        
        Scene scene = OnlyScene;
        root.translateXProperty().set(scene.getHeight());
        StackPane parentContainer = (StackPane) OnlyScene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(signInPane);
        });
        
        timeline.play();   
    }
    
    private void showsignUpPane(Stage stage) throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/signup/signup.fxml"));
        
        Scene scene = OnlyScene;
        root.translateXProperty().set(scene.getHeight());
        StackPane parentContainer = (StackPane) OnlyScene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(signInPane);
        });
        
        timeline.play();
        
        }        
    
    /*=========================================================================================*/

    @Override
    public void switchToSignUpScreen() {
        try {
            showsignUpPane(applicationStage);
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


    @Override
    public void switchToGameScreen() {
        // TODO: 1/9/20
    }

    /*=========================================================================================*/
    public static void main(String[] args) {
        System.out.println("This is the client!");
        launch(args);
    }

}

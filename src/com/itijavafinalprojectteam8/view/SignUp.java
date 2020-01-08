/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author shimaa 
 */
public class SignUp  extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); 
        System.out.println(getClass());
        //E:\Local Disk\ITI - 9 month\Java\Java Project\gitProject\src\com\itijavafinalprojectteam8\view\fxml\signUp_form.fxml
            Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("Sign Up");
        Scene scene =  new Scene(root, 400, 800);
        scene.getStylesheets().add("/CSS/signUpStyleForm.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import playercontroller.PlayerController;
import static playercontroller.PlayerController.*;

/**
 *
 * @author moham
 */
public class Game extends Application {
   
   /* StringProperty status = new SimpleStringProperty("Starting scan...");
      @FXML
void displayFiles(ActionEvent event) throws Exception{
    // model = new Model();
    PlayerController Player =new PlayerController();
          ChangeListener<String> listener = (obs, oldStatus, newStatus)->System.out.println();
    Player.status.addListener(listener);

    Thread thread = new Thread(Player);

    thread.start();
}*/
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameFXML.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            
                        primaryStage.show();

            
          
          
            //            System.out.println("asdadasdsd");
                                   
          
           /* for(int i=0;i<Player.Player_Vector.size();i++){
           Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    status.set(Player.Player_Vector.get(i).getPlayer_Name());
                }
            });}
             // TextField Txt=new TextField();
            //Txt.setText("asasdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
           // anchorPane.getChildren().add(Txt);            
          /* new Thread(new Runnable() {
             @Override
             public void run() {
                 while(true){
                     try {
                         /*
                         synchronized(Player){
                         Player.wait();
                         //while(true){
                         // for(int i=0;i<PlayerController.Player_Vector.size();i++){
                         System.out.println("meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                         /* FlowPane Rec=new FlowPane();
                         TextField Txt=new TextField();
                         Button SendButton =new Button();
                         Txt.setText(PlayerController.Player_Vector.get(i).getPlayer_Name());
                         Rec.getChildren().add(Txt);
                         Rec.getChildren().add(SendButton);
                         //anchorPane.getChildren().add(Rec);
                     }}}}*//*  }} catch (InterruptedException ex) {
                         Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                     }
               }}}).start();*//*
        } */}catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
       
                        
                     
    
    }
    
    
   
}

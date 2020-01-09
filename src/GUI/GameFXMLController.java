/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import playercontroller.PlayerController;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author moham
 */
public class GameFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
private SplitPane sPane;
        
      
        @FXML
private Button dark_blue;
        @FXML
private AnchorPane anchorPane;
            @FXML
private Button Refresh;

PlayerController Player= new PlayerController();
 List<BorderPane> Players=new ArrayList<>(); 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    try{    
          BackgroundImage myBI= new BackgroundImage(new Image("GUI/neon-signage.jpg",650,600,false,true,true),
          BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT); 
  
sPane.setBackground(new Background(myBI));

dark_blue.setStyle("-fx-background-color: \n" +
"        #090a0c,\n" +
"        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
"        linear-gradient(#20262b, #191d22),\n" +
"        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
"    -fx-background-radius: 5,4,3,5;\n" +
"    -fx-background-insets: 0,1,2,0;\n" +
"    -fx-text-fill: white;\n" +
"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
"    -fx-font-family: \"Arial\";\n" +
"    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
"    -fx-font-size: 12px;\n" +
"    -fx-padding: 10 20 10 20;");
    
                dark_blue.setOnAction(new EventHandler<ActionEvent>() {
        @Override
    public void handle(ActionEvent e) {
         Node  source = (Node)  e.getSource(); 
    Stage stage  = (Stage) source.getScene().getWindow();
    stage.close();
                }
});
                Refresh.setOnAction(new EventHandler<ActionEvent>() {
        @Override
    public void handle(ActionEvent e) {
         Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    
                   
                    System.out.println(Player.Player_Vector.size());
                 for(int i=0;i<Player.Player_Vector.size();i++){
                    Label txt=new Label();
                    txt.setText(Player.Player_Vector.get(i).getPlayer_Name());
                    if(Player.Player_Vector.get(i).getPlayer_Status()==0)
                    txt.setText(txt.getText()+"\noffline\n\n");
                    else if(Player.Player_Vector.get(i).getPlayer_Status()==1)
                    txt.setText(txt.getText()+"\nonline\n\n");
                    else if(Player.Player_Vector.get(i).getPlayer_Status()==2)
                    txt.setText(txt.getText()+"\nPlaying\n\n");
                    txt.setLayoutX(85.0);
                    txt.setLayoutY((i+10)*20.0);
                    txt.setScaleX(4.471587334304658);
                    txt.setScaleY(1.0);
                    txt.setFont(new Font(10.0));
                 //   Players.add(txt);
                       
                }
                  //anchorPane.getChildren().addAll(Players);
                  
                }
                
            });
         
         
           
                }
});
                 
        Player.usersProperty().addListener(new ChangeListener<String>() {

                            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  System.out.println("this is inside the controller");
                System.out.println(MessageFormat.format("The name changed from \"{0}\" to \"{1}\"", oldValue, newValue));
                JSONObject RESPONSE = new JSONObject(newValue);
                    JSONArray Data_Array= RESPONSE.getJSONArray("Data");
                        for (int i = 0; i < Data_Array.length(); i++) {
                           System.out.println("Data   "+Data_Array.getJSONObject(i).getString("Email"));
                            //Data_Array.getJSONObject(i).getString("Email");
                            
                            //Integer.parseInt(Data_Array.getJSONObject(i).getString("Points"));
                            
                             BorderPane Plate = new BorderPane();
                     Label txt=new Label();
                      Label txt2=new Label();
                    txt.setText(Data_Array.getJSONObject(i).getString("Name"));
                    Plate.setCenter(txt);
                        try {
                            ImageView img1 = new ImageView();
                            Image image1 = new Image(new File("icon8-user-24.png").toURI().toURL().toExternalForm());
                            img1.setImage(image1);
                            Plate.setRight(img1);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(GameFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    if(Integer.parseInt(Data_Array.getJSONObject(i).getString("Status"))==0)
                    txt2.setText(txt.getText()+"\noffline\n\n");
                    else if(Integer.parseInt(Data_Array.getJSONObject(i).getString("Status"))==1)
                    txt2.setText(txt.getText()+"\nonline\n\n");
                    else if(Integer.parseInt(Data_Array.getJSONObject(i).getString("Status"))==2)
                    txt2.setText(txt.getText()+"\nPlaying\n\n");
                         Plate.setLeft(txt2);
                         Plate.setPrefHeight(35.0);
                          Plate.setPrefWidth(200.0);
                        Players.add(Plate);
                        }
                        anchorPane.getChildren().addAll(Players);
                }
            });              
                
            
            
            }
            
        });
  
    }
    catch(Exception ex){ex.getMessage();}
       

    }    
     
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8.view.gamewithplayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.Player;
import com.itijavafinalprojectteam8.controller.AiLibrary;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author moham
 */
public class GameUiController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> images;
    @FXML
    private TableColumn<Player, String> Player_Name;
    public ObservableList<Player> list = FXCollections.observableArrayList();

    @FXML
    private Button dark_blue;
  @FXML
    private TableView<ClientController> table2;
    @FXML
    private TableColumn<ClientController, String> gameTable;


  @FXML
    private Button list_Game;
    @FXML
    private Button list_Player;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;

    @FXML
    private AnchorPane anchorPane;
            

            ImageView image1 = new ImageView(new Image(this.getClass().getResourceAsStream("on.png")));
            ImageView image2 = new ImageView(new Image(this.getClass().getResourceAsStream("off.png")));
 Player p1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
           images.setCellValueFactory(new PropertyValueFactory<>("images"));
            Player_Name.setCellValueFactory(new PropertyValueFactory<Player, String>("Player_Name"));
           
            ClientController.sendToServer(JsonOperations.getPlayersJson());
          //  ClientController.sendToServer(JsonOperations.getGamesJson());
             ClientController.setgameUicontroller(this);
           
            dark_blue.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Node source = (Node) e.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            });

           list_Game.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    table2.setVisible(true);
                    table.setVisible(false);

                }

            });

            list_Player.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    table.setVisible(true);
                    table2.setVisible(false);

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
       
        
        

    }

    @FXML
    public void clickButton(ActionEvent event) throws IOException {
        final Node source = (Node) event.getSource();
        String id = source.getId();

        char c = id.charAt(id.length() - 1);
        int idnum = Character.getNumericValue(c);
        System.out.println(idnum);
        drawInGui(idnum, 'X', Color.YELLOW);
        int cpuPosition = AiLibrary.cpuMove();
        drawInGui(cpuPosition, 'O', Color.RED);
        AiLibrary.onPlayerMove(1);
        
        int result = AiLibrary.getWinner();
        switch (result) {
            case 0:
                //player won
                System.out.println("player win");
                return;
            case 1:
                //cpu won
                System.out.println("cpu win");
            case 2:
                // draw
                System.out.println("OH !! NO Its a Draw");
        }

        int cpuPos = AiLibrary.onCpuMove();
        //show O on ui at pos = cpuPos

        result = AiLibrary.getWinner();
        switch (result) {
            case 0:
                //player won
                System.out.println("player win");
            case 1:
                //cpu won
                System.out.println("cpu win");
            case 2:
                // draw
                System.out.println("OH !! NO Its a Draw");
        }

    }

    public void drawInGui(int pos, char c, Color color) {

        switch (pos) {
            case 1:
                b1.setText(String.valueOf(c));
                //b1.setStyle("-fx-text-fill: yellow;-fx-background-color:none;-fx-padding:10px");
                b1.setTextFill(color);
                break;

            case 2:
                b2.setText(String.valueOf(c));
                b2.setTextFill(color);
                break;

            case 3:
                b3.setText(String.valueOf(c));
                b3.setTextFill(color);
                break;

            case 4:
                b4.setText(String.valueOf(c));
                b4.setTextFill(color);
                break;

            case 5:
                b5.setText(String.valueOf(c));
                b5.setTextFill(color);
                break;

            case 6:
                b6.setText(String.valueOf(c));
                b6.setTextFill(color);
                break;

            case 7:
                b7.setText(String.valueOf(c));
                b7.setTextFill(color);
                break;

            case 8:
                b8.setText(String.valueOf(c));
                b8.setTextFill(color);
                break;

            case 9:
                b9.setText(String.valueOf(c));
                b9.setTextFill(color);
                break;
        }
    }
   // @FXML
public void fillTableData(String text)
{
    
                 Platform.runLater(new Runnable() {
                @Override
                public void run() {
    System.out.println(text);
                    System.out.println("this is inside the controller");
                JSONObject RESPONSE = new JSONObject(text);
               
                    JSONArray Data_Array= RESPONSE.getJSONArray(Constants.ConnectionTypes.TYPE_GET_ALL_PLAYERS);
                   
                       for (int i = 0; i < Data_Array.length(); i++) {
                           System.out.println("Data   "+Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL)); 
                           
                           
                           int stat = Data_Array.getJSONObject(i).getInt(Constants.JsonKeys.KEY_USER_STATUS);
                           if(stat==1)
                           {
                           p1 = new Player(image1,Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                           }
                           else
                           {
                            p1 = new Player(image2,Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                           }
                            list.add(p1);
                            

                        }
                  table.setItems(list);     
                }
            });              
                
            
            
            
}
}

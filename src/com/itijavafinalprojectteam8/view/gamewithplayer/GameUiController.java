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

import com.itijavafinalprojectteam8.Player;
import com.itijavafinalprojectteam8.controller.AiLibrary;
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
import java.net.URL;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            images.setCellValueFactory(new PropertyValueFactory<>("images"));
            Player_Name.setCellValueFactory(new PropertyValueFactory<Player, String>("Player_Name"));

            ImageView image1 = new ImageView(new Image(this.getClass().getResourceAsStream("on.png")));
            ImageView image2 = new ImageView(new Image(this.getClass().getResourceAsStream("on.png")));
            ImageView image3 = new ImageView(new Image(this.getClass().getResourceAsStream("on.png")));

            ImageView image4 = new ImageView(new Image(this.getClass().getResourceAsStream("off.png")));
            ImageView image5 = new ImageView(new Image(this.getClass().getResourceAsStream("off.png")));
            Player p1 = new Player(image1, "esraa");
            Player p2 = new Player(image2, "shimaa");
            Player p3 = new Player(image3, "atef");
            Player p4 = new Player(image4, "bassam");
            Player p5 = new Player(image5, "aboseree");

            list.add(p1);
            list.add(p2);
            list.add(p3);
            list.add(p4);
            list.add(p5);

            table.setItems(list);

            dark_blue.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Node source = (Node) e.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
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

}

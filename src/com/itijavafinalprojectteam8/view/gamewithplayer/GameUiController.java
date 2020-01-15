package com.itijavafinalprojectteam8.view.gamewithplayer;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.Player;
import com.itijavafinalprojectteam8.controller.AiLibrary;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.controller.Props;
import com.itijavafinalprojectteam8.view.interfaces.GameWithPlayerView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameUiController implements Initializable, GameWithPlayerView {

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



    Player p1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            images.setCellValueFactory(new PropertyValueFactory<>("images"));
            Player_Name.setCellValueFactory(new PropertyValueFactory<Player, String>("Player_Name"));

            ClientController.sendToServer(JsonOperations.getAllPlayersJson());
            ClientController.setGameUiController(this);

            dark_blue.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Node source = (Node) e.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            });
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    showAlertWithHeaderText(newSelection.getPlayer_Email());


                }
            });


            Props.allPlayersServerResponse.addListener((observableValue, oldValue, newValue) -> {
                if(!oldValue.equals(newValue)){
                    fillTableData(newValue);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Show a Information Alert with header Text
    private void showAlertWithHeaderText(String Email) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game request");
        alert.setHeaderText("Send Invetation:");
        alert.setContentText("You are about to send " + Email + "an invetation procced?");

        alert.showAndWait();

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
    public void fillTableData(String text) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(text);
                System.out.println("this is inside the controller");



                JSONObject RESPONSE = new JSONObject(text);

                JSONArray Data_Array = RESPONSE.getJSONArray(Constants.ConnectionTypes.TYPE_GET_ALL_PLAYERS);

                list.clear();
                for (int i = 0; i < Data_Array.length(); i++) {

                    ImageView onlineImageView;
                    ImageView offlineImageView;
                    
//                    System.out.println("Data   " + Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
//                    System.out.println(ClientController.Email);

                    offlineImageView = new ImageView(new Image(this.getClass().getResourceAsStream("off.png")));
                    onlineImageView= new ImageView(new Image(this.getClass().getResourceAsStream("on.png")));

                    int stat = Data_Array.getJSONObject(i).getInt(Constants.JsonKeys.KEY_USER_STATUS);
                    if (stat == 1 || stat == 2) {
                        p1 = new Player(onlineImageView, Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                        p1.setPlayer_Email(Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                    } else {
                        p1 = new Player(offlineImageView, Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                        p1.setPlayer_Email(Data_Array.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                    }
                    list.add(p1);
                }


                table.setItems(list);
            }
        });


    }
}

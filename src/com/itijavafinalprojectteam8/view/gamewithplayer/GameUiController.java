package com.itijavafinalprojectteam8.view.gamewithplayer;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.model.Player;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.interfaces.GameWithPlayerView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class GameUiController implements Initializable, GameWithPlayerView {

        private static GameAppView mApplicationCallback;

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

    private Image offlineImage = new Image(this.getClass().getResourceAsStream("off.png"));
    private Image onlineImage = new Image(this.getClass().getResourceAsStream("on.png"));
        public static  void setAppInterface(GameAppView view)
        {
        mApplicationCallback = view;
        }
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

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Show a Information Alert with header Text
    private void showAlertWithHeaderText(String email) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Send game invitation");
        alert.setContentText("You are about to send " + email + "an invitation, proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == javafx.scene.control.ButtonType.OK) {
            //oke button is pressed
            System.out.println("pressed ok");
            try {
                String emailPlayer = JsonOperations.getInvitationJson(email);
                ClientController.sendToServer(emailPlayer);

            } catch (Exception e) {

                e.printStackTrace();
            }

            alert.close();
        } else if (result.get() == ButtonType.CANCEL) {
            // cancel button is pressed
            alert.close();
        }

        alert.showAndWait();
    }

    // Show a Information Alert with header Text
    @Override
    public void onGameInvitationRequest(String Email) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Game invitation");
                // alert.setHeaderText("recieve Invetation:");
                alert.setContentText(Email + " sent you a Game invetation");
                Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();

                if (result.get() == javafx.scene.control.ButtonType.OK) {
                    //oke button is pressed
                    System.out.println("pressed ok");
                    try {
                        ClientController.sendToServer(JsonOperations.sendInvitationResponse(Email, true));
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else if (result.get() == javafx.scene.control.ButtonType.CANCEL) {
                    // cancel button is pressed
                    try {
                        ClientController.sendToServer(JsonOperations.sendInvitationResponse(Email, false));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }


    public void onGameInvitationResponse() {

        //   Platform.runLater(() -> {


        //    if (mApplicationCallback != null)
        //    mApplicationCallback.showToastMessage(errorMsgFromServer);
//        }

    }

    @FXML
    public void clickButton(ActionEvent event) throws IOException {
        final Node source = (Node) event.getSource();
        String id = source.getId();

        char c = id.charAt(id.length() - 1);
        int idnum = Character.getNumericValue(c);
        System.out.println(idnum);

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

        Platform.runLater(() -> {
            System.out.println(text);
            System.out.println("this is inside the controller");

            JSONArray dataArray = new JSONArray(text);

            list.clear();

            for (int i = 0; i < dataArray.length(); i++) {

                ImageView offlineImageView = new ImageView(offlineImage);
                ImageView onlineImageView = new ImageView(onlineImage);

                int stat = dataArray.getJSONObject(i).getInt(Constants.JsonKeys.KEY_USER_STATUS);
                Player player;
                if (stat == 1 || stat == 2) {
                    player = new Player(onlineImageView, dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                } else {
                    player = new Player(offlineImageView, dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                }
                player.setPlayer_Email(dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                list.add(player);
            }


            table.setItems(list);
        });


    }
        @Override
    public void confirmToast(boolean response) {

        Platform.runLater(() -> {
            System.out.println("response"+response);
                if(response){
                 if (mApplicationCallback != null)
                    
                     mApplicationCallback.showToastMessage("Player Accepted your invitiation");
                }else
                {
                if (mApplicationCallback != null)
                    mApplicationCallback.showToastMessage("Player Rejected your invitiation");
                }
                    

           
        });


    }


    @Override
    public void onGetAllPlayers(String response) {
        fillTableData(response);
    }
}

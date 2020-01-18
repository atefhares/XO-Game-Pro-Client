package com.itijavafinalprojectteam8.view.gamewithplayer;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.controller.AiLibrary;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.controller.Props;
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


public class GameWithPlayerController implements Initializable, GameWithPlayerView {

    private static GameAppView mApplicationCallback;
    @FXML
    private Label howWinner;

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
    private static boolean menuFlag = true;
    public static boolean flag = false;
    public static String oppsiteEmail;
    public static String ch;
    public static String opch;
    private Image offlineImage = new Image(this.getClass().getResourceAsStream("off.png"));
    private Image onlineImage = new Image(this.getClass().getResourceAsStream("on.png"));

    public static void setAppInterface(GameAppView view) {
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
                    System.exit(0);
                }
            });
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null && menuFlag) {
                    System.out.println("hihihi");

                    showAlertWithHeaderText(newSelection.getPlayer_Email(), newSelection.getPlayer_Status());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Show a Information Alert with header Text
    private void showAlertWithHeaderText(String email, int status) {
        System.out.println("status is   " + status);
        if (status == 1) {
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
                    oppsiteEmail = email;
                    menuFlag = false;


                } catch (Exception e) {

                    e.printStackTrace();
                }

                alert.close();
            } else if (result.get() == ButtonType.CANCEL) {
                // cancel button is pressed
                alert.close();
            }

        }
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
                    //ok button is pressed
                    System.out.println("pressed ok");
                    try {
                        ClientController.sendToServer(JsonOperations.sendInvitationResponse(Email, true));
                        flag = true;
                        oppsiteEmail = Email;
                        ch = "o";
                        opch = "X";
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


    @FXML
    public void clickButton(ActionEvent event) throws IOException {
        try {
            final Node source = (Node) event.getSource();
            String id = source.getId();

            char c = id.charAt(id.length() - 1);
            int idnum = Character.getNumericValue(c);
            System.out.println("flag is " + flag);

            if (!(AiLibrary.playerPosition.contains(idnum) || AiLibrary.cpuPosition.contains(idnum)) && flag) {
                AiLibrary.onPlayerMove(idnum);

                drawInGui(idnum, ch, Color.YELLOW);
                flag = false;
                System.out.println("oppsite email is " + oppsiteEmail);
                ClientController.sendToServer(JsonOperations.sendGamecord(oppsiteEmail, idnum));
                int result = AiLibrary.getWinner();
                System.out.println("result is " + result);

                switch (result) {
                    case 0:
                        //player won
                        howWinner.setText("Congratulation You Are Winner");
                        howWinner.setStyle("-fx-background-color:#fff;");
                        flag = false;
                        break;
                    case 1:
                        //cpu won
                        howWinner.setText("You Lose");
                        howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                        flag = false;
                        break;
                    case 2:
                        // draw
                        howWinner.setText("OH !! NO Its a Draw");
                        howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                        flag = false;
                        break;

                }
            } else {
                System.out.println("this is the error");
            }
        } catch (Exception ex) {
            ex.getMessage();
        }


    }

    public void drawInGui(int pos, String c, Color color) {

        switch (pos) {
            case 1:
                b1.setText(c);
                //b1.setStyle("-fx-text-fill: yellow;-fx-background-color:none;-fx-padding:10px");
                b1.setTextFill(color);
                break;

            case 2:
                b2.setText(c);
                b2.setTextFill(color);

                break;

            case 3:
                b3.setText(c);
                b3.setTextFill(color);

                break;

            case 4:
                b4.setText(c);
                b4.setTextFill(color);
                break;

            case 5:
                b5.setText(c);
                b5.setTextFill(color);
                break;

            case 6:
                b6.setText(c);
                b6.setTextFill(color);
                break;

            case 7:
                b7.setText(c);
                b7.setTextFill(color);
                break;

            case 8:
                b8.setText(c);
                b8.setTextFill(color);
                break;

            case 9:
                b9.setText(c);
                b9.setTextFill(color);
                break;
        }
    }

    public void fillTableData(String text) {

        Platform.runLater(() -> {
            System.out.println(text);
            System.out.println("this is inside the controller");

            JSONArray dataArray = new JSONArray(text);

            list.clear();

            for (int i = 0; i < dataArray.length(); i++) {
                if (!Props.mCurrentPlayer.getPlayer_Email().equals(dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL).trim())) {
                    ImageView offlineImageView = new ImageView(offlineImage);
                    ImageView onlineImageView = new ImageView(onlineImage);

                    int stat = dataArray.getJSONObject(i).getInt(Constants.JsonKeys.KEY_USER_STATUS);
                    Player player;
                    if (stat == 1 || stat == 2) {
                        player = new Player(onlineImageView, dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL), stat);
                    } else {
                        player = new Player(offlineImageView, dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL), stat);
                    }
                    player.setPlayer_Email(dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                    list.add(player);
                }
            }


            table.setItems(list);

        });


    }

    @Override
    public void confirmToast(boolean response) {

        Platform.runLater(() -> {
            System.out.println("response" + response);
            if (response) {
                if (mApplicationCallback != null)

                    mApplicationCallback.showToastMessage("Player Accepted your invitiation");
                flag = true;
                ch = "X";
                opch = "o";
                menuFlag = true;
            } else {
                if (mApplicationCallback != null)
                    mApplicationCallback.showToastMessage("Player Rejected your invitiation");
                menuFlag = true;
            }


        });


    }


    @Override
    public void onGetAllPlayers(String response) {
        fillTableData(response);
    }

    @Override
    public void setGamecord(int gamecord) {
        System.out.println("this is inside the game  " + gamecord + " flag is " + flag);
        Platform.runLater(() -> {
            if (AiLibrary.playerPosition.size() + AiLibrary.cpuPosition.size() < 11) {
                int cpuPosition = gamecord;
                AiLibrary.onPlayer2Move(gamecord);
                drawInGui(cpuPosition, opch, Color.RED);
                flag = true;
                int result = AiLibrary.getWinner();
                System.out.println("result is " + result);

                switch (result) {
                    case 0:
                        //player won
                        howWinner.setText("Congratulation You Are Winner");
                        howWinner.setStyle("-fx-background-color:#fff;");
                        flag = false;
                        break;
                    case 1:
                        //cpu won
                        howWinner.setText("You Lose");
                        howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                        flag = false;
                        break;
                    case 2:
                        // draw
                        howWinner.setText("OH !! NO Its a Draw");
                        howWinner.setStyle("-fx-background-color:#fff; -fx-border-radius:10px;");
                        flag = false;
                        break;

                }


            }
        });
    }
}

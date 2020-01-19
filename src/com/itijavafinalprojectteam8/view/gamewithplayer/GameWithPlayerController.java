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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameWithPlayerController implements Initializable, GameWithPlayerView {

    private static GameAppView mApplicationCallback;
    @FXML
    private Label winnerLabel;

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> images;
    @FXML
    private TableColumn<Player, String> Player_Name;

    public ObservableList<Player> list = FXCollections.observableArrayList();

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
    private Pane gameOverPane;

    @FXML
    private Label playerDetails;

    private static boolean menuisGameStarted = true;
    private boolean isGameStarted = false;

    public static String oppsiteEmail;
    public static String ch = "X";
    public static String opch = "o";
    private Image offlineImage = new Image(this.getClass().getResourceAsStream("off.png"));
    private Image onlineImage = new Image(this.getClass().getResourceAsStream("on.png"));

    public static void setAppInterface(GameAppView view) {
        mApplicationCallback = view;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            table.setColumnResizePolicy(resizeFeatures -> true);
            table.setSortPolicy(playerTableView -> true);

            images.setCellValueFactory(new PropertyValueFactory<>("images"));
            Player_Name.setCellValueFactory(new PropertyValueFactory<Player, String>("Player_Name"));

            ClientController.sendToServer(JsonOperations.createGetAllPlayersJson());
            ClientController.setGameUiController(this);

            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null && menuisGameStarted) {
                    System.out.println("hihihi");

                    showAlertWithHeaderText(newSelection.getPlayer_Email(), newSelection.getPlayer_Status());
                }
            });

            resetGame();

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
                    String emailPlayer = JsonOperations.createInvitationJson(email);
                    ClientController.sendToServer(emailPlayer);
                    oppsiteEmail = email;
                    menuisGameStarted = false;


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
        Platform.runLater(() -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game invitation");
            alert.setContentText(Email + " sent you a Game invitation");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                //ok button is pressed
                System.out.println("pressed ok");
                try {
                    ClientController.sendToServer(JsonOperations.createInvitationResponseJson(Email, true));
                    // resetGame();

                    isGameStarted = true;
                    oppsiteEmail = Email;
                    if (ch.equals("o")) {
                        ch = "o";
                        opch = "X";
                    } else {
                        ch = "X";
                        opch = "o";
                    }
                    gameOverPane.setVisible(false);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                // cancel button is pressed
                try {
                    ClientController.sendToServer(JsonOperations.createInvitationResponseJson(Email, false));
                } catch (IOException e) {
                    e.printStackTrace();
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
            System.out.println("isGameStarted is " + isGameStarted);

            if (!(AiLibrary.playerPosition.contains(idnum) || AiLibrary.cpuPosition.contains(idnum)) && isGameStarted) {
                AiLibrary.onPlayerMove(idnum);

                drawInGui(idnum, ch, Color.YELLOW);
                isGameStarted = false;
                System.out.println("oppsite email is " + oppsiteEmail);
                ClientController.sendToServer(JsonOperations.createSendGameCordJson(oppsiteEmail, idnum));
                int result = AiLibrary.getWinner();
                System.out.println("result is " + result);

                handleGameResult(result);

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

                    mApplicationCallback.showToastMessage("Player Accepted your invitation");
                //resetGame();

                isGameStarted = true;
                if (ch.equals("X")) {
                    ch = "X";
                    opch = "o";
                } else {
                    ch = "o";
                    opch = "X";
                }
                menuisGameStarted = false;


                gameOverPane.setVisible(false);

            } else {
                if (mApplicationCallback != null)
                    mApplicationCallback.showToastMessage("Player Rejected your invitation");

                resetGame();


                menuisGameStarted = true;
                isGameStarted = false;
            }
        });


    }

    @Override
    public void onGetAllPlayers(String response) {
        fillTableData(response);
    }

    @Override
    public void setGamecord(int gamecord) {
        System.out.println("this is inside the game  " + gamecord + " isGameStarted is " + isGameStarted);
        Platform.runLater(() -> {
            if (AiLibrary.playerPosition.size() + AiLibrary.cpuPosition.size() < 11) {
                int cpuPosition = gamecord;
                AiLibrary.onPlayer2Move(gamecord);
                drawInGui(cpuPosition, opch, Color.RED);
                isGameStarted = true;
                int result = AiLibrary.getWinner();
                System.out.println("result is " + result);


                try {
                    handleGameResult(result);
                } catch (IOException ex) {
                    Logger.getLogger(GameWithPlayerController.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    handleGameResult(result);
                } catch (IOException ex) {
                    Logger.getLogger(GameWithPlayerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void onChangeModeBtnClicked(ActionEvent actionEvent) {
        if (mApplicationCallback != null) {
            mApplicationCallback.switchToGameChooser();
        }
    }


    private void handleGameResult(int result) throws IOException {

        switch (result) {
            case 0:
                //player won
                gameOverPane.setVisible(true);
                winnerLabel.setText("Congratulation! You won....");

                resetGame();

                try {
                    ClientController.sendToServer(JsonOperations.createUpdatePlayerPointsJson());
                    Props.mCurrentPlayer.Player_Points += 10;
                    playerDetails.setText(Props.mCurrentPlayer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ClientController.sendToServer(JsonOperations.gameEnded(oppsiteEmail));


                break;
            case 1:
                //cpu won
                gameOverPane.setVisible(true);
                winnerLabel.setText("You Lost!");
                resetGame();

                ClientController.sendToServer(JsonOperations.gameEnded(oppsiteEmail));

                break;
            case 2:
                // draw
                gameOverPane.setVisible(true);
                winnerLabel.setText("OH !! NO Its a Draw");
                resetGame();
                ClientController.sendToServer(JsonOperations.gameEnded(oppsiteEmail));

                break;

        }
    }

    private void resetGame() {
        Platform.runLater(() -> {
            b1.setText("");
            b2.setText("");
            b3.setText("");
            b4.setText("");
            b5.setText("");
            b6.setText("");
            b7.setText("");
            b8.setText("");
            b9.setText("");
            isGameStarted = false;
            menuisGameStarted = true;
            gameOverPane.setVisible(true);
            winnerLabel.setText("No game started");
            AiLibrary.reset();

            playerDetails.setText(Props.mCurrentPlayer.toString());
        });

    }

    public void onPauseGameClicked(ActionEvent actionEvent) {
        {
            try {
                if (isGameStarted == true) {
                    if (ch.equals("X"))
                        ClientController.sendToServer(JsonOperations.sendGamePause(oppsiteEmail, 1, Props.mCurrentPlayer.getPlayer_Email()));
                    else
                        ClientController.sendToServer(JsonOperations.sendGamePause(oppsiteEmail, 1, oppsiteEmail));
                    resetGame();
                } else {
                    if (ch.equals("X"))
                        ClientController.sendToServer(JsonOperations.sendGamePause(oppsiteEmail, 2, Props.mCurrentPlayer.getPlayer_Email()));
                    else
                        ClientController.sendToServer(JsonOperations.sendGamePause(oppsiteEmail, 2, oppsiteEmail));
                    resetGame();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void pauseGame() {

        resetGame();

    }

    @Override
    public void handelGameResume(String jsontext) {

        Platform.runLater(() -> {

            String rolePlayer = JsonOperations.parseGameResume(jsontext);
            String Arr[] = rolePlayer.split(":");

            if (Arr[1].equals(Props.mCurrentPlayer.getPlayer_Email())) {
                if (ch.equals("X")) {
                    ch = "X";
                    opch = "o";
                } else {
                    ch = "o";
                    opch = "X";
                }
            } else {
                if (ch.equals("o")) {
                    ch = "o";
                    opch = "X";
                } else {
                    ch = "X";
                    opch = "o";
                }
            }
            for (int i = 0; i < AiLibrary.cpuPosition.size(); i++) {
                drawInGui(AiLibrary.cpuPosition.get(i), opch, Color.RED);

            }
            for (int i = 0; i < AiLibrary.playerPosition.size(); i++) {
                drawInGui(AiLibrary.playerPosition.get(i), ch, Color.YELLOW);

            }

            if (Arr[0].trim().equals(Props.mCurrentPlayer.getPlayer_Email())) {
                isGameStarted = true;
            } else {
                isGameStarted = false;
            }


        });

    }

}
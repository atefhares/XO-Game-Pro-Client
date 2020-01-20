package com.itijavafinalprojectteam8.view.gamewithplayer;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.controller.AiLibrary;
import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.controller.Props;
import com.itijavafinalprojectteam8.model.Player;
import com.itijavafinalprojectteam8.view.chat.ChatUIController;
import com.itijavafinalprojectteam8.view.interfaces.ChatScreenView;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.interfaces.GameWithPlayerView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
    @FXML
    private TableColumn<Player, String> playerPoints;

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
    private Label whoIsTurn;

    @FXML
    private Pane gameOverPane;

    @FXML
    private Label playerDetails;

    private boolean isGameStarted = false;
    private boolean shouldWaitForNextPlayerMove = false;

    public static String oppsiteEmail;
    public static String ch = "X";
    public static String opch = "o";
    private Image offlineImage = new Image(this.getClass().getResourceAsStream("off.png"));
    private Image onlineImage = new Image(this.getClass().getResourceAsStream("on.png"));

    public static void setAppInterface(GameAppView view) {
        mApplicationCallback = view;
    }

    private Stage mChatStage;
    private static ChatScreenView mChatScreenView;

    public static void setChatScreenView(ChatScreenView view) {
        mChatScreenView = view;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            table.setEditable(false);
            table.setColumnResizePolicy(resizeFeatures -> true);
            table.setSortPolicy(playerTableView -> true);

            images.setCellValueFactory(new PropertyValueFactory<>("images"));
            Player_Name.setCellValueFactory(new PropertyValueFactory<Player, String>("Player_Name"));
            playerPoints.setCellValueFactory(new PropertyValueFactory<Player, String>("Player_Points"));

            ClientController.sendToServer(JsonOperations.createGetAllPlayersJson());
            ClientController.setGameUiController(this);

            table.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    showAlertWithHeaderText(table.getSelectionModel().getSelectedItem().getPlayer_Email(), table.getSelectionModel().getSelectedItem().getPlayer_Status());
                }
            });

            /*table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (oldSelection == null) {
                    System.out.println("oldSelection is null");
                } else {
                    System.out.println("oldSelection: " + oldSelection);
                }

                if (newSelection == null) {
                    System.out.println("newSelection is null");
                } else {
                    System.out.println("newSelection: " + newSelection);
                }

                if (newSelection != null) {
                    showAlertWithHeaderText(newSelection.getPlayer_Email(), newSelection.getPlayer_Status());
                }
            });*/

            resetGame(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlertWithHeaderText(String email, int status) {
        System.out.println("[showAlertWithHeaderText] status is   " + status);
        if (status == Constants.PlayerStatus.ONLINE_NOT_IN_GAME) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setHeaderText("Send game invitation");
            alert.setContentText("You are about to send " + email + "an invitation, proceed?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == javafx.scene.control.ButtonType.OK) {
                //oke button is pressed
                System.out.println("pressed ok... sending game invitation");
                try {
                    ClientController.sendToServer(JsonOperations.createInvitationJson(email));
                    oppsiteEmail = email;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert.close();
            } else if (result.get() == ButtonType.CANCEL) {
                // cancel button is pressed
                System.out.println("pressed cancel.... nothing");
                alert.close();
            }
        }
    }

    @Override
    public void onGameInvitationResponse(boolean response) {
        System.out.println("[onGameInvitationResponse] response" + response);
        if (response) {
            Platform.runLater(() -> {
                if (mApplicationCallback != null)
                    mApplicationCallback.showToastMessage("Player Accepted your invitation");
            });

            resetGame(true);

            startGame(true);

        } else {
            Platform.runLater(() -> {
                if (mApplicationCallback != null)
                    mApplicationCallback.showToastMessage("Player Rejected your invitation");
            });

            resetGame(true);
            isGameStarted = false;
        }

    }

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

                    resetGame(true);
                    oppsiteEmail = Email;

                    startGame(false);

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

    private void startGame(boolean isCurrentPlayerTheStarter) {
        System.out.println("[startGame] called");
        System.out.println("[startGame] isCurrentPlayerTheStarter: " + isCurrentPlayerTheStarter);

        if (isCurrentPlayerTheStarter) {
            shouldWaitForNextPlayerMove = false;
            ch = "X";
            opch = "O";

            Platform.runLater(() -> {
                whoIsTurn.setVisible(true);
                whoIsTurn.setText("Your turn");
            });

        } else {
            shouldWaitForNextPlayerMove = true;
            ch = "O";
            opch = "X";
            Platform.runLater(() -> {
                whoIsTurn.setVisible(true);
                whoIsTurn.setText("Not your turn");
            });
        }

        isGameStarted = true;

        Platform.runLater(() -> {
            gameOverPane.setVisible(false);
        });

        Props.mCurrentPlayer.setPlayer_Status(Constants.PlayerStatus.ONLINE_IN_GAME);

        AiLibrary.reset();
    }

    private void resetGame(boolean setWinnerLabelToDefault) {
        System.out.println("[resetGame] called");

        Props.mCurrentPlayer.setPlayer_Status(Constants.PlayerStatus.ONLINE_NOT_IN_GAME);
        isGameStarted = false;
        shouldWaitForNextPlayerMove = false;
        AiLibrary.reset();
        ch = "";
        opch = "";

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

            playerDetails.setText(Props.mCurrentPlayer.toString());

            if (setWinnerLabelToDefault)
                winnerLabel.setText("No game started");

            gameOverPane.setVisible(true);

            whoIsTurn.setVisible(false);
            whoIsTurn.setText("");
        });

    }

    /*===============================================================================*/

    @FXML
    public void onGameBtnClicked(ActionEvent event) {
        try {
            System.out.println("[onGameBtnClicked] oppsiteEmail: " + oppsiteEmail);
            System.out.println("[onGameBtnClicked] isGameStarted: " + isGameStarted);
            System.out.println("[onGameBtnClicked] shouldWaitForNextPlayerMove: " + shouldWaitForNextPlayerMove);

            if (!isGameStarted) {
                System.out.println("[onGameBtnClicked] Game not started... return");
                return;
            }

            if (shouldWaitForNextPlayerMove) {
                System.out.println("[onGameBtnClicked] shouldWaitForNextPlayerMove is TRUE... return");
                return;
            }

            int clickedPos = getClickedPosition(event);

            if (!AiLibrary.playerPosition.contains(clickedPos) && !AiLibrary.cpuPosition.contains(clickedPos)) {

                shouldWaitForNextPlayerMove = true;

                ClientController.sendToServer(JsonOperations.createSendGameCordJson(oppsiteEmail, clickedPos));

                AiLibrary.onPlayerMove(clickedPos);
                drawInGui(clickedPos, ch, Color.YELLOW);

                //check game result
                checkGameResult();

            } else {
                System.out.println("[onGameBtnClicked] position already taken");
            }
        } catch (Exception ex) {
            ex.getMessage();
        }


    }

    private int getClickedPosition(ActionEvent event) {
        final Node source = (Node) event.getSource();
        String id = source.getId();

        char c = id.charAt(id.length() - 1);
        int idnum = Character.getNumericValue(c);

        System.out.println("[getClickedPosition] pos: " + idnum);
        return idnum;
    }

    public void drawInGui(int pos, String c, Color color) {
        whoIsTurn.setVisible(true);
        if (shouldWaitForNextPlayerMove) {
            whoIsTurn.setText("Not your turn");
        } else {
            whoIsTurn.setText("Your turn");
        }
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

    @Override
    public void onGamePlayMoveReceived(int gamecord) {
        Platform.runLater(() -> {

            System.out.println("[onGamePlayMoveReceived] oppsiteEmail: " + oppsiteEmail);
            System.out.println("[onGamePlayMoveReceived] isGameStarted: " + isGameStarted);
            System.out.println("[onGamePlayMoveReceived] shouldWaitForNextPlayerMove: " + shouldWaitForNextPlayerMove);
            System.out.println("[onGamePlayMoveReceived] gamecord: " + gamecord);
            System.out.println("[onGamePlayMoveReceived] AiLibrary.playerPosition.size() + AiLibrary.cpuPosition.size(): " +
                    (AiLibrary.playerPosition.size() + AiLibrary.cpuPosition.size())
            );

            if (!isGameStarted) {
                System.out.println("[onGamePlayMoveReceived] Game not started... return");
                return;
            }

            shouldWaitForNextPlayerMove = false;

            if (AiLibrary.playerPosition.size() + AiLibrary.cpuPosition.size() < 11) {
                AiLibrary.onPlayer2Move(gamecord);

                drawInGui(gamecord, opch, Color.RED);
                //check game result
                checkGameResult();
            } else {
                System.out.println("playerPosition.size + cpuPosition.size < 11 --> nothing to do");
            }

        });
    }

    private void checkGameResult() {
        int result = AiLibrary.getWinner();
        System.out.println("[checkGameResult] result is " + result);

        switch (result) {
            case 0:
                //player won
                winnerLabel.setText("Congratulation! You won....");
                Props.mCurrentPlayer.Player_Points += 10;
                resetGame(false);

                try {
                    ClientController.sendToServer(JsonOperations.createUpdatePlayerPointsJson());
                    ClientController.sendToServer(JsonOperations.gameEnded(oppsiteEmail));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                //cpu won
                winnerLabel.setText("You Lost!");
                resetGame(false);
                try {
                    ClientController.sendToServer(JsonOperations.gameEnded(oppsiteEmail));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // draw
                winnerLabel.setText("OH !! NO Its a Draw");
                resetGame(false);
                try {
                    ClientController.sendToServer(JsonOperations.gameEnded(oppsiteEmail));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    /*=======================================================================*/

    public void onChangeModeBtnClicked(ActionEvent actionEvent) {
        if (mApplicationCallback != null) {
            mApplicationCallback.switchToGameChooser();
        }
    }

    /*=======================================================================*/


    public void onPauseGameClicked(ActionEvent actionEvent) {
        if (!isGameStarted) {
            System.out.println("[onPauseGameClicked] game not started! ---> return");
            return;
        }
        String gameState = createGameState();
        try {
            ClientController.sendToServer(JsonOperations.sendGamePause(oppsiteEmail, gameState));
            resetGame(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createGameState() {
        JSONObject object = new JSONObject();

        String otherPlayerPositions = "";
        for (int i : AiLibrary.cpuPosition) {
            otherPlayerPositions += i;
        }
        object.put(oppsiteEmail, otherPlayerPositions);

        String playerPositions = "";
        for (int i : AiLibrary.playerPosition) {
            playerPositions += i;
        }
        object.put(Props.mCurrentPlayer.getPlayer_Email(), playerPositions);

        if (ch.equals("X"))
            object.put("X", Props.mCurrentPlayer.getPlayer_Email());
        else
            object.put("X", oppsiteEmail);


        if (shouldWaitForNextPlayerMove) {
            // this is other player's turn
            object.put("pt", oppsiteEmail);
        } else {
            object.put("pt", Props.mCurrentPlayer.getPlayer_Email());
        }

        return object.toString();
    }

    @Override
    public void onGamePaused() {
        resetGame(true);
    }

    @Override
    public void handelGameResume(String jsonStr) {
        try {
            System.out.println("[handelGameResume] jsonStr: " + jsonStr);

            shouldWaitForNextPlayerMove = true;
            AiLibrary.reset();

            JSONObject object = new JSONObject(jsonStr);

            String otherPlayerPositions = object.getString(oppsiteEmail);
            String myPositions = object.getString(Props.mCurrentPlayer.getPlayer_Email());
            String playerWithXLetter = object.getString("X");

            String playerWithTurnEmail = object.getString("pt");

            if (playerWithTurnEmail.equals(Props.mCurrentPlayer.getPlayer_Email())) {
                shouldWaitForNextPlayerMove = false;
            } else {
                shouldWaitForNextPlayerMove = true;
            }

            System.out.println("[handelGameResume] otherPlayerPositions: " + otherPlayerPositions);
            System.out.println("[handelGameResume] myPositions: " + myPositions);
            System.out.println("[handelGameResume] playerWithXLetter: " + playerWithXLetter);
            System.out.println("[handelGameResume] shouldWaitForNextPlayerMove: " + shouldWaitForNextPlayerMove);

            if (playerWithXLetter.equals(Props.mCurrentPlayer.getPlayer_Email())) {
                ch = "X";
                opch = "O";
            } else {
                ch = "O";
                opch = "X";
            }

            for (char c : otherPlayerPositions.toCharArray()) {
                System.out.println("[handelGameResume] loop1 ---> c: " + c);

                int pos = Integer.parseInt(c + "");

                if (pos == 0)
                    continue;

                AiLibrary.onPlayer2Move(pos);


                Platform.runLater(() -> {
                    drawInGui(pos, opch, Color.RED);
                });
            }

            for (char c : myPositions.toCharArray()) {
                System.out.println("[handelGameResume] loop2 ---> c: " + c);

                int pos = Integer.parseInt(c + "");
                if (pos == 0)
                    continue;

                AiLibrary.onPlayerMove(pos);
                Platform.runLater(() -> {
                    drawInGui(pos, ch, Color.YELLOW);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*=======================================================================*/

    @Override
    public void onNewPlayerOnline(String playerName, String playerEmail) {
        if (mApplicationCallback != null)
            mApplicationCallback.showSystemNotification(playerName + "(" + playerEmail + ")" + " is now online!");
    }

    /*=======================================================================*/

    public void onChatBtnClicked(ActionEvent actionEvent) {
        createOrShowChatStage();
    }
    /*=======================================================================*/

    @Override
    public void onGetAllPlayers(String response) {
        System.out.println("[onGetAllPlayers] response: " + response);
        try {
            fillTableData(response);
        } catch (Exception e) {
            e.printStackTrace();
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
                        player = new Player(onlineImageView, dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL), stat, dataArray.getJSONObject(i).getInt(Constants.JsonKeys.KEY_USER_POINTS));
                    } else {
                        player = new Player(offlineImageView, dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL), stat, dataArray.getJSONObject(i).getInt(Constants.JsonKeys.KEY_USER_POINTS));
                    }
                    player.setPlayer_Email(dataArray.getJSONObject(i).getString(Constants.JsonKeys.KEY_USER_EMAIL));
                    System.out.println("player: " + player.toString());
                    list.add(player);
                }
            }


            table.setItems(list);

        });


    }


    @Override
    public void onNewMessageReceived(String message) {
        System.out.println("[onNewMessageReceived] msg: " + message);
        Platform.runLater(() -> {
            createOrShowChatStage();
            if (mChatScreenView != null) {
                mChatScreenView.onNewMessageReceived(message);
            }
        });

    }

    private void createOrShowChatStage() {
        if (mChatStage == null) {
            try {
                mChatStage = new Stage();
                ChatUIController.setUserEmail(oppsiteEmail);
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/itijavafinalprojectteam8/view/chat/chatUI.fxml")));
                mChatStage.setScene(scene);
                mChatStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!mChatStage.isShowing()) {
                mChatStage.show();
            }
        }
    }
}
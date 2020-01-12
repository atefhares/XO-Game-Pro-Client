package com.itijavafinalprojectteam8.view.gamechooserscreen;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.interfaces.GameChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameChooserController implements GameChooser {



    @FXML
    private Button player;

    @FXML
    private Button computer;

    @FXML
    private AnchorPane progressPane;


    private static GameAppView mApplicationCallback;

    public GameChooserController() {
        ClientController.setChooserView(this);
    }

    public static void setApplicationCallback(GameAppView callback) {
        mApplicationCallback = callback;
    }

    @FXML
    private void clickOnPlayer(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchToGameScreen();
    }
    @FXML
    private void clickOnComputer(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchToGameCpuScreen();
    }
}

package com.itijavafinalprojectteam8.view.gamechooserscreen;

import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class GameChooserController {


    @FXML
    private Button player;

    @FXML
    private Button computer;


    private static GameAppView mApplicationCallback;

    public static void setApplicationCallback(GameAppView callback) {
        mApplicationCallback = callback;
    }

    @FXML
    private void clickOnPlayer(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchToGameWithOtherPlayerScreen();
    }

    @FXML
    private void clickOnComputer(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchToGameWithCpuScreen();
    }
}

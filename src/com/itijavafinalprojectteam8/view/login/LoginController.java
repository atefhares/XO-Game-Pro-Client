package com.itijavafinalprojectteam8.view.login;

import com.itijavafinalprojectteam8.controller.ClientController;
import com.itijavafinalprojectteam8.controller.JsonOperations;
import com.itijavafinalprojectteam8.controller.UserInputChecker;
import com.itijavafinalprojectteam8.view.interfaces.GameAppView;
import com.itijavafinalprojectteam8.view.interfaces.LoginView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;


/**
 * @author ahares
 */
public class LoginController implements LoginView {

    @FXML
    private TextField emailAddressTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    private Pane progressPane;

    private static GameAppView mApplicationCallback;
    public static void setApplicationCallback(GameAppView callback) {
        mApplicationCallback = callback;
    }

    public LoginController() {
        ClientController.setLoginView(this);
    }

    @FXML
    private void changeScreenHyperLink(ActionEvent event) throws IOException {
        if (mApplicationCallback != null)
            mApplicationCallback.switchToSignUpScreen();
    }

    @FXML
    private void onLoginBtnClicked(ActionEvent event) throws IOException {
        String email = emailAddressTF.getText().trim().toLowerCase();
        String plainPass = passwordPF.getText();

        if (!UserInputChecker.isValidEmail(email)) {
            if (mApplicationCallback != null)
                mApplicationCallback.showToastMessage("Email is invalid");
            return;
        }

        if (!UserInputChecker.isValidPassword(plainPass)) {
            if (mApplicationCallback != null)
                mApplicationCallback.showToastMessage("Password is invalid");
            return;
        }

        try {
            progressPane.setVisible(true);
            ClientController.sendToServer(JsonOperations.createSignInJson(email, plainPass));
            ClientController.start();
        } catch (Exception e) {
            e.printStackTrace();

            progressPane.setVisible(false);
            mApplicationCallback.showToastMessage("Failed to connect to server....");
        }
    }

    @Override
    public void onErrorResponse(String errorMsgFromServer) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressPane.setVisible(false);

                if (mApplicationCallback != null)
                    mApplicationCallback.showToastMessage(errorMsgFromServer);
            }
        });
    }

    @Override
    public void onSuccessResponse() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressPane.setVisible(false);

                if (mApplicationCallback != null)
                    mApplicationCallback.switchToGameChooser();
            }
        });
    }
}

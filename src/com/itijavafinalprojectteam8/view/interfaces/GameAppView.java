package com.itijavafinalprojectteam8.view.interfaces;

/**
 * @author ahares
 */
public interface GameAppView {
    void switchToSignUpScreen();

    void switchToLoginScreen();

    void switchToGameWithOtherPlayerScreen();

    void switchToGameWithCpuScreen();

    void showToastMessage(String text);

    void switchToGameChooser();
}

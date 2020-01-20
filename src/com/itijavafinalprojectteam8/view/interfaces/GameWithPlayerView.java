package com.itijavafinalprojectteam8.view.interfaces;

public interface GameWithPlayerView {
    void onGetAllPlayers(String response);

    void onGameInvitationRequest(String email);

    public void onGameInvitationResponse(boolean parseInvitationResponse);

    public void onGamePlayMoveReceived(int gamecord);

    void onGamePaused();

    public void handelGameResume(String textFromServer);

    void onNewPlayerOnline(String playerName, String playerEmail);

    void onNewMessageReceived(String message);
}

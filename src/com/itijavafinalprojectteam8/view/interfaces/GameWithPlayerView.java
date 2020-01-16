package com.itijavafinalprojectteam8.view.interfaces;

public interface GameWithPlayerView {
    void onGetAllPlayers(String response);

    void onGameInvitationRequest(String email);

    void onGameInvitationResponse(String message);

    public void confirmToast(boolean parseInvitationResponse);
}

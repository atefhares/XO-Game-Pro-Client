package com.itijavafinalprojectteam8.controller;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.view.interfaces.GameWithPlayerView;
import com.itijavafinalprojectteam8.view.interfaces.LoginView;
import com.itijavafinalprojectteam8.view.interfaces.SignUpView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author ahares
 */
public class ClientController {
    private static final String SERVER_ADDRESS = "127.0.0.1";

    private static final int SERVER_PORT = 8000;

    private static AtomicBoolean mIsShutDown = new AtomicBoolean(false);
    private static Socket mSocket = null;
    private static DataInputStream mDataInputStream;
    private static DataOutputStream mDataOutputStream;

    private static Thread mThread;
    private static LoginView mLoginScreenViewCallback;
    private static SignUpView mSignUpScreenViewCallback;
    private static GameWithPlayerView mGameWithPlayerView;

    public static void setGameUiController(GameWithPlayerView view) {
        mGameWithPlayerView = view;
    }

    private ClientController() {
    }

    public static void setLoginView(LoginView loginView) {
        mLoginScreenViewCallback = loginView;
    }

    public static void setSignUpView(SignUpView callback) {
        mSignUpScreenViewCallback = callback;
    }

    public static void open() throws IOException {
        mSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        mDataInputStream = new DataInputStream(mSocket.getInputStream());
        mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());

        mIsShutDown.set(false);
    }

    public static void sendToServer(final String msg) throws IOException {
        if (mSocket == null || mSocket.isClosed()) {
            open();
        }

        mDataOutputStream.writeUTF(msg);
    }

    private static void read() throws IOException {
        if (mSocket == null || mSocket.isClosed()) {
            System.out.println("No socket opened with server!!");
            return;
        }

        if (mDataInputStream == null) {
            System.out.println("No socket opened with server!!");
            return;
        }

        String textFromServer = mDataInputStream.readUTF();
        System.out.println("[read] msg from server: " + textFromServer);
        handleMessageFromServer(textFromServer);
    }

    private static void handleMessageFromServer(final String textFromServer) {
        String responseType = JsonOperations.parseResponseType(textFromServer);
        switch (responseType) {
            case Constants.ConnectionTypes.TYPE_SIGN_IN:
                handleSignInResponse(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_SIGN_UP:
                handleSignUpResponse(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_GET_ALL_PLAYERS:
                handleGetAllPlayersResponse(textFromServer);
                break;


            case Constants.ConnectionTypes.TYPE_SEND_INVITATION:
                System.out.println("TYPE_SEND_INVIVTATION");
                handleInvitationResponse(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_INVITATION_RESULT:
                System.out.println("TYPE_IVITATION_RESULT");
                handleInvitationReturnBack(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_GAME:
                System.out.println("TYPE_IVITATION_RESULT");
                handleGameCord(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_PAUSE_GAME:
                if (mGameWithPlayerView != null)
                    mGameWithPlayerView.onGamePaused();
                break;

            case Constants.ConnectionTypes.TYPE_GAME_RESUME:
                if (mGameWithPlayerView != null)
                    mGameWithPlayerView.handelGameResume(JsonOperations.parseResumeGameResponse(textFromServer));
                break;

            case Constants.ConnectionTypes.TYPE_PLAYER_IS_ONLINE:
                handleNewPlayerIsOnline(textFromServer);

            case Constants.ConnectionTypes.TYPE_CHAT:
                handleChatMessage(textFromServer);
        }
    }

    private static void handleChatMessage(String textFromServer) {
        String message = JsonOperations.parseChatMessage(textFromServer);
        if (mGameWithPlayerView != null) {
            mGameWithPlayerView.onNewMessageReceived(message);
        }
    }

    private static void handleNewPlayerIsOnline(String textFromServer) {
        String playerName = JsonOperations.parseName(textFromServer);
        String playerEmail = JsonOperations.parseEmail(textFromServer);
        if (mGameWithPlayerView != null) {
            mGameWithPlayerView.onNewPlayerOnline(playerName, playerEmail);
        }
    }

    private static void handleGameCord(String jsonText) {
        System.out.println("this is inside the game controller  ");
        if (mGameWithPlayerView != null)
            mGameWithPlayerView.onGamePlayMoveReceived(JsonOperations.parseGameCord(jsonText));

    }

    private static void handleInvitationReturnBack(String jsonText) {

        int responseCode = JsonOperations.parseResponseCode(jsonText);
        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS:
                if (mGameWithPlayerView != null)
                    mGameWithPlayerView.onGameInvitationResponse(JsonOperations.parseInvitationResponse(jsonText));
                break;
        }

    }

    private static void handleGetAllPlayersResponse(String textFromServer) {
        int responseCode = JsonOperations.parseResponseCode(textFromServer);
        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS: {
                System.out.println("[handleGetAllPlayersResponse] list of all players: " + textFromServer);
                if (mGameWithPlayerView != null)
                    mGameWithPlayerView.onGetAllPlayers(JsonOperations.parseAllPlayers(textFromServer));
            }
            break;
        }
    }

    private static void handleSignUpResponse(String textFromServer) {
        int responseCode = JsonOperations.parseResponseCode(textFromServer);

        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                if (mSignUpScreenViewCallback != null) {
                    mSignUpScreenViewCallback.onErrorResponse(JsonOperations.parseResponseMessage(textFromServer));
                }
                shutdown();
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS:
                Props.mCurrentPlayer = JsonOperations.parseCurrentPlayer(textFromServer);

                if (mSignUpScreenViewCallback != null) {
                    mSignUpScreenViewCallback.onSuccessResponse();
                }
                break;
        }
    }

    private static void handleSignInResponse(String textFromServer) {
        int responseCode = JsonOperations.parseResponseCode(textFromServer);
        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                if (mLoginScreenViewCallback != null) {
                    mLoginScreenViewCallback.onErrorResponse(JsonOperations.parseResponseMessage(textFromServer));
                }
                shutdown();
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS:
                Props.mCurrentPlayer = JsonOperations.parseCurrentPlayer(textFromServer);

                if (mLoginScreenViewCallback != null) {
                    mLoginScreenViewCallback.onSuccessResponse();
                }
                break;
        }

    }

    private static void handleInvitationResponse(String textFromServer) {
        int responseCode = JsonOperations.parseResponseCode(textFromServer);
        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                //               mGameWithPlayerView.onGameInvitationResponse(JsonOperations.getResponseMessage(textFromServer));
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS:
                if (mGameWithPlayerView != null)
                    mGameWithPlayerView.onGameInvitationRequest(JsonOperations.parseResponseMessage(textFromServer));
                break;
        }

    }

    private static void shutdown() {
        try {
            mSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mDataInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mDataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mIsShutDown.set(true);

        mThread = null;
    }

    public static synchronized void start() {
        if (mThread != null)
            return;

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsShutDown.get()) {
                    try {
                        read();
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }
}
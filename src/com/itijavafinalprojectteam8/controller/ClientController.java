package com.itijavafinalprojectteam8.controller;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.Player;
import com.itijavafinalprojectteam8.view.gamewithplayer.GameUiController;
import com.itijavafinalprojectteam8.view.interfaces.GameChooser;
import com.itijavafinalprojectteam8.view.interfaces.GameCpuView;
import com.itijavafinalprojectteam8.view.interfaces.LoginView;
import com.itijavafinalprojectteam8.view.interfaces.SignUpView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private static GameChooser gameChooserViewCallback;
    private static LoginView mLoginScreenViewCallback;
    private static SignUpView mSignUpScreenViewCallback;
    private static GameCpuView mGameCpuScreenViewCallback;
    public static StringProperty Users=new SimpleStringProperty("new");
    public static GameUiController tabledate;
    public static final String getUsers() {
        return ClientController.Users.get();
    }
     public static void setgameUicontroller(GameUiController game) {
        tabledate = game;
    }

    public static final void setUsers(String value) {
        ClientController.Users.set(value);
    }

    public static final StringProperty usersProperty() {
        return ClientController.Users;
    }
    
    private ClientController() {
    }

    public static void setChooserView(GameChooser chooserView) {
        gameChooserViewCallback = chooserView;
    }

    public static void setLoginView(LoginView loginView) {
        mLoginScreenViewCallback = loginView;
    }

    public static void setGameCpuView(GameCpuView callback) {
        mGameCpuScreenViewCallback = callback;
    }


     public static void setSignUpView(SignUpView callback) {
         mSignUpScreenViewCallback = callback;
     }
        //error
         public static void open() {
          try {
              mSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            mDataInputStream = new DataInputStream(mSocket.getInputStream());
            mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());

            mIsShutDown.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToServer( final String msg) throws IOException {
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
        String responseType = JsonOperations.getResponseType(textFromServer);
        System.out.println("json object "+ responseType);
        if(JsonOperations.getResponseType(textFromServer)==null) {
            System.out.println("sign in 1"+ responseType);
        } 
        switch (responseType) {
            case Constants.ConnectionTypes.TYPE_SIGN_IN:
                                System.out.println("this is client 2 ");

                handleSignInResponse(textFromServer);
                
                
                break;

            case Constants.ConnectionTypes.TYPE_SIGN_UP:
                                System.out.println("this is client 1 ");

                handleSignUpResponse(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_GET_ALL_PLAYERS:
                System.out.println("this is client view ");
                 tabledate.fillTableData(textFromServer);
               // handleGetAllPlayersResponse(textFromServer);
                break;
            
                
        }
    }

    private static void handleGetAllPlayersResponse(String textFromServer) {
        String allPlayers = JsonOperations.getAllPlayersJsonString(textFromServer);
        System.out.println("[handleGetAllPlayersResponse] list of all players: " + allPlayers);
        if (!allPlayers.isEmpty())
            Props.allPlayersServerResponse.setValue(allPlayers);
    }

    private static void handleSignUpResponse(String textFromServer) {
        int responseCode = JsonOperations.getResponseCode(textFromServer);
                
        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                if (mSignUpScreenViewCallback != null) {
                    mSignUpScreenViewCallback.onErrorResponse(JsonOperations.getResponseMessage(textFromServer));
                }
                shutdown();
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS:
                                

                if (mSignUpScreenViewCallback != null) {
                    mSignUpScreenViewCallback.onSuccessResponse();
                }
                break;
        }
    }

    private static void handleSignInResponse(String textFromServer) {
        int responseCode = JsonOperations.getResponseCode(textFromServer);
        switch (responseCode) {
            case Constants.ResponseCodes.RESPONSE_ERROR:
                if (mLoginScreenViewCallback != null) {
                    mLoginScreenViewCallback.onErrorResponse(JsonOperations.getResponseMessage(textFromServer));
                }
                shutdown();
                break;

            case Constants.ResponseCodes.RESPONSE_SUCCESS:
                if (mLoginScreenViewCallback != null) {
                    mLoginScreenViewCallback.onSuccessResponse();
                    
                             System.out.println("asdasdasdasd");

                }
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

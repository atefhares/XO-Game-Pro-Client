package com.itijavafinalprojectteam8.controller;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.view.login.View;
import com.sun.istack.internal.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {
    private static final String SERVER_ADDRESS = "7.7.7.44";
    private static final int SERVER_PORT = 8000;

    private static Socket mSocket = null;
    private static DataInputStream mDataInputStream;
    private static DataOutputStream mDataOutputStream;

    private static Thread mThread;
    private static View mViewCallback;

    private ClientController() {
    }

    public static void init(View view) {
        mViewCallback = view;
    }

    private static void open() {
        try {
            mSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            mDataInputStream = new DataInputStream(mSocket.getInputStream());
            mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToServer(@NotNull final String msg) throws IOException {
        if (mSocket == null || mSocket.isClosed()) {
            System.out.println("No socket opened with server!!");
            open();
            return;
        }

        if (mDataOutputStream == null) {
            System.out.println("No socket opened with server!!");
            return;
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
        switch (responseType) {
            case Constants.ConnectionTypes.TYPE_SIGN_IN:
                handleSignInResponse(textFromServer);
                break;

            case Constants.ConnectionTypes.TYPE_SIGN_UP:
                handleSignUpResponse(textFromServer);
                break;
        }
    }

    private static void handleSignUpResponse(String textFromServer) {

        if (mViewCallback != null) {
            mViewCallback.showToastMessage(textFromServer);
        }
    }

    private static void handleSignInResponse(String textFromServer) {
        if (mViewCallback != null) {
            mViewCallback.showToastMessage(textFromServer);
        }
    }

    public static synchronized void start() {
        if (mThread != null)
            return;

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
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

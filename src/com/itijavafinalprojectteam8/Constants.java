package com.itijavafinalprojectteam8;

/**
 * @author ahares
 */
public class Constants {
    public static final String GAME_TITLE = "X-O game pro";


    public static class ConnectionTypes {
        public static final String TYPE_SIGN_IN = "1";
        public static final String TYPE_SIGN_UP = "2";
        public static final String TYPE_GET_ALL_PLAYERS = "3";
        public static final String TYPE_SEND_INVIVTATION = "4";
        public static final String TYPE_IVITATION_RESULT = "5";

    }

    public static class JsonKeys {
        public static final String KEY_RESPONSE_TYPE = "1";
        public static final String KEY_RESPONSE_CODE = "2";
        public static final String KEY_RESPONSE_MSG = "3";
        public static final String KEY_REQUEST_TYPE = "4";
        public static final String KEY_USER_NAME = "5";
        public static final String KEY_USER_EMAIL = "6";
        public static final String KEY_USER_PASSWORD = "7";
        public static final String KEY_USER_STATUS = "8";
        public static final String KEY_USER_POINTS = "9";
        public static final String KEY_INVITATION_RESULT = "10";

    }

    public static class PlayerStatus {
        public static final int OFFLINE = 0;
        public static final int ONLINE_NOT_IN_GAME = 1;
        public static final int ONLINE_IN_GAME = 2;
    }

    public static class ResponseCodes {
        public static final int RESPONSE_SUCCESS = 200;
        public static final int RESPONSE_ERROR = 400;
    }
}

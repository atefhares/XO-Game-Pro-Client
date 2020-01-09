package com.itijavafinalprojectteam8;

/**
 * @author ahares
 */
public class Constants {
    public static final String GAME_TITLE = "X-O game pro";

    public static class ConnectionTypes {
        public static final String TYPE_SIGN_IN = "si";
        public static final String TYPE_SIGN_UP = "sp";
    }

    public static class JsonKeys {
        public static final String KEY_RESPONSE_TYPE = "rst";
        public static final String KEY_RESPONSE_CODE = "rsc";
        public static final String KEY_RESPONSE_MSG = "rsm";
        public static final String KEY_REQUEST_TYPE = "rt";
        public static final String KEY_USER_NAME = "un";
        public static final String KEY_USER_EMAIL = "ue";
        public static final String KEY_USER_PASSWORD = "up";
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

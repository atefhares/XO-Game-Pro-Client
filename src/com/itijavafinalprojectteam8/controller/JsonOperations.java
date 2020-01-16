package com.itijavafinalprojectteam8.controller;

import com.itijavafinalprojectteam8.Constants;
import com.itijavafinalprojectteam8.controller.sec.PasswordHelper;
import com.itijavafinalprojectteam8.model.Player;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

/**
 * @author ahares
 */
public class JsonOperations {

    public static String getResponseType(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.optString(Constants.JsonKeys.KEY_RESPONSE_TYPE);
    }

    public static String getSignInJson(String email, String plainPass) throws NoSuchAlgorithmException {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_SIGN_IN);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, email);
        object.put(Constants.JsonKeys.KEY_USER_PASSWORD, PasswordHelper.getEncryptedPassword(plainPass));
        return object.toString();
    }

    public static String getSignUpJson(String name, String email, String plainPass) throws NoSuchAlgorithmException {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_SIGN_UP);
        object.put(Constants.JsonKeys.KEY_USER_NAME, name);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, email);
        object.put(Constants.JsonKeys.KEY_USER_PASSWORD, PasswordHelper.getEncryptedPassword(plainPass));
        return object.toString();
    }

    public static String getInvitationJson(String email) {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_SEND_INVIVTATION);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, email);

        return object.toString();
    }

    public static Player parseCurrentPlayer(String jsonText) {
        Player responsePlayer = new Player();
        JSONObject jsonObject = new JSONObject(jsonText);
        responsePlayer.setPlayer_Email(jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getString(Constants.JsonKeys.KEY_USER_EMAIL));
        responsePlayer.setPlayer_Name(jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getString(Constants.JsonKeys.KEY_USER_NAME));
        responsePlayer.setPlayer_Points(jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getInt(Constants.JsonKeys.KEY_USER_POINTS));
        return responsePlayer;
    }


    public static int getResponseCode(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.getInt(Constants.JsonKeys.KEY_RESPONSE_CODE);
    }

    public static String getResponseMessage(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.optString(Constants.JsonKeys.KEY_RESPONSE_MSG);
    }

    public static String sendInvitationResponse(String Email, boolean accepted) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_IVITATION_RESULT);

        jsonObject.put(Constants.JsonKeys.KEY_INVITATION_RESULT, accepted);
        jsonObject.put(Constants.JsonKeys.KEY_USER_EMAIL, Email);
        return jsonObject.toString();
    }

    public static boolean parseInvitationResponse(String jsonText) {
        JSONObject jsonObject = new JSONObject(jsonText).getJSONObject(Constants.ConnectionTypes.TYPE_IVITATION_RESULT);
        Boolean response = jsonObject.getBoolean(Constants.JsonKeys.KEY_INVITATION_RESULT);
        return response;
    }

    public static String getAllPlayersJson() {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_GET_ALL_PLAYERS);
        return object.toString();
    }


    public static String parseAllPlayers(String jsonString) {
        JSONObject object = new JSONObject(jsonString);
        return object.optString(Constants.JsonKeys.KEY_RESPONSE_MSG);
    }
}

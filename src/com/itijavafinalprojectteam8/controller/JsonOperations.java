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

    public static String parseResponseType(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.optString(Constants.JsonKeys.KEY_RESPONSE_TYPE);
    }

    public static Player parseCurrentPlayer(String jsonText) {
        Player responsePlayer = new Player();
        JSONObject jsonObject = new JSONObject(jsonText);
        responsePlayer.setPlayer_Email(jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getString(Constants.JsonKeys.KEY_USER_EMAIL));
        responsePlayer.setPlayer_Name(jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getString(Constants.JsonKeys.KEY_USER_NAME));
        responsePlayer.setPlayer_Points(jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getInt(Constants.JsonKeys.KEY_USER_POINTS));
        return responsePlayer;
    }

    public static int parseResponseCode(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.getInt(Constants.JsonKeys.KEY_RESPONSE_CODE);
    }

    public static String parseResponseMessage(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.optString(Constants.JsonKeys.KEY_RESPONSE_MSG);
    }

    public static int parseGameCord(String cord) {
        JSONObject jsonObject = new JSONObject(cord);
        return jsonObject.getInt(Constants.JsonKeys.KEY_GAME_CORD);
    }

    public static String parseEmail(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString(Constants.JsonKeys.KEY_USER_EMAIL);
    }

    public static String parseName(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString(Constants.JsonKeys.KEY_USER_NAME);
    }

    public static boolean parseInvitationResponse(String jsonText) {
        JSONObject jsonObject = new JSONObject(jsonText);
        Boolean response = jsonObject.getJSONObject(Constants.JsonKeys.KEY_RESPONSE_MSG).getBoolean(Constants.JsonKeys.KEY_INVITATION_RESULT);
        System.out.println(response);
        return response;
    }

    public static String parseAllPlayers(String jsonString) {
        JSONObject object = new JSONObject(jsonString);
        return object.optString(Constants.JsonKeys.KEY_RESPONSE_MSG);
    }

    /*==============================================================================================*/
    /*==============================================================================================*/
    /*==============================================================================================*/

    public static String createSignInJson(String email, String plainPass) throws NoSuchAlgorithmException {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_SIGN_IN);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, email);
        object.put(Constants.JsonKeys.KEY_USER_PASSWORD, PasswordHelper.getEncryptedPassword(plainPass));
        return object.toString();
    }

    public static String createSignUpJson(String name, String email, String plainPass) throws NoSuchAlgorithmException {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_SIGN_UP);
        object.put(Constants.JsonKeys.KEY_USER_NAME, name);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, email);
        object.put(Constants.JsonKeys.KEY_USER_PASSWORD, PasswordHelper.getEncryptedPassword(plainPass));
        return object.toString();
    }

    public static String createInvitationJson(String email) {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_SEND_INVITATION);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, email);
        return object.toString();
    }

    public static String createInvitationResponseJson(String Email, boolean accepted) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_INVITATION_RESULT);
        jsonObject.put(Constants.JsonKeys.KEY_INVITATION_RESULT, accepted);
        jsonObject.put(Constants.JsonKeys.KEY_USER_EMAIL, Email);
        return jsonObject.toString();
    }

    public static String createSendGameCordJson(String Email, int cord) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_GAME);

        jsonObject.put(Constants.JsonKeys.KEY_USER_EMAIL, Email);
        jsonObject.put(Constants.JsonKeys.KEY_GAME_CORD, cord);
        return jsonObject.toString();
    }

    public static String createGetAllPlayersJson() {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_GET_ALL_PLAYERS);
        return object.toString();
    }

    public static String gameEnded(String Email) {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_GAME_OVER);
        object.put(Constants.JsonKeys.KEY_USER_EMAIL, Email);

        return object.toString();
    }

    public static String sendGamePause(String otherPlayerEmail, String gameState) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_PAUSE_GAME);
        jsonObject.put(Constants.JsonKeys.KEY_USER_EMAIL, otherPlayerEmail);
        jsonObject.put(Constants.JsonKeys.KEY_GAME_STATE, gameState);
        return jsonObject.toString();
    }


    public static String createUpdatePlayerPointsJson() {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_UPDATE_PLAYER_POINTS);
        return object.toString();
    }

    public static String createPauseGameJson(String oppsiteEmail, String[] gameStateString) {
        JSONObject object = new JSONObject();
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_PAUSE_GAME);
        object.put(Constants.JsonKeys.KEY_USER_NAME, oppsiteEmail);

        JSONObject object1 = new JSONObject();
        object1.put(Props.mCurrentPlayer.getPlayer_Email(), gameStateString[0]);
        object1.put(oppsiteEmail, gameStateString[1]);

        object.put(Constants.JsonKeys.KEY_GAME_STATE, object1);

        return object.toString();
    }

    public static String parseResumeGameResponse(String textFromServer) {
        JSONObject object = new JSONObject(textFromServer);
        return object.optString(Constants.JsonKeys.KEY_GAME_STATE);
    }
}
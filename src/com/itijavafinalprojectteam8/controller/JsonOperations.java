package com.itijavafinalprojectteam8.controller;

import com.itijavafinalprojectteam8.Constants;
import static com.itijavafinalprojectteam8.controller.AiLibrary.cpuPosition;
import static com.itijavafinalprojectteam8.controller.AiLibrary.playerPosition;
import com.itijavafinalprojectteam8.controller.sec.PasswordHelper;
import com.itijavafinalprojectteam8.model.Player;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.midi.SysexMessage;

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
        object.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_GAME_ENDED);
         object.put(Constants.JsonKeys.KEY_USER_EMAIL, Email);

        return object.toString();
    }
     public static String parseGameResume(String jsonText)
    {
         JSONObject jsonObject = new JSONObject(jsonText);
        // String playerEmail=jsonObject.getString(Constants.JsonKeys.KEY_USER_EMAIL);
        String Arr[]=jsonObject.getString(Constants.JsonKeys.KEY_GAME_STATE).split(":");
        
         AiLibrary.reset();
      
        
        String replace = Arr[0].replace("[","");
        System.out.println(replace);
        String replace1 = replace.replace("]","");
        System.out.println(replace1);
        String playerList[]=replace1.split(",");
         
        for(int fi=0;fi<playerList.length;fi++){
            int tmp=Integer.parseInt(playerList[fi].trim());
            System.out.println("elemnt is " +Integer.parseInt(playerList[fi].trim()));
        AiLibrary.playerPosition.add(tmp);
        if(tmp!=0)
        AiLibrary.onPlayerMove(tmp);
            System.out.println("player"+AiLibrary.playerPosition.toString());
        }
        
          replace = Arr[1].replace("[","");
        System.out.println(replace);
         replace1 = replace.replace("]","");
        System.out.println(replace1);
         playerList=replace1.split(",");
        
       
        for(int fi=0;fi<playerList.length;fi++){
           int tmp=Integer.parseInt(playerList[fi].trim());
            AiLibrary.cpuPosition.add(tmp);
        if(tmp!=0)
        AiLibrary.onPlayer2Move(tmp);
        System.out.println("cpu"+AiLibrary.cpuPosition.toString());
        }
        
        
        return Arr[2];
    }
     public static String sendGamePause(String Email, int role) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.JsonKeys.KEY_REQUEST_TYPE, Constants.ConnectionTypes.TYPE_PAUSE_GAME);
        String Line=AiLibrary.playerPosition.toString()+":"+AiLibrary.cpuPosition.toString()+":"+Email;
        jsonObject.put(Constants.JsonKeys.KEY_USER_EMAIL, Email);
        jsonObject.put(Constants.JsonKeys.KEY_GAME_STATE, Line);
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
}

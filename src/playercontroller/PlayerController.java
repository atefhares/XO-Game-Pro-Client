/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playercontroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author moham
 */
public class PlayerController {
    private static Socket SOCKET = null;

    private static final String NORESET = "Could not reset.";
    private static final String RESET = "The server has been reset.";
    private static DataInputStream GENRAL_IS;
    private static DataOutputStream GENRAL_OS;
    private static JSONObject RESPONSE;
    private static JSONArray Data_Array;
    public static Boolean SignIn_Status = false;
    public static Boolean SignUp_Status = false;


    /*======================================================================================================*/
    private String Player_Email;
    private String Player_Name;
    private int Player_Points;
    private int Player_Status;
    public static Vector<PlayerController> Player_Vector = new Vector<PlayerController>();

    public int getPlayer_Status() {
        return Player_Status;
    }

    public void setPlayer_Status(int Player_Status) {
        this.Player_Status = Player_Status;
    }


    public String getPlayer_Email() {
        return Player_Email;
    }

    public void setPlayer_Email(String Player_Email) {
        this.Player_Email = Player_Email;
    }

    public String getPlayer_Name() {
        return Player_Name;
    }

    public void setPlayer_Name(String Player_Name) {
        this.Player_Name = Player_Name;
    }

    public int getPlayer_Points() {
        return Player_Points;
    }

    public void setPlayer_Points(int Player_Points) {
        this.Player_Points = Player_Points;
    }

    /*======================================================================================================*/
    public PlayerController() {
        try {
            SOCKET = new Socket(InetAddress.getByName("127.0.0.1"), 5006);
            GENRAL_IS = new DataInputStream(SOCKET.getInputStream());
            GENRAL_OS = new DataOutputStream(SOCKET.getOutputStream());
            Get_Response_JSON();
            //System.err.println("1");
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public boolean SignUp_JSON(String Name, String Email, String Password) {

        boolean Result = true;


        try {
            String string = "{ \"requestStatus\": { \"statusName\": \"SignUp\", }, \"Data\": [ { \"Name\": \"" + Name + "\", \"Email\": \"" + Email + "\", \"Password\": \"" + Password + "\", \"Status\": \"1\", } ] }";

            PrintWriter pw = new PrintWriter(GENRAL_OS);
            pw.println(string);
            pw.flush();
            GENRAL_IS.close();
            GENRAL_OS.close();

        } catch (IOException e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        } catch (Exception e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        }
        System.out.println(RESET);
        return Result;
    }

    public boolean SignIn_JSON(String Email, String Password) {

        boolean Result = true;


        try {
            String string = "{ \"requestStatus\": { \"statusName\": \"SignIn\", }, \"Data\": [ {\"Email\": \"" + Email + "\", \"Password\": \"" + Password + "\", \"Status\": \"1\", } ] }";

            PrintWriter pw = new PrintWriter(GENRAL_OS);
            pw.println(string);
            pw.flush();


        } catch (Exception e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        }
        System.out.println(RESET);
        return Result;
    }

    public boolean Get_Users_JSON() {

        boolean Result = true;


        try {
            String string = "{ \"requestStatus\": { \"statusName\": \"GetUsers\"} }";

            PrintWriter pw = new PrintWriter(GENRAL_OS);
            pw.println(string);
            pw.flush();


        } catch (Exception e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        }
        System.out.println(RESET);
        return Result;
    }

    public boolean Send_GameRequest_JSON(String Email_1, String Email_2) {

        boolean Result = true;


        try {
            String string = "{ \"requestStatus\": { \"statusName\": \"GameRequest\", }, \"Data\": [ {\"Email_1\": \"" + Email_1 + "\", \"Email_2\": \"" + Email_2 + "\"} ] }";

            PrintWriter pw = new PrintWriter(GENRAL_OS);
            pw.println(string);
            pw.flush();


        } catch (Exception e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        }
        System.out.println(RESET);
        return Result;
    }

    public boolean Get_Response_JSON() {

        boolean Result = true;


        try {
            new Thread(new Runnable() {
                public void run() {
                    {
                        while (true) {
                            try {
                                GENRAL_IS = new DataInputStream(SOCKET.getInputStream());


                                RESPONSE = new JSONObject(GENRAL_IS.readLine());
                                String statusName = RESPONSE.getJSONObject("requestStatus").getString("statusName");
                                System.out.println(statusName);
                                if ("SignIn".equalsIgnoreCase(statusName.trim())) {
                                    //ToDO Player object
                                    Fill_player_Data();
                                    SignIn_Status = true;
                                } else if ("SignUp".equalsIgnoreCase(statusName.trim())) {
                                    //ToDO Player object
                                    Fill_player_Data();
                                    System.out.println(getPlayer_Name());
                                    SignUp_Status = true;
                                } else if ("GetUsers".equalsIgnoreCase(statusName.trim())) {

                                    Data_Array = RESPONSE.getJSONArray("Data");
                                    for (int i = 0; i < Data_Array.length(); i++) {
                                        PlayerController player = new PlayerController();
                                        System.out.println("Data   " + Data_Array.getJSONObject(i).getString("Email"));
                                        player.setPlayer_Email(Data_Array.getJSONObject(i).getString("Email"));
                                        player.setPlayer_Name(Data_Array.getJSONObject(i).getString("Name"));
                                        player.setPlayer_Points(Integer.parseInt(Data_Array.getJSONObject(i).getString("Points")));
                                        player.setPlayer_Status(Integer.parseInt(Data_Array.getJSONObject(i).getString("Status")));
                                        Player_Vector.add(player);
                                    }
                                }

                            } catch (JSONException ex) {
                                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        }
        System.out.println(RESET);
        return Result;
    }

    public void Fill_player_Data() {
        Data_Array = RESPONSE.getJSONArray("Data");
        for (int i = 0; i < Data_Array.length(); i++) {
            System.out.println("Data   " + Data_Array.getJSONObject(i).getString("Email"));
            setPlayer_Email(Data_Array.getJSONObject(i).getString("Email"));
            setPlayer_Name(Data_Array.getJSONObject(i).getString("Name"));
            setPlayer_Points(Integer.parseInt(Data_Array.getJSONObject(i).getString("Points")));
        }
        System.out.println(getPlayer_Email());

    }

    public boolean CloseConnection() {

        boolean Result = true;


        try {

            GENRAL_IS.close();
            GENRAL_OS.close();

        } catch (Exception e) {
            Result = false;
            System.out.println(NORESET);
            e.printStackTrace();
        }
        System.out.println(RESET);
        return Result;
    }

    public static void main(String[] args) {
        PlayerController x = new PlayerController();
        //x.SignInJSON("medoaboserii@gmail.com","test123");
        //x.GetUsersJSON();
        x.Send_GameRequest_JSON("medoaboserii@gmail.com", "ahmed@gmail.com");
    }

}

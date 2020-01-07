package com.itijavafinalprojectteam8.view.login;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginController {

    /*
    This method to switch to Signup page
    
    */
public void changeScreenHyperLink(ActionEvent event ) throws IOException
{

       Parent signup = FXMLLoader.load(getClass().getResource("view/login/signup.fxml"));
       Scene signscene = new Scene(signup);
       
       Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
       window.setScene(signscene);
      window.show();



}
}

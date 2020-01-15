package com.itijavafinalprojectteam8.controller;

import com.itijavafinalprojectteam8.model.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Props {

    // this property is used to add a listener in Game gui to handle
    // server response when sending new updated  players list
    public static final StringProperty allPlayersServerResponse = new SimpleStringProperty("");

    public static Player mCurrentPlayer = new Player();

}

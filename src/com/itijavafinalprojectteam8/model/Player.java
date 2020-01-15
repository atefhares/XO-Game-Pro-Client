package com.itijavafinalprojectteam8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Player {

    private String Player_Email;
    private int Player_Points;
    private int Player_Status;
    private ImageView images;
    SimpleStringProperty Player_Name;

    public Player() {
    }

    public Player(ImageView images, String Player_Name) {
        this.Player_Name = new SimpleStringProperty(Player_Name);
        // this.player_status = new  SimpleStringProperty (player_status);
        this.images = images;

    }

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
        return Player_Name.get();
    }

    public void setPlayer_Name(String name) {
        this.Player_Name.set(name);
    }

    public int getPlayer_Points() {
        return Player_Points;
    }

    public void setPlayer_Points(int Player_Points) {
        this.Player_Points = Player_Points;
    }

    public ImageView getImages() {
        return images;
    }

    public void setImages(ImageView images) {
        this.images = images;

    }
}

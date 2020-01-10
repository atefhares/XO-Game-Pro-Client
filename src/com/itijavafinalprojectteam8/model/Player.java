package com.itijavafinalprojectteam8.model;

public class Player {
    public int id;
    public String name;
    public String email;
    public int status;

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}

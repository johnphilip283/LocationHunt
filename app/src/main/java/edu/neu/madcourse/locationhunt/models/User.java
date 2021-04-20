package edu.neu.madcourse.locationhunt.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String clientToken;
    public String password;

    public User() {

    }

    public User(String username, String password, String clientToken) {
        this.username = username;
        this.password = password;
        this.clientToken = clientToken;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

}

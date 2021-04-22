package edu.neu.madcourse.locationhunt.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@IgnoreExtraProperties
public class User {

    public String username;
    public String clientToken;
    public String password;
    public List<Hunt> hunts;

    public User() {

    }

    public User(String username, String password, String clientToken) {
        this.username = username;
        this.password = password;
        this.clientToken = clientToken;
        this.hunts = new ArrayList<>();
    }

    public List<Hunt> getHunts() { return hunts; }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setHunts(List<Hunt> hunts) { this.hunts = hunts; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: " + username);
        sb.append("Password: " + password);
        sb.append("Client token: " + clientToken);
        sb.append("Hunts: \n");
        for (Hunt hunt: hunts) {
            sb.append(hunt.toString());
        }

        return sb.toString();
    }
}

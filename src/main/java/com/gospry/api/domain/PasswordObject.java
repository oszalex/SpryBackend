package com.gospry.api.domain;

/**
 * Created by chris on 12.11.14.
 */
public class PasswordObject {
    private String password;

    public PasswordObject(String pw){
        this.password = pw;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

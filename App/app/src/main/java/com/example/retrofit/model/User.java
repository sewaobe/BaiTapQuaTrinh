package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("sodienthoai")
    private String sodienthoai;

    public User(String email, String password, String sodienthoai) {
        this.email = email;
        this.password = password;
        this.sodienthoai = sodienthoai;
    }

    public User(String email, String name, String password, String sodienthoai) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.sodienthoai = sodienthoai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
}

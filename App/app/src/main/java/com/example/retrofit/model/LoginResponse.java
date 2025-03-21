package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginResponse implements Serializable {

    @SerializedName("email")
    private String email;



    // Getter
    public String getEmail() {
        return email;
    }


    // Setter
    public void setEmail(String email) {
        this.email = email;
    }

}

//22110281-Nguyen Duc Cong Anh
package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginRequest implements Serializable {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;



    // Getter
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

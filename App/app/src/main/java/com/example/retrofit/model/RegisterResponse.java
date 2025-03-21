//22110281-Nguyen Duc Cong Anh
package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class RegisterResponse implements Serializable {

    @SerializedName("message")
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

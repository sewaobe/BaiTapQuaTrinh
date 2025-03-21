package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OtpRequest implements Serializable {

    @SerializedName("user")
    private User user;

    @SerializedName("otp")
    private String otp;

    public OtpRequest() {}

    public OtpRequest(User user, String otp) {
        this.user = user;
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

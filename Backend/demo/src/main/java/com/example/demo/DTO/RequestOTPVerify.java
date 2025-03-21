package com.example.demo.DTO;

import com.example.demo.entitys.User;

public class RequestOTPVerify {
    private User user;
    private String otp;

    public RequestOTPVerify(User user, String otp) {
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

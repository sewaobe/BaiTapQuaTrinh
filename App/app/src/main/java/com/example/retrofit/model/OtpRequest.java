//22110281-Nguyen Duc Cong Anh
package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OtpRequest implements Serializable {

    @SerializedName("email")
    private String email;

    @SerializedName("otp")
    private String otp;


    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retrofit.model.LoginResponse;
import com.example.retrofit.model.OtpRequest;
import com.example.retrofit.model.RegisterResponse;
import com.example.retrofit.model.User;
import com.example.retrofit.network.RetrofitClient;
import com.example.retrofit.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);
        EditText otp = (EditText) findViewById(R.id.editTextOTP);
        ImageButton btnVerify = (ImageButton) findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                User user = (User) getIntent().getSerializableExtra("user");
                OtpRequest otpRequest = new OtpRequest(user, otp.getText().toString());
                apiService.verifyOtp(otpRequest).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()) {
                            Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Logg", t.getMessage());
                        Toast.makeText(OtpActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
     }
}
package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retrofit.model.LoginRequest;
import com.example.retrofit.model.LoginResponse;
import com.example.retrofit.model.User;
import com.example.retrofit.network.RetrofitClient;
import com.example.retrofit.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = (EditText) findViewById(R.id.email);
        EditText editTextPass = (EditText) findViewById(R.id.password);

        // Bắt sự kiện khi nhấn vào nút Register
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                LoginRequest user = new LoginRequest(editTextEmail.getText().toString(), editTextPass.getText().toString());
                apiService.loginUser(user).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, Home.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Logg", t.getMessage());

                    }
                });

            }
        });
    }
}

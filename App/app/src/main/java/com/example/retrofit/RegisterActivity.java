package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retrofit.model.RegisterResponse;
import com.example.retrofit.model.User;
import com.example.retrofit.network.RetrofitClient;
import com.example.retrofit.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        EditText editName =(EditText) findViewById(R.id.name);
        EditText editEmail =(EditText) findViewById(R.id.email);
        EditText editPhone =(EditText) findViewById(R.id.phone);
        EditText editPassword =(EditText) findViewById(R.id.password);
        TextView btnRegister = (TextView) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                User user = new User(editEmail.getText().toString(),editName.getText().toString(), editPassword.getText().toString(), editPhone.getText().toString());
                apiService.registerUser(user).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(response.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Log.d("Logg", t.getMessage());

                    }
                });
            }
        });



    }
}
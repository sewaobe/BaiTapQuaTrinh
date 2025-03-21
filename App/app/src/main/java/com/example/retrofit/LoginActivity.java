package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.retrofit.model.LoginRequest;
import com.example.retrofit.model.LoginResponse;
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
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        // Bắt sự kiện khi nhấn vào nút Register
        Button btnRegister = findViewById(R.id.btnRegisterActivity);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "ABC", Toast.LENGTH_SHORT).show();
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                LoginRequest user = new LoginRequest(editTextEmail.getText().toString(), editTextPass.getText().toString());
                apiService.loginUser(user).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        // Kiểm tra response có thành công và không null
                        if(response.isSuccessful()) {
                            // Lấy thông báo từ response

                            // Chuyển sang Home nếu đăng nhập thành công
                            Intent intent = new Intent(LoginActivity.this, Home.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Logg", t.getMessage());
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}

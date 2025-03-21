package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.model.LoginResponse;
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

        // Lấy các view từ layout
        EditText editName = findViewById(R.id.name);
        EditText editEmail = findViewById(R.id.email);
        EditText editPhone = findViewById(R.id.phone);
        EditText editPassword = findViewById(R.id.password);
        TextView btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setClickable(true); // Đảm bảo có thể click

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                User user = new User(
                        editEmail.getText().toString().trim(),
                        editName.getText().toString().trim(),
                        editPassword.getText().toString().trim(),
                        editPhone.getText().toString().trim()
                );
                // Gọi API đăng ký
                apiService.registerUser(user).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();

                        if (response.isSuccessful() && response.body() != null) {
                            // Nếu đăng ký thành công, hiển thị Toast và chuyển sang OtpActivity
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        } else {
                            // Nếu response không thành công, hiển thị thông báo lỗi
                            Toast.makeText(RegisterActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                            Log.d("RegisterActivity", "Response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("RegisterActivity", t.getMessage());
                    }
                });
            }
        });
    }
}

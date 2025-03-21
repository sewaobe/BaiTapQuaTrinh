//22110281-Nguyen Duc Cong Anh
package com.example.retrofit.service;

import com.example.retrofit.model.Category;
import com.example.retrofit.model.RegisterResponse;
import com.example.retrofit.model.User;
import com.example.retrofit.model.OtpRequest;
import com.example.retrofit.model.LoginResponse;
import com.example.retrofit.model.LoginRequest;
import com.example.retrofit.model.Product;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();


    @POST("register.php")
    Call<RegisterResponse> registerUser(@Body User user);

    @POST("verify-otp.php")
    Call<RegisterResponse> verifyOtp(@Body OtpRequest otpRequest);

    @POST("login.php")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("categories.php")
    Call<List<Category>> getAllCategories();

    @GET("products.php")
    Call<List<Product>> getProducts();
}

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
import retrofit2.http.Query;

public interface APIService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();

    @POST("/api/authen/register")
    Call<RegisterResponse> registerUser(@Body User user);

    @POST("/api/authen/verify")
    Call<RegisterResponse> verifyOtp(@Body OtpRequest otpRequest);

    @POST("/api/authen/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("/api/categories")
    Call<List<Category>> getAllCategories();

    @GET("/category/")
    Call<List<Product>> getProducts(@Query("categoryId") Long categoryId);
}

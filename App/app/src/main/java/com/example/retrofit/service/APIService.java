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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {


    @POST("/api/authen/register")
    Call<LoginResponse> registerUser(@Body User user);

    @POST("/api/authen/verify")
    Call<LoginResponse> verifyOtp(@Body OtpRequest otpRequest);

    @POST("/api/authen/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("/api/categories")
    Call<List<Category>> getAllCategories();

    @GET("/api/products/category/{id}")
    Call<List<Product>> getProducts(@Path("id") Long categoryId);
}

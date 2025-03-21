package com.example.retrofit;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.adapter.CategoryAdapter;
import com.example.retrofit.model.Category;
import com.example.retrofit.network.RetrofitClient;
import com.example.retrofit.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    RecyclerView rcCate;
    //Khai báo Adapter
    CategoryAdapter categoryAdapter;
    APIService apiService;
    List<Category> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        AnhXa();
        GetCategory();//load dữ liệu cho category
    }
    private void AnhXa() {
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
    }
    private void GetCategory() {
//Goi Interface trong APIService
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse (Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body(); //nhận măng
//khởi tạo Adapter
                    categoryAdapter = new CategoryAdapter (Home.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcCate.setLayoutManager (layoutManager);
                    rcCate.setAdapter (categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    int statusCode = response.code();
// handle request errors depending on status code
                }
            }
            @Override
            public void onFailure (Call<List<Category>> call, Throwable t) {
                Log.d("Logg", t.getMessage());
            }
        });
    }
}
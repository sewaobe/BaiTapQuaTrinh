package com.example.retrofit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofit.Home;
import com.example.retrofit.MainActivity;
import com.example.retrofit.R;
import com.example.retrofit.model.Category;
import com.example.retrofit.model.Product;
import com.example.retrofit.network.RetrofitClient;
import com.example.retrofit.service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> array;
    APIService apiService;

    List<Product> productList;


    ProductAdapter adapter;
    GridView gridView;

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public CategoryAdapter (Context context, List<Category> array) {
        this.array = array;
        this.context = context;
        this.productList = new ArrayList<>();

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, null);
        MyViewHolder myViewHolder = new MyViewHolder (view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.tenSp.setText(category.getName());
        Glide.with(context).load(category.getImage()).into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tenSp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_cate);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // Lấy vị trí của item trong danh sách
                    if (position != RecyclerView.NO_POSITION) { // Kiểm tra xem vị trí hợp lệ
                        Toast.makeText(context, "Bạn đã chọn category: " + position, Toast.LENGTH_SHORT).show();

                        // Nếu `tenSp.getId()` không phải ID của category, bạn có thể lấy ID từ danh sách
                        int categoryId = array.get(position).getId();
                        fetchAllProducts(categoryId);
                    }
                }
            });
        }
    }
    private void fetchAllProducts(int categoryId) {
        Long id = (long) categoryId;
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        if (productList == null) {
            productList = new ArrayList<>();
        }

        Call<List<Product>> call = apiService.getProducts(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = new ArrayList<>(response.body()); // Tạo danh sách mới
                    adapter = new ProductAdapter(context, productList);
                    gridView.setAdapter(adapter);


                    if (adapter == null) {
                        adapter = new ProductAdapter(context, productList);
                        gridView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("API_ERROR", "Failed to load products", t);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 2)) {
                    isLoading = true;
                    gridView.post(() -> {
                        if (adapter != null) {
                            adapter.loadMoreItems();
                        }
                        isLoading = false;
                    });
                }
            }
        });



    }

}



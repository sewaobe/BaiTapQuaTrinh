package com.example.retrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofit.R;
import com.example.retrofit.model.Product;

import java.util.ArrayList;
import java.util.List;
public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> fullProductList; // Danh sách đầy đủ
    private List<Product> displayedProducts; // Danh sách hiển thị dần dần
    private int itemCount = 4; // Số item hiển thị ban đầu

    public ProductAdapter(Context context, List<Product> fullProductList) {
        this.context = context;
        this.fullProductList = fullProductList;
        this.displayedProducts = new ArrayList<>(fullProductList.subList(0, Math.min(itemCount, fullProductList.size())));
    }

    @Override
    public int getCount() {
        return displayedProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return displayedProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = displayedProducts.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("$" + product.getPrice());
        Glide.with(context).load(product.getImage()).into(holder.ivProductImage);

        return convertView;
    }

    public void loadMoreItems() {
        int currentSize = displayedProducts.size();
        int nextItemCount = currentSize + 4; // Tăng thêm 4 sản phẩm

        if (currentSize >= fullProductList.size()) {
            return; // Không còn sản phẩm để tải thêm
        }

        nextItemCount = Math.min(nextItemCount, fullProductList.size()); // Giới hạn số lượng tối đa

        List<Product> newItems = fullProductList.subList(currentSize, nextItemCount); // Chỉ lấy sản phẩm mới
        displayedProducts.addAll(newItems); // Thêm sản phẩm mới vào danh sách hiện tại

        notifyDataSetChanged();
    }



    private static class ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivProductImage;

        ViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_product_name);
            tvPrice = view.findViewById(R.id.tv_product_price);
            ivProductImage = view.findViewById(R.id.iv_product_image);
        }
    }
}

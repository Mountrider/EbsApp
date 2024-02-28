package com.example.ebsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsapp.Product;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ProductAdapter(productList, product -> openProductDetails(product.getId()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Инициировать загрузку данных при запуске приложения
        loadProducts(1, 10);
    }

    private void loadProducts(int page, int pageSize) {
        ProductService productService = ApiClient.getProductService();
        Call<List<Product>> call = productService.getProducts(page, pageSize);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> newProducts = response.body();
                    productList.addAll(newProducts);
                    adapter.notifyDataSetChanged();
                } else {
                    // Обработка ошибки
                    Toast.makeText(MainActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Обработка ошибки
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openProductDetails(String productId) {
        // Здесь можно перейти к экрану с деталями продукта, используя productId
        // Создайте новую активити или фрагмент для отображения деталей продукта
        // и передайте ей productId
    }
}


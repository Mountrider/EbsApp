package com.example.ebsapp;

// ProductService.java
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("products")
    Call<List<Product>> getProducts(@Query("page") int page, @Query("page_size") int pageSize);

    @GET("products/{id}")
    Call<Product> getProductDetails(@Path("id") String productId);
}


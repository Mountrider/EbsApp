package com.example.ebsapp;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductViewModel extends ViewModel {

    private ProductRepository productRepository;
    private MutableLiveData<ArrayList<Product>> productsLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public void init() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
            productsLiveData = productRepository.getProductsLiveData();
            productRepository.loadProducts();

            // Наблюдение за изменениями в LiveData с продуктами
            productsLiveData.observeForever(products -> {
                if (products != null) {
                    Log.d("ProductViewModel", "Products loaded: " + products.size());
                } else {
                    Log.d("ProductViewModel", "Products are null");
                }
            });
        }
    }


    public void loadProducts() {
        new LoadProductsTask().execute();
    }

    private class LoadProductsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            // Выполняем HTTP-запрос для получения данных о продуктах
            try {
                URL url = new URL("http://mobile-shop-api.hiring.devebs.net/products");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try (InputStream in = urlConnection.getInputStream()) {
                    Scanner scanner = new Scanner(in).useDelimiter("\\A");
                    return scanner.hasNext() ? scanner.next() : "";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Обработка полученных данных и уведомление LiveData
            if (result != null) {
                try {
                    JSONArray productsArray = new JSONArray(result);
                    ArrayList<Product> products = new ArrayList<>();

                    for (int i = 0; i < productsArray.length(); i++) {
                        JSONObject productObject = productsArray.getJSONObject(i);
                        Product product = new Product(
                                productObject.getString("name"),
                                productObject.getString("description"),
                                productObject.getString("image_url")
                        );
                        products.add(product);
                    }

                    productsLiveData.setValue(products);


                    Log.d("ProductRepository", "Products loaded: " + products.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


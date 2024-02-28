package com.example.ebsapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductRepository {

    private final MutableLiveData<ArrayList<Product>> productsLiveData;

    public ProductRepository() {
        productsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public void loadProducts() {
        new LoadProductsTask().execute();
    }

    private class LoadProductsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mobile-shop-api.hiring.devebs.net/products?page=1&page_size=10");
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



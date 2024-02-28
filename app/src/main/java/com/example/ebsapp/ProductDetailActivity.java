package com.example.ebsapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Получение данных о продукте из Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            // Извлечение объекта Product из Intent
            Product product = intent.getParcelableExtra("product");
            if (product != null) {
                // Отображение данных о продукте в макете
                ImageView imageView = findViewById(R.id.imageView);
                TextView nameTextView = findViewById(R.id.nameProduct);
                TextView descriptionTextView = findViewById(R.id.descriptionProduct);

                // Заполнение макета данными о продукте
                nameTextView.setText(product.getName());
                descriptionTextView.setText(product.getDescription());
                Picasso.get().load(product.getImageUrl()).into(imageView);
            }
        }
    }
}



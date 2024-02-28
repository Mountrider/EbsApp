package com.example.ebsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Добавьте код для ожидания некоторого времени или выполнения других задач, если необходимо

        // Переход к MainActivity после заданного времени (например, 2 секунды)
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Закрывает экран загрузки, чтобы он не появлялся при нажатии "назад"
        }, 3000);  // Задержка в миллисекундах (в данном случае, 3 секунды)
    }
}




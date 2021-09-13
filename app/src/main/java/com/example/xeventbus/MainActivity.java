package com.example.xeventbus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xeventbus.pages.PageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XEventBus.bind(this);
        findViewById(R.id.btnGo).setOnClickListener(v -> {
            startActivity(new Intent(this, PageActivity.class));
        });
    }

}
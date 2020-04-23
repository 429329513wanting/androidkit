package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alihamuh.fastTableLayout.FastLayout;

public class FastActivity extends AppCompatActivity {

    private FastLayout fastLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast);
        fastLayout = findViewById(R.id.fast_view);


    }
}

package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(e -> {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
        });
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(e -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(e -> {
            Intent intent = new Intent(this, FourthActivity.class);
            startActivity(intent);
        });
    }
}

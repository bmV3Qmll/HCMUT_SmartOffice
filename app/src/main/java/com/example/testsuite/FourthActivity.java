package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.StringReader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class FourthActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    TextView txtMysql;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
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
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(e -> {
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        });
        txtMysql = (TextView) findViewById(R.id.txt_mysql);
        String url = "http://10.0.2.2/CO3107/index.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> txtMysql.setText(response),
                error -> txtMysql.setText(error.toString())
        );
        requestQueue = Volley.newRequestQueue(FourthActivity.this);
        requestQueue.add(stringRequest);
    }
}

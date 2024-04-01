package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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

        String url = "http://smartoffice.wuaze.com/";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        String result = "";
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
                        List<Map<String, String>> jsonArray = gson.fromJson(response, listType);
                        for (Map<String, String> item : jsonArray) {
                            result += item.get("name") + ", " + item.get("time") + ", " + item.get("stat") + "\n";
                        }
                        txtMysql.setText(result);
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        txtMysql.setText(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", "__test=dbc99bf8163359a3f3053fdb9ae0a690");
                headers.put("Upgrade-Insecure-Requests", "1");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0");
                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(FourthActivity.this);
        requestQueue.add(request);
    }
}

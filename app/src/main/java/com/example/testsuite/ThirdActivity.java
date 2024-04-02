package com.example.testsuite;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.util.JsonReader;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
public class ThirdActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    // for the gant chart
    private class CustomDataEntry extends DataEntry {
        public CustomDataEntry(String x, Number low, Number high) {
            setValue("x", x);
            setValue("low", low);
            setValue("high", high);
        }
    }
    private Number convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("WebView", "Page loading started: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("WebView", "Page finished loading: " + url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("WebView", "Error loading page: " + error.toString());
            }
        });

        webView.loadUrl("file:///android_asset/gantt_chart.html");


        // Button and click listeners
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

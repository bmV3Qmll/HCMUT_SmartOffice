package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.widget.TextView;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.lang.reflect.Type;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/*
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
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
*/
public class ThirdActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    TextView txtMysql;
    ScatterChart chart;
    RequestQueue requestQueue;
    ArrayList<Entry> inEntries = new ArrayList<>();
    ArrayList<Entry> outEntries = new ArrayList<>();
    String[] labels = {"Hieu", "Kiet", "Nam", "Thanh", "Tuan"};
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String url = "http://192.168.30.189/CO3107";
    /*
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
    */
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
        /*
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    InputStream inputStream = getAssets().open("schedules.json");
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    inputStream.close();
                    String json = new String(buffer, "UTF-8");
                    json = json.replace("\n", "").replace("\r", "");
                    webView.evaluateJavascript("javascript: " +
                            "window.jsonData = " + json + "; " +
                            "drawChart();", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        webView.loadUrl("file:///android_asset/gantt_chart.html");
        */
        chart = (ScatterChart) findViewById(R.id.chart);
        txtMysql = (TextView) findViewById(R.id.txt_mysql);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
                        List<Map<String, String>> jsonArray = gson.fromJson(response, listType);
                        for (Map<String, String> item : jsonArray) {
                            int time = convertTimeToMinutes(item.get("time"));
                            int index = getIndex(labels, item.get("name"));
                            if (index == -1) {
                                txtMysql.setText("?user");
                                break;
                            }
                            if (Objects.equals(item.get("stat"), "1")) {
                                inEntries.add(new Entry(time, index));
                            } else {
                                outEntries.add(new Entry(time, index));
                            }
                        }
                        updateChart();
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                txtMysql.setText(error.toString());
            }
        }
        );
        requestQueue = Volley.newRequestQueue(ThirdActivity.this);
        requestQueue.add(request);
    }
    private void updateChart() {
        ScatterDataSet inData = new ScatterDataSet(inEntries, "In");
        inData.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        inData.setColor(Color.GREEN);
        inData.setDrawValues(false);

        ScatterDataSet outData = new ScatterDataSet(outEntries, "Out");
        outData.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        outData.setColor(Color.RED);
        outData.setDrawValues(false);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(inData);
        dataSets.add(outData);

        ScatterData scatterData = new ScatterData(dataSets);
        chart.setData(scatterData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(10);
        xAxis.setLabelCount(8);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(1439);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int hours = (int) value / 60;
                int minutes = (int) value % 60;
                return String.format("%02d:%02d", hours, minutes);
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setGranularity(1); // One row for each label
        yAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        chart.getAxisRight().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setVisibleXRangeMaximum(4*60);
        chart.moveViewToX(360);
        chart.invalidate();
    }

    private int convertTimeToMinutes(String time) {
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        return hour * 60 + minute;
    }
    public int getIndex(String[] labels, String input) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].equals(input)) {
                return i;
            }
        }
        return -1;
    }
}

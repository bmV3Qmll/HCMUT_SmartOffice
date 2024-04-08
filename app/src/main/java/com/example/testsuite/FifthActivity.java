package com.example.testsuite;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class FifthActivity extends AppCompatActivity {
    ScatterChart chart;
    TextView txtMysql;
    RequestQueue requestQueue;
    ArrayList<Entry> inEntries = new ArrayList<>();
    ArrayList<Entry> outEntries = new ArrayList<>();
    String[] labels = {"A", "B", "C", "D"};
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String url = "http://172.20.10.3/CO3107";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        chart = (ScatterChart) findViewById(R.id.chart);
        txtMysql = (TextView) findViewById(R.id.txt_mysql);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
                        List<Map<String, String>> jsonArray = gson.fromJson(response, listType);
                        for (Map<String, String> item : jsonArray) {
                            LocalDateTime dateTime = LocalDateTime.parse(item.get("time"), formatter);
                            int hour = dateTime.getHour();
                            int minute = dateTime.getMinute();
                            int index = getIndex(labels, item.get("name"));
                            if (index == -1) {
                                txtMysql.setText("?user");
                                break;
                            }
                            if (Objects.equals(item.get("stat"), "1")) {
                                inEntries.add(new Entry(getTimeInMinute(hour, minute), index));
                            } else {
                                outEntries.add(new Entry(getTimeInMinute(hour, minute), index));
                            }
                        }
                        updateChart();
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
                // headers.put("Cookie", "__test=dbc99bf8163359a3f3053fdb9ae0a690");
                //headers.put("Upgrade-Insecure-Requests", "1");
                //headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0");
                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(FifthActivity.this);
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

    private int getTimeInMinute(int hourOfDay, int minute) {
        return hourOfDay * 60 + minute;
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

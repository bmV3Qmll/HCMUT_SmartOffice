package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
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
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.RangeColumn;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
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

    private List<Schedule> readSchedulesFromJson() {
        List<Schedule> schedules = new ArrayList<>();

        try {
            InputStream is = getAssets().open("schedules.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();

                String name = "";
                String start = "";
                String end = "";

                while (reader.hasNext()) {
                    String key = reader.nextName();

                    if (key.equals("name")) {
                        name = reader.nextString();
                    } else if (key.equals("start")) {
                        start = reader.nextString();
                    } else if (key.equals("end")) {
                        end = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                }

                schedules.add(new Schedule(name, start, end));

                reader.endObject();
            }
            reader.endArray();

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schedules;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Cartesian cartesian = AnyChart.cartesian();

        List<DataEntry> data = new ArrayList<>();
        for (Schedule schedule : readSchedulesFromJson()) {
            String name = schedule.getName();
            Number start = convertTimeToMinutes(schedule.getStart());
            Number end = convertTimeToMinutes(schedule.getEnd());
            data.add(new CustomDataEntry(name, start, end));
        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping seriesData = set.mapAs("{ x: 'x', high: 'high', low: 'low' }");

        RangeColumn column = cartesian.rangeColumn(seriesData);

        anyChartView.setChart(cartesian);

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

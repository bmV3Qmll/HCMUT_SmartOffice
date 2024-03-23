package com.example.testsuite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class ThirdActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    XYPlot plot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        plot = findViewById(R.id.plot);

        //Create a arrays of y-value to plot:
        final Number[] domainLabels = {1,2,3,6,7,8,9,10,13,14};
        Number[] series1Numbers = {1,4,2,8,88,16,8,32,16,64};
        Number[] series2Numbers = {2,6,3,12,8,0,17,64,20,40};

        // Turn the above arrays into XYSeries
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series 1");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series 2");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED,Color.GREEN,null,null);
        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.YELLOW,Color.BLUE,null,null);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10,
                CatmullRomInterpolator.Type.Centripetal));
        series2Format.setInterpolationParams(new CatmullRomInterpolator.Params(10,
                CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1,series1Format);
        plot.addSeries(series2,series2Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round( ((Number)obj).floatValue() );
                return toAppendTo.append(domainLabels[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        PanZoom.attach(plot);

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

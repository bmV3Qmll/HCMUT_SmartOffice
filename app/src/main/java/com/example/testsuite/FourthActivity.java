package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.StringReader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.widget.ImageView;
import java.util.Calendar;
import android.view.Gravity;

public class FourthActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    TextView txtMysql;
    RequestQueue requestQueue;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String url = "http://192.168.30.189/CO3107";
    TableLayout tableLayout;
    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            1.0f
    );
    // Set margins to create space between columns
     // Adjust as needed
    int N = 5;
    String[] Name_List = {"Hieu", "Kiet", "Nam", "Thanh", "Tuan"};
    int[] already_check = {0, 0, 0, 0, 0};
    float[] Active = {0, 0, 0, 0, 0};
    int[] Absent_check = {1, 1, 1, 1, 1};
    int[] OnTime = {0, 0, 0, 0, 0};
    int[] prevTime = {-1, -1, -1, -1, -1};

    private int convertTimeToMinutes(String time) {
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        return hour * 60 + minute;
    }

    public static boolean isOnTime(int start){
        return start <= 7 * 60;
    }

    public void Create_Row(){
        for (int i = 0; i < N; i++) {
            TableRow row = new TableRow(this);

            TextView textName = new TextView(this);
            textName.setText(Name_List[i]);

            TextView active = new TextView(this);
            DecimalFormat df = new DecimalFormat("#.#");
            active.setText(String.valueOf(df.format(Active[i]/60)));

            ImageView icon = new ImageView(this);
            ImageView icon2 = new ImageView(this);

            if(OnTime[i] == 1){
                icon.setImageResource(R.drawable.ic_checkmark);
            }
            else{
                icon.setImageResource(R.drawable.ic_cross);
            }

            if(Absent_check[i] == 1){
                icon2.setImageResource(R.drawable.ic_checkmark);
            }
            else{
                icon2.setImageResource(R.drawable.ic_cross);
            }


            //layoutParams.setMargins(50, 50, 50, 50);
            textName.setLayoutParams(layoutParams);
            active.setLayoutParams(layoutParams);
            icon.setLayoutParams(layoutParams);
            icon2.setLayoutParams(layoutParams);

            row.addView(textName);
            textName.setGravity(Gravity.CENTER);
            row.addView(active);
            active.setGravity(Gravity.CENTER);
            row.addView(icon);
            row.addView(icon2);

            tableLayout.addView(row);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        tableLayout = findViewById(R.id.tableLayout);
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

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
                        List<Map<String, String>> jsonArray = gson.fromJson(response, listType);
                        Calendar calendar = Calendar.getInstance();
                        int currentTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
                        for (Map<String, String> item : jsonArray) {
                            String name = item.get("name");
                            int index = getIndex(Name_List, item.get("name"));
                            int time = convertTimeToMinutes(item.get("time"));
                            String stat = item.get("stat");

                            if (index == -1) {
                                txtMysql.setText("?user");
                                break;
                            }

                            Absent_check[index] = 0;
                            if (isOnTime(time) && already_check[index] == 0){
                                OnTime[index] = 1;
                                already_check[index] = 1;
                            }

                            if (Objects.equals(stat, "0")) {
                                Active[index] += (time - prevTime[index]);
                                prevTime[index] = -1;
                            } else {
                                prevTime[index] = time;
                            }
                        }
                        for (int i = 0; i < N; i++) {
                            if (prevTime[i] == -1) continue;
                            Active[i] += currentTime - prevTime[i];
                            prevTime[i] = -1;
                        }
                        Create_Row();
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                txtMysql.setText(error.toString());
            }
        }
        );
        requestQueue = Volley.newRequestQueue(FourthActivity.this);
        requestQueue.add(request);
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

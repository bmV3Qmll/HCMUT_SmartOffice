package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class FirstActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    TextView txtTemp, txtHumi, txtIllu;
    MQTTHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(e -> {
            Intent intent = new Intent(this, FifthActivity.class);
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
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(e -> {
            Intent intent = new Intent(this, FourthActivity.class);
            startActivity(intent);
        });

        txtTemp = (TextView) findViewById(R.id.txt_temp);
        txtHumi = (TextView) findViewById(R.id.txt_humi);
        txtIllu = (TextView) findViewById(R.id.txt_illu);

        startMQTT();
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("Test",topic+ "***"+ message.toString());
                if (topic.endsWith("V1")) {
                    txtTemp.setText("Temperature: " + message.toString() + "°C");
                }
                else if (topic.endsWith("V2")) {
                    txtHumi.setText("Humidity: " + message.toString() + "%");
                }
                else if (topic.endsWith("V3")) {
                    txtIllu.setText("Illumination: " + message.toString() + "lx");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}

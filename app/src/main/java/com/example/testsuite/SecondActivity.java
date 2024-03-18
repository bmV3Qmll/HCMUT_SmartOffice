package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import org.eclipse.paho.client.mqttv3.MqttException;

public class SecondActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3;
    Switch btnLight;
    ImageView imgLight;
    TextView txtSpeed;
    Slider sliderFan;
    MQTTHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(e -> {
            Intent intent = new Intent(this, FirstActivity.class);
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

        btnLight = (Switch) findViewById(R.id.btn_light);
        imgLight = (ImageView) findViewById(R.id.img_light);
        btnLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    imgLight.setImageResource(R.mipmap.bulb_on);
                    sendDataMQTT("csIs2G00d/feeds/V10", "1");
                } else {
                    imgLight.setImageResource(R.mipmap.bulb_off);
                    sendDataMQTT("csIs2G00d/feeds/V10", "0");
                }
            }
        });

        txtSpeed = (TextView) findViewById(R.id.txt_speed);
        sliderFan = (Slider) findViewById(R.id.slider_fan);
        sliderFan.setLabelBehavior(LabelFormatter.LABEL_GONE);
        sliderFan.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                txtSpeed.setText(Float.toString(v));
            }
        });
        sliderFan.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                sendDataMQTT("csIs2G00d/feeds/V12", Float.toString(slider.getValue()));
            }
        });
        startMQTT();
    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);
        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic,msg);
        }catch (MqttException e){
            e.printStackTrace();
        }
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
                if (topic.endsWith("V10")) {
                    btnLight.setChecked(message.toString().equals("1"));
                }
                else if (topic.endsWith("V12")) {
                    sliderFan.setValue(Float.parseFloat(message.toString()));
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
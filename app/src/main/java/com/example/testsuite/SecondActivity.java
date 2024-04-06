package com.example.testsuite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    ImageView mainImgColor;
    ImageView redImgColor;
    ImageView orangeImgColor;
    ImageView yellowImgColor;
    ImageView greenImgColor;
    ImageView blueImgColor;
    ImageView dark_blueImgColor;
    ImageView purpleImgColor;
    ImageView pinkImgColor;
    ImageView brownImgColor;
    ImageView bluepinkImgColor;
    ImageView greyImgColor;
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
                    sendDataMQTT("YoloHome2907/feeds/V10", "1");
                } else {
                    imgLight.setImageResource(R.mipmap.bulb_off);
                    sendDataMQTT("YoloHome2907/feeds/V10", "0");
                }
            }
        });

    // This section deal with color change

        mainImgColor = (ImageView) findViewById(R.id.main_color);
        redImgColor = (ImageView) findViewById(R.id.img_color0);
        orangeImgColor = (ImageView) findViewById(R.id.img_color1);
        yellowImgColor = (ImageView) findViewById(R.id.img_color2);
        greenImgColor = (ImageView) findViewById(R.id.img_color3);
        blueImgColor = (ImageView) findViewById(R.id.img_color4);
        dark_blueImgColor = (ImageView) findViewById(R.id.img_color5);
        purpleImgColor = (ImageView) findViewById(R.id.img_color6);
        pinkImgColor = (ImageView) findViewById(R.id.img_color7);
        brownImgColor = (ImageView) findViewById(R.id.img_color8);
        bluepinkImgColor = (ImageView) findViewById(R.id.img_color9);
        greyImgColor = (ImageView) findViewById(R.id.img_color10);

        redImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.red);
                sendDataMQTT("YoloHome2907/feeds/V11", "#ff0000");
            }
        });

        orangeImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.orange);
                sendDataMQTT("YoloHome2907/feeds/V11", "#ffa500");
            }
        });

        yellowImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.yellow);
                sendDataMQTT("YoloHome2907/feeds/V11", "#ffff00");
            }
        });

        greenImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.green);
                sendDataMQTT("YoloHome2907/feeds/V11", "#00ff00");
            }
        });

        blueImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.blue);
                sendDataMQTT("YoloHome2907/feeds/V11", "#0000ff");
            }
        });

        dark_blueImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.dark_blue);
                sendDataMQTT("YoloHome2907/feeds/V11", "#4b0082");
            }
        });

        purpleImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.purple);
                sendDataMQTT("YoloHome2907/feeds/V11", "#800080");
            }
        });

        pinkImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.pink);
                sendDataMQTT("YoloHome2907/feeds/V11", "#ff00ff");
            }
        });

        brownImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.brown);
                sendDataMQTT("YoloHome2907/feeds/V11", "#c66300");
            }
        });

        bluepinkImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.bluepink);
                sendDataMQTT("YoloHome2907/feeds/V11", "#0080ff");
            }
        });

        greyImgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainImgColor.setImageResource(R.mipmap.grey);
                sendDataMQTT("YoloHome2907/feeds/V11", "#8080c0");
            }
        });
    // end

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
                sendDataMQTT("YoloHome2907/feeds/V12", Integer.toString(Math.round(slider.getValue())));
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
                else if (topic.endsWith("V11")) {
                    if (message.toString().equals("#ff0000")) {
                        mainImgColor.setImageResource(R.mipmap.red);
                    }
                    else if (message.toString().equals("#ffa500")) {
                        mainImgColor.setImageResource(R.mipmap.orange);
                    }
                    else if (message.toString().equals("#ffff00")) {
                        mainImgColor.setImageResource(R.mipmap.yellow);
                    }
                    else if (message.toString().equals("#00ff00")) {
                        mainImgColor.setImageResource(R.mipmap.green);
                    }
                    else if (message.toString().equals("#0000ff")) {
                        mainImgColor.setImageResource(R.mipmap.blue);
                    }
                    else if (message.toString().equals("#4b0082")) {
                        mainImgColor.setImageResource(R.mipmap.dark_blue);
                    }
                    else if (message.toString().equals("#800080")) {
                        mainImgColor.setImageResource(R.mipmap.purple);
                    }
                    else if (message.toString().equals("#ff00ff")) {
                        mainImgColor.setImageResource(R.mipmap.pink);
                    }
                    else if (message.toString().equals("#c66300")) {
                        mainImgColor.setImageResource(R.mipmap.brown);
                    }
                    else if (message.toString().equals("#0080ff")) {
                        mainImgColor.setImageResource(R.mipmap.bluepink);
                    }
                    else if (message.toString().equals("#8080c0")) {
                        mainImgColor.setImageResource(R.mipmap.grey);
                    }
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
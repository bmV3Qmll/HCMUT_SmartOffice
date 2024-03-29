#pip install paho-mqtt==1.6.1
import paho.mqtt.client as mqtt
import time
from ai import *
from sql_insert import *

MQTT_SERVER = "mqtt.ohstem.vn"
MQTT_PORT = 1883
MQTT_USERNAME = "csIs2G00d"
MQTT_PASSWORD = ""
MQTT_TOPIC_PUB = MQTT_USERNAME + "/feeds/V5"
MQTT_TOPIC_SUB = MQTT_USERNAME + "/feeds/V4"


def mqtt_connected(client, userdata, flags, rc):
    print("Connected succesfully!!")
    client.subscribe(MQTT_TOPIC_SUB)

def mqtt_subscribed(client, userdata, mid, granted_qos):
    print("Subscribed to Topic!!!")

def mqtt_recv_message(client, userdata, message):
    #print("Received: ", message.payload.decode("utf-8"))
    print(" Received message " + message.payload.decode("utf-8")
          + " on topic '" + message.topic
          + "' with QoS " + str(message.qos))
    label, score = capture_img()
    result = label + " " + str(score)[:-2] + "%"
    print(result)
    mqttClient.publish(MQTT_TOPIC_PUB, result)
    if label != "None" and score >= 70:
        query_sql(label)


mqttClient = mqtt.Client()
mqttClient.username_pw_set(MQTT_USERNAME, MQTT_PASSWORD)
mqttClient.connect(MQTT_SERVER, int(MQTT_PORT), 60)

#Register mqtt events
mqttClient.on_connect = mqtt_connected
mqttClient.on_subscribe = mqtt_subscribed
mqttClient.on_message = mqtt_recv_message

mqttClient.loop_start()
print("alo")

counter = 0
while True:
    pass
    '''
    time.sleep(5)
    counter += 1
    mqttClient.publish(MQTT_TOPIC_PUB, counter)
    '''
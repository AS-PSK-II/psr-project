#include <iostream>
#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <Adafruit_BME280.h>
#include <WiFiUdp.h>
#include <Ticker.h>
#include <NTPClient.h>
#include <ArduinoJson.h>

#define NTP_OFFSET   0
#define NTP_INTERVAL 60000
#define NTP_ADDRESS  "europe.pool.ntp.org"

#define SEALEVELPRESSURE_HPA (1013.25)
#define LED_PIN 16

const int SERVER_PORT = 12346;
const std::string DEVICE_UUID = "ec94c61f-82a7-48ba-8503-57a0f0a4ef8a";

Adafruit_BME280 bme;
WiFiClient net;
WiFiUDP udpClient;
WiFiServer server(SERVER_PORT);
Ticker temperatureTicker, humidityTicker, pressureTicker, altitudeTicker, ledTicker, healthCheckTicker;
NTPClient timeClient(udpClient, NTP_ADDRESS, NTP_OFFSET, NTP_INTERVAL);

int temperatureDelay = 1000;
int humidityDelay = 1000;
int pressureDelay = 1000;
int altitudeDelay = 1000;
int ledDelay = 1000;

void sendData(const char* message);
void readConfiguration();
void adjustConfiguration(String property, int value);

void readAndSendTemperature() {
    float temperature = bme.readTemperature();

    unsigned long timestamp = timeClient.getEpochTime();

    const std::string message = DEVICE_UUID + ";temperature;" + std::to_string(temperature) + ";" + std::to_string(timestamp) + "000";
    sendData(message.c_str());
    temperatureTicker.once_ms(temperatureDelay, readAndSendTemperature);
}

void readAndSendHumidity() {
    float humidity = bme.readHumidity();

    unsigned long timestamp = timeClient.getEpochTime();

    const std::string message = DEVICE_UUID + ";humidity;" + std::to_string(humidity) + ";" + std::to_string(timestamp) + "000";
    sendData(message.c_str());
    humidityTicker.once_ms(humidityDelay, readAndSendHumidity);
}

void readAndSendPressure() {
    float pressure = bme.readPressure() / 100.0F;

    unsigned long timestamp = timeClient.getEpochTime();

    const std::string message = DEVICE_UUID + ";pressure;" + std::to_string(pressure) + ";" + std::to_string(timestamp) + "000";
    sendData(message.c_str());
    pressureTicker.once_ms(pressureDelay, readAndSendPressure);
}

void readAndSendAltitude() {
    float altitude = bme.readAltitude(SEALEVELPRESSURE_HPA);

    unsigned long timestamp = timeClient.getEpochTime();

    const std::string message = DEVICE_UUID + ";altitude;" + std::to_string(altitude) + ";" + std::to_string(timestamp) + "000";
    sendData(message.c_str());
    altitudeTicker.once_ms(altitudeDelay, readAndSendAltitude);
}

void blinkLed() {
    pinMode(LED_PIN, OUTPUT);
    delay(ledDelay);
    pinMode(LED_PIN, INPUT);
    ledTicker.once_ms(ledDelay, blinkLed);
}

void sendDeviceHealthcheck() {
    unsigned long timestamp = timeClient.getEpochTime();

    JsonDocument object;
    object["id"] = DEVICE_UUID;
    object["name"] = "NodeMCU";
    object["isActive"] = true;
    object["isConnected"] = true;
    object["timestamp"] = std::to_string(timestamp) + "000";
    JsonArray tcpServer = object["tcpServer"].to<JsonArray>();
    JsonObject tcpServerProps = tcpServer.add<JsonObject>();
    tcpServerProps["port"] = SERVER_PORT;
    tcpServerProps["address"] = WiFi.localIP().toString();

    String json = "";
    serializeJson(object, json);
    sendData(json.c_str());
}

void sendData(const char* message) {
    udpClient.beginPacket("192.168.50.110", 12345);
    udpClient.write(message);
    udpClient.endPacket();
}

void connectWithWiFi() {
    WiFi.mode(WIFI_STA);
    WiFi.hostname("ESP8266");
    WiFi.begin("ssid", "password");

    Serial.print("Connecting with wifi");
    while(WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("Connected!");
    Serial.println("'s IP Address = " + WiFi.localIP().toString());
}

void setup() {
    Serial.begin(115200);

    timeClient.begin();

    bme.begin(0x76);
    connectWithWiFi();
    server.begin();
    Serial.println("Hello");
  
    temperatureTicker.once_ms(temperatureDelay, readAndSendTemperature);
    humidityTicker.once_ms(humidityDelay, readAndSendHumidity);
    pressureTicker.once_ms(pressureDelay, readAndSendPressure);
    altitudeTicker.once_ms(altitudeDelay, readAndSendAltitude);
    ledTicker.once_ms(ledDelay, blinkLed);

    sendDeviceHealthcheck();
    healthCheckTicker.attach_ms(60000, sendDeviceHealthcheck);
}

void loop() {
    timeClient.update();
    readConfiguration();
}

void readConfiguration() {
    if (WiFiClient client = server.accept()) {
        if(client.connected())
        {
            Serial.println("Client Connected");

            String json;

            while(client.connected()){
                while(client.available()>0){
                    json = client.readString();
                }
            }
            client.stop();

            JsonDocument data;
            deserializeJson(data, json);
            String property = data["property"].as<String>();
            int value = data["value"].as<int>();

            adjustConfiguration(property, value);

            Serial.println(json);
            Serial.println("Client disconnected");
        }
    }
}

void adjustConfiguration(String property, int value) {
    if (property == "temperature") {
        temperatureDelay = value;
    } else if (property == "humidity") {
        humidityDelay = value;
    } else if (property == "pressure") {
        pressureDelay = value;
    } else if (property == "altitude") {
        altitudeDelay = value;
    } else if (property == "led") {
        ledDelay = value;
    } else {
        Serial.println("Invalid property");
    }
}

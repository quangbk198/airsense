package com.example.doan1.Model;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.doan1.Object.AirQualityInfo;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataMqtt extends Service {
    ArrayList<String> deviceList = new ArrayList<>();        //arraylist chứa tên của các loại máy airsense. ví dụ ESP_00979359
    public static ArrayList<AirQualityInfo> airInfoList = new ArrayList<>();        //chứa danh sách thông tin các chỉ số các địa điểm đo
    AirQualityInfo info;
    MqttAndroidClient client;
    MqttConnectOptions option;
    final String topic1 = "/airsense/+/";

    //Khai báo biến takeData là một biến AtomicBoolean, tức là nó thay đổi giá trị true, false một cách thường xuyên
    //Chúng ta dùng nó để kiểm tra xem ứng dụng đang được chạy hay đg tạm dừng.
    //Nếu ứng dụng chạy thì set biến takeData = true, ngược lại set bằng false.

    public static AtomicBoolean takeData = new AtomicBoolean(true);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Thiết lập kết nối với mqtt
        option = new MqttConnectOptions();
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://hanoiair.de:1883", clientId);
        connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //  Toast.makeText(TakeDataActivity.this, "disconnect ", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Toast.makeText(TakeDataActivity.this, "un_disconnect ", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setSub() {
        try {
            client.subscribe(topic1, 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            client.setCallback(new MqttCallback() {     //Description copied from interface
                @Override
                public void connectionLost(Throwable cause) {   //Phương thức này được gọi khi mất kết nối với máy chủ

                }

                @Override   //Một message mới đã đến server và sẵn sàng được xử lý. Topic ở đây là chủ đề mà broker tạo ra để device gửi dữ liệu vào
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String nameDevice = topic.substring(10,22); //Lấy ra tên thiết bị từ topic. Ví dụ topic có dạng: /airsense/ESP_00979359/
                    //Đưa tên device vào trong arraylist
                    if(deviceList.size() == 0) deviceList.add(nameDevice);
                    else {
                        Boolean check = true;
                        for(int i = 0; i < deviceList.size(); i++) {
                            if(nameDevice.equals(deviceList.get(i))) {
                                check = false;
                                break;
                            }
                        }
                        if(check == true) deviceList.add(nameDevice);
                    }

                    String data = message.toString(); //đưa chuỗi message json nhận được từ mqtt về dạng string
                    JSONObject object = new JSONObject(data);

                    String _data = object.getString("data");

                    JSONObject m_obj = new JSONObject(_data);
                    String tem = m_obj.getString("tem");
                    String humi = m_obj.getString("humi");
                    String pm1 = m_obj.getString("pm1");
                    String pm2p5 = m_obj.getString("pm2p5");
                    String pm10 = m_obj.getString("pm10");
                    String time = m_obj.getString("time");

                    info = new AirQualityInfo(nameDevice, Integer.parseInt(pm1), Integer.parseInt(pm10), Integer.parseInt(pm2p5),
                            (int) AQI_US.AQI_PM2_5(Integer.parseInt(pm2p5)), Integer.parseInt(time), Integer.parseInt(humi), Integer.parseInt(tem));

                    if (airInfoList.size() == 0) {
                        airInfoList.add(info);
                    }
                    else {
                        Boolean check1 = true;
                        for(int i = 0; i < airInfoList.size(); i++) {
                            if((info.getID()).equals(airInfoList.get(i).getID())) {
                                airInfoList.set(i, info);       // cập nhật giá trị mới khi máy đo được giá trị mới
                                check1 = false;
                            }
                        }
                        if (check1 == true) airInfoList.add(info);
                    }
                    //Intent intent = new Intent(DataMqtt.this, MainActivity.class);
                    //Bundle bundle = new Bundle();
                    //bundle.putParcelableArrayList("Data", airInfoList);
//                    bundle.putInt("checkIntent", 1);
                    //intent.putExtras(bundle);
                    Log.d("hihi", "nhảy vào đây");
                    //if(takeData.get()) {    //Nếu takeData = true thì chuyển intent, và dữ liệu đc chuyển
                    //từ file này sang file mainactivity thông qua bundle. Vì cái này dùng handler chạy
                    //đa luồng nên qua trình startactivity đc chạy dưới background
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //chức năng của cờ này tương tự như singleTask... Lệnh này chỉ định MainActivity chạy singletask
                    //startActivity(intent);
                    //}

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }
    };

    public void connect() {
        try {
            IMqttToken token = client.connect(option);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            setSub();
                            handler.sendEmptyMessage(0);
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

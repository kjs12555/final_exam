package com.example.igx.problem1;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener, SensorEventListener/* implements Something1, Something2 */ {

    LocationManager lm;
    SensorManager sm;
    Sensor s;
    Sensor s1;
    Sensor s2;
    Sensor s3;
    String str1="\n";
    String str2="\n";
    String str3="\n";
    String str4="";
    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
        Button btn_getSensors = (Button) findViewById(R.id.btn_getSensors);
        Button btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        final TextView text_selectedData = (TextView) findViewById(R.id.text_selectedData);
        final TextView text_selectedType = (TextView) findViewById(R.id.text_selectedType);
        final EditText edit_phoneNumber = (EditText) findViewById(R.id.edit_phoneNumber);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        s = sm.getDefaultSensor(Sensor.TYPE_ALL);
        s1 = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        s2 = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        s3 = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);


        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType.setText("Location");
                sm.unregisterListener(MainActivity.this);
                try {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity.this);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MainActivity.this);
                    Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    str = "위치 정보 \n";
                    str = str.concat("위도 : "+location.getLatitude());
                    str = str.concat("경도 : "+location.getLongitude());
                    text_selectedData.setText(str);
                }catch (SecurityException e){}
                text_selectedData.setTextSize(18);
            }
        });

        btn_getSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lm.removeUpdates(MainActivity.this);
                }catch(SecurityException e){}
                text_selectedType.setText("Sensors");
                sm.registerListener(MainActivity.this, s, SensorManager.SENSOR_DELAY_GAME);
                sm.registerListener(MainActivity.this, s1, SensorManager.SENSOR_DELAY_GAME);
                sm.registerListener(MainActivity.this, s2, SensorManager.SENSOR_DELAY_GAME);
                sm.registerListener(MainActivity.this, s3, SensorManager.SENSOR_DELAY_GAME);
                text_selectedData.setTextSize(14);
            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = edit_phoneNumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+number));
                //이 곳에 SMS에 대한 정보를 넣는다.
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            str1 = event.values[0]+" " + event.values[1]+" " + event.values[2]+"\n";
        }else if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            str2 = event.values[0]+" " + event.values[1]+" " + event.values[2]+"\n";
        }else if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            str3 = event.values[0]+" " + event.values[1]+" " + event.values[2]+"\n";
        }else if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
            str4 = event.values[0]+" " + event.values[1]+" " + event.values[2];
        }
        str = str1+str2+str3+str4;
        TextView textView = (TextView) MainActivity.this.findViewById(R.id.text_selectedData);
        textView.setText(str);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onLocationChanged(Location location) {
        str = "위치 정보 \n";
        str = str.concat("위도 : "+location.getLatitude());
        str = str.concat("경도 : "+location.getLongitude());
        TextView textView = (TextView) MainActivity.this.findViewById(R.id.text_selectedData);
        textView.setText(str);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}

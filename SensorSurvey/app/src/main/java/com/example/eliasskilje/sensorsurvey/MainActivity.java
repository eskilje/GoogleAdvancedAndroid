package com.example.eliasskilje.sensorsurvey;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    // Individual light and proximity sensors.
    private Sensor sensorProximity;
    private Sensor sensorLight;

    // TextViews to display current sensor values
    private TextView textSensorLight;
    private TextView textSensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSensorLight = findViewById(R.id.label_light);
        textSensorProximity = findViewById(R.id.label_proximity);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorProximity = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if(sensorProximity == null) textSensorProximity.setText(sensor_error);
        if(sensorLight == null) textSensorLight.setText(sensor_error);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sensorProximity != null) {
            manager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorLight != null) {
            manager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                textSensorLight.setText(getResources().getString(
                        R.string.label_light, currentValue));
                if(currentValue > 1000)
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                else
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                break;
            case Sensor.TYPE_PROXIMITY:
                textSensorProximity.setText(getResources().getString(
                        R.string.label_proximity, currentValue));
                if(currentValue > 5)
                    textSensorProximity.setWidth(1000);
                else
                    textSensorProximity.setWidth(10);
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package com.skbuf.iodemo;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SensorService extends Service implements SensorEventListener {

    private final String TAG = "Demo1: SensorService";
    private IBinder binder = new MyBinder();
    private SensorManager sensorManager;
    float gravity[] = new float[3];

    public SensorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "in onCreate");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "in onDestroy");
        super.onDestroy();

        sensorManager.unregisterListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "in onBind");
        return binder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha = 0.5f;
        float linear_acceleration[] =  new float[3];

        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];


        Log.i(TAG, "linear acceleration: ("  +
                 Float.toString(linear_acceleration[0]) + "," +
                 Float.toString(linear_acceleration[1]) + "," +
                 Float.toString(linear_acceleration[2]) + ")");

        Log.i(TAG, "gravity: ("  +
                Float.toString(gravity[0]) + "," +
                Float.toString(gravity[1]) + "," +
                Float.toString(gravity[2]) + ")");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "accuracy changed to " + Integer.toString(accuracy));
    }

    public void setPrecision(int precision) {
        Log.i(TAG, "Should do smth with the precision");
    }

    public class MyBinder extends Binder {
        SensorService getService() {
            return SensorService.this;
        }
    }
}

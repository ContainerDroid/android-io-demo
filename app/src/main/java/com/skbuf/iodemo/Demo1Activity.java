package com.skbuf.iodemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class Demo1Activity extends AppCompatActivity {

    final static String TAG = "Demo1";

    private Switch activeSwitch;
    private SeekBar precisionSeekBar;

    private SensorService mSensorService;
    private boolean mServiceBound = false;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SensorService.MyBinder myBinder = (SensorService.MyBinder) iBinder;
            mSensorService = myBinder.getService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        activeSwitch = (Switch) findViewById(R.id.demo1_active);
        precisionSeekBar = (SeekBar) findViewById(R.id.demo1_precision);

        activeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.i(TAG, "active switch changed state to " + Boolean.toString(checked));
                if (checked) {
                    Log.d(TAG, "started SensorService");
                    Intent intent = new Intent(getApplicationContext(), SensorService.class);
                    startService(intent);
                    bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                    mServiceBound = true;
                } else {
                    Log.d(TAG, "stopped SensorService");
                    if (mServiceBound) {
                        unbindService(mServiceConnection);
                        mServiceBound = false;
                    }
                    Intent intent = new Intent(getApplicationContext(), SensorService.class);
                    stopService(intent);
                }
            }
        });

        precisionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int precision, boolean b) {
                Log.i(TAG, "precision changed to " + Integer.toString(precision));
                mSensorService.setPrecision(precision);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "started tracking touch of precision seekbar");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "stopped tracking touch of precision seekbar");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

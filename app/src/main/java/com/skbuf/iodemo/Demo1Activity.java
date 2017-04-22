package com.skbuf.iodemo;

import android.os.Bundle;
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
                    Log.d(TAG, "should do smth");
                } else {
                    Log.d(TAG, "should do smth");
                }
            }
        });
        
        precisionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i(TAG, "precision changed to " + Integer.toString(i));
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

}
